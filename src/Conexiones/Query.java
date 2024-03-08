package src.Conexiones;

import java.util.HashSet;

public class Query {
    private String consulta;
    private HashSet<String> palabrasReservadas;
    //debes buscar los atributos los cuales coindicen con el from
    //atributos del where
    //
    public Query(String consulta) {
        this.consulta = consulta;
        this.palabrasReservadas = new HashSet<>();
        palabrasReservadas.add("select");
        palabrasReservadas.add("insert");
        palabrasReservadas.add("delete");
        palabrasReservadas.add("update");
        palabrasReservadas.add("from");
        palabrasReservadas.add("where");
    }

    public String getCondicion() {
        String cond = "";

        return cond;
    }
}
