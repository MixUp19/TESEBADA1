package src.Conexiones;

import org.neo4j.driver.*;
import src.Configuracion.ConfiguracionMetodos;

import java.util.ArrayList;
import java.util.HashMap;

public class Neo4j extends BD implements AutoCloseable, Runnable{
    private Driver driver;
    private Session sesion;
    private String user, password;
    private String sentenciaAEjecutar;
    private Transaction transaccion;
    public Neo4j(String sentenciaAEjecutar, String ip, String nombreFragmento, String user, String password) {
        super(ip, nombreFragmento);
        this.user = user;
        this.sentenciaAEjecutar = sentenciaAEjecutar;
        this.password = password;
    }

    @Override
    public void crearConexion() {
        driver = GraphDatabase.driver("bolt://" + ip + ":7687", AuthTokens.basic(user, password));
    }

    @Override
    public void cerrarConexion() {
        try {
            close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ocurrio un problema al cerrar la conexion en el fragmento " + nombreFragmento);
        }
    }

    @Override
    public String select(String consulta) {
        HashMap<String,String> mapeo = ConfiguracionMetodos.mapearAtributos(nombreFragmento);

        String nombreTabla = ConfiguracionMetodos.getNombreTabla(nombreFragmento);

        Query q = new Query(consulta);

        String cipherCodigo = "MATCH (n:" + nombreTabla + ") ";
        String[] condicion = q.getCondicion().split(" ");
        for(String str : condicion) {
            if(mapeo.containsKey(str)) {
                cipherCodigo += "n." + mapeo.get(str) + " ";
            }else {
                cipherCodigo += str + " ";
            }
        }
        ArrayList<String> atributosRequeridos = q.getAtributosUsados();
        cipherCodigo += "RETURN ";
        if(!atributosRequeridos.get(0).equals("*")) {
            for(int i = 0; i < atributosRequeridos.size(); i++) {
                if(i < atributosRequeridos.size()-1) {
                    cipherCodigo += "n." + mapeo.get(atributosRequeridos.get(i)) + ",";
                    continue;
                }
                cipherCodigo += "n." + mapeo.get(atributosRequeridos.get(i));
            }
        }else {
            cipherCodigo += "n";
        }

        return cipherCodigo;
    }

    /*public static void selectdos(String consulta, String nombreFragmento) {
        HashMap<String,String> mapeo = ConfiguracionMetodos.mapearAtributos(nombreFragmento);

        String nombreTabla = ConfiguracionMetodos.getNombreTabla(nombreFragmento);

        Query q = new Query(consulta);

        String cipherCodigo = "MATCH (n:" + nombreTabla + ") ";
        String[] condicion = q.getCondicion().split(" ");
        for(String str : condicion) {
            if(mapeo.containsKey(str)) {
                cipherCodigo += "n." + mapeo.get(str) + " ";
            }else {
                cipherCodigo += str + " ";
            }
        }
        ArrayList<String> atributosRequeridos = q.getAtributosUsados();
        cipherCodigo += "RETURN ";
        if(!atributosRequeridos.get(0).equals("*")) {
            for(int i = 0; i < atributosRequeridos.size(); i++) {
                if(i < atributosRequeridos.size()-1) {
                    cipherCodigo += "n." + mapeo.get(atributosRequeridos.get(i)) + ",";
                    continue;
                }
                cipherCodigo += "n." + mapeo.get(atributosRequeridos.get(i));
            }
        }else {
            cipherCodigo += "n";
        }
        System.out.println(cipherCodigo);
    }*/

