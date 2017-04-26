package Client.EventHandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by Boufle on 26/04/2017.
 */
public class EventHandler_UserChan implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
        System.out.println("test");
    }
}
