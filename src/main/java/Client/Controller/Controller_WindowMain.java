package Client.Controller;/**
 * Created by Boufle on 24/04/2017.
 */

import Client.Component.Component_Label_Group;
import Client.EventHandler.EventHandler_Home;
import Client.EventHandler.EventHandler_Message;
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


        EventHandler_Home.loadPublicChan_Home(homeChan);
        EventHandler_UserChan.loadUserChan_UserChan(chanelPan);

    }

    public void userChanClicked(Event event) {

        Channel channel = ((Component_Label_Group)chanelPan.getSelectionModel().getSelectedItem()).getChannel();
        showGroupChat();
        EventHandler_Message.loadHistory_Message(centerPan, channel.getIdChannel());
    }

    public void showGroupChat(){

        homeChan.setVisible(false);
        homeChan.setManaged(false);
        chatGroupBanner.setVisible(true);
        chatGroupBanner.setManaged(true);
        controllerChat.setVisible(true);
        controllerChat.setManaged(true);

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
