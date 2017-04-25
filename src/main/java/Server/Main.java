package Server;

import Objet.Channel.Channel;
import Objet.Message.Message;
import Objet.User.User;
import Objet.User.UserUtils;
import Server.Data.Singleton_Data;
import Server.Events.Login_Signup.Login_Signup_Events;
import Server.Events.Messages.Messages_Events;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

import java.util.HashMap;

/**
 * Created by theobeaudenon on 24/04/2017.
 */
public class Main {

    public static void main(String[] args) {

        //Creation du signleton de données
        Singleton_Data.getInstance();


        //Demarage du serveur
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        config.setUpgradeTimeout(10000000);
        config.setPingTimeout(10000000);
        config.setPingInterval(10000000);

        SocketIOServer server = new SocketIOServer(config);



        //LOGIN SIGNUP EVENTS
        Login_Signup_Events.socketLoginEvent(server);
        Login_Signup_Events.socketSingupEvent(server);

        //messages Events
        Messages_Events.newMessage(server);






        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                //System.out.printf("con");
            }
        });

        server.start();
    }



}
