package Client.EventHandler;

import Client.Component.Component_Label_Group;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Channel.Channel;
import com.jfoenix.controls.JFXListView;
import io.socket.client.Ack;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
}
