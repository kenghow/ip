package minnie;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Minnie minnie;

    private final Image userImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaUser.jpeg")));
    private final Image minnieImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaDuke.jpeg")));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Minnie instance */
    public void setMinnie(Minnie d) {
        minnie = d;
        dialogContainer.getChildren().add(
                DialogBox.getMinnieDialog(minnie.getWelcomeMessage(), minnieImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Minnie's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = minnie.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMinnieDialog(response, minnieImage)
        );
        userInput.clear();
    }
}
