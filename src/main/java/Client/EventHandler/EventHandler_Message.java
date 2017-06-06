package Client.EventHandler;

import Client.Component.Component_Button_Chat_Fichier;
import Client.Component.Component_Button_Chat_Image;
import Client.Messages.Bubble.BubbleSpec;
import Client.Messages.Bubble.BubbledLabel;
import Client.Messages.Bubble.BubbledLink;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Message.Message;
import com.jfoenix.controls.JFXListView;
import emoji4j.EmojiUtils;
import io.socket.client.Ack;
import io.socket.emitter.Emitter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Boufle on 26/04/2017.
 */
public class EventHandler_Message {
    
    static String ourMessageColorBackground = "#fac";
    static String theirMessageColorBackgroud = "#f1f0f0";
    static String ourMessageColor = "#FFF";
    static String theirMessageColor = "#000";

    public static void loadHistory_Message(BorderPane centerPan, int chanId, final ListView list){

        final ArrayList<Message> myMessageArrayList = new ArrayList<Message>();

        Singleton_ClientSocket.getInstance().socket.emit("channelMessages", chanId, new Ack() {
            public void call(final Object... args) {
                /*if(args[0] != null){

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
                               messageFormate(list, message);
                            }

                        }
                    });

                }else{
                    System.out.printf("Erreur mdp/User");
                    //error.setVisible(true);
                }*/
            }
        });

        // list.getStyleClass().add("mylistview");
        //list.setPrefHeight(800);
        StackPane container = new StackPane(list);
        // container.setPadding(new Insets(24));
        ScrollPane pane = new ScrollPane();
        pane.setContent(container);
        pane.setFitToWidth(true);
        pane.setFitToHeight(true);
        centerPan.setCenter(pane);
    }

