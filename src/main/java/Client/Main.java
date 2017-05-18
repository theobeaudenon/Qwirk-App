package Client;

import Client.Controller.Controller_WindowMain;
import Objet.Message.Message;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;
import java.util.Date;

/**
 * Created by theobeaudenon on 24/04/2017.
 */
public class Main {
    public static void main( String[] argss) throws URISyntaxException {

        IO.Options opt = new IO.Options();
        opt.reconnectionDelay = 3000;
        opt.timeout = 15000 ;
        final Socket socket = IO.socket("http://10.29.16.55:9092", opt);


        final String[] art = argss;

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            public void call(Object... args) {

                //Controller_WindowMain controller_windowMain = new Controller_WindowMain(socket);


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
