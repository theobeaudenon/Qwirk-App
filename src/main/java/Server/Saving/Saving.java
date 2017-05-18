package Server.Saving;

import Server.Data.Singleton_Data;

import java.io.*;

/**
 * Server.Saving
 * Created by Theo on 18/05/2017 for app.
 */
public class Saving {



    public static Singleton_Data deserialzeSingleton() {
        Singleton_Data address = null;

        try (ObjectInputStream ois
                     = new ObjectInputStream(new FileInputStream("sav.ser"))) {

            address = (Singleton_Data) ois.readObject();

        } catch (Exception ex) {
return null;        }

        return address;

    }

    public static void serializeSingleton() {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream("sav.ser"))) {

            oos.writeObject(Singleton_Data.getInstance());
            //System.out.println("Sauvegarde ok");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
