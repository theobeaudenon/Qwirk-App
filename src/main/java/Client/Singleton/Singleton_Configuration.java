package Client.Singleton;

import Objet.Alerte.Call;
import Objet.Configuration.Configuration;
import Objet.User.User;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by Boufle on 25/04/2017.
 */
public class Singleton_Configuration {


    private static Singleton_Configuration INSTANCE = null;

    private Configuration configuration;

    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized Singleton_Configuration getInstance()
    {
        if (INSTANCE == null)
        { 	INSTANCE = new Singleton_Configuration();
        }
        return INSTANCE;
    }

    private Singleton_Configuration()  {


    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
