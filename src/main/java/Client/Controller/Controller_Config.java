package Client.Controller;

import Client.Singleton.Singleton_ClientSocket;
import Client.Singleton.Singleton_Configuration;
import Client.Singleton.Singleton_UserInfo;
import Objet.Call.CallData;
import Objet.Configuration.Configuration;
import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Boufle on 06/06/2017.
 */
public class Controller_Config implements Initializable {


    @FXML
    JFXComboBox webcamList;

    @FXML
    JFXComboBox microphoneList;

    @FXML
    JFXTextField port;

    @FXML
    JFXComboBox speakerList;

    @FXML
    JFXTextField pseudo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

/*        if (System.getProperty("os.name").equals("Mac OS X")) {
            webcamList.getItems().addAll(Webcam.getWebcams().get(0));
        }
        webcamList.getItems().addAll(Webcam.getWebcams());*/

        pseudo.setText(Singleton_UserInfo.getInstance().getUser().getUserName());

        Task<Void> webCamTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                webcamList.getItems().addAll(Webcam.getWebcams());
                Platform.runLater(new Runnable(){

                    @Override
                    public void run() {
                        webcamList.getSelectionModel().selectFirst();
                    }
                });

                return null;
            }
        };

        Thread webCamThread = new Thread(webCamTask);
        webCamThread.setDaemon(true);
        webCamThread.start();


        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (Mixer.Info mixerInfo : mixers){
           // System.out.println(mixerInfo);
            Mixer m = AudioSystem.getMixer(mixerInfo);
            if(m.isLineSupported(Port.Info.MICROPHONE)){
                microphoneList.getItems().add(mixerInfo.getName());
            }
            else if(m.isLineSupported(Port.Info.SPEAKER)){
                speakerList.getItems().add(mixerInfo.getName());
            }
        }

        microphoneList.getSelectionModel().selectFirst();
        speakerList.getSelectionModel().selectFirst();
    }

    public void configEditAction(ActionEvent actionEvent) {


        Configuration conf = new Configuration((Webcam) webcamList.getSelectionModel().getSelectedItem(), Integer.parseInt(port.getText()));
        Singleton_Configuration.getInstance().setConfiguration(conf);

        Singleton_UserInfo.getInstance().getUser().setUserName(pseudo.getText());

        Node node=(Node) actionEvent.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        stage.close();
    }
}
