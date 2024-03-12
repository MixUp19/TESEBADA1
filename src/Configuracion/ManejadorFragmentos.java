package src.Configuracion;

import org.apache.commons.lang3.StringUtils;
import src.Conexiones.BD;
import src.Conexiones.Neo4j;
import src.Conexiones.ObjectDB;
import src.Conexiones.SqlServer;
import src.archivos.Archivos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

public class ManejadorFragmentos {
    private final HashMap<Object, Object> fragmentos;
    private final Object[][] info;
    private boolean continuar, bandera;
    private  ArrayList<String> destinos;
    private ArrayList<BD> fragmentosInvolucrados;
    private String zonas;

    public ManejadorFragmentos() {
        this.fragmentos = new HashMap<Object, Object>();
        this.destinos = new ArrayList<>();
        Archivos arch = new Archivos();
        info = arch.cargarConfiguracionFragmentos();
        info();
    }
    public static int obtenerId(){
        String url ="jdbc:sqlserver:// 25.7.117.176:1433; databasename=fragmentoSQL;trustServerCertificate=true";
        String query= "{call darNumero(?)}";
        int numero=0;
        try {
            Connection c = DriverManager.getConnection(url, "sa", "sa");
            CallableStatement sentencia = c.prepareCall(query);
            sentencia.registerOutParameter(1, java.sql.Types.INTEGER);
            sentencia.execute();
            numero = sentencia.getInt(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return numero;
    }

    public boolean isContinuar() {
        return continuar;
    }

    public String[] obtenerAtributos(String consulta){
        if(consulta.contains("*")){
            Archivos a = new Archivos();
            String linea = a.cargarConfiguracionTablaDist()[1];
            return linea.split(",");
        }
        consulta = StringUtils.substringBefore(consulta, "from");
        consulta = StringUtils.replaceIgnoreCase(consulta, "select","");
        return consulta.split(",");
    }

   /*public static void main(String[] arg){
        ManejadorFragmentos m = new ManejadorFragmentos();
        System.out.println(m.verificadorZonaActiva("insert into clientes (idCliente, nombre, estado, credito, deuda, zona) values (10, 'Jorge', 'Sinaloa',200,200, 'Norte')"));
   }*/

    private void info() {
        for (Object[] objects : info) {
            fragmentos.put(objects[4], objects[objects.length - 1]);
        }
    }
    public String comprobarInsertManual(String consulta){
        if(!StringUtils.containsIgnoreCase(consulta, "insert")){
            continuar= true;
            return consulta;
        }
        if(StringUtils.containsIgnoreCase(consulta, "idCliente")){
            continuar= false;
            return "La consulta no debe de incluir el atributo idCliente";
        }
        int numero = ManejadorFragmentos.obtenerId();
        consulta= StringUtils.replaceIgnoreCase(consulta,"(","(idcliente,");
        consulta= StringUtils.replaceIgnoreCase(consulta,"values(idCliente","values ("+numero);
        consulta= StringUtils.replaceIgnoreCase(consulta,"values (idCliente","values("+numero);
        System.out.println(consulta);
        continuar= true;
        return consulta;
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
                consulta = StringUtils.removeIgnoreCase(consulta, dato[i].trim());
                dato[i]= dato[i].replace("'","").trim();
                dato[i] = dato[i].toUpperCase().charAt(0)+ dato[i].substring(1 );
                System.out.println(dato[i]);
                if (fragmentos.get(dato[i]).equals("Inactivo")){
                    return "Zona '"+dato[i] +"' inactiva";
                }
                destinos.add(dato[i]);
                consulta = StringUtils.removeIgnoreCase(consulta, "zona");
                consulta = consulta.replaceAll(",\\)", ")");
                consulta = consulta.replaceAll(", \\)", ")");
            }
        }
        continuar =true;
        return consulta;
    }

