package Client.Controller;

import Client.Component.Component_Label_Contact;
import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_UserInfo;
import Objet.Alerte.Call;
import Objet.Call.CallData;
import Objet.User.User;
import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import io.socket.client.Ack;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Boufle on 18/05/2017.
 */
public class Controller_WindowSoloChat implements Initializable {

    private User contactUser = Singleton_UserInfo.getInstance().getUserSoloChat();

    @FXML
    private Label userName;

    @FXML
    private JFXButton callButton;

    @FXML
    private StackPane webCamPaneContainer;

    private ImageView imgWebCamCapturedImage;
    private ImageView imgWebCamCapturedImage2;
    private Webcam webCam = null;
    private boolean stopCamera = false;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
    private ObjectProperty<Image> imageProperty2 = new SimpleObjectProperty<Image>();
    private AnchorPane webCamPane;
    private HBox webCamPane2;

    private boolean isInCall = false;

    TargetDataLine microphone;
    SourceDataLine speakers;
    AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);

    private boolean stopMicro = false;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userName.setText(contactUser.getUserName());

        Singleton_UserInfo.getInstance().setSoloChatOpen(true);
        SVGGlyph call = new SVGGlyph(0,
                "FULLSCREEN",
                " M577.83,456.128c1.225,9.385-1.635,17.545-8.568,24.48l-81.396,80.781\n" +
                        "        c-3.672,4.08-8.465,7.551-14.381,10.404c-5.916,2.857-11.729,4.693-17.439,5.508c-0.408,0-1.635,0.105-3.676,0.309\n" +
                        "        c-2.037,0.203-4.689,0.307-7.953,0.307c-7.754,0-20.301-1.326-37.641-3.979s-38.555-9.182-63.645-19.584\n" +
                        "        c-25.096-10.404-53.553-26.012-85.376-46.818c-31.823-20.805-65.688-49.367-101.592-85.68\n" +
                        "        c-28.56-28.152-52.224-55.08-70.992-80.783c-18.768-25.705-33.864-49.471-45.288-71.299\n" +
                        "        c-11.425-21.828-19.993-41.616-25.705-59.364S4.59,177.362,2.55,164.51s-2.856-22.95-2.448-30.294\n" +
                        "        c0.408-7.344,0.612-11.424,0.612-12.24c0.816-5.712,2.652-11.526,5.508-17.442s6.324-10.71,10.404-14.382L98.022,8.756\n" +
                        "        c5.712-5.712,12.24-8.568,19.584-8.568c5.304,0,9.996,1.53,14.076,4.59s7.548,6.834,10.404,11.322l65.484,124.236\n" +
                        "        c3.672,6.528,4.692,13.668,3.06,21.42c-1.632,7.752-5.1,14.28-10.404,19.584l-29.988,29.988c-0.816,0.816-1.53,2.142-2.142,3.978\n" +
                        "        s-0.918,3.366-0.918,4.59c1.632,8.568,5.304,18.36,11.016,29.376c4.896,9.792,12.444,21.726,22.644,35.802\n" +
                        "        s24.684,30.293,43.452,48.653c18.36,18.77,34.68,33.354,48.96,43.76c14.277,10.4,26.215,18.053,35.803,22.949\n" +
                        "        c9.588,4.896,16.932,7.854,22.031,8.871l7.648,1.531c0.816,0,2.145-0.307,3.979-0.918c1.836-0.613,3.162-1.326,3.979-2.143\n" +
                        "        l34.883-35.496c7.348-6.527,15.912-9.791,25.705-9.791c6.938,0,12.443,1.223,16.523,3.672h0.611l118.115,69.768\n" +
                        "        C571.098,441.238,576.197,447.968,577.83,456.128z",
                Color.web("#04fd00"));
        call.setSize(40, 40);

        SVGGlyph callClose = new SVGGlyph(0,
                "FULLSCREEN",
                " M577.83,456.128c1.225,9.385-1.635,17.545-8.568,24.48l-81.396,80.781\n" +
                        "        c-3.672,4.08-8.465,7.551-14.381,10.404c-5.916,2.857-11.729,4.693-17.439,5.508c-0.408,0-1.635,0.105-3.676,0.309\n" +
                        "        c-2.037,0.203-4.689,0.307-7.953,0.307c-7.754,0-20.301-1.326-37.641-3.979s-38.555-9.182-63.645-19.584\n" +
                        "        c-25.096-10.404-53.553-26.012-85.376-46.818c-31.823-20.805-65.688-49.367-101.592-85.68\n" +
                        "        c-28.56-28.152-52.224-55.08-70.992-80.783c-18.768-25.705-33.864-49.471-45.288-71.299\n" +
                        "        c-11.425-21.828-19.993-41.616-25.705-59.364S4.59,177.362,2.55,164.51s-2.856-22.95-2.448-30.294\n" +
                        "        c0.408-7.344,0.612-11.424,0.612-12.24c0.816-5.712,2.652-11.526,5.508-17.442s6.324-10.71,10.404-14.382L98.022,8.756\n" +
                        "        c5.712-5.712,12.24-8.568,19.584-8.568c5.304,0,9.996,1.53,14.076,4.59s7.548,6.834,10.404,11.322l65.484,124.236\n" +
                        "        c3.672,6.528,4.692,13.668,3.06,21.42c-1.632,7.752-5.1,14.28-10.404,19.584l-29.988,29.988c-0.816,0.816-1.53,2.142-2.142,3.978\n" +
                        "        s-0.918,3.366-0.918,4.59c1.632,8.568,5.304,18.36,11.016,29.376c4.896,9.792,12.444,21.726,22.644,35.802\n" +
                        "        s24.684,30.293,43.452,48.653c18.36,18.77,34.68,33.354,48.96,43.76c14.277,10.4,26.215,18.053,35.803,22.949\n" +
                        "        c9.588,4.896,16.932,7.854,22.031,8.871l7.648,1.531c0.816,0,2.145-0.307,3.979-0.918c1.836-0.613,3.162-1.326,3.979-2.143\n" +
                        "        l34.883-35.496c7.348-6.527,15.912-9.791,25.705-9.791c6.938,0,12.443,1.223,16.523,3.672h0.611l118.115,69.768\n" +
                        "        C571.098,441.238,576.197,447.968,577.83,456.128z",
                Color.web("#FE2E2E"));
        callClose.setSize(40, 40);

        callButton.setGraphic(call);
        callButton.setRipplerFill(Color.GREENYELLOW);

        webCamPane = new AnchorPane();
        webCamPane.setStyle("-fx-background-color:transparent;");
        imgWebCamCapturedImage = new ImageView();
        webCamPane.getChildren().addAll(imgWebCamCapturedImage);
        webCamPane.setPrefWidth(10);
        webCamPane.setMaxWidth(130);
        webCamPane.setPrefSize(130,100);
        webCamPane.setMaxHeight(100);
        webCamPane.setPrefHeight(100);
        //ourWebCamPan.getChildren().addAll(webCamPane);

        webCamPane2 = new HBox();
        webCamPane2.setStyle("-fx-background-color: #fff;");
        imgWebCamCapturedImage2 = new ImageView();
        webCamPane2.getChildren().addAll(imgWebCamCapturedImage2);
        webCamPane2.setPrefWidth(400);
        webCamPane2.setPrefHeight(300);
        //theirWebCamPane.getChildren().addAll(webCamPane2);

        webCamPaneContainer.getChildren().addAll(webCamPane2, webCamPane); // hbox with button and text on top of image view
        webCamPaneContainer.setMargin(webCamPane, new Insets(180, 0,0,240));


        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                setImageViewSize();
            }
        });


        Singleton_ClientSocket.getInstance().socket.on("callInboud", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                byte[] obj = (byte[]) args[0];
                Image image = convertToJavaFXImage(obj, 400,300);
                imageProperty2.set(image);
            }
        });

        Singleton_ClientSocket.getInstance().socket.on("audioCallInboud", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                byte[] obj = (byte[]) args[0];
                speakers.write(obj, 0,1024);
            }
        });

        imgWebCamCapturedImage2.imageProperty().bind(imageProperty2);

        initializeWebCam(0);
        startMicro();
        startSpeaker();



    }

    public void shutdown() {
       disposeWebCamCamera();

        Singleton_UserInfo.getInstance().setInCall(false);
        Singleton_UserInfo.getInstance().setSoloChatOpen(false);

    }

    private static Image convertToJavaFXImage(byte[] raw, final int width, final int height) {
        WritableImage image = new WritableImage(width, height);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(raw);
            BufferedImage read = ImageIO.read(bis);
            image = SwingFXUtils.toFXImage(read, null);
        } catch (IOException ex) {
            ex.getStackTrace();
        }
        return image;
    }

    public void caller(){

    }

    protected void startWebCamStream() {

        stopCamera = false;

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                final AtomicReference<WritableImage> ref = new AtomicReference<>();
                BufferedImage img = null;

                while (!stopCamera) {
                    try {
                        if ((img = webCam.getImage()) != null) {

                            ref.set(SwingFXUtils.toFXImage(img, ref.get()));
                            img.flush();

                            Platform.runLater(new Runnable(){

                                @Override
                                public void run() {
                                    imageProperty.set(ref.get());
                                    //socket du caller
                                    if (Singleton_UserInfo.getInstance().isInCall()){
                                        BufferedImage bImage = SwingFXUtils.fromFXImage(ref.get(), null);
                                        ByteArrayOutputStream s = new ByteArrayOutputStream();
                                        try {
                                            ImageIO.write(bImage, "jpg", s);
                                            byte[] res  = s.toByteArray();
                                            //on envoie l'image au client
                                            Singleton_ClientSocket.getInstance().socket.emit("callFlux",new CallData(Singleton_UserInfo.getInstance().getCall(),res).toJson());
                                            s.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        imgWebCamCapturedImage.imageProperty().bind(imageProperty);
    }

    public void startMicro(){

        try {
            microphone = AudioSystem.getTargetDataLine(format);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);


            int CHUNK_SIZE = 1024;
            byte[] data = new byte[microphone.getBufferSize() / 5];
            microphone.start();


            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    while (!stopMicro){
                        if (Singleton_UserInfo.getInstance().isInCall()){
                            microphone.read(data, 0, CHUNK_SIZE);
                            // write the mic data to a stream for use later

                            Singleton_ClientSocket.getInstance().socket.emit("audioCallFlux",new CallData(Singleton_UserInfo.getInstance().getCall(),data).toJson());
                            //out.write(data, 0, numBytesRead);
                        }
                    }
                    return null;
                }
            };


            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();


        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void startSpeaker(){

        try{
            int bytesRead = 0;
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void closeMirco(){
        microphone.close();
    }

    public void closeSpeaker(){
        speakers.drain();
        speakers.close();
    }



    protected void initializeWebCam(final int webCamIndex) {

        Task<Void> webCamTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                if (webCam != null) {
                    disposeWebCamCamera();
                }

                webCam = Webcam.getWebcams().get(webCamIndex);
                webCam.open();

                startWebCamStream();

                return null;
            }
        };

        Thread webCamThread = new Thread(webCamTask);
        webCamThread.setDaemon(true);
        webCamThread.start();

    }

    protected void disposeWebCamCamera() {
        stopCamera = true;
        webCam.close();
    }

    protected void dispoMircro(){
        stopMicro = true;
        microphone.close();
    }

    protected void dispoSpeaker(){
        speakers.close();
    }

    protected void setImageViewSize() {

        double height = webCamPane.getHeight();
        double width = webCamPane.getWidth();

        imgWebCamCapturedImage.setFitHeight(height);
        imgWebCamCapturedImage.setFitWidth(width);
        imgWebCamCapturedImage.prefHeight(height);
        imgWebCamCapturedImage.prefWidth(width);
        imgWebCamCapturedImage.setPreserveRatio(true);

        double height2 = webCamPane2.getHeight();
        double width2 = webCamPane2.getWidth();

        imgWebCamCapturedImage2.setFitHeight(height2);
        imgWebCamCapturedImage2.setFitWidth(width2);
        imgWebCamCapturedImage2.prefHeight(height2);
        imgWebCamCapturedImage2.prefWidth(width2);
        imgWebCamCapturedImage2.setPreserveRatio(true);

    }

    public void makeACall(MouseEvent mouseEvent) {
        Call call = new Call(Singleton_UserInfo.getInstance().getUser().getUserID(), contactUser.getUserID());
        Singleton_ClientSocket.getInstance().socket.emit("makeACall", call.toJson());
    }


}
