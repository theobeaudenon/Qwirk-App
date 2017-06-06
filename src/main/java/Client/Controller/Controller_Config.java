package Client.Controller;

import Client.Singleton.Singleton_Configuration;
import Objet.Configuration.Configuration;
import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

/*        if (System.getProperty("os.name").equals("Mac OS X")) {
            webcamList.getItems().addAll(Webcam.getWebcams().get(0));
        }
        webcamList.getItems().addAll(Webcam.getWebcams());*/

        Task<Void> webCamTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                webcamList.getItems().addAll(Webcam.getWebcams());

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

        webcamList.getSelectionModel().selectFirst();
        microphoneList.getSelectionModel().selectFirst();
        speakerList.getSelectionModel().selectFirst();
    }

    public void configEditAction(ActionEvent actionEvent) {


        Configuration conf = new Configuration((Webcam) webcamList.getSelectionModel().getSelectedItem(), Integer.parseInt(port.getText()));
        Singleton_Configuration.getInstance().setConfiguration(conf);


        Node node=(Node) actionEvent.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        stage.close();
    }
}
