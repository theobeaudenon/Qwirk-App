package Objet.User;

import java.util.HashMap;
import java.util.Random;

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

    public static User registerUser(HashMap<Integer, User> messageHashMap , User user) throws Exception {

        if(user.getPass().equals("")){
            throw new Exception("mot de passe vide");

        }
        if(user.getUserName().equals("")){
            throw new Exception("Nom utilisateur vide");

        }

        if(user.getMail().equals("")){
            throw new Exception("Email est vide");

        }
        if(user.getMail().equals("")){
            throw new Exception("Question vide est vide");

        }

        if(!isValidEmailAddress(user.getMail())){
            throw new Exception("Email est invalide");

        }


        for (User message:
                messageHashMap.values()) {
            if (message.getMail().equals(user.getMail())){
                throw new Exception("Email existe deja");
            }
        }
        Random r = new Random();
        Integer userId = 100 + r.nextInt(999999999 - 100);
        user.setUserID(userId);
        messageHashMap.put(userId,user);
        return user;

    }
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static User getUserFromMail(HashMap<Integer, User> userHashMap, String mail) {
        for (User message:
                userHashMap.values()) {
            if (message.getMail().equals(mail)){
                return message;
            }
        }
        return null;
    }

    public static Boolean resetUser(HashMap<Integer, User> userHashMap, User user) {
        User userFromMail = getUserFromMail(userHashMap, user.getMail());
        if(userFromMail !=null ){

            if(userFromMail.getVerif().equals(user.getVerif())){
                userFromMail.setPass(user.getPass());
                return true;

            }else {
                return false;
            }
        }else {
            return false;
        }

    }
}
