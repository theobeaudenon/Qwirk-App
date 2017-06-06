package Client.Component;

import javafx.scene.control.Button;

/**
 * Created by Boufle on 06/06/2017.
 */
public class Component_Button_Chat_Fichier extends Button {

    public byte[] file;

    public String fileName;

    public Component_Button_Chat_Fichier(){

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