    public static void main(String[] args) {

        //inserto into Clientes(IdCliente,Nombre,Estado,Credito,Deuda) values(13, 'Joaquin', 'Sinaloa', 1000, 700)
        //DELETE FROM Clientes where Nombre = 'Joaquin'
        //update Clientes set Deuda = 3333 where IdCliente = 12
        String sentenciaDistribuida = "inserto into Clientes(noCliente,Nombre,Estado,Credito,Deuda) values(1111, 'Ricardo', 'Sinaloa', 1000, 700)";

        Neo4j neo = new Neo4j(sentenciaDistribuida, "25.9.55.242", "Ventas", "neo4j", "emiliano");

        try {
            neo.ejecutarTransaccion();
            neo.commit();
        } catch (Exception e) {
            try {
                neo.rollback();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    /*public static void insertDos(String consulta,String nombreFragmento) {
        Query q = new Query(consulta);
        String nombreTabla = ConfiguracionMetodos.getNombreTabla(nombreFragmento);
        String cipherCodigo = "CREATE (n:" + nombreTabla + " {";
        ArrayList<String> atributos = q.getAtributosUsados();
        ArrayList<String> expresiones = q.getExpresiones();
        for(int i = 0; i < atributos.size(); i++) {
            if(i < atributos.size()-1) {
                cipherCodigo += atributos.get(i) + ":" + expresiones.get(i) + ",";
                continue;
            }
            cipherCodigo += atributos.get(i) + ":" + expresiones.get(i);
        }
        cipherCodigo += "})";
        System.out.println(cipherCodigo);
    }*/

    @Override
    public String insert(String consulta) {
        Query q = new Query(consulta);
        String nombreTabla = ConfiguracionMetodos.getNombreTabla(nombreFragmento);
        String cipherCodigo = "CREATE (n:" + nombreTabla + " {";
        ArrayList<String> atributos = q.getAtributosUsados();
        ArrayList<String> expresiones = q.getExpresiones();
        for(int i = 0; i < atributos.size(); i++) {
            if(i < atributos.size()-1) {
                cipherCodigo += atributos.get(i) + ":" + expresiones.get(i) + ",";
                continue;
            }
            cipherCodigo += atributos.get(i) + ":" + expresiones.get(i);
        }
        cipherCodigo += "})";

        return cipherCodigo;
    }

    /*public static void updateDos(String consulta,String nombreFragmento) {
        HashMap<String,String> mapeo = ConfiguracionMetodos.mapearAtributos(nombreFragmento);
        Query q = new Query(consulta);
        String nombreTabla = ConfiguracionMetodos.getNombreTabla(nombreFragmento);
        String cipherCodigo = "MATCH (n:" + nombreTabla + ") ";
        String[] condicion = q.getCondicion().split(" ");
        for(String str : condicion) {
            if(mapeo.containsKey(str)) {
                cipherCodigo += "n." + mapeo.get(str) + " ";
            }else {
                cipherCodigo += str + " ";
            }
        }
        ArrayList<String> atributos = q.getAtributosUsados();
        ArrayList<String> expresiones = q.getExpresiones();
        cipherCodigo += "SET n." + mapeo.get(atributos.get(0)) + " = " + expresiones.get(0);
        System.out.println(cipherCodigo);
    }*/

    @Override
    public String update(String consulta) {
        HashMap<String,String> mapeo = ConfiguracionMetodos.mapearAtributos(nombreFragmento);
        Query q = new Query(consulta);
        String nombreTabla = ConfiguracionMetodos.getNombreTabla(nombreFragmento);
        String cipherCodigo = "MATCH (n:" + nombreTabla + ") ";
        String[] condicion = q.getCondicion().split(" ");
        for(String str : condicion) {
            if(mapeo.containsKey(str)) {
                cipherCodigo += "n." + mapeo.get(str) + " ";
            }else {
                cipherCodigo += str + " ";
            }
        }
        ArrayList<String> atributos = q.getAtributosUsados();
        ArrayList<String> expresiones = q.getExpresiones();
        cipherCodigo += "SET n." + mapeo.get(atributos.get(0)) + " = " + expresiones.get(0);

        return cipherCodigo;
    }

    /*public static void deleteDos(String consulta, String nombreFragmento) {
        HashMap<String,String> mapeo = ConfiguracionMetodos.mapearAtributos(nombreFragmento);
        Query q = new Query(consulta);
        String nombreTabla = ConfiguracionMetodos.getNombreTabla(nombreFragmento);
        String cipherCodigo = "MATCH (n:" + nombreTabla + ") ";
        String[] condicion = q.getCondicion().split(" ");
        for(String str : condicion) {
            if(mapeo.containsKey(str)) {
                cipherCodigo += "n." + mapeo.get(str) + " ";
            }else {
                cipherCodigo += str + " ";
            }
        }
        cipherCodigo += "DELETE n";
        System.out.println(cipherCodigo);
    }*/

    @Override
    public String delete(String consulta) {
        HashMap<String,String> mapeo = ConfiguracionMetodos.mapearAtributos(nombreFragmento);
        Query q = new Query(consulta);
        String nombreTabla = ConfiguracionMetodos.getNombreTabla(nombreFragmento);
        String cipherCodigo = "MATCH (n:" + nombreTabla + ")";
        String[] condicion = q.getCondicion().split(" ");
        for(String str : condicion) {
            if(mapeo.containsKey(str)) {
                cipherCodigo += "n." + mapeo.get(str) + " ";
            }else {
                cipherCodigo += str + " ";
            }
        }
        cipherCodigo += "DELETE n";

        return cipherCodigo;
    }

    @Override
    public void commit() throws Exception {
        this.transaccion.commit();
        this.sesion.close();
    }

    @Override
    public void rollback() throws Exception {
        this.transaccion.rollback();
        this.sesion.close();
    }

    @Override
    public void ejecutarTransaccion() throws Exception {
        String cipherCodigo;
        if(sentenciaAEjecutar.contains("insert")) {
            cipherCodigo = insert(sentenciaAEjecutar);
        }else if(sentenciaAEjecutar.contains("update")) {
            cipherCodigo = update(sentenciaAEjecutar);
        }else if(sentenciaAEjecutar.contains("select")) {
            cipherCodigo = select(sentenciaAEjecutar);
        }else {
            cipherCodigo = delete(sentenciaAEjecutar);
        }
        System.out.println(cipherCodigo);
        crearConexion();
        sesion = driver.session(SessionConfig.builder().withDatabase("neo4j").build());
        transaccion = sesion.beginTransaction();
        transaccion.run(cipherCodigo);
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    @Override
    public void run() {
        try {
            ejecutarTransaccion();
            TERMINADO = true;
            cerrarConexion();
        } catch (Exception e) {
            TERMINADO = false;
            cerrarConexion();
        }
    }
}
