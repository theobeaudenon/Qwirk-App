package Client.EventHandler;

import Client.Messages.Bubble.BubbleSpec;
import Client.Messages.Bubble.BubbledLabel;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Message.Message;
import com.jfoenix.controls.JFXListView;
import io.socket.client.Ack;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Boufle on 26/04/2017.
 */
public class EventHandler_Message {

    public static void loadHistory_Message(BorderPane centerPan, int chanId){

        final ListView list = new ListView();

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

                    list.setFocusTraversable(false);
                    list.setId("Messagelist");
                    list.getScene().getStylesheets().add("/style/listViewChat.css");
                    Platform.runLater(new Runnable() {
                        public void run() {
                            for (Message message : myMessageArrayList) {

                                if (message.getUser() == Singleton_UserInfo.getInstance().getUser().getUserID()){
                                    BubbledLabel bl6 = new BubbledLabel();
                                    bl6.setText( message.getUser() + ": " + message.getMessage());
                                    bl6.setBackground(new Background(new BackgroundFill(Color.web("#fac"),null, null)));
                                    bl6.setTextFill(Color.web("#FFF"));
                                    bl6.setBubbleSpec(BubbleSpec.FACE_RIGHT_CENTER);
                                    HBox hBox = new HBox();
                                    hBox.getChildren().addAll(bl6);
                                    list.getItems().add(hBox);
                                }
                                else {
                                    BubbledLabel bl6 = new BubbledLabel();
                                    bl6.setText( message.getUser() + ": " + message.getMessage());
                                    bl6.setBackground(new Background(new BackgroundFill(Color.web("#f1f0f0"),null, null)));
                                    bl6.setTextFill(Color.web("#000"));
                                    bl6.setBubbleSpec(BubbleSpec.FACE_LEFT_CENTER);
                                    HBox hBox = new HBox();
                                    hBox.getChildren().addAll(bl6);
                                    list.getItems().add(hBox);
                                }

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
       // list.setMaxHeight(3400);


        StackPane container = new StackPane(list);
        // container.setPadding(new Insets(24));

        ScrollPane pane = new ScrollPane();
        pane.setContent(container);

        pane.setFitToWidth(true);


        centerPan.setCenter(pane);
    }
}
