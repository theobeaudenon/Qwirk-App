package Client.Controller;/**
 * Created by Boufle on 24/04/2017.
 */

import Client.Component.Component_Label_Contact;
import Client.Component.Component_Label_Group;
import Client.EventHandler.*;
import Client.EventHandler.ContextMenu.EventHander_ContextMenu_Contact_DeleteAction;
import Client.EventHandler.ContextMenu.EventHandler_ContextMenu_Contact;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Channel.Channel;
import Objet.Channel.ChannelOpperation;
import Objet.Contacts.Contact;
import Objet.Message.Message;
import Objet.Utils.Action;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.svg.SVGGlyph;
import io.socket.client.Ack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
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

    MenuItem delectContactAction = new MenuItem("Supprimer");

    ContextMenu contactContextMenu = new ContextMenu();

    @FXML
    private JFXButton addContact;

    @FXML
    private JFXTextField addUserTextBox;

    @FXML
    private JFXButton chanAddButton;

    @FXML
    private JFXTextField chanAddText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        list.setFocusTraversable(false);
        list.setId("Messagelist");

       // userContactList.getItems().add(new Label("test"));

        homeDisplay();
        EventHandler_Home.loadPublicChan_Home(homeChan);
        EventHandler_UserChan.loadUserChan_UserChan(chanelPan);
        EventHandler_Message.updateMessage(list);
        EventHandler_Contact.loadContact_Contact(userContactList);

        delectContactAction.setOnAction(new EventHander_ContextMenu_Contact_DeleteAction(userContactList));
        contactContextMenu.getItems().add(delectContactAction);
        userContactList.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler_ContextMenu_Contact(userContactList, contactContextMenu));

        SVGGlyph plus = new SVGGlyph(0,
                "FULLSCREEN",
                "M357,204H204v153h-51V204H0v-51h153V0h51v153h153V204z",
                Color.web("#04fd00"));
        plus.setSize(20, 16);
        SVGGlyph plus2 = new SVGGlyph(0,
                "FULLSCREEN",
                "M357,204H204v153h-51V204H0v-51h153V0h51v153h153V204z",
                 Color.web("#04fd00"));
        plus2.setSize(20, 16);
        addContact.setGraphic(plus);
        addContact.setRipplerFill(Color.GREENYELLOW);

        chanAddButton.setGraphic(plus2);
        chanAddButton.setRipplerFill(Color.GREENYELLOW);


    }

    public void userChanClicked(Event event) {

        if (chanelPan.getSelectionModel().getSelectedItem() != null) {
            channel = ((Component_Label_Group) chanelPan.getSelectionModel().getSelectedItem()).getChannel();
            if (channel != null) {
                list.getItems().clear();
                showGroupChat();
                EventHandler_Message.loadHistory_Message(centerPan, channel.getIdChannel(), list);
            }
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

    public void addUserAction(Event event) {
        if (!addUserTextBox.getText().equals("")) {
            String contactMail = addUserTextBox.getText();
            Contact contact = new Contact(Singleton_UserInfo.getInstance().getUser().getUserID(), null, Action.AJOUTER, contactMail);
            Singleton_ClientSocket.getInstance().socket.emit("oppContacts", contact);
            addUserTextBox.setText("");
        }
    }

    public void chanAddAction(Event event) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (!chanAddText.getText().equals("")){
            String chanName = chanAddText.getText();
            Channel channel = new Channel(null, chanName, "", (int)timestamp.getTime(), Singleton_UserInfo.getInstance().getUser().getUserID(), false);
            ChannelOpperation channelOpperation = new ChannelOpperation(null, channel, Action.AJOUTER);
            Singleton_ClientSocket.getInstance().socket.emit("channelOpperation", channelOpperation.toJson(), new Ack() {
                public void call(final Object... args) {
                  //  if ((boolean)args[0]){
                        EventHandler_UserChan.loadUserChan_UserChan(chanelPan);
                   // }
                }
            });
            chanAddText.setText("");
        }
    }
}
