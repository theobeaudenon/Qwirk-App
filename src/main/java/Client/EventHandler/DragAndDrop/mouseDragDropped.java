package Client.EventHandler.DragAndDrop;

import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.io.File;

/**
 * Created by Boufle on 20/05/2017.
 */
public class MouseDragDropped {

    public static boolean isFileOk(final DragEvent e){
        final Dragboard dragboard = e.getDragboard();
        final boolean isAccepted = dragboard.getFiles().get(0).getName().toLowerCase().endsWith(".png")
                || dragboard.getFiles().get(0).getName().toLowerCase().endsWith(".jpg")
                || dragboard.getFiles().get(0).getName().toLowerCase().endsWith(".zip");

        if (dragboard.hasFiles()){
            if (isAccepted){
                return true;
            }
            else {
                e.consume();
                return false;
            }
        }
        e.consume();
        return false;
    }

    public static String getFile(final DragEvent e){
        final Dragboard dragboard = e.getDragboard();
        final File file = dragboard.getFiles().get(0);

        return file.getName();
    }
}
