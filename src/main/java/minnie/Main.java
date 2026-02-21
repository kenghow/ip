package minnie;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Minnie using FXML.
 */
public class Main extends Application {

    private final Minnie minnie = new Minnie();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Minnie - your task sidekick");

            stage.setResizable(true);
            stage.setMinWidth(400);
            stage.setMinHeight(600);
            fxmlLoader.<MainWindow>getController().setMinnie(minnie);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
