package Client.Component;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Boufle on 06/06/2017.
 */
public class Component_Button_Chat_Image extends Button {

    private static final int IMG_WIDTH = 50;
    private static final int IMG_HEIGHT = 50;

    public byte[] file;

    public String fileName;

    public Component_Button_Chat_Image(byte[] file, String fileName){

        this.file = file;
        this.fileName = fileName;
        BufferedImage img = null;

        try {
             img = ImageIO.read(new ByteArrayInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int type = img.getType() == 0? BufferedImage.TYPE_INT_ARGB : img.getType();
        img = resizeImage(img, type);

        WritableImage image = SwingFXUtils.toFXImage(img, null);
        setGraphic(new ImageView(image));
        setStyle(" -fx-background-color: linear-gradient(#00f8ff, #0573be);\n" +
                "    -fx-border-width: 2px;"+
                "    -fx-border-color: #2b2b2b;");
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
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
