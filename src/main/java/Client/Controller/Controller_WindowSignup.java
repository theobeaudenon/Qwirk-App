package Client.Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by Boufle on 25/04/2017.
 */
public class Controller_WindowSignup {

    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField pseudo;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXPasswordField passwordConfirmation;
    @FXML
    private Label error;

    public void resetError(Event event) {
        if (error.isVisible()){
            error.setVisible(false);
        }
    }

    public void signupConfirmation(Event event) {
        if (!password.getText().equals(passwordConfirmation.getText())){
            error.setVisible(true);
        }
    }
}
