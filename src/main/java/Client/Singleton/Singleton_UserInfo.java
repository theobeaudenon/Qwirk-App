package Client.Singleton;

import Objet.User.User;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

/**
 * Created by Boufle on 25/04/2017.
 */
public class Singleton_UserInfo {


    private static Singleton_UserInfo INSTANCE = null;

    private User user;

    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized Singleton_UserInfo getInstance()
    {
        if (INSTANCE == null)
        { 	INSTANCE = new Singleton_UserInfo();
        }
        return INSTANCE;
    }

    private Singleton_UserInfo()  {


    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){

        return this.user;
    }
}
