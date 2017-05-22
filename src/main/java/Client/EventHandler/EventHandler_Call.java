package Client.EventHandler;

import Client.Controller.Controller_WindowMain;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Alerte.Alerte;
import Objet.Alerte.Call;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;
import org.json.JSONObject;

/**
 * Created by Boufle on 26/04/2017.
 */
public class EventHandler_Call {


    public static void incommingCall(final Controller_WindowMain controller_windowMain){
        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(controller_windowMain.getPrincipalPane() , content , JFXDialog.DialogTransition.CENTER );



        Singleton_ClientSocket.getInstance().socket.on("incommingCall", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final JSONObject obj = (JSONObject)args[0];
                Call alerte = new Call(obj.toString());
                if(Singleton_UserInfo.getInstance().getUser().getUserID().equals(alerte.getCalled())){
                    Platform.runLater(new Runnable() {
                        public void run() {
                            content.setHeading(new Text("Appel Entrant"));

                            content.setBody(new Text("Vous avez un appel de "+alerte.getUserCaller().getUserName()));

                            JFXButton button = new JFXButton("Refuser");
                            button.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    Singleton_ClientSocket.getInstance().socket.emit("deniedCall", alerte.toJson());

                                    dialog.close();
                                }
                            });

                            JFXButton buttonapcepter = new JFXButton("Apcepter");
                            buttonapcepter.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    Singleton_ClientSocket.getInstance().socket.emit("acceptedCall", alerte.toJson());

                                    dialog.close();

                                }
                            });

                            content.setActions(button,buttonapcepter);
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

                if(Singleton_UserInfo.getInstance().getUser().getUserID().equals(alerte.getCaller())){
                    Platform.runLater(new Runnable() {
                        public void run() {

                             content.setHeading(new Text("Appel en cours"));
                            content.setBody(new Text("Vous etes en train d'appeler "+alerte.getUserCalled().getUserName()));


                           // content.setActions(button,buttonapcepter);
                            dialog.show();
                        }
                    });
                }
            }
        });


        Singleton_ClientSocket.getInstance().socket.on("deniedCallBack", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final JSONObject obj = (JSONObject) args[0];
                Call alerte = new Call(obj.toString());
                if( Singleton_UserInfo.getInstance().getUser().getUserID().equals(alerte.getCaller())){

                    Platform.runLater(new Runnable() {
                        public void run() {
                            dialog.close();

                        }
                    });

                }
            }
        });

        Singleton_ClientSocket.getInstance().socket.on("callInboud", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                byte[] obj = (byte[]) args[0];


            }
        });



        Singleton_ClientSocket.getInstance().socket.on("callprosses", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final JSONObject obj = (JSONObject) args[0];
                Call alerte = new Call(obj.toString());
                if(Singleton_UserInfo.getInstance().getUser().getUserID().equals(alerte.getCalled()) || Singleton_UserInfo.getInstance().getUser().getUserID().equals(alerte.getCaller())){

                    Platform.runLater(new Runnable() {
                        public void run() {
                            Singleton_UserInfo.getInstance().setCall(alerte);
                            Singleton_UserInfo.getInstance().setInCall(true);

                            dialog.close();



                          //  content.setHeading(new Text("Appel OK"));
                           // content.setBody(new Text(" "));

                            // content.setActions(button,buttonapcepter);
                            //dialog.show();

                        }
                    });

                }
            }
        });

    }

    public static void callprosses(final Controller_WindowMain controller_windowMain){




    }


    public static void deniedCallBack(final Controller_WindowMain controller_windowMain){

    }



}
