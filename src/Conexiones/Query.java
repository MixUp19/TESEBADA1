package src.Conexiones;

import src.Configuracion.ConfiguracionMetodos;
import java.util.HashSet;
import java.util.ArrayList;

public class Query {
    private String consulta, nombreTabla;
    private ArrayList<String> atributosUsados, atributosCondicion, expresiones;
    private ArrayList<Integer> condicionBoolean;

    public Query(String consulta) {
        this.consulta = consulta.toLowerCase();
        this.nombreTabla = obtenerNombreTabla();
        this.atributosUsados = new ArrayList<>();
        this.atributosCondicion = new ArrayList<>();
        this.expresiones = new ArrayList<>();
        this.condicionBoolean = new ArrayList<>();
    }

    public void obtenerCondicionBoolean() {
        HashSet<String> misAtributos = new HashSet<>();
        for(String atributos : ConfiguracionMetodos.obtenerAtributosTablaDist()) {
            misAtributos.add(atributos);
        }
        String aux = getCondicion();
        if(aux.isEmpty()){
            return;
        }
        String[] condicionArr = aux.split(" ");
        int cont = 0;
        for(String str : condicionArr) {
            if(misAtributos.contains(str)) {
                condicionBoolean.add(cont);
                cont++;
            }else {
                condicionBoolean.add(-1);
            }
        }
    }

    public void obtenerAtributosCondicion() {
        HashSet<String> misAtributos = new HashSet<>();
        for(String atributos : ConfiguracionMetodos.obtenerAtributosTablaDist()) {
            misAtributos.add(atributos);
        }
        String condicionAux = consulta.substring(consulta.indexOf("where"));
        String[] condicionAuxArr = condicionAux.split(" ");
        for(int i = 1; i < condicionAuxArr.length; i++) {
            if(misAtributos.contains(condicionAuxArr[i])) {
                atributosCondicion.add(condicionAuxArr[i]);
            }
        }
    }


    public void obtenerAtributosUsados() {
        String[] consultaArr = consulta.split(" ");
        if(consulta.contains("insert")) {
            String soloAtributos = consulta.substring(consulta.indexOf("(")+1, consulta.indexOf(")"))
                    .replaceAll(" ","");
            String[] soloAtributosArr = soloAtributos.split(",");
            for(String atributos : soloAtributosArr) {
                atributosUsados.add(atributos);
            }
        }else if(consulta.contains("update")) {
            atributosUsados.add(consultaArr[3]);
        }else {
            String soloatributos = consulta.substring(6, consulta.indexOf("from")).replaceAll(" ", "");
            String[] soloAtributosArr = soloatributos.split(",");
            for(String atributos :  soloAtributosArr) {
                atributosUsados.add(atributos);
            }
        }
    }

    public void obtenerExpresiones() {
        if(consulta.contains("insert")) {
            String expresionesAux = consulta.substring(consulta.lastIndexOf("(")+1, consulta.lastIndexOf(")"))
                    .replaceAll(" ","");
            String[] expresionesAuxArr = expresionesAux.split(",");
            for(String exp : expresionesAuxArr) {
                expresiones.add(exp);
            }
        }else {
            String[] consultaArr = consulta.split(" ");
            expresiones.add(consultaArr[5]);
        }
    }

    public String obtenerNombreTabla() {
        String[] auxiliar = consulta.split(" ");
        String nombre = "";
        if(consulta.contains("select") || consulta.contains("delete")) {
            for(int i = 0; i < auxiliar.length; i++) {
                if(auxiliar[i].equalsIgnoreCase("from")) {
                    nombre = auxiliar[i+1];
                    break;
                }
            }
            return nombre;
        }else if(consulta.contains("update")) {
            nombre = auxiliar[1];
            return nombre;
        }else {
            nombre = auxiliar[2];
        }
        return nombre;
    }

    public String getCondicion() {
        int index = consulta.indexOf("where");
        if(index == -1) {
            return "";
        }
        return consulta.substring(index);
    }

    public ArrayList<String> getAtributosCondicion() {
        obtenerAtributosCondicion();
        return atributosCondicion;
    }

    public ArrayList<String> getAtributosUsados() {
        obtenerAtributosUsados();
        return atributosUsados;
    }

    public ArrayList<String> getExpresiones() {
        obtenerExpresiones();
        return expresiones;
    }

    public ArrayList<Integer> getCondicionBoolean() {
        obtenerCondicionBoolean();
        return condicionBoolean;
    }

    public String getConsulta() {
        return consulta;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }
}
