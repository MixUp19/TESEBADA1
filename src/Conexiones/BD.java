package src.Conexiones;

public abstract class BD {
    protected String ip;
    protected String nombreFragmento;
    public BD(String ip, String nombreFragmento){
        this.ip =ip;
        this.nombreFragmento= nombreFragmento;
    }

    public abstract void crearConexion() throws Exception;
    public abstract void cerrarConexion() throws Exception;
    public abstract Object select(String consulta);
    public abstract Object insert(String consulta);
    public abstract Object update(String consulta);
    public abstract Object delete(String consulta);
    public abstract void commit() throws Exception;
    public abstract void rollback() throws Exception;
    public abstract void ejecutarTransaccion();
}
