package Client.EventHandler;

import Client.Component.Component_Label_Contact;
import Client.Singleton.Singleton_UserInfo;
import Objet.Channel.Channel;
import Objet.User.User;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.scene.control.Label;

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
                   // label.setText(user.getUserName());
                    label.setName(user.getUserName());
                    userContactList.getItems().add(label);
                   /* userContactList.getItems().add(user.getUserName());*/
                }
            }
        });
    }
}
