package Server.Data;

import Objet.Channel.Channel;
import Objet.Message.Message;
import Objet.User.User;

import java.util.HashMap;

/**
 * Created by theobeaudenon on 25/04/2017.
 */
public class Singleton_Data {



    HashMap<Integer, Message> messageHashMap = new HashMap<Integer, Message>();
    HashMap<Integer, Channel> channelHashMap = new HashMap<Integer, Channel>();
    HashMap<Integer, User> userHashMap = new HashMap<Integer, User>();




    /** Instance unique non préinitialisée */
    private static Singleton_Data INSTANCE = null;

    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized Singleton_Data getInstance()
    {
        if (INSTANCE == null)
        { 	INSTANCE = new Singleton_Data();
        }
        return INSTANCE;
    }
    private Singleton_Data() {



        //VAleurs par defaut
        userHashMap.put(0,new User("theo","theo@beaudenon.pro","toto"));
        userHashMap.put(1,new User("test","test@test","test"));




    }


    public HashMap<Integer, Message> getMessageHashMap() {
        return messageHashMap;
    }

    public void setMessageHashMap(HashMap<Integer, Message> messageHashMap) {
        this.messageHashMap = messageHashMap;
    }

    public HashMap<Integer, Channel> getChannelHashMap() {
        return channelHashMap;
    }

    public void setChannelHashMap(HashMap<Integer, Channel> channelHashMap) {
        this.channelHashMap = channelHashMap;
    }

    public HashMap<Integer, User> getUserHashMap() {
        return userHashMap;
    }

    public void setUserHashMap(HashMap<Integer, User> userHashMap) {
        this.userHashMap = userHashMap;
    }


}
