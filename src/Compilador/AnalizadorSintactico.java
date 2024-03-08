package src.Compilador;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class AnalizadorSintactico {
    private Vector<String> tokens;
    private int indiceActual;
    private String errorSintactico;
    private String todosLosTokensString, armandoEstructura;

    public AnalizadorSintactico(Vector<String> tokens) {
        this.tokens = tokens;
        this.indiceActual = 0;
        for (String token : tokens) {
            todosLosTokensString += token + " ";
        }
        System.out.println(todosLosTokensString);
    }

    public void analizar() {
        while (indiceActual < tokens.size()) {
            analizarSentencia();
            indiceActual++;
        }
    }

    private void analizarSentencia() {
    	String tokenActual = tokens.elementAt(indiceActual);

    	if (esSelectStatement()) {
    		return;
    	} 
    	if (esInsertStatement()) {
    		return;
    	} 
    	if (esUpdateStatement()) {
    		return;
    	}

    	if (esDeleteStatement()) {
    	}
}

    private boolean evaluarTxt(String expresionRegular, String texto) {
        if (texto == null) {
            return false; // o realiza alguna acción de manejo de error
        }
        Pattern pattern = Pattern.compile(expresionRegular, Pattern.UNICODE_CASE); 
        Matcher matcher = pattern.matcher(texto);
        return matcher.find();
    }

    private boolean esSelectStatement() {
        boolean band = false;
        // SELECT ... FROM ... WHERE ...
        if (evaluarTxt("^SELECT.*FROM.*WHERE.*", armandoEstructura)) {
            band = true;
        }
        return band;
    }

    private boolean esInsertStatement() {
        boolean band = false;
        // INSERT INTO ... VALUES ...
        if (evaluarTxt("^INSERT INTO.*VALUES.*", armandoEstructura)) {
            band = true;
        }
        return band;
    }

    private boolean esUpdateStatement() {
        boolean band = false;
        // UPDATE ... SET ...
        if (evaluarTxt("^UPDATE.*SET.*", armandoEstructura)) {
            band = true;
        }
        return band;
    }

    private boolean esDeleteStatement() {
        boolean band = false;
        // DELETE FROM ...
        if (evaluarTxt("^DELETE FROM.*", armandoEstructura)) {
            band = true;
        }
        return band;
    }
}
