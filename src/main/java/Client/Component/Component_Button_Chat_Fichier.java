package Client.Component;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Created by Boufle on 06/06/2017.
 */
public class Component_Button_Chat_Fichier extends Button {

    public byte[] file;

    public String fileName;

    public Component_Button_Chat_Fichier(byte[] file, String fileName){

        this.file = file;
        this.fileName = fileName;

        setText(fileName);
        setStyle(" -fx-background-color: linear-gradient(#00f8ff, #0573be);\n" +
                "-fx-text-fill: white;"+
                "    -fx-border-width: 2px;"+
                "    -fx-border-color: #2b2b2b;");
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
