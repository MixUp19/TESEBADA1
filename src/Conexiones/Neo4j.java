package src.Conexiones;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import src.Configuracion.ConfiguracionMetodos;

public class Neo4j {
    private final Driver driver;
    private String servidor, nombreFragmento;
    public Neo4j(String servidor, String user, String password, String nombreFragmento) {
        this.nombreFragmento = nombreFragmento;
        driver = GraphDatabase.driver("bolt://" + servidor + ":7687", AuthTokens.basic(user, password));
    }
    public void ejecutarTransaccion(String query) {
        String Q = transformaQuery(query);
        //aqui ejecuta la transaccion con el metodo ese de Transaction
    }
    public void ejecutarConsulta(String query) {

    }
    public String transformaQuery(String query) {
        String cypherQuery = "";

        String[] partesQuery = query.split(" ");

        if(partesQuery[0].equalsIgnoreCase("select")) {
            //haz el match con lo de
            cypherQuery += "MATCH " + "(n:" + ConfiguracionMetodos.getNombreTabla(nombreFragmento) +")";
            //hacer lo del where
            //puede haber problemas con el IS NOT(reemplaza ese is not)
            //en el return retorna lo que viene en el select
        }else if(partesQuery[0].equalsIgnoreCase("update")) {

        }else if(partesQuery[0].equalsIgnoreCase("insert")) {
            //insert into clientes(nombre,edad) values(xxx,xxx)
        }else {
            //delete
        }
        return cypherQuery;
    }
}
