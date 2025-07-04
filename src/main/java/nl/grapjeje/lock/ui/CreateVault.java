package nl.grapjeje.lock.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.StageStyle;
import nl.grapjeje.lock.vault.Vault;

public class CreateVault extends Frame {
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private Label statusLabel;
    private Button createButton;
    private Button cancelButton;
    private ProgressBar strengthBar;
    private Label strengthLabel;

    public CreateVault() {
        super("Create Vault", 400, 550);
    }

    @Override
    public void frame() {
        VBox headerContainer = new VBox(5);
        headerContainer.setAlignment(Pos.CENTER);
        headerContainer.setPadding(new Insets(30, 40, 20, 40));

        Label titleLabel = new Label("Nieuwe Vault Aanmaken");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.WHITE);

        Label subtitleLabel = new Label("Stel een sterk wachtwoord in voor je nieuwe vault");
        subtitleLabel.setFont(Font.font("Segoe UI", 13));
        subtitleLabel.setTextFill(Color.web("#CCCCCC"));

        headerContainer.getChildren().addAll(titleLabel, subtitleLabel);

        VBox formContainer = new VBox(15);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(0, 40, 20, 40));

        VBox passwordContainer = new VBox(8);
        Label passwordLabel = new Label("Vault Wachtwoord:");
        passwordLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
        passwordLabel.setTextFill(Color.WHITE);

        passwordField = new PasswordField();
        passwordField.setPromptText("Kies een sterk wachtwoord");
        passwordField.setPrefWidth(320);
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

        VBox strengthContainer = new VBox(5);
        strengthContainer.setAlignment(Pos.CENTER_LEFT);

        Label strengthTitleLabel = new Label("Wachtwoord Sterkte:");
        strengthTitleLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 12));
        strengthTitleLabel.setTextFill(Color.web("#CCCCCC"));

        strengthBar = new ProgressBar(0);
        strengthBar.setPrefWidth(320);
        strengthBar.setPrefHeight(8);
        strengthBar.setStyle(
                "-fx-accent: #FF6B6B; " +
                        "-fx-background-color: #3C3C3C; " +
                        "-fx-border-radius: 4; " +
                        "-fx-background-radius: 4;"
        );

        strengthLabel = new Label("Zwak");
        strengthLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 11));
        strengthLabel.setTextFill(Color.web("#FF6B6B"));

        strengthContainer.getChildren().addAll(strengthTitleLabel, strengthBar, strengthLabel);

        VBox confirmContainer = new VBox(8);
        Label confirmLabel = new Label("Bevestig Wachtwoord:");
        confirmLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
        confirmLabel.setTextFill(Color.WHITE);

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Herhaal je wachtwoord");
        confirmPasswordField.setPrefWidth(320);
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

        VBox requirementsContainer = new VBox(5);
        requirementsContainer.setAlignment(Pos.CENTER_LEFT);
        requirementsContainer.setPadding(new Insets(0, 0, 10, 0));

        Label requirementsTitle = new Label("Vereisten:");
        requirementsTitle.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 12));
        requirementsTitle.setTextFill(Color.web("#CCCCCC"));

        Label requirements = new Label("• Minimaal 8 tekens\n• Gebruik hoofdletters, kleine letters, cijfers en symbolen\n• Vermijd persoonlijke informatie");
        requirements.setFont(Font.font("Segoe UI", 11));
        requirements.setTextFill(Color.web("#999999"));

        requirementsContainer.getChildren().addAll(requirementsTitle, requirements);

        statusLabel = new Label("");
        statusLabel.setFont(Font.font("Segoe UI", 12));
        statusLabel.setTextFill(Color.web("#FF6B6B"));
        statusLabel.setVisible(false);

        formContainer.getChildren().addAll(passwordContainer, strengthContainer, confirmContainer, requirementsContainer, statusLabel);

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

        createButton = new Button("Vault Aanmaken");
        createButton.setPrefWidth(150);
        createButton.setPrefHeight(35);
        createButton.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold;"
        );

        createButton.setOnMouseEntered(e ->
                createButton.setStyle(
                        "-fx-background-color: #5CBF60; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 6; " +
                                "-fx-background-radius: 6; " +
                                "-fx-font-size: 13px; " +
                                "-fx-font-weight: bold;"
                )
        );

        createButton.setOnMouseExited(e ->
                createButton.setStyle(
                        "-fx-background-color: #4CAF50; " +
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

        buttonContainer.getChildren().addAll(cancelButton, createButton);

        this.add(headerContainer);
        this.add(formContainer);
        this.add(buttonContainer);

        this.setupEventHandlers();
    }

    @Override
    protected void setupEventHandlers() {
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            updatePasswordStrength(newVal);
            validatePasswords();
        });

        confirmPasswordField.textProperty().addListener((obs, oldVal, newVal) -> {
            validatePasswords();
        });

        createButton.setOnAction(e -> {
            if (validatePasswords()) {
                String password = passwordField.getText();
                if (this.isStrongPassword(password)) {
                    try {
                        Vault.createVault(password);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    stage.close();
                } else this.showStatus("Wachtwoord is niet sterk genoeg", true);
            }
        });

        cancelButton.setOnAction(e -> {
            System.out.println("Vault aanmaken geannuleerd");
            stage.close();
        });

        passwordField.setOnAction(e -> confirmPasswordField.requestFocus());
        confirmPasswordField.setOnAction(e -> createButton.fire());
    }

    private void updatePasswordStrength(String password) {
        double strength = calculatePasswordStrength(password);
        strengthBar.setProgress(strength);

        if (strength < 0.3) {
            strengthLabel.setText("Zwak");
            strengthLabel.setTextFill(Color.web("#FF6B6B"));
            strengthBar.setStyle(
                    "-fx-accent: #FF6B6B; " +
                            "-fx-background-color: #3C3C3C; " +
                            "-fx-border-radius: 4; " +
                            "-fx-background-radius: 4;"
            );
        } else if (strength < 0.6) {
            strengthLabel.setText("Gemiddeld");
            strengthLabel.setTextFill(Color.web("#FFA726"));
            strengthBar.setStyle(
                    "-fx-accent: #FFA726; " +
                            "-fx-background-color: #3C3C3C; " +
                            "-fx-border-radius: 4; " +
                            "-fx-background-radius: 4;"
            );
        } else if (strength < 0.8) {
            strengthLabel.setText("Sterk");
            strengthLabel.setTextFill(Color.web("#66BB6A"));
            strengthBar.setStyle(
                    "-fx-accent: #66BB6A; " +
                            "-fx-background-color: #3C3C3C; " +
                            "-fx-border-radius: 4; " +
                            "-fx-background-radius: 4;"
            );
        } else {
            strengthLabel.setText("Zeer Sterk");
            strengthLabel.setTextFill(Color.web("#4CAF50"));
            strengthBar.setStyle(
                    "-fx-accent: #4CAF50; " +
                            "-fx-background-color: #3C3C3C; " +
                            "-fx-border-radius: 4; " +
                            "-fx-background-radius: 4;"
            );
        }
    }

    private double calculatePasswordStrength(String password) {
        if (password.isEmpty()) return 0;

        double strength = 0;

        if (password.length() >= 8) strength += 0.25;
        if (password.length() >= 12) strength += 0.1;
        if (password.length() >= 16) strength += 0.1;

        if (password.matches(".*[a-z].*")) strength += 0.15; // lowercase
        if (password.matches(".*[A-Z].*")) strength += 0.15; // uppercase
        if (password.matches(".*[0-9].*")) strength += 0.15; // numbers
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) strength += 0.15; // symbols

        return Math.min(strength, 1.0);
    }

    private boolean isStrongPassword(String password) {
        return calculatePasswordStrength(password) >= 0.6;
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

        if (password.length() < 8) {
            this.showStatus("Wachtwoord moet minimaal 8 tekens bevatten", true);
            return false;
        }

        this.showStatus("Wachtwoord is geldig ✓", false);
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