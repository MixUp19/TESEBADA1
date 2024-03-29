package src.Conexiones;

import java.util.ArrayList;

public abstract class BD implements Runnable{
    protected String ip;
    protected String nombreFragmento;
    protected boolean terminado;
    public BD(String ip, String nombreFragmento) {
        this.ip = ip;
        this.nombreFragmento = nombreFragmento;
        this.terminado = false;
    }

    public abstract ArrayList<ArrayList<String>> getResultadoConsulta();
    public abstract void crearConexion();
    public abstract void cerrarConexion();
    public abstract Object select(String consulta);
    public abstract Object insert(String consulta);
    public abstract Object update(String consulta);
    public abstract Object delete(String consulta);
    public abstract void commit() throws Exception;
    public abstract void rollback() throws Exception;
    public abstract void ejecutarTransaccion() throws Exception;

    public boolean isTerminado() {
        return terminado;
    }
}
