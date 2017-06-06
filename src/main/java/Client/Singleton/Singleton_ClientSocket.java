package Client.Singleton;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

/**
 * Created by theobeaudenon on 25/04/2017.
 */
public class Singleton_ClientSocket {
    public Socket socket;




    /** Instance unique non préinitialisée */
    private static Singleton_ClientSocket INSTANCE = null;

    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized Singleton_ClientSocket getInstance()
    {
        if (INSTANCE == null)
        { 	INSTANCE = new Singleton_ClientSocket();
        }
        return INSTANCE;
    }

    private Singleton_ClientSocket()  {

        IO.Options opt = new IO.Options();
        opt.reconnectionDelay = 3000;
        opt.timeout = 15000 ;
        try {
            socket = IO.socket("http://10.29.18.4:9092", opt);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            public void call(Object... args) {


                System.out.printf("connect");
               /* socket.emit("foo", "hi");


                socket.emit("foo", "woot", new Ack() {
                    public void call(Object... args) {

                    }
                });


                socket.emit("chatevent", new Message("theo","coucou", new Date(), 1,1).toJson(), new Ack() {
                    public void call(Object... args) {

                    }
                });
                */


                //socket.disconnect();
            }

        }).on("event", new Emitter.Listener() {

            public void call(Object... args) {}

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            public void call(Object... args) {}

        });




        socket.connect();

    }
}
