package Client.EventHandler;

import Client.Component.Component_Label_Contact;
import Client.Component.Component_Label_Group;
import Client.Controller.Controller_WindowMain;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.LinkObjects.BanChannel;
import Objet.Message.Message;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boufle on 06/06/2017.
 */
public class EventHandler_Action {

    public static void updateAction(JFXListView chanelPan, final Controller_WindowMain controller_windowMain){
        Singleton_ClientSocket.getInstance().socket.on("banKickEvent", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final JSONObject obj = (JSONObject)args[0];
                Platform.runLater(new Runnable() {
                    public void run() {

                        BanChannel banChan = new BanChannel(obj.toString());

                        List<Object> nodesToRemove = new ArrayList<>();

                        if(banChan.type.equals("ban")){
                            if(banChan.banID == Singleton_UserInfo.getInstance().getUser().getUserID()){
                                chanelPan.getItems().stream().filter(component_label_group -> ((Component_Label_Group) component_label_group).getChannel().getIdChannel().equals(banChan.channelID)).forEach(nodesToRemove::add);
                            }
                            if(nodesToRemove.size() > 0){
                                //affiche popup
                                JFXDialogLayout content = new JFXDialogLayout();
                                content.setHeading(new Text("Ban"));
                                content.setBody(new Text("Vous avez été bannie du chan "+banChan.channelID));
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

                            }
                            chanelPan.getItems().removeAll(nodesToRemove);
                            controller_windowMain.homeDisplay();
                        }else if(banChan.type.equals("kick")){
                            if(banChan.banID == Singleton_UserInfo.getInstance().getUser().getUserID()){
                                chanelPan.getItems().stream().filter(component_label_group -> ((Component_Label_Group) component_label_group).getChannel().getIdChannel().equals(banChan.channelID)).forEach(nodesToRemove::add);
                            }
                            if(nodesToRemove.size() > 0){
                                //affiche popup
                                JFXDialogLayout content = new JFXDialogLayout();
                                content.setHeading(new Text("Ban"));
                                content.setBody(new Text("Vous avez été Kick du chan "+banChan.channelID));
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

                            }
                            chanelPan.getItems().removeAll(nodesToRemove);
                            controller_windowMain.homeDisplay();
                        }

                    }
                });
            }
        });
    }
}
