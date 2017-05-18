package Objet.LinkObjects;

import java.io.Serializable;

/**
 * Objet.LinkObjects
 * Created by Theo on 18/05/2017 for app.
 */
public class UserContacts implements Serializable {
    public int userID;
    public int user2ID;

    public UserContacts(int userID, int user2ID) {
        this.userID = userID;
        this.user2ID = user2ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUser2ID() {
        return user2ID;
    }

    public void setUser2ID(int user2ID) {
        this.user2ID = user2ID;
    }
}
