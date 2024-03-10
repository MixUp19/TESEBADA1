package src.Compilador;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompruebaLexico {

    private HashMap<String, String> keywordsAndOperatorsMap;

    public CompruebaLexico() {
        this.keywordsAndOperatorsMap = new HashMap<String, String>();
        keywordsAndOperatorsMap.put("select", "prSelect");
        keywordsAndOperatorsMap.put("from", "prFrom");
        keywordsAndOperatorsMap.put("where", "prWhere");
        keywordsAndOperatorsMap.put("insert", "prInsert");
        keywordsAndOperatorsMap.put("into", "prInto");
        keywordsAndOperatorsMap.put("values", "prValues");
        keywordsAndOperatorsMap.put("update", "prUpdate");
        keywordsAndOperatorsMap.put("set", "prSet");
        keywordsAndOperatorsMap.put("delete", "prDelete");
        keywordsAndOperatorsMap.put("create", "prCreate");

        keywordsAndOperatorsMap.put("=", "assignop");
        keywordsAndOperatorsMap.put(">", "relaop");
        keywordsAndOperatorsMap.put("<", "relaop");
        keywordsAndOperatorsMap.put(">=", "relaop");
        keywordsAndOperatorsMap.put("<=", "relaop");
        keywordsAndOperatorsMap.put("<>", "relaop");
        keywordsAndOperatorsMap.put("+", "addiop");
        keywordsAndOperatorsMap.put("-", "addiop");
        keywordsAndOperatorsMap.put("*", "multop");
        keywordsAndOperatorsMap.put("/", "multop");
        keywordsAndOperatorsMap.put("(", "codeblock");
        keywordsAndOperatorsMap.put(")", "codeblock");
        keywordsAndOperatorsMap.put(",", "separador");
        keywordsAndOperatorsMap.put("clientes", "tabla");
        keywordsAndOperatorsMap.put("idcliente", "atributo");
        keywordsAndOperatorsMap.put("nombre", "atributo");
        keywordsAndOperatorsMap.put("nombreEstado", "atributo");
        keywordsAndOperatorsMap.put("credito", "atributo");
        keywordsAndOperatorsMap.put("deuda", "atributo");
        keywordsAndOperatorsMap.put("'sur'", "atributo");
        keywordsAndOperatorsMap.put("'norte'", "atributo");
        keywordsAndOperatorsMap.put("'centro'", "atributo");
        keywordsAndOperatorsMap.put("zona", "atributo");




    }

    private boolean evaluarToken(String expresionRegular, String token){
        Pattern pattern = Pattern.compile(expresionRegular, Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(token);
        return matcher.find();
    }

    public String analizadorDeTokens(String token){
        if(keywordsAndOperatorsMap.containsKey(token)){
            return keywordsAndOperatorsMap.get(token);
        }
        if (token.contains(",")) {
            String[] atributos = token.split(",");
            for (String atributo : atributos) {
                if (!keywordsAndOperatorsMap.containsKey(atributo.trim())) {
                    return null;
                }
            }
            return "atributo";
        }
        if(evaluarToken("^\\d+$", token)) return "intnum";

        if(evaluarToken("^[0-9]+\\.[0-9]+$", token)) return "floatnum";


        return null;
    }
}