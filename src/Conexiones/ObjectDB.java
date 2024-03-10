package src.Conexiones;

import org.apache.commons.lang3.StringUtils;
import src.archivos.Archivos;

import javax.persistence.*;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectDB extends BD{
    private String objeto, consultaEjecutar;
    private EntityManager em;
    private EntityManagerFactory emf;
    private String resultadoConsulta;
    public ObjectDB(String ip, String nombreFragmento, String objeto, String consultaEjecutar){
        super(ip,nombreFragmento);
        this.objeto=objeto;
        this.consultaEjecutar=reemplazo(consultaEjecutar,obtenerAtributos());
    }
    public static String[] obtenerAtributos(){
        Archivos archivos = new Archivos();
        Object[][] configuracion = archivos.cargarConfiguracionFragmentos();
        String nombreFragmento="";
        for (Object[] objects : configuracion) {
            if (objects[2].toString().equalsIgnoreCase("ObjectDB")) {
                nombreFragmento = objects[0].toString();
            }
        }
        String [] atributos = archivos.cargarMapeoAtributos();
        String [] fragmentos = atributos[0].split("-");
        StringBuilder atributosObject = new StringBuilder();
        StringBuilder atributosDist= new StringBuilder();
        int indice =0;
        for (int i = 0; i < fragmentos.length; i++) {
            if(fragmentos[i].equalsIgnoreCase(nombreFragmento)){
                indice=i;
            }
        }
        for (int i = 1; i < atributos.length; i++) {
            String[] atributo = atributos[i].split("-");
            atributosDist.append(atributo[0]).append("-");
            atributo = atributo[1].split(",");
            atributosObject.append(atributo[indice-1]).append("-");
        }
        ArrayList<String> mapeo = new ArrayList<>();
        mapeo.add(atributosDist.substring(0, atributosObject.length()-1));
        mapeo.add(atributosObject.substring(0, atributosObject.length()-1));
        return mapeo.toArray(new String[0]);
    }
    public static String reemplazo(String consultaSQL, String[] atributos){
        String[] atributosDist = atributos[0].split("-");
        String[] atributosFrag = atributos[1].split("-");
        for (int i = 0; i < atributosFrag.length; i++) {
            consultaSQL = consultaSQL.replaceAll("(?i)"+atributosDist[i], atributosFrag[i]);
        }
        return consultaSQL;
    }
    public static String traducirCondicionSQL(String consultaSQL, char letra){
        StringBuilder condicionBuilder = new StringBuilder();
        String condiciones = StringUtils.substring(consultaSQL, StringUtils.indexOfIgnoreCase(consultaSQL, "where"));
        condiciones = condiciones.trim();
        condiciones= StringUtils.removeStartIgnoreCase(condiciones,"where");
        condiciones= condiciones.trim();
        String[] descompuesto = condiciones.split(" ");
        condicionBuilder.append("where ");
        for (String componente: descompuesto) {
            if(!componente.trim().matches("^[0-9!@#$%^&*()_+\\-=\\[\\]{};:\",./<>?\\\\|]+$") &&
                    !componente.equalsIgnoreCase("and") &&
                    !componente.equalsIgnoreCase("or") &&
                    !componente.equalsIgnoreCase("in") &&
                    !componente.equalsIgnoreCase("not") &&
                    !componente.equalsIgnoreCase("is") &&
                    componente.charAt(0)!='\''&&
                    componente.charAt(0) != '('){
                componente = letra+"."+componente.trim();
            }
            condicionBuilder.append(componente).append(" ");
        }
        condicionBuilder.trimToSize();
        return  condicionBuilder.toString();
    }
    @Override
    public void crearConexion() throws PersistenceException{
        Map<String,String> properties = new HashMap<String, String>();
        properties.put("javax.persistence.jdbc.user", "admin");
        properties.put("javax.persistence.jdbc.password", "admin");
        emf = Persistence.createEntityManagerFactory("objectdb://"+ip+":6136/"+objeto+".odb", properties);
        em = emf.createEntityManager();
    }
    @Override
    public void cerrarConexion(){
        if (emf == null)
            return;
        emf.close();
        emf = null;
    }

    @Override
    public String select(String consulta){
        String consultaSQLaux= consulta.toLowerCase();
        String recurso = StringUtils.substringAfter(consultaSQLaux, "from");
        recurso = recurso.split(" ")[1];
        char letra = recurso.charAt(0);
        System.out.println(recurso);
        recurso = recurso.toUpperCase().charAt(0) + recurso.substring(1).toLowerCase();
        String condicion="";
        if(StringUtils.containsIgnoreCase(consultaSQLaux,"where"))
            condicion= traducirCondicionSQL(consulta, letra);
        StringBuilder consultaTraducida = new StringBuilder();
        String atributos = StringUtils.removeStartIgnoreCase(consulta, "select");
        atributos= atributos.trim();
        atributos = StringUtils.substring(atributos, 0,StringUtils.indexOfIgnoreCase(atributos, "from"));
        String[] atributo;
        consultaTraducida.append("Select ");
        if(atributos.contains(",")) {
            atributo = atributos.split(",");
            for (String atrib: atributo) {
                atrib = letra + "." + atrib.trim();
                consultaTraducida.append(atrib).append(", ");
            }
            consultaTraducida = new StringBuilder(consultaTraducida.substring(0,consultaTraducida.length()-2));
        }else
            consultaTraducida.append(letra);
        consultaTraducida.append(" from ");
        consultaTraducida.append(recurso);
        consultaTraducida.append(" ").append(letra);
        return consultaTraducida.append(" ").append(condicion).toString().trim();
    }

    @Override
    public Clientes insert(String consulta){
        consulta = StringUtils.removeStartIgnoreCase(consulta,"insert");
        String campos = StringUtils.substring(consulta, consulta.indexOf('(')+1, consulta.indexOf(')') );
        String datos = StringUtils.substring(consulta, StringUtils.indexOfIgnoreCase(consulta, "values"));
        datos= StringUtils.removeIgnoreCase(datos, "values" ).trim();
        datos= datos.replace("(","").replace(")","");
        String[] campo = campos.split(",");
        String[] dato = datos.split(",");
        Clientes c = new Clientes();
        for (int i = 0; i < campo.length; i++) {
            switch (campo[i].trim()){
                case "idCliente":{
                    c.setIdCliente(Integer.parseInt(dato[i].trim()));
                    break;}
                case "nombre":{
                    c.setNombre(dato[i].trim().replaceAll("'", ""));
                    break;}
                case "estado":{
                    c.setEstado(dato[i].trim().replaceAll("'", ""));
                    break;}
                case "credito":{
                    c.setCredito(Double.parseDouble(dato[i].trim()));
                    break;}
                case "deuda":{
                    c.setDeuda(Double.parseDouble(dato[i].trim()));
                    break; }
            }
        }
        return c;
    }

    @Override
    public String update(String consulta)  {
        String[] partes = consulta.split(" ");
        StringBuilder constructor = new StringBuilder();
        for (int i = 0; i < partes.length; i++) {
            if (i==1)
                partes[i]= partes[1].toUpperCase().charAt(0)+partes[1].toLowerCase().substring(1, partes[1].length());
            constructor.append(partes[i]).append(" ");
        }
        return constructor.toString().trim();
    }

    @Override
    public String delete(String consulta) {
        String consultaSQLaux= consulta.toLowerCase();
        String recurso = StringUtils.substringAfter(consultaSQLaux, "from");
        recurso = recurso.split(" ")[1];
        char letra = recurso.charAt(0);
        recurso = recurso.toUpperCase().charAt(0) + recurso.substring(1).toLowerCase();
        String condicion="";
        if(StringUtils.containsIgnoreCase(consultaSQLaux,"where"))
            condicion= traducirCondicionSQL(consulta, letra);
        StringBuilder consultaTraducida = new StringBuilder();
            consultaTraducida.append("delete ");
            consultaTraducida.append("from ");
            consultaTraducida.append(recurso).append(" ");
            consultaTraducida.append(letra);
        return consultaTraducida.append(" ").append(condicion).toString().trim();
    }

    @Override
    public void commit() throws PersistenceException {
        em.getTransaction().commit();
    }

    @Override
    public void rollback() throws Exception {

    }

    @Override
    public void ejecutarTransaccion(){
        String consulta;
        try {
            crearConexion();
            em.getTransaction().begin();
            if(StringUtils.containsIgnoreCase(consultaEjecutar, "select")){
                consulta = this.select(consultaEjecutar);
                TypedQuery<Object> query= em.createQuery(consulta, Object.class);
                List<Object> resultado = query.getResultList();
                mostrarConsulta(resultado);
                return;
            }
            if(StringUtils.containsIgnoreCase(consultaEjecutar, "update")){
                Query query = em.createQuery(update(consultaEjecutar));
                query.executeUpdate();
                return;
            }
            if(StringUtils.containsIgnoreCase(consultaEjecutar, "delete")){
                consulta = this.delete(consultaEjecutar);
                Query query = em.createQuery(consulta);
                query.executeUpdate();
                return;
            }
            if (StringUtils.containsIgnoreCase(consultaEjecutar, "insert")){
                Clientes c= insert(consultaEjecutar);
                em.persist(c);
            }
        }catch (PersistenceException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void mostrarConsulta(List<Object> resultado){
        for (Object objeto: resultado) {
            System.out.println(objeto);
        }
    }
}
