package Client.EventHandler.ContextMenu;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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
            Parent root;
            try {
                root  = FXMLLoader.load(getClass().getResource("/soloChat.fxml"));
                Stage stage = new Stage();
                stage.setTitle("My New Stage Title");
                stage.setScene(new Scene(root, 600, 600));
                stage.show();
                // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
