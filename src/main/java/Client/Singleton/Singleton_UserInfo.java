package Client.Singleton;

import Objet.Alerte.Call;
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
public class Singleton_UserInfo {


    private static Singleton_UserInfo INSTANCE = null;

    private User user;
    private ArrayList<User> contactList;
    private User userSoloChat;
    private boolean isInCall = false;
    private boolean isSoloChatOpen = false;
    private Call call;

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
    public ArrayList<User> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<User> contactList) {
        this.contactList = contactList;
    }


    public User getUserSoloChat() {
        return userSoloChat;
    }

    public void setUserSoloChat(User userSoloChat) {
        this.userSoloChat = userSoloChat;
    }

    public boolean isInCall() {
        return isInCall;
    }

    public void setInCall(boolean inCall) {
        isInCall = inCall;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public Call getCall() {
        return call;
    }

    public boolean isSoloChatOpen() {
        return isSoloChatOpen;
    }

    public void setSoloChatOpen(boolean soloChatOpen) {
        isSoloChatOpen = soloChatOpen;
    }

}
