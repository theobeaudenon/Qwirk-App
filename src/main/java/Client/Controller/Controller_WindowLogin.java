package Client.Controller;/**
 * Created by Boufle on 25/04/2017.
 */

import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Message.Message;
import Objet.User.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.socket.client.Ack;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Resources;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller_WindowLogin extends Application implements Initializable {

    @FXML
    private JFXTextField login;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private Label error;

    @FXML
    private Pane leftPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Singleton_ClientSocket.getInstance();
        if (Singleton_UserInfo.getInstance().getUser() != null){
            login.setText(Singleton_UserInfo.getInstance().getUser().getMail());
            pass.setText(Singleton_UserInfo.getInstance().getUser().getPass());
        }

        int numberOfSquares = 30;
        while (numberOfSquares > 0){
            generateAnimation();
            numberOfSquares--;
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

                    User user = new User(args[0].toString());
                    Singleton_UserInfo.getInstance().setUser(user);

                    final ArrayList<User> myContactArrayList = new ArrayList<User>();
                    Singleton_ClientSocket.getInstance().socket.emit("getMyContacts",  Singleton_UserInfo.getInstance().getUser().getUserID(), new Ack() {
                        public void call(final Object... args) {
                            if(args[0] != null){

                                JSONArray array = (JSONArray) args[0];

                                for (int i = 0; i < array.length(); i++) {
                                    try {

                                        JSONObject jsonObject = array.getJSONObject(i);
                                        myContactArrayList.add(new User(jsonObject.toString()));

                                    } catch (JSONException e) {

                                    }
                                }
                                Singleton_UserInfo.getInstance().setContactList(myContactArrayList);
                            }else{
                                System.out.printf("Erreur dans le chargement des contacts");
                                //error.setVisible(true);
                            }
                        }
                    });
                    Platform.runLater(new Runnable() {
                        public void run() {
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
        scene.getStylesheets().add("/style/listViewChat.css");
        stage.setScene(scene);
        stage.show();
    }


    public void resetError(Event event) {
        error.setVisible(false);
    }

    public void generateAnimation(){
        Random rand = new Random();
        int sizeOfSqaure = rand.nextInt(40) + 1;
        int speedOfSqaure = rand.nextInt(10) + 10;
        int startXPoint = rand.nextInt(190);
        int startYPoint = rand.nextInt(397);
        int direction = rand.nextInt(5) + 1;

        KeyValue moveXAxis = null;
        KeyValue moveYAxis = null;
        Rectangle r1 = null;

        switch (direction){
            case 1 :
                // MOVE LEFT TO RIGHT
                r1 = new Rectangle(0,startYPoint,sizeOfSqaure,sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 222 -  sizeOfSqaure);
                break;
            case 2 :
                // MOVE TOP TO BOTTOM
                r1 = new Rectangle(startXPoint,0,sizeOfSqaure,sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), 397 - sizeOfSqaure);
                break;
            case 3 :
                // MOVE LEFT TO RIGHT, TOP TO BOTTOM
                r1 = new Rectangle(startXPoint,0,sizeOfSqaure,sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 222 -  sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), 397 - sizeOfSqaure);
                break;
            case 4 :
                // MOVE BOTTOM TO TOP
                r1 = new Rectangle(startXPoint,397-sizeOfSqaure ,sizeOfSqaure,sizeOfSqaure);
                moveYAxis = new KeyValue(r1.xProperty(), 0);
                break;
            case 5 :
                // MOVE RIGHT TO LEFT
                r1 = new Rectangle(222-sizeOfSqaure,startYPoint,sizeOfSqaure,sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 0);
                break;
            case 6 :
                //MOVE RIGHT TO LEFT, BOTTOM TO TOP
                r1 = new Rectangle(startXPoint,0,sizeOfSqaure,sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 222 -  sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), 397 - sizeOfSqaure);
                break;

            default:
                System.out.println("default");
        }

        r1.setFill(Color.web("#7289DA"));
        r1.setOpacity(0.3);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(speedOfSqaure * 1000), moveXAxis, moveYAxis);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        leftPane.getChildren().add(leftPane.getChildren().size()-1,r1);
    }


    public void mdpoublierActionc(Event event) {
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/resetPassword.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
