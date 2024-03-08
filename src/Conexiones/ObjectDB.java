package src.Conexiones;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectDB extends BD{
    private String objeto;
    EntityManager em;
    EntityManagerFactory emf;
    public ObjectDB(String ip, String nombreFragmento, String objeto){
        super(ip,nombreFragmento);
        this.objeto=objeto;
    }

    @Override
    public void crearConexion() throws Exception {
        Map<String,String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.user", "admin");
        properties.put("javax.persistence.jdbc.password", "admin");
        emf = Persistence.createEntityManagerFactory("objectdb://"+ip+":6136/"+objeto+".odb", properties);
    }

    public void conexion(){
  }
    public void cerrarConexion(){
        if (emf == null)
            return;
        emf.close();
        emf = null;
    }

    public void seleccionarTuplas(String tuplas) throws PersistenceException{

    }

    @Override
    public void select() throws PersistenceException{
        conexion();
        em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
            List<Cliente> results = query.getResultList();
        }catch (PersistenceException ex){
            throw ex;
        }
    }

    @Override
    public void insert() throws Exception {

    }

    @Override
    public void update() throws Exception {

    }

    @Override
    public void delete() throws Exception {

    }
}
