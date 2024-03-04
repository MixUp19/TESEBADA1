package src.Conexiones;

import javax.persistence.*;
public class ObjectDB {
    private String ip, objeto;
    public ObjectDB(String ip, String objeto){
        this.ip = ip;
        this.objeto= objeto;
    }
}
