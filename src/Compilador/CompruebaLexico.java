package src.Compilador;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompruebaLexico {
    
    private HashMap<String, String> keywordsAndOperatorsMap;

    public CompruebaLexico() {
        this.keywordsAndOperatorsMap = new HashMap<String, String>();
        
        keywordsAndOperatorsMap.put("SELECT", "prSelect");
        keywordsAndOperatorsMap.put("FROM", "prFrom");
        keywordsAndOperatorsMap.put("WHERE", "prWhere");
        keywordsAndOperatorsMap.put("INSERT", "prInsert");
        keywordsAndOperatorsMap.put("INTO", "prInto");
        keywordsAndOperatorsMap.put("VALUES", "prValues");
        keywordsAndOperatorsMap.put("UPDATE", "prUpdate");
        keywordsAndOperatorsMap.put("SET", "prSet");
        keywordsAndOperatorsMap.put("DELETE", "prDelete");
        keywordsAndOperatorsMap.put("CREATE", "prCreate");
        keywordsAndOperatorsMap.put("TABLE", "prTable");
        keywordsAndOperatorsMap.put("ALTER", "prAlter");
        keywordsAndOperatorsMap.put("DROP", "prDrop");
        keywordsAndOperatorsMap.put("JOIN", "prJoin");
        keywordsAndOperatorsMap.put("INNER", "prInner");
        keywordsAndOperatorsMap.put("LEFT", "prLeft");
        keywordsAndOperatorsMap.put("RIGHT", "prRight");
        keywordsAndOperatorsMap.put("OUTER", "prOuter");
        keywordsAndOperatorsMap.put("ON", "prOn");
        keywordsAndOperatorsMap.put("AND", "prAnd");
        keywordsAndOperatorsMap.put("OR", "prOr");
        keywordsAndOperatorsMap.put("NOT", "prNot");
        keywordsAndOperatorsMap.put("NULL", "prNull");

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
        keywordsAndOperatorsMap.put(",", "codeblock");
        keywordsAndOperatorsMap.put(";", "linebreak");
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

        if(evaluarToken("^[a-zA-Z_][a-zA-Z0-9_]*$", token)) {
            return "identifier"; 
        }

        if(evaluarToken("^\\d+$", token)) return "intnum";
        
        if(evaluarToken("^\\d*\\.\\d+$", token)) return "floatnum";

        return null;
    }

    
}
