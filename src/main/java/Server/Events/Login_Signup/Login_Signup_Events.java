package Server.Events.Login_Signup;

import Objet.User.User;
import Objet.User.UserUtils;
import Server.Data.Singleton_Data;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

/**
 * Created by theobeaudenon on 25/04/2017.
 */
public class Login_Signup_Events {

    /*
    Event : login
    Paramètres : User
    Return : User
    Permet a un client de reset de ce connecter a son compte
     */
    public static void socketLoginEvent(SocketIOServer server) {
        server.addEventListener("login", User.class, new DataListener<User>() {
            public void onData(SocketIOClient client, User data, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("chatevent", data);

                User userLogin = UserUtils.getUserLogin(Singleton_Data.getInstance().getUserHashMap(), data.getMail(), data.getPass());

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    ackRequest.sendAckData(userLogin);
                }

                System.out.print("Login : "+data.getMail());
                // System.out.print(data.getPass());

            }
        });
    }

    /*
    Event : singup
    Paramètres : User
    Return : User
    Permet a un client de creer un compte sur le serveur
     */
    public static void socketSingupEvent(SocketIOServer server) {
        server.addEventListener("singup", User.class, new DataListener<User>() {
            public void onData(SocketIOClient client, User data, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("chatevent", data);

                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client

                    try {
                        User userLogin = UserUtils.registerUser(Singleton_Data.getInstance().getUserHashMap(), data);
                        ackRequest.sendAckData(userLogin);
                        System.out.print("signup : "+userLogin.getUserName());

                    } catch (Exception e) {
                        ackRequest.sendAckData(e.getMessage());
                    }

                }


            }
        });
    }
}
