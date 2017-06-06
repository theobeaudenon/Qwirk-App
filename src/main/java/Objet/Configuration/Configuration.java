package Objet.Configuration;

import com.github.sarxos.webcam.Webcam;

/**
 * Created by Boufle on 06/06/2017.
 */
public class Configuration {

    protected Webcam webcam;
    protected int port;

    public Configuration(Webcam webcam, int port){
        this.webcam = webcam;
        this.port = port;
    }

    public Webcam getWebcam() {
        return webcam;
    }

    public void setWebcam(Webcam webcam) {
        this.webcam = webcam;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
