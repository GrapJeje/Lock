package nl.grapjeje.lock;

import nl.grapjeje.lock.ui.CreateVault;
import nl.grapjeje.lock.ui.Frame;
import nl.grapjeje.lock.ui.Login;
import nl.grapjeje.lock.vault.Vault;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.*;
import java.util.Scanner;

public class StartUp {

    public static void main(String[] args) throws Exception {
        if (!Vault.exists()) Frame.launchFrame(CreateVault.class);
        else Frame.launchFrame(Login.class);
    }
}
