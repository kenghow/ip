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

    /**
     * Starts the JavaFX application and initializes the main window from FXML.
     *
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
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
