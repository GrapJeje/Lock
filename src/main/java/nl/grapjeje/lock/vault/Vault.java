package nl.grapjeje.lock.vault;

import nl.grapjeje.lock.Account;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
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
        byte[] keyBytes = new byte[16];
        byte[] pwdBytes = password.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < keyBytes.length; i++) {
            keyBytes[i] = (i < pwdBytes.length ? pwdBytes[i] : 0);
            keyBytes[i] ^= (i < salt.length ? salt[i] : 0);
        }
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
