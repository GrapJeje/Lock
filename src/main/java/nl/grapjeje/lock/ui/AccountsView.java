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

public class AccountsView extends Frame {
    private final String password;
    private VBox accountsContainer;

    public AccountsView(String password) {
        super("Mijn Accounts", 500, 600);
        this.password = password;
    }

    @Override
    public void frame() {
        if (this.password == null || this.password.isEmpty()) {
            System.out.println("Geen wachtwoord opgegeven");
            return;
        }

        VBox headerContainer = new VBox(5);
        headerContainer.setAlignment(Pos.CENTER);
        headerContainer.setPadding(new Insets(30, 40, 20, 40));

        Label titleLabel = new Label("Mijn Accounts");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.WHITE);

        Label subtitleLabel = new Label("Alle opgeslagen accounts in je vault");
        subtitleLabel.setFont(Font.font("Segoe UI", 13));
        subtitleLabel.setTextFill(Color.web("#CCCCCC"));

        headerContainer.getChildren().addAll(titleLabel, subtitleLabel);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        accountsContainer = new VBox(10);
        accountsContainer.setAlignment(Pos.TOP_CENTER);
        accountsContainer.setPadding(new Insets(10, 20, 20, 20));

        this.loadAccounts();

        scrollPane.setContent(accountsContainer);

        VBox mainContainer = new VBox(10);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.getChildren().addAll(headerContainer, scrollPane);
        mainContainer.setPadding(new Insets(0, 0, 20, 0));

        this.add(mainContainer);

        Button addButton = new Button("Account Toevoegen");
        addButton.setPrefWidth(180);
        addButton.setPrefHeight(40);
        addButton.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold;"
        );

        addButton.setOnMouseEntered(e ->
                addButton.setStyle(
                        "-fx-background-color: #5CBF60; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 6; " +
                                "-fx-background-radius: 6; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: bold;"
                )
        );

        addButton.setOnMouseExited(e ->
                addButton.setStyle(
                        "-fx-background-color: #4CAF50; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 6; " +
                                "-fx-background-radius: 6; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: bold;"
                )
        );

        addButton.setOnAction(e -> {
            System.out.println("Account toevoegen clicked");
        });

        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10, 0, 20, 0));
        buttonContainer.getChildren().add(addButton);

        this.add(buttonContainer);
    }

    private void loadAccounts() {
        try {
            List<Account> accounts = Vault.loadAccounts(password);

            if (accounts.isEmpty()) {
                this.showNoAccountsMessage();
                return;
            }

            for (Account acc : accounts) {
                this.addAccountCard(acc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.showErrorMessage("Fout bij het laden van accounts");
        }
    }

    private void addAccountCard(Account account) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(15));
        card.setStyle(
                "-fx-background-color: #3C3C3C; " +
                        "-fx-border-color: #555555; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8;"
        );
        card.setPrefWidth(400);

        Label nameLabel = new Label(account.name());
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.WHITE);

        HBox usernameBox = new HBox(5);
        Label usernameLabel = new Label("Gebruikersnaam:");
        usernameLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 12));
        usernameLabel.setTextFill(Color.web("#CCCCCC"));

        Label usernameValue = new Label(account.username());
        usernameValue.setFont(Font.font("Segoe UI", 12));
        usernameValue.setTextFill(Color.WHITE);

        usernameBox.getChildren().addAll(usernameLabel, usernameValue);

        HBox passwordBox = new HBox(5);
        Label passwordLabel = new Label("Wachtwoord:");
        passwordLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 12));
        passwordLabel.setTextFill(Color.web("#CCCCCC"));

        PasswordField passwordValue = new PasswordField();
        passwordValue.setText(account.password());
        passwordValue.setEditable(false);
        passwordValue.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-width: 0; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 12px;"
        );

        Button showHideButton = new Button("Toon");
        showHideButton.setStyle(
                "-fx-background-color: #4A4A4A; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 4; " +
                        "-fx-background-radius: 4; " +
                        "-fx-font-size: 11px; " +
                        "-fx-padding: 2 8 2 8;"
        );

        showHideButton.setOnAction(_ -> {
            TextField textField = new TextField(passwordValue.getText());
            textField.setEditable(false);
            textField.setStyle(passwordValue.getStyle());
            passwordBox.getChildren().set(1, textField);
            showHideButton.setText("Verberg");
        });

        passwordBox.getChildren().addAll(passwordLabel, passwordValue, showHideButton);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        Button editButton = new Button("Bewerken");
        editButton.setStyle(
                "-fx-background-color: #2196F3; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 4; " +
                        "-fx-background-radius: 4; " +
                        "-fx-font-size: 12px; " +
                        "-fx-padding: 4 12 4 12;"
        );

        Button deleteButton = new Button("Verwijderen");
        deleteButton.setStyle(
                "-fx-background-color: #F44336; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 4; " +
                        "-fx-background-radius: 4; " +
                        "-fx-font-size: 12px; " +
                        "-fx-padding: 4 12 4 12;"
        );

        buttonBox.getChildren().addAll(editButton, deleteButton);

        card.getChildren().addAll(nameLabel, usernameBox, passwordBox, buttonBox);
        accountsContainer.getChildren().add(card);
    }

    private void showNoAccountsMessage() {
        VBox messageBox = new VBox(10);
        messageBox.setAlignment(Pos.CENTER);
        messageBox.setPadding(new Insets(50, 0, 50, 0));

        Label iconLabel = new Label("😢");
        iconLabel.setFont(Font.font(36));

        Label messageLabel = new Label("Geen accounts gevonden");
        messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        messageLabel.setTextFill(Color.web("#CCCCCC"));

        Label hintLabel = new Label("Klik op 'Account Toevoegen' om je eerste account op te slaan");
        hintLabel.setFont(Font.font("Segoe UI", 12));
        hintLabel.setTextFill(Color.web("#888888"));

        messageBox.getChildren().addAll(iconLabel, messageLabel, hintLabel);
        accountsContainer.getChildren().add(messageBox);
    }

    private void showErrorMessage(String message) {
        VBox errorBox = new VBox(10);
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setPadding(new Insets(50, 0, 50, 0));

        Label iconLabel = new Label("⚠️");
        iconLabel.setFont(Font.font(36));

        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        messageLabel.setTextFill(Color.web("#FF6B6B"));

        errorBox.getChildren().addAll(iconLabel, messageLabel);
        accountsContainer.getChildren().add(errorBox);
    }
}