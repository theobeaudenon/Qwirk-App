package Objet.Contacts;

import Objet.Channel.Channel;
import Objet.User.User;
import Objet.User.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Objet.Contacts
 * Created by Theo on 16/05/2017 for app.
 */
public class ContactUtils {

    public static ArrayList<User> getMyContacts(HashMap<Integer, User> userHashMap, HashMap<Integer, Integer> userContactsHashMap, Integer data) {

        ArrayList<User> tmp = new ArrayList<User>();

        for (Integer integer : userContactsHashMap.keySet()) {
            if(integer.equals(data)){
                User channelFromId = UserUtils.getUserFromId(userHashMap,userContactsHashMap.get(integer));
                if(channelFromId!=null){
                    tmp.add(channelFromId);
                }

            }
        }
        return tmp;
    }
}
