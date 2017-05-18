package Client.EventHandler.ContextMenu;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Boufle on 18/05/2017.
 */
public class EventHandler_ContextMenu_Contact implements EventHandler<MouseEvent> {

    protected ListView component;
    protected ContextMenu contextMenu;

    public EventHandler_ContextMenu_Contact(ListView component, ContextMenu contextMenu){
        this.component = component;
        this.contextMenu = contextMenu;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton().equals(MouseButton.SECONDARY)) {
            if (component.getSelectionModel().getSelectedItems() != null){
                contextMenu.show(component, event.getScreenX(), event.getScreenY());
            }
        }
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            int i = 0;
        }
    }
}
