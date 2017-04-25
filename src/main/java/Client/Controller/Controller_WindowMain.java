package Client.Controller;/**
 * Created by Boufle on 24/04/2017.
 */

import Client.ClientSocket;
import Objet.User.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class Controller_WindowMain extends Application {

    @FXML
    private JFXTextField login;

    @FXML
    private JFXPasswordField pass;



    public static void main(String[] args)  {
        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws URISyntaxException {




        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1080, 720);

        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();

        ClientSocket.getInstance();

    }

    public void test(Event event) {
        System.out.println("test");
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;/* Exception */
        try {
            root = FXMLLoader.load(getClass().getResource("/second.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void login(Event event) {

        System.out.println(login.getText() + " : " + pass.getText());

        ClientSocket.getInstance().socket.emit("login", new User(login.getText(), pass.getText()).toJson(), new Ack() {
            public void call(Object... args) {
                if(args[0] != null){
                    User user =  new User(args[0].toString()) ;
                    System.out.printf(user.getUserName());

                }else{
                    System.out.printf("Erreur mdp/User");

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
}
