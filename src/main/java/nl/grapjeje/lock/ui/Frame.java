package nl.grapjeje.lock.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        this.stage = stage;

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Wachtwoord Verificatie");
        stage.setAlwaysOnTop(true);

        stage.setOnShowing(e -> {
            Screen screen = Screen.getPrimary();
            javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();

            double margin = 20;
            stage.setX(bounds.getMaxX() - 400 - margin);
            stage.setY(bounds.getMaxY() - 350 - margin);
        });

        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setStyle("-fx-background-color: #2B2B2B;");
        mainContainer.setEffect(new DropShadow(20, Color.BLACK));

        this.root = mainContainer;
        this.frame();

        Scene scene = new Scene(mainContainer, width, height);
        this.scene = scene;
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    public abstract void frame();

    public void add(Node e) {
        this.root.getChildren().add(e);
    }

    public void addAll(Node... nodes) {
        this.root.getChildren().addAll(nodes);
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