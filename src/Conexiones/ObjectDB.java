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
    public void crearConexion() {
        Map<String,String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.user", "admin");
        properties.put("javax.persistence.jdbc.password", "admin");
        emf = Persistence.createEntityManagerFactory("objectdb://"+ip+":6136/"+objeto+".odb", properties);
    }
    @Override
    public void cerrarConexion(){
        if (emf == null)
            return;
        emf.close();
        emf = null;
    }

    @Override
    public void select(String consulta) throws PersistenceException{
        crearConexion();
        em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            TypedQuery<Cliente> query = em.createQuery(consulta, Cliente.class);
            List<Cliente> results = query.getResultList();
        }catch (PersistenceException ex){
            throw ex;
        }
    }

    @Override
    public void insert(String consulta) throws Exception {

    }

    @Override
    public void update(String consulta) throws Exception {

    }

    @Override
    public void delete(String consulta) throws Exception {

    }
}
