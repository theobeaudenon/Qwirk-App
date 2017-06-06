package Client.Controller;/**
 * Created by Boufle on 24/04/2017.
 */

import Client.Component.Component_Label_Group;
import Client.EventHandler.*;
import Client.EventHandler.ContextMenu.EventHander_ContextMenu_Contact_DeleteAction;
import Client.EventHandler.ContextMenu.EventHandler_ContextMenu_Contact;
import Client.EventHandler.DragAndDrop.MouseDragDropped;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Channel.Channel;
import Objet.Channel.ChannelOpperation;
import Objet.Contacts.Contact;
import Objet.Message.Message;
import Objet.Utils.Action;
import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;
import io.socket.client.Ack;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class Controller_WindowMain implements Initializable {

    @FXML
    private JFXListView chanelPan;

    @FXML
    private StackPane principalPane;

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

    @FXML
    private JFXRadioButton isPrivate;

    private String nameGroupChat;

    private Channel channel;

    ListView list = new ListView();

    MenuItem delectContactAction = new MenuItem("Supprimer");
    MenuItem addToChanAction = new MenuItem("Ajouter au channel");


    ContextMenu contactContextMenu = new ContextMenu();

    @FXML
    private JFXButton addContact;

    @FXML
    private JFXTextField addUserTextBox;

    @FXML
    private JFXButton chanAddButton;

    @FXML
    private JFXTextField chanAddText;

    @FXML
    private JFXButton optionButton;

    @FXML
    private JFXButton deconectButton;

    @FXML
    private ImageView imageProfil;

    @FXML
    private Label nameProfil;

    final KeyCombination kb = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN);

    private JFXSnackbar messageNotifOk;
    private JFXSnackbar messageNotifError;



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        nameProfil.setText(Singleton_UserInfo.getInstance().getUser().getUserName());

        list.setFocusTraversable(false);
        list.setId("Messagelist");

       // userContactList.getItems().add(new Label("test"));

        messageNotifOk =  new JFXSnackbar(principalPane);
        messageNotifError = new JFXSnackbar(principalPane);

        final String cssUrl1 = getClass().getResource("/style/notifOK.css").toExternalForm();
        final String cssUrl2 = getClass().getResource("/style/notifError.css").toExternalForm();
        messageNotifOk.getStylesheets().add(cssUrl1);
        messageNotifError.getStylesheets().add(cssUrl2);

        homeDisplay();
        EventHandler_Action.updateAction(chanelPan, this);
        EventHandler_Home.loadPublicChan_Home(homeChan);
        EventHandler_UserChan.loadUserChan_UserChan(chanelPan);
        EventHandler_Message.updateMessage(list, chanelPan);
        EventHandler_Contact.loadContact_Contact(userContactList);
        EventHandler_Alerte.updateMessage(this);
        EventHandler_Call.incommingCall(this);
        EventHandler_Call.callprosses(this);
        EventHandler_Contact.updateContact(userContactList);
        EventHandler_UserChan.updateChanel(chanelPan);


        addToChanAction.setOnAction(new EventHander_ContextMenu_Group_JoinAction(chanelPan, userContactList));
        contactContextMenu.getItems().add(addToChanAction);


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


        SVGGlyph option = new SVGGlyph(0,
                "FULLSCREEN",
                "M18.622,8.371l-0.545-1.295c0,0,1.268-2.861,1.156-2.971l-1.679-1.639c-0.116-0.113-2.978,1.193-2.978,1.193l-1.32-0.533\n" +
                        "        c0,0-1.166-2.9-1.326-2.9H9.561c-0.165,0-1.244,2.906-1.244,2.906L6.999,3.667c0,0-2.922-1.242-3.034-1.131L2.289,4.177\n" +
                        "        C2.173,4.29,3.507,7.093,3.507,7.093L2.962,8.386c0,0-2.962,1.141-2.962,1.295v2.322c0,0.162,2.969,1.219,2.969,1.219l0.545,1.291\n" +
                        "        c0,0-1.268,2.859-1.157,2.969l1.678,1.643c0.114,0.111,2.977-1.195,2.977-1.195l1.321,0.535c0,0,1.166,2.898,1.327,2.898h2.369\n" +
                        "        c0.164,0,1.244-2.906,1.244-2.906l1.322-0.535c0,0,2.916,1.242,3.029,1.133l1.678-1.641c0.117-0.115-1.22-2.916-1.22-2.916\n" +
                        "        l0.544-1.293c0,0,2.963-1.143,2.963-1.299v-2.32C21.59,9.425,18.622,8.371,18.622,8.371z M14.256,10.794\n" +
                        "        c0,1.867-1.553,3.387-3.461,3.387c-1.906,0-3.461-1.52-3.461-3.387s1.555-3.385,3.461-3.385\n" +
                        "        C12.704,7.41,14.256,8.927,14.256,10.794z",
                Color.web("#000"));
        option.setSize(26, 26);
        optionButton.setGraphic(option);
        optionButton.setRipplerFill(Color.GRAY);

        SVGGlyph deco = new SVGGlyph(0,
                "FULLSCREEN",
                "M51.213,180h173.785c8.284,0,15-6.716,15-15s-6.716-15-15-15H51.213l19.394-19.393         c5.858-5.857,5.858-15.355,0-21.213c-5.856-5.858-15.354-5.858-21.213,0L4.397,154.391c-0.348,0.347-0.676,0.71-0.988,1.09         c-0.076,0.093-0.141,0.193-0.215,0.288c-0.229,0.291-0.454,0.583-0.66,0.891c-0.06,0.09-0.109,0.185-0.168,0.276         c-0.206,0.322-0.408,0.647-0.59,0.986c-0.035,0.067-0.064,0.138-0.099,0.205c-0.189,0.367-0.371,0.739-0.53,1.123         c-0.02,0.047-0.034,0.097-0.053,0.145c-0.163,0.404-0.314,0.813-0.442,1.234c-0.017,0.053-0.026,0.108-0.041,0.162         c-0.121,0.413-0.232,0.83-0.317,1.257c-0.025,0.127-0.036,0.258-0.059,0.386c-0.062,0.354-0.124,0.708-0.159,1.069         C0.025,163.998,0,164.498,0,165s0.025,1.002,0.076,1.498c0.035,0.366,0.099,0.723,0.16,1.08c0.022,0.124,0.033,0.251,0.058,0.374         c0.086,0.431,0.196,0.852,0.318,1.269c0.015,0.049,0.024,0.101,0.039,0.15c0.129,0.423,0.28,0.836,0.445,1.244         c0.018,0.044,0.031,0.091,0.05,0.135c0.16,0.387,0.343,0.761,0.534,1.13c0.033,0.065,0.061,0.133,0.095,0.198         c0.184,0.341,0.387,0.669,0.596,0.994c0.056,0.088,0.104,0.181,0.162,0.267c0.207,0.309,0.434,0.603,0.662,0.895         c0.073,0.094,0.138,0.193,0.213,0.285c0.313,0.379,0.641,0.743,0.988,1.09l44.997,44.997C52.322,223.536,56.161,225,60,225         s7.678-1.464,10.606-4.394c5.858-5.858,5.858-15.355,0-21.213L51.213,180z M207.299,42.299c-40.944,0-79.038,20.312-101.903,54.333c-4.62,6.875-2.792,16.195,4.083,20.816\n" +
                        "        c6.876,4.62,16.195,2.794,20.817-4.083c17.281-25.715,46.067-41.067,77.003-41.067C258.414,72.299,300,113.884,300,165\n" +
                        "        s-41.586,92.701-92.701,92.701c-30.845,0-59.584-15.283-76.878-40.881c-4.639-6.865-13.961-8.669-20.827-4.032\n" +
                        "        c-6.864,4.638-8.67,13.962-4.032,20.826c22.881,33.868,60.913,54.087,101.737,54.087C274.956,287.701,330,232.658,330,165\n" +
                        "        S274.956,42.299,207.299,42.299z" ,
                Color.web("#000"));
        deco.setSize(26, 26);
        deconectButton.setGraphic(deco);
        deconectButton.setRipplerFill(Color.GRAY);

        chatSendBox.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (kb.match(ke)){
                    chatSendBox.appendText("\n");
                }
                else if (ke.getCode().equals(KeyCode.ENTER)) {
                    sendButtonEvent();
                    ke.consume(); // necessary to prevent event handlers for this event
                }
            }
        });

        chatSendBox.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.COPY);
            }
        });

        chatSendBox.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (MouseDragDropped.isFileOk(event)){

                    final boolean isImage = event.getDragboard().getFiles().get(0).getName().toLowerCase().endsWith(".png")
                            || event.getDragboard().getFiles().get(0).getName().toLowerCase().endsWith(".jpg");

                    System.out.println(MouseDragDropped.getFile(event));
                    messageNotifOk.show("Le fichier : " +MouseDragDropped.getFile(event) + " a bien été ajouter", 3000);

                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                    String file = event.getDragboard().getFiles().get(0).getAbsolutePath();
                    Path path = Paths.get(file);
                    byte[] data = null;
                    try {
                        data = Files.readAllBytes(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Envoi du message
                    Message message = new Message(Singleton_UserInfo.getInstance().getUser().getUserName(), null, new Integer((int) timestamp.getTime()), channel.getIdChannel(), Singleton_UserInfo.getInstance().getUser().getUserID(),isImage,data,event.getDragboard().getFiles().get(0).getName());
                    Singleton_ClientSocket.getInstance().socket.emit("chatevent", message.toJson());

                    chatSendBox.setText("");
                }
                else {
                    messageNotifError.show("Le fichier : " +MouseDragDropped.getFile(event) + " n'a le bon format", 3000);
                }
            }
        });


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
        list.setVisible(true);
        list.setManaged(true);

    }

    public void homeDisplay(){

        homeChan.setVisible(true);
        homeChan.setManaged(true);
        chatGroupBanner.setVisible(false);
        chatGroupBanner.setManaged(false);
        controllerChat.setVisible(false);
        controllerChat.setManaged(false);
        list.setVisible(false);
        list.setManaged(false);
    }

    public void sendButtonEvent() {
        System.out.println(chatSendBox.getText());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        //Envoi du message
        Message message = new Message(Singleton_UserInfo.getInstance().getUser().getUserName(), chatSendBox.getText(), new Integer((int) timestamp.getTime()), channel.getIdChannel(), Singleton_UserInfo.getInstance().getUser().getUserID(),false,null,null);
        Singleton_ClientSocket.getInstance().socket.emit("chatevent", message.toJson());

        chatSendBox.setText("");

    }

    public void addUserAction(Event event) {
        if (!addUserTextBox.getText().equals("")) {
            String contactMail = addUserTextBox.getText();
            Contact contact = new Contact(null,Singleton_UserInfo.getInstance().getUser().getUserID() , Action.AJOUTER, contactMail);
            Singleton_ClientSocket.getInstance().socket.emit("oppContacts", contact.toJson());
            addUserTextBox.setText("");
        }
    }

    public void chanAddAction(Event event) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        boolean isPrivateChan = isPrivate.isSelected();
        if (!chanAddText.getText().equals("")){
            String chanName = chanAddText.getText();
            Channel channel = new Channel(null, chanName, "", (int)timestamp.getTime(), Singleton_UserInfo.getInstance().getUser().getUserID(), isPrivateChan);
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

    public StackPane getPrincipalPane() {
        return principalPane;
    }

    public void optionAction(Event event) {
        Parent root;
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/config.fxml"));
            root  = loader.load();
            stage.setTitle("Configuration");
            stage.setScene(new Scene(root, 600, 400));
           // stage.setResizable(false);

           /* Controller_WindowSoloChat controller = loader.getController();
            stage.setOnHidden(e -> controller.shutdown());
*/
            stage.show();


            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deconectAction(Event event) {

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