    public static void updateMessage(final ListView list){
        Singleton_ClientSocket.getInstance().socket.on("newmessage", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final JSONObject obj = (JSONObject)args[0];
                Platform.runLater(new Runnable() {
                    public void run() {
                        Message message = new Message(obj.toString());
                        messageFormate(list, message);
                        list.refresh();
                       // ScrollPane scrollPane = (ScrollPane) list.getParent().getParent().getParent().getParent();
                        //scrollPane.setVvalue(1.0);
                    }
                });
            }
        });
    }

    private static void messageFormate(ListView list, Message message){

        int matchStart = 0;
        int matchEnd = 0;
        if (message.getData() == null){
            Matcher matcher = urlPattern.matcher(message.getMessage());
            while (matcher.find()) {
                matchStart = matcher.start(1);
                matchEnd = matcher.end();
                // now you have the offsets of a URL match
            }
        }

        // si l'utilisateur envoie un fichier de type image
        if (message.getData() != null && message.getImage()){
            if (message.getUser() == Singleton_UserInfo.getInstance().getUser().getUserID()){
                // x.setMaxWidth(list.getWidth() - 20);
                Component_Button_Chat_Image bl6 = new Component_Button_Chat_Image(message.getData(), message.getDataName());
                bl6.setOnAction(new EventHandler_Message_Fichier());
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.TOP_RIGHT);
                hBox.getChildren().addAll(bl6);
                list.getItems().add(hBox);
            }
            else {
                // x.setMaxWidth(list.getWidth() - 20);
                Component_Button_Chat_Image bl6 = new Component_Button_Chat_Image(message.getData(), message.getDataName());
                bl6.setOnAction(new EventHandler_Message_Fichier());
                HBox hBox = new HBox();
                hBox.getChildren().addAll(bl6);
                list.getItems().add(hBox);
            }
        }

        // si l'utilisateur envoie un fichier de type autre qu'une image

        if (message.getData() != null && !message.getImage()){
            if (message.getUser() == Singleton_UserInfo.getInstance().getUser().getUserID()){
                // x.setMaxWidth(list.getWidth() - 20);
                Component_Button_Chat_Fichier bl6 = new Component_Button_Chat_Fichier();
                bl6.setFile(message.getData());
                bl6.setFileName(message.getDataName());
                bl6.setOnAction(new EventHandler_Message_Fichier());
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.TOP_RIGHT);
                hBox.getChildren().addAll(bl6);
                list.getItems().add(hBox);
            }
            else {
                // x.setMaxWidth(list.getWidth() - 20);
                Component_Button_Chat_Fichier bl6 = new Component_Button_Chat_Fichier();
                bl6.setFile(message.getData());
                bl6.setFileName(message.getDataName());
                bl6.setOnAction(new EventHandler_Message_Fichier());
                HBox hBox = new HBox();
                hBox.getChildren().addAll(bl6);
                list.getItems().add(hBox);
            }
        }

        // si l'utilisateur envoie un lien

        if (matchEnd != 0){
            // si on est le mec qui envoie le msg avec un lien
            if (message.getUser() == Singleton_UserInfo.getInstance().getUser().getUserID()){
                String messageString = message.getMessage();
                String linkString = message.getMessage().substring(matchStart, matchEnd);
                boolean linkIsAnImage = false   ;
                // si le lien est en première position dans le msg
                if (matchStart == 0){
                    BubbledLabel bl6 = new BubbledLabel();
                    bl6.setText( message.getUserName() + " :");
                    bl6.setBackground(new Background(new BackgroundFill(Color.web(ourMessageColorBackground),null, null)));
                    bl6.setTextFill(Color.web(ourMessageColor));

                    BubbledLabel bl7 = new BubbledLabel();
                    bl7.setText("" + EmojiUtils.emojify(messageString.substring(matchEnd, messageString.length())));
                    bl7.setBackground(new Background(new BackgroundFill(Color.web(ourMessageColorBackground),null, null)));
                    bl7.setTextFill(Color.web(ourMessageColor));
                    bl7.setBubbleSpec(BubbleSpec.FACE_RIGHT_CENTER);
                    if (linkIsAnImage){
                        ImageView hyperlink = new ImageView(linkString);
                        HBox hBox = new HBox();
                        hBox.setAlignment(Pos.TOP_RIGHT);
                        hBox.getChildren().addAll(bl6, hyperlink ,bl7);
                        list.getItems().add(hBox);
                    }
                    else {
                        BubbledLink hyperlink = new BubbledLink();
                        hyperlink.setText(linkString);
                        hyperlink.setBackground(new Background(new BackgroundFill(Color.web(ourMessageColorBackground),null, null)));
                        hyperlink.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent t) {
                                try {
                                    Desktop.getDesktop().browse(new URI(hyperlink.getText()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        HBox hBox = new HBox();
                        hBox.setAlignment(Pos.TOP_RIGHT);
                        hBox.getChildren().addAll(bl6, hyperlink ,bl7);
                        list.getItems().add(hBox);
                    }

                }
                // si le lien est au milieu du msg
                else {
                    BubbledLabel bl6 = new BubbledLabel();
                    bl6.setText( message.getUserName() + " : " + EmojiUtils.emojify(messageString.substring(0, matchStart)));
                    bl6.setBackground(new Background(new BackgroundFill(Color.web(ourMessageColorBackground),null, null)));
                    bl6.setTextFill(Color.web(ourMessageColor));

                    BubbledLabel bl7 = new BubbledLabel();
                    bl7.setText("" + EmojiUtils.emojify(messageString.substring(matchEnd, messageString.length())));
                    bl7.setBackground(new Background(new BackgroundFill(Color.web(ourMessageColorBackground),null, null)));
                    bl7.setTextFill(Color.web(ourMessageColor));
                    bl7.setBubbleSpec(BubbleSpec.FACE_RIGHT_CENTER);

                    BubbledLink hyperlink = new BubbledLink();
                    hyperlink.setText(linkString);
                    hyperlink.setBackground(new Background(new BackgroundFill(Color.web(ourMessageColorBackground),null, null)));
                    hyperlink.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent t) {
                            try {
                                Desktop.getDesktop().browse(new URI(hyperlink.getText()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.TOP_RIGHT);
                    hBox.getChildren().addAll(bl6, hyperlink ,bl7);
                    list.getItems().add(hBox);
                }
            }
            // si on recois un message avec un lien 
            else {
                String messageString = message.getMessage();
                String linkString = message.getMessage().substring(matchStart, matchEnd);
                // si le lien est en première position dans le msg
                if (matchStart == 0){
                    BubbledLabel bl6 = new BubbledLabel();
                    bl6.setText( message.getUserName() + " :");
                    bl6.setBackground(new Background(new BackgroundFill(Color.web(theirMessageColorBackgroud),null, null)));
                    bl6.setTextFill(Color.web(theirMessageColor));
                    bl6.setBubbleSpec(BubbleSpec.FACE_LEFT_CENTER);

                    BubbledLabel bl7 = new BubbledLabel();
                    bl7.setText("" + EmojiUtils.emojify(messageString.substring(matchEnd, messageString.length())));
                    bl7.setBackground(new Background(new BackgroundFill(Color.web(theirMessageColorBackgroud),null, null)));
                    bl7.setTextFill(Color.web(theirMessageColor));

                    BubbledLink hyperlink = new BubbledLink();
                    hyperlink.setText(linkString);
                    hyperlink.setBackground(new Background(new BackgroundFill(Color.web(theirMessageColorBackgroud),null, null)));
                    hyperlink.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent t) {
                            try {
                                Desktop.getDesktop().browse(new URI(hyperlink.getText()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    HBox hBox = new HBox();
                    hBox.getChildren().addAll(bl6, hyperlink ,bl7);
                    list.getItems().add(hBox);
                }
                // si le lien est au milieu du msg
                else {
                    BubbledLabel bl6 = new BubbledLabel();
                    bl6.setText( message.getUserName() + " : " + EmojiUtils.emojify(messageString.substring(0, matchStart)));
                    bl6.setBackground(new Background(new BackgroundFill(Color.web(theirMessageColorBackgroud),null, null)));
                    bl6.setTextFill(Color.web(theirMessageColor));
                    bl6.setBubbleSpec(BubbleSpec.FACE_LEFT_CENTER);

                    BubbledLabel bl7 = new BubbledLabel();
                    bl7.setText("" + EmojiUtils.emojify(messageString.substring(matchEnd, messageString.length())));
                    bl7.setBackground(new Background(new BackgroundFill(Color.web(theirMessageColorBackgroud),null, null)));
                    bl7.setTextFill(Color.web(theirMessageColor));

                    BubbledLink hyperlink = new BubbledLink();
                    hyperlink.setText(linkString);
                    hyperlink.setBackground(new Background(new BackgroundFill(Color.web(theirMessageColorBackgroud),null, null)));
                    hyperlink.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent t) {
                            try {
                                Desktop.getDesktop().browse(new URI(hyperlink.getText()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    HBox hBox = new HBox();
                    hBox.getChildren().addAll(bl6, hyperlink ,bl7);
                    list.getItems().add(hBox);
                }
            }
        }
        
        // si il n'y a pas de lien dans le msg
        else if(matchEnd == 0 && message.getData() == null) {
            if (message.getUser() == Singleton_UserInfo.getInstance().getUser().getUserID()){
                // x.setMaxWidth(list.getWidth() - 20);
                BubbledLabel bl6 = new BubbledLabel();
                bl6.setText( message.getUserName() + " : " + EmojiUtils.emojify(message.getMessage()));
                bl6.setBackground(new Background(new BackgroundFill(Color.web(ourMessageColorBackground),null, null)));
                bl6.setTextFill(Color.web(ourMessageColor));
                bl6.setBubbleSpec(BubbleSpec.FACE_RIGHT_CENTER);
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.TOP_RIGHT);
                hBox.getChildren().addAll(bl6);
                list.getItems().add(hBox);
            }
            else {
                BubbledLabel bl6 = new BubbledLabel();
                bl6.setText( message.getUserName() + " : " + EmojiUtils.emojify(message.getMessage()));
                bl6.setBackground(new Background(new BackgroundFill(Color.web(theirMessageColorBackgroud),null, null)));
                bl6.setTextFill(Color.web(theirMessageColor));
                bl6.setBubbleSpec(BubbleSpec.FACE_LEFT_CENTER);
                HBox hBox = new HBox();
                hBox.getChildren().addAll(bl6);
                list.getItems().add(hBox);
            }
        }

    }

    private static final Pattern urlPattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);


    public static Boolean testImage(String url){
        try {
            URL url2 = new URL(url);
            Image image = ImageIO.read(url2.openStream());
            //BufferedImage image = ImageIO.read(new URL("http://someimage.jpg"));
            if(image != null){
                return true;
            } else{
                return false;
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.err.println("URL error with image");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.err.println("IO error with image");
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}

