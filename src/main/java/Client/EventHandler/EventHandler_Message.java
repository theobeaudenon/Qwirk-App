package Client.EventHandler;

import Client.Singleton.Singleton_ClientSocket;
import Objet.Message.Message;
import com.jfoenix.controls.JFXListView;
import io.socket.client.Ack;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Boufle on 26/04/2017.
 */
public class EventHandler_Message {

    public static void loadHistory_Message(BorderPane centerPan, int chanId){

        final JFXListView<Label> list = new JFXListView<>();

        final ArrayList<Message> myMessageArrayList = new ArrayList<Message>();

        Singleton_ClientSocket.getInstance().socket.emit("channelMessages", chanId, new Ack() {
            public void call(final Object... args) {
                if(args[0] != null){

                    JSONArray array = (JSONArray) args[0];



                    for (int i = 0; i < array.length(); i++) {
                        try {

                            JSONObject jsonObject = array.getJSONObject(i);
                            myMessageArrayList.add(new Message(jsonObject.toString()));

                        } catch (JSONException e) {

                        }
                    }


                    Platform.runLater(new Runnable() {
                        public void run() {
                            for (Message message : myMessageArrayList) {
                                list.getItems().add(new Label(message.getMessage()));
                            }

                        }
                    });

                }else{
                    System.out.printf("Erreur mdp/User");
                    //error.setVisible(true);
                }
            }
        });

        // list.getStyleClass().add("mylistview");
        list.setMaxHeight(3400);


        StackPane container = new StackPane(list);
        // container.setPadding(new Insets(24));

        ScrollPane pane = new ScrollPane();
        pane.setContent(container);

        pane.setFitToWidth(true);


        centerPan.setCenter(pane);
    }
}
