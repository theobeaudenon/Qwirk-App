package Client.EventHandler.ContextMenu;

import Client.Component.Component_Label_Contact;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Contacts.Contact;
import Objet.Utils.Action;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;

/**
 * Created by Boufle on 18/05/2017.
 */
public class EventHander_ContextMenu_Contact_DeleteAction implements EventHandler<ActionEvent> {

    private ListView listView;

    public EventHander_ContextMenu_Contact_DeleteAction(ListView listView){
        this.listView = listView;
    }

    @Override
    public void handle(ActionEvent event) {
        int userID = Singleton_UserInfo.getInstance().getUser().getUserID();
        Component_Label_Contact contactID = (Component_Label_Contact)listView.getSelectionModel().getSelectedItem();
        Contact contact = new Contact(userID ,contactID.getUser().getUserID(), Action.SUPPRIMER);
        Singleton_ClientSocket.getInstance().socket.emit("oppContacts", contact);
        //déselectionner la liste
    }
}
