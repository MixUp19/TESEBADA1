package src.Pruebas;
import java.util.HashMap;
import java.util.Scanner;

public class Ejemplo {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        String consultaSQL = "SELECT id, nombre, credito FROM usuarios WHERE id = 10";
        String consultaCypher = traducirConsultaSQL(consultaSQL);
        System.out.println("Consulta Cypher equivalente:");
        System.out.println(consultaCypher);
    }

    public static String traducirConsultaSQL(String consultaSQL) {
        HashMap<String, String> keywordsAndOperatorsMap = new HashMap<>();
        
        											//Usuarios sera cambiado por el nombre de la tabla palabra despues del from
        keywordsAndOperatorsMap.put("SELECT", "MATCH (n:usuarios)");
        keywordsAndOperatorsMap.put("FROM", ":");//: nombre de la tabla
        keywordsAndOperatorsMap.put("WHERE", " WHERE");
        keywordsAndOperatorsMap.put("AND", " AND ");
        keywordsAndOperatorsMap.put("OR", " OR ");
        keywordsAndOperatorsMap.put("NOT", " NOT ");
        keywordsAndOperatorsMap.put("CREATE", "CREATE");
        keywordsAndOperatorsMap.put("UPDATE", "UPDATE");
        keywordsAndOperatorsMap.put("SET", "SET");
        keywordsAndOperatorsMap.put("DELETE", "DELETE");
        keywordsAndOperatorsMap.put("=", "=");
        keywordsAndOperatorsMap.put(">", ">");
        keywordsAndOperatorsMap.put("<", "<");
        keywordsAndOperatorsMap.put(">=", ">=");
        keywordsAndOperatorsMap.put("<=", "<=");
        keywordsAndOperatorsMap.put("<>", "<>");
        keywordsAndOperatorsMap.put("+", "+");
        keywordsAndOperatorsMap.put("-", "-");
        keywordsAndOperatorsMap.put("*", "");
        keywordsAndOperatorsMap.put("/", "/");
        keywordsAndOperatorsMap.put(",", ",");
        keywordsAndOperatorsMap.put(";", "");




        String[] palabras = consultaSQL.split(" ");
        StringBuilder consultaCypher = new StringBuilder();
        for (String palabra : palabras) {
            String equivalenteCypher = keywordsAndOperatorsMap.get(palabra.toUpperCase());
            if (equivalenteCypher != null && !equivalenteCypher.isEmpty()) {
                consultaCypher.append(equivalenteCypher).append(" ");
            } else if (palabra.equalsIgnoreCase("ID")) {
                // Trata específicamente el campo id
                consultaCypher.append("n.").append(palabra).append(" ");
            } else {
                // Si la palabra no tiene equivalente la agrega a la cadena
            	//Agregar como keywords los nombres de los atributos como tablas para manejarlas dentro del mapeo y asi hacerlo directamente
                consultaCypher.append(" ");
            }
        }
        consultaCypher.append("RETURN");

        return consultaCypher.toString().trim();
    }
}


















/*
 * chat gpt version 
 * import java.util.HashMap;

public class ConsultaSQLaCypher {
    public static void main(String[] args) {
        String consultaSQL = "SELECT id FROM usuarios WHERE id = 10";
        String consultaCypher = traducirConsultaSQL(consultaSQL);
        System.out.println("Consulta Cypher equivalente:");
        System.out.println(consultaCypher);
    }

    public static String traducirConsultaSQL(String consultaSQL) {
        HashMap<String, String> keywordsAndOperatorsMap = new HashMap<>();
        
        keywordsAndOperatorsMap.put("SELECT", "MATCH (n:usuarios)");
        keywordsAndOperatorsMap.put("FROM", "");
        keywordsAndOperatorsMap.put("WHERE", "WHERE");
        keywordsAndOperatorsMap.put("AND", "AND");
        keywordsAndOperatorsMap.put("OR", "OR");
        keywordsAndOperatorsMap.put("NOT", "NOT");
        keywordsAndOperatorsMap.put("=", "=");
        keywordsAndOperatorsMap.put(">", ">");
        keywordsAndOperatorsMap.put("<", "<");
        keywordsAndOperatorsMap.put(">=", ">=");
        keywordsAndOperatorsMap.put("<=", "<=");
        keywordsAndOperatorsMap.put("<>", "<>");
        keywordsAndOperatorsMap.put("+", "+");
        keywordsAndOperatorsMap.put("-", "-");
        keywordsAndOperatorsMap.put("*", "");
        keywordsAndOperatorsMap.put("/", "/");
        keywordsAndOperatorsMap.put(",", ",");
        keywordsAndOperatorsMap.put(";", "");

        String[] palabras = consultaSQL.split(" ");
        StringBuilder consultaCypher = new StringBuilder();
        boolean whereClauseStarted = false;
        for (String palabra : palabras) {
            String equivalenteCypher = keywordsAndOperatorsMap.get(palabra.toUpperCase());
            if (equivalenteCypher != null && !equivalenteCypher.isEmpty()) {
                if (equivalenteCypher.equals("WHERE")) {
                    if (!whereClauseStarted) {
                        consultaCypher.append(" WHERE ");
                        whereClauseStarted = true;
                    } else {
                        consultaCypher.append(" AND ");
                    }
                } else if (!equivalenteCypher.equals("")) {
                    consultaCypher.append(equivalenteCypher).append(" ");
                }
            } else if (palabra.equalsIgnoreCase("ID")) {
                consultaCypher.append("n.").append(palabra).append(" ");
            } else {
                // Si la palabra no tiene equivalente, la agregamos tal cual
                consultaCypher.append(palabra).append(" ");
            }
        }
        consultaCypher.append("RETURN n.id");

        return consultaCypher.toString().trim();
    }
}
*/
