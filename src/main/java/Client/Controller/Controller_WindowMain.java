package Client.Controller;/**
 * Created by Boufle on 24/04/2017.
 */

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller_WindowMain implements Initializable {

    @FXML
    private JFXListView chanelPan;

    @FXML
    private JFXMasonryPane homeChan;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 100; i++) {
            chanelPan.getItems().add(new Label("Item " + i));
        }

        for (int i = 0; i < 20; i++) {
            Label l = new Label();
            l.setPrefSize(340,100);
            l.setStyle("-fx-background-image: url(http://www.sosiphone.com/blogiphone/wp-content/uploads//2011/02/Pac-Man-banniere.jpg)");
            l.setText(""+i);
            homeChan.getChildren().add(l);
        }
    }
}
