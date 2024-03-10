package src.Conexiones;

import org.neo4j.driver.*;
import src.Configuracion.ConfiguracionMetodos;

import java.util.ArrayList;
import java.util.HashMap;

public class Neo4j extends BD implements AutoCloseable{
    private Driver driver;
    private String ip, nombreFragmento, user, password;
    public Neo4j(String ip, String nombreFragmento, String user, String password) {
        super(ip, nombreFragmento);
        this.user = user;
        this.password = password;
    }

    @Override
    public void crearConexion() throws Exception {
        driver = GraphDatabase.driver("bolt://" + ip + ":7687", AuthTokens.basic(user, password));
    }

    @Override
    public void cerrarConexion() throws Exception {
        close();
    }

    @Override
    public String select(String consulta){
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


        //mandar a llamar con la transaccion
        //aca abajo el return
        //MATCH (n:Clientes) WHERE n.edad = 12 and n.nombre = 'Yosef' RETURN n si es * o cada uno de los atributos
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
        /*selectdos("select * from Clientes", "Centro");
        insertDos("insert into Clientes(IdCliente, Nombre, Estado, Credito, Deuda) values(1, 'yosef', 'sinaloa', 12.2, 10000)","Centro");
        updateDos("update Clientes set Nombre = 'yosef' where IdCliente = 2 and Estado = Sinaloa", "Centro");
        deleteDos("DELETE from Clientes where IdCliente = 2 and Estado = 'sinaloa'", "Centro");*/
        Neo4j fragmento_centro = new Neo4j("25.9.55.242", "Centro", "neo4j", "emiliano");
        //intentar insertar un nodo

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
        //aqui es donde va a hacer commit
    }

    @Override
    public void rollback() throws Exception {
        //aqui es donde va a hacer el rollback
    }

    @Override
    public void ejecutarTransaccion() {
        //aqui es donde van a abrir la transaccion pero no van a hacer el commit
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }
}
