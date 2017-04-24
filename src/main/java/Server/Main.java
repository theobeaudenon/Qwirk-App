package Server;

import Objet.Channel.Channel;
import Objet.Message.Message;
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


        HashMap<Integer, Message> messageHashMap = new HashMap<Integer, Message>();
        HashMap<Integer, Channel> channelHashMap = new HashMap<Integer, Channel>();



        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        config.setUpgradeTimeout(10000000);
        config.setPingTimeout(10000000);
        config.setPingInterval(10000000);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", Message.class, new DataListener<Message>() {
            public void onData(SocketIOClient client, Message data, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("chatevent", data);
                System.out.print(data.getUserName());
                System.out.print(data.getMessage());

            }
        });

        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.printf("con");
            }
        });
        server.addEventListener("foo", String.class, new DataListener<String>() {

            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                System.out.print(data);

            }

        });

        server.start();

        //Thread.sleep(Integer.MAX_VALUE);

        //server.stop();
    }

}
