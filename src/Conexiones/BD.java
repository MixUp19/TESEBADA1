package src.Conexiones;

public abstract class BD {
    String ip;
    String nombreFragmento;

    public BD(String ip, String nombreFragmento){
        this.ip =ip;
        this.nombreFragmento= nombreFragmento;
    }
    public abstract void crearConexion();
    public abstract void cerrarConexion() throws Exception;
    public abstract void select(String consulta) throws Exception;
    public abstract void insert(String consulta) throws Exception;
    public abstract void update(String consulta) throws Exception;
    public abstract void delete(String consulta) throws Exception;
}
