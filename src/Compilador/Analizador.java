package src.Compilador;
import javax.swing.*;
import java.util.Vector;

public class Analizador {
    private  Vector<String> todosLosTokens;
    boolean bandErrorLexico;
    StringBuilder txtAreaErrores;
    public Analizador(){
        todosLosTokens = new Vector<>();
        bandErrorLexico=false;
        txtAreaErrores= new StringBuilder();
    }

    public Boolean realizarAnalisisLexico(String codigoFuente) {
        CompruebaLexico analizador = new CompruebaLexico();
        String tipo;
        String[] lineas = codigoFuente.split("\\n+");
        int i = 0;
        for (String linea : lineas) {
            linea = extracted(linea);
            String[] tokens = linea.split("\\s+");
            i++;
            for (String token : tokens) {
                if (token.equals("")) continue;
                tipo = analizador.analizadorDeTokens(token);
                if (tipo == null) {
                    bandErrorLexico = true;
                    txtAreaErrores.append("Error lexico en la linea ").append(i).append(". <").append(token).append("> es invalido.\n");
                }
                todosLosTokens.add(tipo);
            }
        }
        return bandErrorLexico;
    }
    public String getTxtAreaErrores (){
        return txtAreaErrores.toString();
    }

    public void realizarAnalisisSintactico() {
        AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(todosLosTokens);
        analizadorSintactico.analizar();
    }

    private String extracted(String linea) {
        linea = linea.replace("{", " { ");
        linea = linea.replace("}", " } ");
        linea = linea.replace("(", " ( ");
        linea = linea.replace(")", " ) ");
        linea = linea.replace(")", " ) ");
        linea = linea.replace("[", " [ ");
        linea = linea.replace("]", " ] ");
        linea = linea.replace("=", " = ");
        linea = linea.replace("!=", " != ");
        linea = linea.replace(">", " > ");
        linea = linea.replace("<", " < ");
        linea = linea.replace(">=", " >= ");
        linea = linea.replace("<=", " <= ");
        linea = linea.replace("+", " + ");
        linea = linea.replace("-", " - ");
        linea = linea.replace("*", " * ");
        linea = linea.replace("/", " / ");
        if (!linea.contains("=")) return linea;
        for (int i = 0; i < linea.length(); i++) {
            if (linea.charAt(i) != '=')
                continue;
            else if (linea.charAt(i + 1) == '=') {
                i++;
                continue;
            } else if (linea.charAt(i - 1) == '<' || linea.charAt(i - 1) == '>' || linea.charAt(i - 1) == '!' || linea.charAt(i - 1) == '=') {
                continue;
            } else {
                linea = linea.substring(0, i) + " = " + linea.substring(i + 1);
                i += 2;
                System.out.println(linea);
            }
        }
        return linea;
    }
}
