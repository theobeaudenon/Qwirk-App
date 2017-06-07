package Server.Events.Users;

import Objet.Channel.Channel;
import Objet.Channel.ChannelUtils;
import Objet.Contacts.ContactUtils;
import Objet.User.User;
import Objet.User.UserUtils;
import Server.Data.Singleton_Data;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

import java.util.ArrayList;

/**
 * Server.Events.Users
 * Created by Theo on 18/05/2017 for app.
 */
public class Users_Events {

    /*
    Event : getUserID
    Paramètres : Integer
    Return : User
    Permet a un client de recuperer un utilisateur a partir de l'ID
     */
    public static void getUserID(SocketIOServer server){

        server.addEventListener("getUserID", Integer.class, new DataListener<Integer>() {
            public void onData(SocketIOClient client, Integer idUser, AckRequest ackRequest) {

                User publicChannels = UserUtils.getUserFromId(Singleton_Data.getInstance().getUserHashMap(),idUser);

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    ackRequest.sendAckData(publicChannels);
                }

                System.out.println("getUserID : "+publicChannels.getUserName());

            }
        });



    }

    /*
    Event : resetUser
    Paramètres : User
    Return : Boolean
    Permet a un client de reset un utilisateur et changer le mot de passe
     */
    public static void resetUserID(SocketIOServer server) {

        server.addEventListener("resetUser", User.class, new DataListener<User>() {
            public void onData(SocketIOClient client, User user, AckRequest ackRequest) {

                Boolean publicChannels = UserUtils.resetUser(Singleton_Data.getInstance().getUserHashMap(), user);

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    ackRequest.sendAckData(publicChannels);
                }

                System.out.println("resetUser : " + publicChannels);

            }
        });
    }



           /*
    Event : updateUser
    Paramètres : User
    Return : Boolean
    Permet a un client de changer ses infos perso
     */
    public static void updateUser(SocketIOServer server){

        server.addEventListener("updateUser", User.class, new DataListener<User>() {
            public void onData(SocketIOClient client, User user, AckRequest ackRequest) {

                Boolean publicChannels = UserUtils.updateUser(Singleton_Data.getInstance().getUserHashMap(),user);

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    ackRequest.sendAckData(publicChannels);
                }

                System.out.println("resetUser : "+publicChannels);

            }
        });



    }
}
