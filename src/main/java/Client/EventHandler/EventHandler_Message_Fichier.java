package Client.EventHandler;

import Client.Component.Component_Button_Chat_Fichier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Boufle on 06/06/2017.
 */
public class EventHandler_Message_Fichier implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        System.out.println(event);

        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();

        Component_Button_Chat_Fichier fichierButton = (Component_Button_Chat_Fichier) event.getSource();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Chemin pour enregistrer le fichier");
        File outputFolder = directoryChooser.showDialog(stage);

        if (outputFolder != null) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(outputFolder+"\\"+fichierButton.getFileName());
                fos.write(fichierButton.getFile());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
