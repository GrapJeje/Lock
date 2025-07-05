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
    private Button floatingAddButton;

    public AccountsView(String password) {
        super("SecureVault - Mijn Accounts", 400, 500);
        this.password = password;
    }

    @Override
    public void frame() {
        if (this.password == null || this.password.isEmpty()) {
            System.out.println("Geen wachtwoord opgegeven");
            return;
        }

        BorderPane mainLayout = new BorderPane();
        VBox headerSection = createHeader();
        ScrollPane contentArea = createContentArea();
        createFloatingButton();

        mainLayout.setTop(headerSection);
        mainLayout.setCenter(contentArea);

        StackPane rootContainer = new StackPane();
        rootContainer.getChildren().addAll(mainLayout, floatingAddButton);
        StackPane.setAlignment(floatingAddButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(floatingAddButton, new Insets(0, 30, 30, 0));

        this.add(rootContainer);
        this.loadAccounts();
    }

    private VBox createHeader() {
        VBox headerContainer = new VBox();
        headerContainer.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2E2E2E, #1E1E1E); " +
                        "-fx-border-color: #404040; " +
                        "-fx-border-width: 0 0 1 0; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 2);"
        );
        headerContainer.setPadding(new Insets(20, 25, 20, 25));

        // Top row with title and close button
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        VBox titleSection = new VBox(3);
        titleSection.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label("🔐 SecureVault");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);

        Label mottoLabel = new Label("Jouw digitale sleutelkluis");
        mottoLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 12));
        mottoLabel.setTextFill(Color.web("#B0B0B0"));

        titleSection.getChildren().addAll(titleLabel, mottoLabel);

        // Close button
        Button closeButton = new Button("✕");
        closeButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #CCCCCC; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-min-width: 30; " +
                        "-fx-min-height: 30; " +
                        "-fx-max-width: 30; " +
                        "-fx-max-height: 30;"
        );

        closeButton.setOnMouseEntered(e ->
                closeButton.setStyle(
                        "-fx-background-color: #F44336; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 18px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-border-radius: 15; " +
                                "-fx-background-radius: 15; " +
                                "-fx-min-width: 30; " +
                                "-fx-min-height: 30; " +
                                "-fx-max-width: 30; " +
                                "-fx-max-height: 30;"
                )
        );

        closeButton.setOnMouseExited(e ->
                closeButton.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-text-fill: #CCCCCC; " +
                                "-fx-font-size: 18px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-border-radius: 15; " +
                                "-fx-background-radius: 15; " +
                                "-fx-min-width: 30; " +
                                "-fx-min-height: 30; " +
                                "-fx-max-width: 30; " +
                                "-fx-max-height: 30;"
                )
        );

        closeButton.setOnAction(e -> {
            System.out.println("Applicatie wordt gesloten");
            stage.close();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topRow.getChildren().addAll(titleSection, spacer, closeButton);

        // Subtitle section
        VBox subtitleSection = new VBox(5);
        subtitleSection.setPadding(new Insets(15, 0, 0, 0));

        Label subtitleLabel = new Label("Mijn Accounts");
        subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        subtitleLabel.setTextFill(Color.WHITE);

        Label descriptionLabel = new Label("Alle opgeslagen accounts in je vault");
        descriptionLabel.setFont(Font.font("Segoe UI", 13));
        descriptionLabel.setTextFill(Color.web("#CCCCCC"));

        subtitleSection.getChildren().addAll(subtitleLabel, descriptionLabel);

        headerContainer.getChildren().addAll(topRow, subtitleSection);
        return headerContainer;
    }

    private ScrollPane createContentArea() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background-color: #1A1A1A; " +
                        "-fx-background: #1A1A1A;" +
                        "-fx-border-width: 0;"
        );

        accountsContainer = new VBox(15);
        accountsContainer.setAlignment(Pos.TOP_CENTER);
        accountsContainer.setPadding(new Insets(25, 25, 100, 25)); // Extra bottom padding for floating button

        scrollPane.setContent(accountsContainer);
        return scrollPane;
    }

    private void createFloatingButton() {
        floatingAddButton = new Button("+ Account");
        floatingAddButton.setPrefWidth(140);
        floatingAddButton.setPrefHeight(45);
        floatingAddButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #4CAF50, #45A049); " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 25; " +
                        "-fx-background-radius: 25; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 12, 0, 0, 4);"
        );

        floatingAddButton.setOnMouseEntered(e ->
                floatingAddButton.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #5CBF60, #4CAF50); " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 25; " +
                                "-fx-background-radius: 25; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 15, 0, 0, 6); " +
                                "-fx-cursor: hand;"
                )
        );

        floatingAddButton.setOnMouseExited(e ->
                floatingAddButton.setStyle(
                        "-fx-background-color: linear-gradient(to bottom, #4CAF50, #45A049); " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 25; " +
                                "-fx-background-radius: 25; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 12, 0, 0, 4);"
                )
        );

        floatingAddButton.setOnAction(e -> {
            System.out.println("Account toevoegen clicked");
            // Hier kan je de AddAccountView openen
        });
    }

    private void loadAccounts() {
        try {
            List<Account> accounts = Vault.loadAccounts(password);

            accountsContainer.getChildren().clear();

            List<Account> validAccounts = accounts.stream()
                    .filter(acc -> acc != null &&
                            acc.name() != null && !acc.name().isEmpty() &&
                            acc.username() != null && !acc.username().isEmpty())
                    .toList();

            if (validAccounts.isEmpty()) {
                this.showNoAccountsMessage();
                return;
            }

            for (Account acc : validAccounts) {
                this.addAccountCard(acc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.showErrorMessage("Fout bij het laden van accounts");
        }
    }

    private void addAccountCard(Account account) {
        VBox card = new VBox(12);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2E2E2E, #252525); " +
                        "-fx-border-color: #404040; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 12; " +
                        "-fx-background-radius: 12; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);"
        );
        card.setMaxWidth(480);

        // Account name with icon
        HBox nameContainer = new HBox(8);
        nameContainer.setAlignment(Pos.CENTER_LEFT);

        Label iconLabel = new Label("🔑");
        iconLabel.setFont(Font.font(16));

        Label nameLabel = new Label(account.name());
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        nameLabel.setTextFill(Color.WHITE);

        nameContainer.getChildren().addAll(iconLabel, nameLabel);

        // Username section
        VBox usernameSection = new VBox(3);
        Label usernameLabel = new Label("GEBRUIKERSNAAM");
        usernameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        usernameLabel.setTextFill(Color.web("#888888"));

        Label usernameValue = new Label(account.username());
        usernameValue.setFont(Font.font("Segoe UI", 14));
        usernameValue.setTextFill(Color.WHITE);

        usernameSection.getChildren().addAll(usernameLabel, usernameValue);

        // Password section
        VBox passwordSection = new VBox(3);
        Label passwordLabel = new Label("WACHTWOORD");
        passwordLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        passwordLabel.setTextFill(Color.web("#888888"));

        HBox passwordBox = new HBox(10);
        passwordBox.setAlignment(Pos.CENTER_LEFT);

        PasswordField passwordValue = new PasswordField();
        passwordValue.setText(account.password());
        passwordValue.setEditable(false);
        passwordValue.setPrefWidth(200);
        passwordValue.setStyle(
                "-fx-background-color: #1A1A1A; " +
                        "-fx-border-color: #404040; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 8;"
        );

        Button showHideButton = new Button("👁");
        showHideButton.setStyle(
                "-fx-background-color: #3C3C3C; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 8 12 8 12; " +
                        "-fx-min-width: 40; " +
                        "-fx-min-height: 36;"
        );

        showHideButton.setOnMouseEntered(e ->
                showHideButton.setStyle(
                        "-fx-background-color: #4A4A4A; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 6; " +
                                "-fx-background-radius: 6; " +
                                "-fx-font-size: 14px; " +
                                "-fx-padding: 8 12 8 12; " +
                                "-fx-min-width: 40; " +
                                "-fx-min-height: 36;"
                )
        );

        showHideButton.setOnMouseExited(e ->
                showHideButton.setStyle(
                        "-fx-background-color: #3C3C3C; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 6; " +
                                "-fx-background-radius: 6; " +
                                "-fx-font-size: 14px; " +
                                "-fx-padding: 8 12 8 12; " +
                                "-fx-min-width: 40; " +
                                "-fx-min-height: 36;"
                )
        );

        final boolean[] isPasswordVisible = {false};
        showHideButton.setOnAction(_ -> {
            if (!isPasswordVisible[0]) {
                TextField textField = new TextField(passwordValue.getText());
                textField.setEditable(false);
                textField.setPrefWidth(200);
                textField.setStyle(passwordValue.getStyle());
                passwordBox.getChildren().set(0, textField);
                showHideButton.setText("🙈");
                isPasswordVisible[0] = true;
            } else {
                passwordBox.getChildren().set(0, passwordValue);
                showHideButton.setText("👁");
                isPasswordVisible[0] = false;
            }
        });

        passwordBox.getChildren().addAll(passwordValue, showHideButton);
        passwordSection.getChildren().addAll(passwordLabel, passwordBox);

        // Action buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        Button editButton = new Button("✏ Bewerken");
        editButton.setStyle(
                "-fx-background-color: #2196F3; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 8 16 8 16;"
        );

        editButton.setOnMouseEntered(e ->
                editButton.setStyle(
                        "-fx-background-color: #1976D2; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 6; " +
                                "-fx-background-radius: 6; " +
                                "-fx-font-size: 12px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 8 16 8 16;"
                )
        );

        editButton.setOnMouseExited(e ->
                editButton.setStyle(
                        "-fx-background-color: #2196F3; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 6; " +
                                "-fx-background-radius: 6; " +
                                "-fx-font-size: 12px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 8 16 8 16;"
                )
        );

        Button deleteButton = new Button("🗑 Verwijderen");
        deleteButton.setStyle(
                "-fx-background-color: #F44336; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 8 16 8 16;"
        );

        deleteButton.setOnMouseEntered(e ->
                deleteButton.setStyle(
                        "-fx-background-color: #D32F2F; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 6; " +
                                "-fx-background-radius: 6; " +
                                "-fx-font-size: 12px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 8 16 8 16;"
                )
        );

        deleteButton.setOnMouseExited(e ->
                deleteButton.setStyle(
                        "-fx-background-color: #F44336; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 6; " +
                                "-fx-background-radius: 6; " +
                                "-fx-font-size: 12px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 8 16 8 16;"
                )
        );

        buttonBox.getChildren().addAll(editButton, deleteButton);

        card.getChildren().addAll(nameContainer, usernameSection, passwordSection, buttonBox);
        accountsContainer.getChildren().add(card);
    }

    private void showNoAccountsMessage() {
        VBox messageBox = new VBox(15);
        messageBox.setAlignment(Pos.CENTER);
        messageBox.setPadding(new Insets(60, 40, 60, 40));
        messageBox.setStyle(
                "-fx-background-color: transparent;"
        );

        Label iconLabel = new Label("🔐");
        iconLabel.setFont(Font.font(32));
        iconLabel.setStyle("-fx-opacity: 0.6;");

        Label messageLabel = new Label("Geen accounts gevonden");
        messageLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 16));
        messageLabel.setTextFill(Color.web("#888888"));
        messageLabel.setStyle("-fx-font-style: italic;");

        Label hintLabel = new Label("Klik op de '+ Account' knop om je eerste account op te slaan");
        hintLabel.setFont(Font.font("Segoe UI", 12));
        hintLabel.setTextFill(Color.web("#666666"));
        hintLabel.setStyle("-fx-font-style: italic;");
        hintLabel.setWrapText(true);

        messageBox.getChildren().addAll(iconLabel, messageLabel, hintLabel);
        accountsContainer.getChildren().add(messageBox);
    }

    private void showErrorMessage(String message) {
        VBox errorBox = new VBox(15);
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setPadding(new Insets(60, 40, 60, 40));
        errorBox.setStyle(
                "-fx-background-color: #2E1A1A; " +
                        "-fx-border-color: #804040; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 12; " +
                        "-fx-background-radius: 12;"
        );

        Label iconLabel = new Label("⚠️");
        iconLabel.setFont(Font.font(48));

        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        messageLabel.setTextFill(Color.web("#FF6B6B"));

        errorBox.getChildren().addAll(iconLabel, messageLabel);
        accountsContainer.getChildren().add(errorBox);
    }
}