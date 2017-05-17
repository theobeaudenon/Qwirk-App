package Client.Controller;/**
 * Created by Boufle on 24/04/2017.
 */

import Client.Component.Component_Label_Contact;
import Client.Component.Component_Label_Group;
import Client.EventHandler.EventHandler_Contact;
import Client.EventHandler.EventHandler_Home;
import Client.EventHandler.EventHandler_Message;
import Client.EventHandler.EventHandler_UserChan;
import Client.Messages.Bubble.BubbleSpec;
import Client.Messages.Bubble.BubbledLabel;
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
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
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

    @FXML
    private JFXListView userContactList;

    private String nameGroupChat;

    private Channel channel;

    ListView list = new ListView();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        list.setFocusTraversable(false);
        list.setId("Messagelist");

       // userContactList.getItems().add(new Label("test"));

        homeDisplay();
        EventHandler_Home.loadPublicChan_Home(homeChan);
        EventHandler_UserChan.loadUserChan_UserChan(chanelPan);
        EventHandler_Message.updateMessage(list);

/*        userContactList.setCellFactory(lv -> {

            ListCell<Component_Label_Contact> cell = new ListCell<>();

            ContextMenu contextMenu = new ContextMenu();

            MenuItem editItem = new MenuItem();
            editItem.textProperty().bind(Bindings.format("Edit \"%s\"", cell.itemProperty()));
            editItem.setOnAction(event -> {
                Component_Label_Contact item = cell.getItem();
                // code to edit item...
            });
            MenuItem deleteItem = new MenuItem();
            deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty().get().nameProperty()));
            deleteItem.setOnAction(event -> userContactList.getItems().remove(cell.getItem()));
            contextMenu.getItems().addAll(editItem, deleteItem);

            cell.textProperty().bind(cell.itemProperty().get().nameProperty());
            cell.setTextFill(Color.WHITE);
            cell.setStyle("-fx-background-color: #3c3f41 ;  -fx-padding: 10 ; -fx-background-insets: 0 ;");


            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell ;
        });

        EventHandler_Contact.loadContact_Contact(userContactList);*/

    }

    public void userChanClicked(Event event) {

        channel = ((Component_Label_Group)chanelPan.getSelectionModel().getSelectedItem()).getChannel();
        if (channel != null){
            list.getItems().clear();
            showGroupChat();
            EventHandler_Message.loadHistory_Message(centerPan, channel.getIdChannel(), list);
        }

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
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        //Envoi du message
        Message message = new Message(Singleton_UserInfo.getInstance().getUser().getUserName(), chatSendBox.getText(), new Integer((int) timestamp.getTime()), channel.getIdChannel(), Singleton_UserInfo.getInstance().getUser().getUserID(),false,null,null);
        Singleton_ClientSocket.getInstance().socket.emit("chatevent", message.toJson());

        chatSendBox.setText("");

    }

    public void userContactClick(Event event) {
        Parent root;
        try {
            root  = FXMLLoader.load(getClass().getResource("/soloChat.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 600, 600));
            stage.show();
            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
