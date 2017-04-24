package Server;

import Objet.Message;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

/**
 * Created by theobeaudenon on 24/04/2017.
 */
public class Main {

    public static void main(String[] args) {


        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", Message.class, new DataListener<Message>() {
            public void onData(SocketIOClient client, Message data, AckRequest ackRequest) {
                // broadcast messages to all clients
                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });

        server.start();

        //Thread.sleep(Integer.MAX_VALUE);

        //server.stop();
    }

}
