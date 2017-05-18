package Objet.Contacts;

import Objet.LinkObjects.UserContacts;
import Objet.User.User;
import Objet.User.UserUtils;
import Objet.Utils.Action;
import Objet.Utils.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Objet.Contacts
 * Created by Theo on 16/05/2017 for app.
 */
public class ContactUtils {

    public static ArrayList<User> getMyContacts(HashMap<Integer, User> userHashMap, ArrayList<UserContacts> userContactsHashMap, Integer data) {

        ArrayList<User> tmp = new ArrayList<User>();

        for (UserContacts integer : userContactsHashMap) {
            if(data.equals(integer.getUserID())){
                User channelFromId = UserUtils.getUserFromId(userHashMap,integer.getUser2ID());
                if(channelFromId!=null){
                    tmp.add(channelFromId);
                }
            }
            if(data.equals(integer.getUser2ID())){
                User channelFromId = UserUtils.getUserFromId(userHashMap,integer.getUserID());
                if(channelFromId!=null){
                    tmp.add(channelFromId);
                }
            }
        }
        return tmp;
    }
    public static ArrayList<Integer> getMyContactsID(HashMap<Integer, User> userHashMap,  ArrayList<UserContacts> userContactsHashMap, Integer data) {
        ArrayList<Integer> tmp = new ArrayList<Integer>();

        for (User integer : getMyContacts(  userHashMap,   userContactsHashMap,   data) ) {
            tmp.add(integer.getUserID());
        }
        return tmp;
    }

    public static UserContacts isMyContacts(HashMap<Integer, User> userHashMap,  ArrayList<UserContacts> userContactsHashMap, Integer data, Integer data2 ) {

        for (UserContacts integer : userContactsHashMap) {
            if(data.equals(integer.getUserID())){
                if(data2.equals(integer.getUser2ID())){
                    return integer;
                }

            }
            if(data.equals(integer.getUser2ID())){
                if(data2.equals(integer.getUserID())){
                    return integer;
                }
             }
        }
        return null;
    }

    @SuppressWarnings("Since15")
    public static Boolean addMyContacts(HashMap<Integer, User> userHashMap,  ArrayList<UserContacts> userContactsHashMap, Contact idUser) {


        if(idUser.getAction().equals(Action.AJOUTER)){

            User user = UserUtils.getUserFromMail(userHashMap, idUser.getMail());
            if(user != null){
                UserContacts myContacts = isMyContacts(userHashMap, userContactsHashMap, idUser.getIdUser1(),user.getUserID());

                if (myContacts== null){
                    userContactsHashMap.add(new UserContacts(idUser.getIdUser1(),user.getUserID()));


                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }
        if(idUser.getAction().equals(Action.SUPPRIMER)){
            UserContacts myContacts = isMyContacts(userHashMap, userContactsHashMap, idUser.getIdUser1(), idUser.getIdUser2());

            if (myContacts != null){

                userContactsHashMap.remove(myContacts);


                return true;
            }else {
                return false;
            }
        }

        return false;
    }


}
