package nl.grapjeje.lock.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.*;

public abstract class Frame extends Application {
    protected Stage stage;
    protected Scene scene;
    protected VBox root;

    public int width;
    public int height;
    public String name;

    public Frame(String name) {
        this.name = name;
        this.width = 400;
        this.height = 350;
    }

    public Frame(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    @Override
    public void start(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);
        this.initialize(stage);
        stage.show();
    }

    public void initialize(Stage stage) {
        this.stage = stage;
        stage.setTitle(this.name);
        stage.setAlwaysOnTop(true);

        final int borderOffset = 10;

        stage.setOnShowing(e -> {
            Screen screen = Screen.getPrimary();
            javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMaxX() - (width + borderOffset) - 5);
            stage.setY(bounds.getMaxY() - (height + borderOffset) - 5);
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle(
                "-fx-background-color: #555555; " +
                        "-fx-padding: 5;"
        );

        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setStyle(
                "-fx-background-color: #2B2B2B; " +
                        "-fx-background-insets: 0;" +
                        "-fx-border-width: 0;"
        );
        mainContainer.setEffect(new DropShadow(20, Color.BLACK));

        borderPane.setCenter(mainContainer);
        this.root = mainContainer;
        this.frame();

        Scene scene = new Scene(borderPane, width + borderOffset, height + borderOffset);
        this.scene = scene;
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
    }

    public abstract void frame();

    protected void setupEventHandlers() {

    }

    public void show() {
        if (stage != null && !stage.isShowing()) {
            this.initialize(stage);
            stage.show();
        }
    }

    public void add(Node e) {
        this.root.getChildren().add(e);
    }

    public void addAll(Node... nodes) {
        this.root.getChildren().addAll(nodes);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void launchFrame(Class<? extends Application> appClass) {
        System.out.println("Starting up the frame...");
        try {
            Application.launch(appClass);
        } catch (Exception e) {
            System.out.println("Something went wrong while loading a frame....");
            e.printStackTrace();
        }
    }
}