    public String verificadorZonaActiva(String consulta) {
        destinos = new ArrayList<>();
        if(StringUtils.containsIgnoreCase(consulta, "insert")){
            return comprobarInsert(consulta);
        }
        if (!StringUtils.containsIgnoreCase(consulta, "zona")) {
            for (Object fragmento :fragmentos.keySet()) {
                if("Inactivo".equalsIgnoreCase(fragmentos.get(fragmento).toString())){
                    continuar= false;
                    return "No están todas la zonas activas";
                }
                destinos.add(fragmento.toString());
            }
        }
        int tieneWhere =StringUtils.indexOfIgnoreCase(consulta, "where");
        if (tieneWhere==-1) {
            continuar= true;
            return consulta;
        }
        String[] partes = consulta.substring(tieneWhere).split("zona");
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

        int posicionInvalido = StringUtils.indexOfIgnoreCase(consulta,"where and");
        if (posicionInvalido != -1) {
            consulta = consulta.substring(0, posicionInvalido+5) +
                    consulta.substring(posicionInvalido + "where and".length() );
        }
        posicionInvalido = StringUtils.indexOfIgnoreCase(consulta,"where or");
        if (posicionInvalido != -1) {
            consulta = consulta.substring(0, posicionInvalido+5) +
                    consulta.substring(posicionInvalido + "where or".length() );
        }
        consulta = consulta.trim();
        if (StringUtils.endsWithIgnoreCase(consulta, "where")) {
            consulta = consulta.substring(0, consulta.length() - 5);
        }
        consulta = consulta.trim();
        return consulta;
    }
    public boolean contiene(String cadena){
        for (String destino: destinos) {
            if(destino.equalsIgnoreCase(cadena)){
                return true;
            }
        }
        return false;
    }
    public ArrayList<BD> obtenerFragmentosRequeridos(String consulta) {
        fragmentosInvolucrados = new ArrayList<>();
        Archivos archivo = new Archivos();
        Object[][] configuracion = archivo.cargarConfiguracionFragmentos();
        for(int i = 0; i < configuracion.length; i++) {
            if(contiene(configuracion[i][4].toString())){
                if(configuracion[i][2].toString().equalsIgnoreCase("neo4j")) {
                    fragmentosInvolucrados.add(new Neo4j(consulta, configuracion[i][1].toString(),configuracion[i][0].toString(),"neo4j", "emiliano"));
                }else if(configuracion[i][2].toString().equalsIgnoreCase("sql")) {
                    fragmentosInvolucrados.add(new SqlServer(consulta, "sa","sa",configuracion[i][1].toString(), configuracion[i][0].toString(), "fragmentoSQL"));
                }else {
                    fragmentosInvolucrados.add(new ObjectDB(configuracion[i][1].toString(),"",configuracion[i][3].toString(), consulta));
                }
            }
        }
        System.out.println();
        return fragmentosInvolucrados;
    }

    public void ejecutarConsulta(String consulta) {
        ArrayList<BD> fragmentosInvolucrados = obtenerFragmentosRequeridos(consulta);
        Thread[] hilosFragmentos = new Thread[fragmentosInvolucrados.size()];
        for(int i = 0; i < fragmentosInvolucrados.size(); i++) {
            hilosFragmentos[i] = new Thread(fragmentosInvolucrados.get(i));
        }
        for(Thread fragmentos : hilosFragmentos) {
            fragmentos.start();
        }
        while(fragmentosIsAlive(hilosFragmentos));
        bandera = true;
        for(BD fragmento : fragmentosInvolucrados) {
            System.out.println(fragmento.isTerminado());
            if(!fragmento.isTerminado()) {
                bandera = false;
                break;
            }
        }
        if(bandera) {
            for(BD fragmento : fragmentosInvolucrados) {
                try {
                    System.out.println("Se hizo commit");
                    fragmento.commit();
                }catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Ocurrio algun error");
                }
            }
        }else {
            for(BD fragmento : fragmentosInvolucrados) {
                try {
                    System.out.println("se hizo rollback");
                    fragmento.rollback();
                }catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Ocurrio algun error");
                }
            }
        }
    }
    public String[][] resultadoConsulta(){
        if(!bandera){
            return null;
        }
        ArrayList<ArrayList<String>> consulta = new ArrayList<>();
        for (BD fragmento: fragmentosInvolucrados) {
            consulta.addAll(fragmento.getResultadoConsulta());
        }
        String[][] datos = new String[consulta.size()][];
        for (int i = 0; i < datos.length; i++) {
            datos[i] = consulta.get(i).toArray(new String[0]);
        }
        return datos;
    }
    public boolean fragmentosIsAlive(Thread[] fragmentos) {
        for (Thread frag : fragmentos) {
            if (frag.isAlive()) {
                return true;
            }
        }
        return false;
    }
}

