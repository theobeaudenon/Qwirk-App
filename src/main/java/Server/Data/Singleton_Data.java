package Server.Data;

import Objet.Bot.Bot;
import Objet.Channel.Channel;
import Objet.LinkObjects.BotChannel;
import Objet.LinkObjects.UserChannels;
import Objet.LinkObjects.UserContacts;
import Objet.Message.Message;
import Objet.User.User;
import Objet.Utils.Tuple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by theobeaudenon on 25/04/2017.
 */
public class Singleton_Data implements Serializable{

    Integer messageIncrement = 0;
    Integer channelIncrement = 0;
    Integer botIncrement = 0;

    HashMap<Integer, Message> messageHashMap = new HashMap<Integer, Message>();
    HashMap<Integer, Channel> channelHashMap = new HashMap<Integer, Channel>();
    HashMap<Integer, User> userHashMap = new HashMap<Integer, User>();

    ArrayList<Bot> botArrayList = new ArrayList<Bot>();

    ArrayList<UserChannels> userChannelsHashMap = new ArrayList<UserChannels>();
    ArrayList<UserContacts> userContactsHashMap = new ArrayList<UserContacts>();
    ArrayList<BotChannel> botChannelHashMap = new ArrayList<BotChannel>();




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

    public static void setData(Singleton_Data data){
        Singleton_Data instance = Singleton_Data.getInstance();
        instance.setBotArrayList(data.getBotArrayList());
        instance.setBotChannelHashMap(data.getBotChannelHashMap());
        instance.setBotIncrement(data.getBotIncrement());
        instance.setChannelHashMap(data.getChannelHashMap());
        instance.setChannelIncrement(data.getChannelIncrement());
        instance.setMessageHashMap(data.getMessageHashMap());
        instance.setUserChannelsHashMap(data.getUserChannelsHashMap());
        instance.setUserHashMap(data.getUserHashMap());

    }

    private Singleton_Data() {



        //VAleurs par defaut
        userHashMap.put(0,new User(0,"theo","theo@beaudenon.pro","toto","reset"));
        userHashMap.put(1,new User(1,"test","test@test","test","reset"));

        Integer channelIncrement = getChannelIncrement();
        channelHashMap.put(channelIncrement,new Channel(channelIncrement,"toto","",1493132555,0,true));
        channelIncrement = getChannelIncrement();
        channelHashMap.put(channelIncrement,new Channel(channelIncrement,"tata","",1493132555,0,true));
        channelIncrement = getChannelIncrement();
        channelHashMap.put(channelIncrement,new Channel(channelIncrement,"titi","",1493132555,0,true));
        channelIncrement = getChannelIncrement();
        channelHashMap.put(channelIncrement,new Channel(channelIncrement,"tutu","",1493132595,0,true));
        channelIncrement = getChannelIncrement();
        channelHashMap.put(channelIncrement,new Channel(channelIncrement,"je suis chan","",1493132595,0,true));
        channelIncrement = getChannelIncrement();
        channelHashMap.put(channelIncrement,new Channel(channelIncrement,"botchan","",1493132595,0,true));

        messageHashMap.put(getMessageIncrement(),new Message("Théo","message de test",1493132555,2,1,false,null,null));
        messageHashMap.put(getMessageIncrement(),new Message("Théo","autre message de test",1493132999,2,1,false,null,null));
        messageHashMap.put(getMessageIncrement(),new Message("Théo","autre message de test",1493152999,2,1,false,null,null));
        messageHashMap.put(getMessageIncrement(),new Message("Théo","autre message de test",1493142999,2,1,false,null,null));

        userChannelsHashMap.add(new UserChannels(1,2));
        userChannelsHashMap.add(new UserChannels(1,3));
        userChannelsHashMap.add(new UserChannels(1,5));
        userChannelsHashMap.add(new UserChannels(1,6));
        userChannelsHashMap.add(new UserChannels(0,6));

        userContactsHashMap.add(new UserContacts(0,1));



    }

    public ArrayList<UserChannels>  getUserChannelsHashMap() {
        return userChannelsHashMap;
    }

    public void setUserChannelsHashMap(ArrayList<UserChannels> userChannelsHashMap) {
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

    public void setChannelIncrement(Integer channelIncrement) {
        this.channelIncrement = channelIncrement;
    }

    public Integer getChannelIncrement() {
        channelIncrement++;
        return channelIncrement;
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

    public ArrayList<UserContacts> getUserContactsHashMap() {
        return userContactsHashMap;
    }

    public ArrayList<BotChannel> getBotChannelHashMap() {
        return botChannelHashMap;
    }

    public void setBotChannelHashMap(ArrayList<BotChannel> botChannelHashMap) {
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
