package Objet.Call;

import Objet.Alerte.Call;

import java.util.UUID;

/**
 * Created by theobeaudenon on 22/05/2017.
 */
public class CallData {

    UUID callerUUID;
    UUID calledUUID;
    byte[] data;

    public CallData(UUID callerUUID, UUID calledUUID, byte[] data) {
        this.callerUUID = callerUUID;
        this.calledUUID = calledUUID;
        this.data = data;
    }

    public CallData(Call call, byte[] res) {
        this.data = res;
        this.callerUUID = call.getCallerUUID();
        this.calledUUID = call.getCalledUUID();
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
