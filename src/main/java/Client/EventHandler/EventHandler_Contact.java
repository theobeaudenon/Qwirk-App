package Client.EventHandler;

import Client.Component.Component_Label_Contact;
import Client.Component.Component_Label_Group;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Channel.Channel;
import Objet.Contacts.Contact;
import Objet.Message.Message;
import Objet.User.User;
import Objet.Utils.Action;
import com.jfoenix.controls.JFXListView;
import io.socket.client.Ack;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

/**
 * Created by Boufle on 17/05/2017.
 */
public class EventHandler_Contact {

    public static void loadContact_Contact(final JFXListView userContactList){
        Platform.runLater(new Runnable() {
            public void run() {
                for (User user : Singleton_UserInfo.getInstance().getContactList()) {

                    Component_Label_Contact label = new Component_Label_Contact();
                    label.setUser(user);
                    label.setText(user.getUserName());
                    //label.setName(user.getUserName());
                    userContactList.getItems().add(label);
                   /* userContactList.getItems().add(user.getUserName());*/
                }
            }
        });
    }

    public static void delete_Contact(){

    }

    public static void updateContact(final JFXListView userContactList){
        Singleton_ClientSocket.getInstance().socket.on("contactupdate", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final JSONObject obj = (JSONObject)args[0];
                Platform.runLater(new Runnable() {
                    public void run() {
                        Contact contact = new Contact(obj.toString());
                        if (contact.getAction() == Action.AJOUTER){
                            int idContact = 0;
                            if (contact.getIdUser1().equals(Singleton_UserInfo.getInstance().getUser().getUserID())){
                                idContact = contact.getIdUser2();
                            }
                            else if(contact.getIdUser2().equals(Singleton_UserInfo.getInstance().getUser().getUserID())){
                                idContact = contact.getIdUser1();
                            }
                            Singleton_ClientSocket.getInstance().socket.emit("getUserID", idContact, new Ack() {
                                public void call(final Object... args) {
                                    Platform.runLater(new Runnable() {
                                        public void run() {
                                            final JSONObject obj = (JSONObject)args[0];
                                            User user = new User(obj.toString());
                                            Component_Label_Contact label = new Component_Label_Contact();
                                            label.setUser(user);
                                            label.setText(user.getUserName());
                                            userContactList.getItems().add(label);
                                        }
                                    });
                                }
                            });
                        }
                        else if(contact.getAction() == Action.SUPPRIMER){
                            List<Object> nodesToRemove = new ArrayList<>();
                            if(contact.getIdUser1().equals(Singleton_UserInfo.getInstance().getUser().getUserID())){
                                userContactList.getItems().stream().filter(component_label_contact -> ((Component_Label_Contact) component_label_contact).getUser().getUserID().equals(contact.getIdUser2())).forEach(nodesToRemove::add);
                            }
                            else if(contact.getIdUser2().equals(Singleton_UserInfo.getInstance().getUser().getUserID())){
                                userContactList.getItems().stream().filter(component_label_contact -> ((Component_Label_Contact) component_label_contact).getUser().getUserID().equals(contact.getIdUser1())).forEach(nodesToRemove::add);
                            }
                            userContactList.getItems().removeAll(nodesToRemove);
                        }
                    }
                });
            }
        });
    }
}
