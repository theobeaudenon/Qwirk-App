package Server.Data;

import Objet.Bot.Bot;
import Objet.Channel.Channel;
import Objet.Message.Message;
import Objet.User.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by theobeaudenon on 25/04/2017.
 */
public class Singleton_Data {

    Integer messageIncrement = 0;
    Integer botIncrement = 0;

    HashMap<Integer, Message> messageHashMap = new HashMap<Integer, Message>();
    HashMap<Integer, Channel> channelHashMap = new HashMap<Integer, Channel>();
    HashMap<Integer, User> userHashMap = new HashMap<Integer, User>();

    ArrayList<Bot> botArrayList = new ArrayList<Bot>();

    HashMap<Integer, Integer> userChannelsHashMap = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> userContactsHashMap = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> botChannelHashMap = new HashMap<Integer, Integer>();




    /** Instance unique non préinitialisée */
    private static Singleton_Data INSTANCE = null;

    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized Singleton_Data getInstance()
    {
        if (INSTANCE == null)
        { 	INSTANCE = new Singleton_Data();
        }
        return INSTANCE;
    }
    private Singleton_Data() {



        //VAleurs par defaut
        userHashMap.put(0,new User(0,"theo","theo@beaudenon.pro","toto"));
        userHashMap.put(1,new User(1,"test","test@test","test"));


        channelHashMap.put(0,new Channel(0,"toto","",1493132555,0,true));
        channelHashMap.put(2,new Channel(2,"tata","",1493132555,0,true));
        channelHashMap.put(3,new Channel(3,"titi","",1493132555,0,true));
        channelHashMap.put(3,new Channel(3,"tutu","",1493132595,0,true));

        messageHashMap.put(getMessageIncrement(),new Message("Théo","message de test",1493132555,2,1,false,null,null));
        messageHashMap.put(getMessageIncrement(),new Message("Théo","autre message de test",1493132999,2,1,false,null,null));
        messageHashMap.put(getMessageIncrement(),new Message("Théo","autre message de test",1493152999,2,1,false,null,null));
        messageHashMap.put(getMessageIncrement(),new Message("Théo","autre message de test",1493142999,2,1,false,null,null));

        userChannelsHashMap.put(1,2);

        userContactsHashMap.put(0,1);
        userContactsHashMap.put(1,0);



    }

    public HashMap<Integer, Integer> getUserChannelsHashMap() {
        return userChannelsHashMap;
    }

    public void setUserChannelsHashMap(HashMap<Integer, Integer> userChannelsHashMap) {
        this.userChannelsHashMap = userChannelsHashMap;
    }

    public HashMap<Integer, Message> getMessageHashMap() {
        return messageHashMap;
    }

    public Integer getBotIncrement() {
        botIncrement++;
        return botIncrement;
    }

    public void setBotIncrement(Integer botIncrement) {
        this.botIncrement = botIncrement;
    }

    public Integer getMessageIncrement() {
        messageIncrement++;
        return messageIncrement;
    }

    public ArrayList<Bot> getBotArrayList() {
        return botArrayList;
    }

    public void setBotArrayList(ArrayList<Bot> botArrayList) {
        this.botArrayList = botArrayList;
    }

    public void setMessageHashMap(HashMap<Integer, Message> messageHashMap) {
        this.messageHashMap = messageHashMap;
    }

    public HashMap<Integer, Channel> getChannelHashMap() {
        return channelHashMap;
    }

    public void setChannelHashMap(HashMap<Integer, Channel> channelHashMap) {
        this.channelHashMap = channelHashMap;
    }

    public HashMap<Integer, User> getUserHashMap() {
        return userHashMap;
    }

    public void setUserHashMap(HashMap<Integer, User> userHashMap) {
        this.userHashMap = userHashMap;
    }

    public HashMap<Integer, Integer> getUserContactsHashMap() {
        return userContactsHashMap;
    }

    public HashMap<Integer, Integer> getBotChannelHashMap() {
        return botChannelHashMap;
    }

    public void setBotChannelHashMap(HashMap<Integer, Integer> botChannelHashMap) {
        this.botChannelHashMap = botChannelHashMap;
    }

    public Bot getBotArrayList(Integer value) {
        for (Bot bot : botArrayList) {
            if(bot.getIdBot().equals(value)){
                return bot;
            }
        }

        return null;
    }
}
