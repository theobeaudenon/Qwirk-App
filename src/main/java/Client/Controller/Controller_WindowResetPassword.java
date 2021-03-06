package Client.Controller;

import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.User.User;
import com.google.gson.JsonSyntaxException;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.socket.client.Ack;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Boufle on 18/05/2017.
 */
public class Controller_WindowResetPassword {

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
            error.setText("Les mots de passe sont différents");
            error.setVisible(true);
            return;
        }

        User user = new User(null,null, email.getText(), password.getText(), pseudo.getText());


        Singleton_ClientSocket.getInstance().socket.emit("resetUser", user.toJson(), new Ack() {
            public void call(final Object... args) {

                Platform.runLater(new Runnable() {
                    public void run() {
                        if ((boolean)args[0]){
                            goToLogin(event);
                        }
                        else {
                            error.setText("blabla sa marche pas");
                            error.setVisible(true);
                        }
                    }
                });

            }
        });

    }

    public void goToLogin(Event event){

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
