package Client.Controller;/**
 * Created by Boufle on 24/04/2017.
 */

import Client.EventHandler.EventHandler_UserChan;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Channel.Channel;
import Objet.Message.Message;
import Objet.User.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import io.socket.client.Ack;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller_WindowMain implements Initializable {

    @FXML
    private JFXListView chanelPan;

    @FXML
    private JFXMasonryPane homeChan;

    @FXML
    private ImageView chatGroupBanner;

    @FXML
    private TextArea chatSendBox;

    @FXML
    private BorderPane centerPan;

    @FXML
    private BorderPane controllerChat;

    private String nameGroupChat;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        homeDisplay();

        final ArrayList<Channel> channelArrayList = new ArrayList<Channel>();

        Singleton_ClientSocket.getInstance().socket.emit("getPublicChannels", Singleton_UserInfo.getInstance().getUser().getUserID(),new Ack() {
            public void call(final Object... args) {
                if(args[0] != null){
                    JSONArray array = (JSONArray) args[0];

                    for (int i = 0; i < array.length(); i++) {
                        try {
                            JSONObject jsonObject = array.getJSONObject(i);
                            channelArrayList.add(new Channel(jsonObject.toString()));

                        } catch (JSONException e) {

                        }
                    }

                    Platform.runLater(new Runnable() {
                        public void run() {
                            for (Channel channel : channelArrayList) {
                                Label l = new Label();
                                l.setPrefSize(340,100);
                                l.setStyle("-fx-background-image: url(http://www.sosiphone.com/blogiphone/wp-content/uploads//2011/02/Pac-Man-banniere.jpg)");
                                l.setText(channel.getChannelName());
                                homeChan.getChildren().add(l);
                            }
                        }
                    });


                }else{
                    //System.out.printf("Erreur mdp/User");
                    //error.setVisible(true);
                }
            }
        });


        final ArrayList<Channel> myChannelArrayList = new ArrayList<Channel>();


        Singleton_ClientSocket.getInstance().socket.emit("getMyChannels", Singleton_UserInfo.getInstance().getUser().getUserID(), new Ack() {
            public void call(final Object... args) {
                if(args[0] != null){

                    JSONArray array = (JSONArray) args[0];



                    for (int i = 0; i < array.length(); i++) {
                        try {

                            JSONObject jsonObject = array.getJSONObject(i);
                            myChannelArrayList.add(new Channel(jsonObject.toString()));

                        } catch (JSONException e) {

                        }
                    }


                   Platform.runLater(new Runnable() {
                        public void run() {
                            for (Channel channel : myChannelArrayList) {
                                Label label = new Label();
                                label.setText(channel.getChannelName());
                                label.setId(String.valueOf(channel.getIdChannel()));
                                chanelPan.getItems().add(label);
                            }
                        }
                    });

                }else{
                    System.out.printf("Erreur mdp/User");
                    //error.setVisible(true);
                }
            }
        });






    }

    public void userChanClicked(Event event) {

        //System.out.println("clicked on " + chanelPan.getSelectionModel().getSelectedItem());
        nameGroupChat = ((Label) chanelPan.getSelectionModel().getSelectedItem()).getText();
        System.out.println( ((Label) chanelPan.getSelectionModel().getSelectedItem()).getId());
        showGroupChat();

    }

    public void showGroupChat(){

        homeChan.setVisible(false);
        homeChan.setManaged(false);
        chatGroupBanner.setVisible(true);
        chatGroupBanner.setManaged(true);
        controllerChat.setVisible(true);
        controllerChat.setManaged(true);

        final JFXListView<Label> list = new JFXListView<>();

        final ArrayList<Message> myMessageArrayList = new ArrayList<Message>();

        Singleton_ClientSocket.getInstance().socket.emit("channelMessages", 2, new Ack() {
            public void call(final Object... args) {
                if(args[0] != null){

                    JSONArray array = (JSONArray) args[0];



                    for (int i = 0; i < array.length(); i++) {
                        try {

                            JSONObject jsonObject = array.getJSONObject(i);
                            myMessageArrayList.add(new Message(jsonObject.toString()));

                        } catch (JSONException e) {

                        }
                    }


                    Platform.runLater(new Runnable() {
                        public void run() {
                            for (Message message : myMessageArrayList) {
                                list.getItems().add(new Label(message.getMessage()));
                            }

                        }
                    });

                }else{
                    System.out.printf("Erreur mdp/User");
                    //error.setVisible(true);
                }
            }
        });





       // list.getStyleClass().add("mylistview");
        list.setMaxHeight(3400);


        StackPane container = new StackPane(list);
       // container.setPadding(new Insets(24));

        ScrollPane pane = new ScrollPane();
        pane.setContent(container);

        pane.setFitToWidth(true);


        centerPan.setCenter(pane);


    }

    public void homeDisplay(){

        homeChan.setVisible(true);
        homeChan.setManaged(true);
        chatGroupBanner.setVisible(false);
        chatGroupBanner.setManaged(false);
        controllerChat.setVisible(false);
        controllerChat.setManaged(false);
    }

    public void chatSendEventHandler(KeyEvent event) {
        if (chatSendBox.isVisible()){
            if (event.getCode() == KeyCode.ENTER){

            }
        }
    }

    public void sendButtonEvent(Event event) {
        System.out.println(chatSendBox.getText());
        chatSendBox.setText("");
    }
}
