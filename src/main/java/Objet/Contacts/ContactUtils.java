package Objet.Contacts;

import Objet.User.User;
import Objet.User.UserUtils;
import Objet.Utils.Action;
import Objet.Utils.Tuple;

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
    public static ArrayList<Integer> getMyContactsID(HashMap<Integer, User> userHashMap, HashMap<Integer, Integer> userContactsHashMap, Integer data) {

        ArrayList<Integer> tmp = new ArrayList<Integer>();

        for (Integer integer : userContactsHashMap.keySet()) {
            if(integer.equals(data)){
                User channelFromId = UserUtils.getUserFromId(userHashMap,userContactsHashMap.get(integer));
                if(channelFromId!=null){
                    tmp.add(userContactsHashMap.get(integer));
                }

            }
        }
        return tmp;
    }

    @SuppressWarnings("Since15")
    public static Boolean addMyContacts(HashMap<Integer, User> userHashMap, HashMap<Integer, Integer> userContactsHashMap, Contact idUser) {


        if(idUser.getAction().equals(Action.AJOUTER)){
            if (! getMyContactsID(userHashMap,userContactsHashMap,idUser.getIdUser1()).contains(idUser.getIdUser2())){
                userContactsHashMap.putIfAbsent(idUser.getIdUser1(),idUser.getIdUser2());
                userContactsHashMap.putIfAbsent(idUser.getIdUser2(),idUser.getIdUser1());

                return true;
            }else {
                return false;
            }
        }
        if(idUser.getAction().equals(Action.SUPPRIMER)){
            if (getMyContactsID(userHashMap,userContactsHashMap,idUser.getIdUser1()).contains(idUser.getIdUser2())){
                userContactsHashMap.remove(idUser.getIdUser1(),idUser.getIdUser2());
                userContactsHashMap.remove(idUser.getIdUser2(),idUser.getIdUser1());

                return true;
            }else {
                return false;
            }
        }

        return false;
    }
}
