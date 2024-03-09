package src.Compilador;


import java.util.ArrayList;


import org.apache.commons.lang3.StringUtils;
import src.archivos.Archivos;

public class TraductorObject {
        public static void main(String[] args) {
            String consultaSQL = "Select IDCliente, Nombre, Credito, Deuda FROM clientes WHERE zona is not ('Norte', 'Centro')";
            System.out.println(traducirConsultaSQL(reemplazo(consultaSQL,obtenerAtributos())));
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
                consultaSQL =StringUtils.replaceIgnoreCase(consultaSQL, atributosDist[i],atributosFrag[i]);
            }
            return consultaSQL;
        }
        public static String traducirConsultaSQL(String consultaSQL) {

            String consultaSQLaux= consultaSQL.toLowerCase();
            String recurso = StringUtils.substringAfter(consultaSQLaux, "from");
            recurso = recurso.split(" ")[1];
            char letra = recurso.charAt(0);
            recurso = recurso.toUpperCase().charAt(0) + recurso.substring(1).toLowerCase();
            String condicion="";
            if(StringUtils.containsIgnoreCase(consultaSQLaux,"where"))
                condicion= traducirCondicionSQL(consultaSQL, letra);

            String[] palabras = consultaSQL.split(" ");
            StringBuilder consultaTraducida = new StringBuilder();
            if (palabras[0].equalsIgnoreCase("select")) {
                String atributos = StringUtils.removeStartIgnoreCase(consultaSQL, "select");
                atributos= atributos.trim();
                atributos = StringUtils.substring(atributos, 0,StringUtils.indexOfIgnoreCase(atributos, "from"));
                System.out.println(atributos);
                String[] atributo;
                consultaTraducida.append("Select ");
                if(atributos.contains(",")) {
                    atributo = atributos.split(",");
                    for (String atrib: atributo) {
                        System.out.println(atrib);
                        atrib = letra + "." + atrib.trim();
                        consultaTraducida.append(atrib).append(", ");
                    }
                    consultaTraducida = new StringBuilder(consultaTraducida.substring(0,consultaTraducida.length()-2));
                }else
                    consultaTraducida.append(letra);
                consultaTraducida.append(" from ");
                consultaTraducida.append(recurso);
                consultaTraducida.append(" ").append(letra);
            }
            if (palabras[0].equalsIgnoreCase("delete")){
                consultaTraducida.append("delete ");
                consultaTraducida.append("from ");
                consultaTraducida.append(recurso).append(" ");
                consultaTraducida.append(letra);
            }

            return consultaTraducida.append(" ").append(condicion).toString().trim();
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
                    System.out.println(componente);
                    if(!componente.trim().matches("^[0-9!@#$%^&*()_+\\-=\\[\\]{};:\",./<>?\\\\|]+$") &&
                            !componente.equalsIgnoreCase("and") &&
                            !componente.equalsIgnoreCase("or") &&
                            !componente.equalsIgnoreCase("in") &&
                            !componente.equalsIgnoreCase("not") &&
                            !componente.equalsIgnoreCase("is") &&
                            componente.charAt(0)!='\''&&
                            componente.charAt(0) != '('){
                        componente = letra+"."+componente.trim().toLowerCase();
                    }
                    condicionBuilder.append(componente).append(" ");
                }
                condicionBuilder.trimToSize();
            return  condicionBuilder.toString();
        }
    }
