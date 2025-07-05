package nl.grapjeje.lock.vault;

import nl.grapjeje.lock.Account;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Vault {

    private static final String VAULT_PATH = "vault.enc";
    private static final int SALT_LENGTH = 16;
    private static final int IV_LENGTH = 12;

    private static byte[] generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    private static SecretKey deriveKey(String password, byte[] salt) throws Exception {
        int iterations = 65536;
        int keyLength = 128;

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();

        return new SecretKeySpec(keyBytes, "AES");
    }

    public static boolean exists() {
        return Files.exists(Path.of(VAULT_PATH));
    }

    public static void saveAccounts(List<Account> accounts, String masterPassword) throws Exception {
        String json = Account.listToJson(accounts);

        byte[] salt = generateRandomBytes(SALT_LENGTH);
        SecretKey key = deriveKey(masterPassword, salt);
        byte[] iv = generateRandomBytes(IV_LENGTH);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));
        byte[] encrypted = cipher.doFinal(json.getBytes(StandardCharsets.UTF_8));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(salt);
        output.write(iv);
        output.write(encrypted);

        Files.write(Path.of(VAULT_PATH), output.toByteArray());
    }

    public static List<Account> loadAccounts(String masterPassword) throws Exception {
        if (!Files.exists(Path.of(VAULT_PATH))) {
            throw new IllegalStateException("Vault bestand bestaat niet");
        }

        byte[] data = Files.readAllBytes(Path.of(VAULT_PATH));
        if (data.length < SALT_LENGTH + IV_LENGTH) {
            throw new IllegalStateException("Vault bestand is corrupt (te klein)");
        }

        try {
            byte[] salt = new byte[SALT_LENGTH];
            byte[] iv = new byte[IV_LENGTH];
            byte[] encrypted = new byte[data.length - SALT_LENGTH - IV_LENGTH];

            System.arraycopy(data, 0, salt, 0, SALT_LENGTH);
            System.arraycopy(data, SALT_LENGTH, iv, 0, IV_LENGTH);
            System.arraycopy(data, SALT_LENGTH + IV_LENGTH, encrypted, 0, encrypted.length);

            SecretKey key = deriveKey(masterPassword, salt);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv));
            byte[] decrypted = cipher.doFinal(encrypted);

            String json = new String(decrypted, StandardCharsets.UTF_8);
            return Account.JsonToList(json);
        } catch (AEADBadTagException e) {
            throw new SecurityException("Onjuist wachtwoord of corrupt bestand", e);
        } catch (Exception e) {
            throw new RuntimeException("Fout bij decryptie: " + e.getMessage(), e);
        }
    }

    public static void createVault(String masterPassword) throws Exception {
        try {
            List<Account> emptyList = new ArrayList<>();
            saveAccounts(emptyList, masterPassword);
            System.out.println("Vault created...");
        } catch (Exception e) {
            System.err.println("Failed to create vault: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
