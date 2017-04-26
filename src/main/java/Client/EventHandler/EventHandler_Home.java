package Client.EventHandler;

import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Channel.Channel;
import com.jfoenix.controls.JFXMasonryPane;
import io.socket.client.Ack;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Boufle on 26/04/2017.
 */
public class EventHandler_Home {

    public static void loadPublicChan_Home(final JFXMasonryPane homeChan){
        final ArrayList<Channel> channelArrayList = new ArrayList<Channel>();

        Singleton_ClientSocket.getInstance().socket.emit("getPublicChannels", Singleton_UserInfo.getInstance().getUser().getUserID(),new Ack() {
            public void call(final Object... args) {
                if(args[0] != null){
                    JSONArray array = (JSONArray) args[0];

                    for (int i = 0; i < array.length(); i++) {
                        try {
                            JSONObject jsonObject = array.getJSONObject(i);
                            channelArrayList.add(new Channel(jsonObject.toString()));

                        } catch (JSONException e) {

                        }
                    }

                    Platform.runLater(new Runnable() {
                        public void run() {
                            for (Channel channel : channelArrayList) {
                                Label l = new Label();
                                l.setPrefSize(340,100);
                                l.setStyle("-fx-background-image: url(http://www.sosiphone.com/blogiphone/wp-content/uploads//2011/02/Pac-Man-banniere.jpg)");
                                l.setText(channel.getChannelName());
                                homeChan.getChildren().add(l);
                            }
                        }
                    });


                }else{
                    //System.out.printf("Erreur mdp/User");
                    //error.setVisible(true);
                }
            }
        });
    }
}
