package src.Configuracion;

import org.apache.commons.lang3.StringUtils;
import src.Conexiones.BD;
import src.Conexiones.Neo4j;
import src.Conexiones.ObjectDB;
import src.Conexiones.SqlServer;
import src.archivos.Archivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ManejadorFragmentos {
    private final HashMap<Object, Object> fragmentos;
    private final Object[][] info;
    private boolean continuar;
    private final ArrayList<String> destinos;
    private String zonas;

    public ManejadorFragmentos() {
        this.fragmentos = new HashMap<Object, Object>();
        this.destinos = new ArrayList<>();
        Archivos arch = new Archivos();
        info = arch.cargarConfiguracionFragmentos();
        info();
    }
    public static void main(String[] arg){
        ManejadorFragmentos m = new ManejadorFragmentos();
        System.out.println(m.verificadorZonaActiva("Select * from clientes where nombre = 'Jorge' and zona = 'norte' and zona = 'sur'"));
    }

    private void info() {
        for (Object[] objects : info) {
            fragmentos.put(objects[4], objects[objects.length - 1]);
        }
    }
    public String comprobarInsert(String consulta){
        String atributos = StringUtils.substring(consulta,0, StringUtils.indexOfIgnoreCase(consulta,"Values"));
        String datos = StringUtils.substring(consulta, StringUtils.indexOfIgnoreCase(consulta,"Values"));
        atributos = StringUtils.substringBetween(atributos, "(",")");
        datos = StringUtils.substringBetween(datos, "(",")");
        String[] atributo = atributos.split(",");
        String[] dato = datos.split(",");
        for (int i = 0; i < atributo.length; i++) {
            if (atributo[i].trim().equalsIgnoreCase("zona")){
                destinos.add(dato[i].replace("'","").trim());
                consulta = StringUtils.removeIgnoreCase(consulta, "zona");
                consulta = StringUtils.removeIgnoreCase(consulta, dato[i].trim());
                consulta = consulta.replaceAll(",\\)", ")");
                consulta = consulta.replaceAll(", \\)", ")");
                System.out.println(consulta);
            }
        }
        return consulta;
    }

    public String verificadorZonaActiva(String consulta) {
        if(StringUtils.containsIgnoreCase(consulta, "insert")){
            return comprobarInsert(consulta);
        }
        if (!StringUtils.containsIgnoreCase(consulta, "zona")) {
            for (Object fragmento :fragmentos.keySet()) {
                if("Inactivo".equalsIgnoreCase(fragmentos.get(fragmento).toString())){
                    continuar= false;
                    return consulta;
                }
                destinos.add(fragmento.toString());
            }
        }
        String[] partes = consulta.split("zona");
        consulta = consulta.replaceAll("zona=", "");
        consulta = consulta.replaceAll("zona =", "");
        for (int i = 1; i < partes.length; i++) {
            String[] zonas = partes[i].split(",");
            for (String zonaNueva : zonas) {

                zonaNueva = zonaNueva.trim().replace(" or", "");
                zonaNueva = zonaNueva.trim().replace(" and", "");
                zonaNueva = zonaNueva.trim().replace("=", "");
                zonaNueva = zonaNueva.trim().split(" ")[0];
                consulta = consulta.replaceAll(zonaNueva,"");
                zonaNueva = zonaNueva.trim().replace("'", "");
                zonaNueva = zonaNueva.trim();
                zonaNueva = zonaNueva.substring(0, 1).toUpperCase() + zonaNueva.substring(1).toLowerCase();
                if (fragmentos.containsKey(zonaNueva)) {
                    Object valor = fragmentos.get(zonaNueva);
                    if ("Inactivo".equals(valor)) {
                        continuar =false;
                        return "La zona '" + zonaNueva + "' está inactiva.";
                    }
                    destinos.add(zonaNueva);
                } else {
                    continuar =false;
                    return "La zona '" + zonaNueva + "' no está configurada.";
                }
            }

        }
        continuar= true;
        consulta = consulta.replaceAll("\\s+", " ");
        consulta = consulta.replaceAll("\\bAND\\b(\\s*\\bAND\\b)+", "AND");
        consulta = consulta.replaceAll("\\band\\b(\\s*\\band\\b)+", "and");
        consulta = consulta.replaceAll("\\bOR\\b(\\s*\\bOR\\b)+", "OR");
        consulta = consulta.replaceAll("\\bor\\b(\\s*\\bor\\b)+", "or");
        consulta = consulta.trim();
        if (StringUtils.endsWithIgnoreCase(consulta, "and")) {
            consulta = consulta.substring(0, consulta.length() - 3);
        }
        if (StringUtils.endsWithIgnoreCase(consulta, "or")) {
            consulta = consulta.substring(0, consulta.length() - 2);
        }
        consulta = consulta.trim();
        return consulta;
    }

    public ArrayList<BD> obtenerFragmentosRequeridos() {
        ArrayList<BD> fragmentos = new ArrayList<>();
        Archivos archivo = new Archivos();
        Object[][] configuracion = archivo.cargarConfiguracionFragmentos();
        for(int i = 1; i < configuracion.length; i++) {
            if(destinos.contains(configuracion[i][4].toString())) {
                if(configuracion[i][2].toString().equalsIgnoreCase("neo4j")) {
                    fragmentos.add(new Neo4j(configuracion[i][1].toString(),configuracion[i][0].toString(),"neo4j", "emiliano"));
                }else if(configuracion[i][2].toString().equalsIgnoreCase("sql")) {
                    fragmentos.add(new SqlServer("sa","sa",configuracion[i][1].toString(), configuracion[i][0].toString(), "fragmentoSQL"));
                }else {
                    //objectdb
                    fragmentos.add(new ObjectDB(configuracion[i][1].toString(),"",configuracion[i][3].toString()));
                }
            }
        }
        return fragmentos;
    }

    public void ejecutarConsulta() {
        //aqui mandar la consulta a los nodos los cuales requiera la consulta
        //mandale un start a todos los fragmentos
        ArrayList<BD> fragmentosInvolucrados = obtenerFragmentosRequeridos();
        Thread[] hilosFragmentos = new Thread[fragmentosInvolucrados.size()];
        for(int i = 0; i < fragmentosInvolucrados.size(); i++) {
            hilosFragmentos[i] = new Thread(fragmentosInvolucrados.get(i));
        }
    }
    //identificar en el hashmap que zonas va ir,

}

