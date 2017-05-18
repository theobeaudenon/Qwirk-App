package Client.Controller;

import Client.Component.Component_Label_Contact;
import Client.Component.Component_Label_Group;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Contacts.Contact;
import Objet.LinkObjects.UserChannels;
import Objet.Utils.Action;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;

/**
 * Created by Boufle on 18/05/2017.
 */
public class EventHander_ContextMenu_Group_JoinAction implements EventHandler<ActionEvent> {

    JFXListView chanelpan;
    ListView userList;

    public EventHander_ContextMenu_Group_JoinAction(JFXListView chanelpan, ListView userContactList) {
        this.userList = userContactList;
        this.chanelpan = chanelpan;
    }

    @Override
    public void handle(ActionEvent event) {
        Component_Label_Contact contactID = (Component_Label_Contact)userList.getSelectionModel().getSelectedItem();
        Component_Label_Group groupID = (Component_Label_Group)chanelpan.getSelectionModel().getSelectedItem();
        UserChannels userChannels = new UserChannels(contactID.getUser().getUserID(), groupID.getChannel().getIdChannel());
        Singleton_ClientSocket.getInstance().socket.emit("inviteChannel", userChannels.toJson());
    }
}
