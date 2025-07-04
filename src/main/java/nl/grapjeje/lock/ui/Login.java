package nl.grapjeje.lock.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.grapjeje.lock.Account;
import nl.grapjeje.lock.vault.Vault;

import java.util.List;

public class Login extends Frame {
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private Label statusLabel;
    private Button confirmButton;
    private Button cancelButton;

    public Login() {
        super("Wachtwoord Verificatie", 400, 350);
    }

    @Override
    public void frame() {
        VBox headerContainer = new VBox(5);
        headerContainer.setAlignment(Pos.CENTER);
        headerContainer.setPadding(new Insets(30, 40, 20, 40));

        Label titleLabel = new Label("Authenticatie Vereist");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.WHITE);

        Label subtitleLabel = new Label("Voer je wachtwoord tweemaal in om verder te gaan");
        subtitleLabel.setFont(Font.font("Segoe UI", 13));
        subtitleLabel.setTextFill(Color.web("#CCCCCC"));

        headerContainer.getChildren().addAll(titleLabel, subtitleLabel);

        VBox formContainer = new VBox(15);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(0, 40, 30, 40));

        VBox passwordContainer = new VBox(8);
        Label passwordLabel = new Label("Wachtwoord (1e keer):");
        passwordLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
        passwordLabel.setTextFill(Color.WHITE);

        passwordField = new PasswordField();
        passwordField.setPromptText("Voer je wachtwoord in");
        passwordField.setPrefWidth(280);
        passwordField.setPrefHeight(40);
        passwordField.setStyle(
                "-fx-background-color: #3C3C3C; " +
                        "-fx-border-color: #555555; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6; " +
                        "-fx-text-fill: white; " +
                        "-fx-prompt-text-fill: #888888; " +
                        "-fx-font-size: 13px;"
        );

        passwordContainer.getChildren().addAll(passwordLabel, passwordField);

        VBox confirmContainer = new VBox(8);
        Label confirmLabel = new Label("Wachtwoord (2e keer):");
        confirmLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
        confirmLabel.setTextFill(Color.WHITE);

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Voer je wachtwoord nogmaals in");
        confirmPasswordField.setPrefWidth(280);
        confirmPasswordField.setPrefHeight(40);
        confirmPasswordField.setStyle(
                "-fx-background-color: #3C3C3C; " +
                        "-fx-border-color: #555555; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6; " +
                        "-fx-text-fill: white; " +
                        "-fx-prompt-text-fill: #888888; " +
                        "-fx-font-size: 13px;"
        );

        confirmContainer.getChildren().addAll(confirmLabel, confirmPasswordField);

        statusLabel = new Label("");
        statusLabel.setFont(Font.font("Segoe UI", 12));
        statusLabel.setTextFill(Color.web("#FF6B6B"));
        statusLabel.setVisible(false);

        formContainer.getChildren().addAll(passwordContainer, confirmContainer, statusLabel);

        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(0, 40, 30, 40));

        cancelButton = new Button("Annuleren");
        cancelButton.setPrefWidth(120);
        cancelButton.setPrefHeight(35);
        cancelButton.setStyle(
                "-fx-background-color: #4A4A4A; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold;"
        );

        confirmButton = new Button("Toegang Verlenen");
        confirmButton.setPrefWidth(150);
        confirmButton.setPrefHeight(35);
        confirmButton.setStyle(
                "-fx-background-color: #007ACC; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold;"
        );

        confirmButton.setOnMouseEntered(e ->
                confirmButton.setStyle(
                        "-fx-background-color: #0087E8; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 6; " +
                                "-fx-background-radius: 6; " +
                                "-fx-font-size: 13px; " +
                                "-fx-font-weight: bold;"
                )
        );

        confirmButton.setOnMouseExited(e ->
                confirmButton.setStyle(
                        "-fx-background-color: #007ACC; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 6; " +
                                "-fx-background-radius: 6; " +
                                "-fx-font-size: 13px; " +
                                "-fx-font-weight: bold;"
                )
        );

        cancelButton.setOnMouseEntered(e ->
                cancelButton.setStyle(
                        "-fx-background-color: #555555; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 6; " +
                                "-fx-background-radius: 6; " +
                                "-fx-font-size: 13px; " +
                                "-fx-font-weight: bold;"
                )
        );

        cancelButton.setOnMouseExited(e ->
                cancelButton.setStyle(
                        "-fx-background-color: #4A4A4A; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 6; " +
                                "-fx-background-radius: 6; " +
                                "-fx-font-size: 13px; " +
                                "-fx-font-weight: bold;"
                )
        );

        buttonContainer.getChildren().addAll(cancelButton, confirmButton);

        this.add(headerContainer);
        this.add(formContainer);
        this.add(buttonContainer);

        this.setupEventHandlers();
    }

    @Override
    protected void setupEventHandlers() {
        confirmPasswordField.textProperty().addListener((_, _, _)
                -> this.validatePasswords());

        passwordField.textProperty().addListener((_, _, _)
                -> this.validatePasswords());

        confirmButton.setOnAction(e -> {
            if (this.validatePasswords()) {
                String password = passwordField.getText();
                if (!password.isEmpty()) {

                    try {
                        List<Account> accounts = Vault.loadAccounts(password);
                        for (Account acc : accounts) {
                            System.out.println("Account: " + acc.name() + " - " + acc.username());
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    stage.close();
                } else this.showStatus("Voer een wachtwoord in", true);
            }
        });

        cancelButton.setOnAction(e -> {
            System.out.println("Login geannuleerd");
            stage.close();
        });

        passwordField.setOnAction(_ -> confirmPasswordField.requestFocus());
        confirmPasswordField.setOnAction(_ -> confirmButton.fire());
    }

    private boolean validatePasswords() {
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            this.hideStatus();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            this.showStatus("Wachtwoorden komen niet overeen", true);
            return false;
        }

        this.showStatus("Wachtwoorden komen overeen ✓", false);
        return true;
    }

    private void showStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setTextFill(isError ? Color.web("#FF6B6B") : Color.web("#4CAF50"));
        statusLabel.setVisible(true);
    }

    private void hideStatus() {
        statusLabel.setVisible(false);
    }
}