package Client.EventHandler;

import Client.Controller.Controller_WindowMain;
import Client.Messages.Bubble.BubbleSpec;
import Client.Messages.Bubble.BubbledLabel;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Alerte.Alerte;
import Objet.Message.Message;
import Objet.User.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import io.socket.client.Ack;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Boufle on 26/04/2017.
 */
public class EventHandler_Alerte {


    public static void updateMessage(final Controller_WindowMain controller_windowMain){
        Singleton_ClientSocket.getInstance().socket.on("alerte", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final JSONObject obj = (JSONObject)args[0];
                Alerte alerte = new Alerte(obj.toString());
                 Platform.runLater(new Runnable() {
                    public void run() {

                        JFXDialogLayout content = new JFXDialogLayout();
                        content.setHeading(new Text(alerte.getHead()));
                        content.setBody(new Text(alerte.getMessage()));
                        JFXDialog dialog = new JFXDialog(controller_windowMain.getPrincipalPane() , content , JFXDialog.DialogTransition.CENTER );
                        JFXButton button = new JFXButton("Fermer");
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                dialog.close();
                            }
                        });
                        content.setActions(button);
                        dialog.show();

                        /*final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                       // dialog.initOwner(Controller_WindowMain);
                        VBox dialogVbox = new VBox(20);
                        dialogVbox.getChildren().add(new Text(message));
                        Scene dialogScene = new Scene(dialogVbox, 300, 200);
                        dialog.setScene(dialogScene);
                        dialog.show();*/
                    }
                });
            }
        });
    }

}
