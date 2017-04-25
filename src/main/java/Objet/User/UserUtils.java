package Objet.User;

import Objet.Channel.Channel;
import Objet.Message.Message;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by theobeaudenon on 25/04/2017.
 */
public class UserUtils {

    public static User getUserFromId(HashMap<Integer, User> messageHashMap , Integer idMessage){


        return messageHashMap.get(idMessage);

    }

    public static User getUserLogin(HashMap<Integer, User> messageHashMap , String mail,String mdp){

        for (User message:
                messageHashMap.values()) {
            if (message.getMail().equals(mail) && message.getPass().equals(mdp)){
                return message;
            }
        }
        return null;

    }

}
