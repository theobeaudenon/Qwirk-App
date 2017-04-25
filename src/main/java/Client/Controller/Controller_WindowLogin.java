package Client.Controller;/**
 * Created by Boufle on 25/04/2017.
 */

import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.User.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.socket.client.Ack;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.annotation.Resources;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_WindowLogin extends Application implements Initializable {

    @FXML
    private JFXTextField login;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private Label error;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Singleton_ClientSocket.getInstance();
        if (Singleton_UserInfo.getInstance().getUser() != null){
            login.setText(Singleton_UserInfo.getInstance().getUser().getMail());
            pass.setText(Singleton_UserInfo.getInstance().getUser().getPass());
        }
    }


    @Override
    public void start(Stage primaryStage) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Qwirk");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void login(final Event event) {

        Singleton_ClientSocket.getInstance().socket.emit("login", new User(login.getText(), pass.getText()).toJson(), new Ack() {
            public void call(final Object... args) {
                if(args[0] != null){
                    Platform.runLater(new Runnable() {
                        public void run() {
                            User user = new User(args[0].toString());
                            Singleton_UserInfo.getInstance().setUser(user);
                            goToMainFrame(event);
                        }
                    });

                }else{
                    System.out.printf("Erreur mdp/User");
                    error.setVisible(true);
                }
            }
        });

        /*
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/tmpChat.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        */
    }

    public void signup(Event event){

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/signup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void goToMainFrame(Event event){
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void resetError(Event event) {
        error.setVisible(false);
    }


}
