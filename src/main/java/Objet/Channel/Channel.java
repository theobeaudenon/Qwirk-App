package Objet.Channel;

import com.google.gson.Gson;

/**
 * Created by theobeaudenon on 24/04/2017.
 */
public class Channel {
    private Integer idChannel;

    private String channelName;
    private String description;
    private Integer creationDate;
    private Integer idCreator;
    private Boolean ispublic;


    public Channel(Integer idChannel, String channelName, String description, Integer creationDate, Integer idCreator, Boolean ispublic) {
        this.idChannel = idChannel;
        this.channelName = channelName;
        this.description = description;
        this.creationDate = creationDate;
        this.idCreator = idCreator;
        this.ispublic = ispublic;
    }

    public Channel(String messageJson) {
        Gson gson = new Gson();
        Channel grupoAplicacao = gson.fromJson(messageJson, Channel.class);
        this.channelName = grupoAplicacao.getChannelName();
        this.description = grupoAplicacao.getDescription();
        this.creationDate = grupoAplicacao.getCreationDate();
        this.idCreator = grupoAplicacao.getIdCreator();
        this.idChannel = grupoAplicacao.getIdChannel();
        this.ispublic = grupoAplicacao.ispublic();

    }

    public Boolean ispublic() {
        return ispublic;
    }

    public void setIspublic(Boolean ispublic) {
        this.ispublic = ispublic;
    }

    public Integer getIdChannel() {
        return idChannel;
    }

    public void setIdChannel(Integer idChannel) {
        this.idChannel = idChannel;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Integer creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(Integer idCreator) {
        this.idCreator = idCreator;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);

    }
}
