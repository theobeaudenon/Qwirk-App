package Client.EventHandler;

import Client.Component.Component_Label_Contact;
import Client.Component.Component_Label_Group;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Channel.Channel;
import Objet.Contacts.Contact;
import Objet.LinkObjects.UserChannels;
import Objet.User.User;
import Objet.Utils.Action;
import com.jfoenix.controls.JFXListView;
import io.socket.client.Ack;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boufle on 26/04/2017.
 */
public class EventHandler_UserChan {

   public static void loadUserChan_UserChan(final JFXListView chanelPan){

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
                           chanelPan.getItems().clear();
                           for (Channel channel : myChannelArrayList) {
                               Component_Label_Group label = new Component_Label_Group();
                               label.setChannel(channel);
                               label.setText(channel.getChannelName());
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

    public static void updateChanel(final JFXListView chanelPan){
        Singleton_ClientSocket.getInstance().socket.on("inviteContactChannel", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final JSONObject obj = (JSONObject)args[0];
                Platform.runLater(new Runnable() {
                    public void run() {
                        chanelPan.getItems().removeAll();
                        loadUserChan_UserChan(chanelPan);
                    }
                });
            }
        });
    }
}
