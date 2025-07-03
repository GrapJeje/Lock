package nl.grapjeje.lock.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Frame extends Application {
    @Override
    public void start(Stage stage) {
        Button btn = new Button("Klik mij!");

        btn.setOnAction(e -> System.out.println("Knop geklikt!"));

        VBox root = new VBox(10, btn);
        root.setPadding(new javafx.geometry.Insets(15));

        Scene scene = new Scene(root, 320, 240);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
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
