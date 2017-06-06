package Client.EventHandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Created by Boufle on 06/06/2017.
 */
public class EventHandler_Message_Fichier implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        System.out.println(event);
    }
}
