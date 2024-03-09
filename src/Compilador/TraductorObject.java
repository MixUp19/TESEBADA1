package src.Compilador;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

public class TraductorObject {
        public static void main(String[] args) {
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);
            String consultaSQL = "Delete FROM clientes WHERE id = 10 AND nombre = 'Luis'";
            String consultaCypher = traducirConsultaSQL(consultaSQL);
            System.out.println("Consulta Cypher equivalente:");
            System.out.println(consultaCypher);
        }

        public static String traducirConsultaSQL(String consultaSQL) {

            consultaSQL= consultaSQL.toLowerCase();
            String recurso = StringUtils.substringAfter(consultaSQL, "from");
            recurso = recurso.split(" ")[1];
            char letra = recurso.charAt(0);
            recurso = recurso.toUpperCase().charAt(0) + recurso.substring(1).toLowerCase();
            System.out.println(recurso);
            String condicion="";
            if(consultaSQL.contains("where"))
                condicion= traducirCondicionSQL(consultaSQL, letra);

            String[] palabras = consultaSQL.split(" ");
            StringBuilder consultaTraducida = new StringBuilder();
            if (palabras[0].equalsIgnoreCase("select")) {
                String atributos = StringUtils.substringAfter(consultaSQL, "select");
                atributos = StringUtils.substringBefore(atributos, "from");
                String atributo[]= atributos.split(",");
                consultaTraducida.append("Select ");
                for (String atrib: atributo) {
                    atrib = letra + "." + atrib.trim();
                    consultaTraducida.append(atrib).append(", ");
                }
                consultaTraducida = new StringBuilder(consultaTraducida.substring(0,consultaTraducida.length()-2));
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

                String condiciones = StringUtils.substringAfter(consultaSQL, "where");
                condiciones = condiciones.trim();
                String descompuesto[] = condiciones.split(" ");
                condicionBuilder.append("where ");
                for (String componente: descompuesto) {
                    if(!componente.matches("^[0-9!@#$%^&*()_+\\-=\\[\\]{};:\",./<>?\\\\|]+$") && !componente.equals("and") && !componente.equals("or")&& componente.charAt(0)!='\''){
                        componente = letra+"."+componente.trim();
                    }
                    condicionBuilder.append(componente).append(" ");
                }
                condicionBuilder.trimToSize();
            return  condicionBuilder.toString();
        }
    }
