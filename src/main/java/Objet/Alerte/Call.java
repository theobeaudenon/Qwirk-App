package Objet.Alerte;

import Objet.User.User;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.UUID;

/**
 * Objet.Alerte
 * Created by Theo on 18/05/2017 for app.
 */
public class Call implements Serializable {
    int caller;
    int called;

    UUID callerUUID;
    UUID calledUUID;

    User userCaller;
    User userCalled;

    public Call(int caller, int called) {
        this.caller = caller;
        this.called = called;
    }

    public Call(String messageJson) {
        Gson gson = new Gson();
        Call grupoAplicacao = gson.fromJson(messageJson, Call.class);
        this.caller = grupoAplicacao.getCaller();
        this.called = grupoAplicacao.getCalled();
        this.userCaller = grupoAplicacao.getUserCaller();
        this.userCalled = grupoAplicacao.getUserCalled();
        this.callerUUID = grupoAplicacao.getCallerUUID();
        this.calledUUID = grupoAplicacao.getCalledUUID();
    }

    public int getCaller() {
        return caller;
    }

    public void setCaller(int caller) {
        this.caller = caller;
    }

    public int getCalled() {
        return called;
    }

    public void setCalled(int called) {
        this.called = called;
    }

    public User getUserCaller() {
        return userCaller;
    }

    public void setUserCaller(User userCaller) {
        this.userCaller = userCaller;
    }

    public User getUserCalled() {
        return userCalled;
    }

    public void setUserCalled(User userCalled) {
        this.userCalled = userCalled;
    }
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);

    }

    public UUID getCallerUUID() {
        return callerUUID;
    }

    public void setCallerUUID(UUID callerUUID) {
        this.callerUUID = callerUUID;
    }

    public UUID getCalledUUID() {
        return calledUUID;
    }

    public void setCalledUUID(UUID calledUUID) {
        this.calledUUID = calledUUID;
    }
}
