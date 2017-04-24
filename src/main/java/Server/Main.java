package Server;

/**
 * Created by theobeaudenon on 24/04/2017.
 */
public class Main {

    public static void main(String[] args) {


        SocketIOServer logServer = SocketIOServer.newInstance(5000 /*port*/);
        logServer.setListener(new SocketIOListener() {
            public void onConnect(Session session) {
                System.out.println("Connected: " + session);
            }

            public void onMessage(Session session, ByteBuf message) {
                System.out.println("Received: " + message.toString(CharsetUtil.UTF_8));
                message.release();
            }

            public void onDisconnect(Session session) {
                System.out.println("Disconnected: " + session);
            }
        });
        logServer.start();
    }

}
