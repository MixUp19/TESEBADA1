package src.Configuracion;

import org.apache.commons.lang3.StringUtils;
import src.archivos.Archivos;

import java.sql.SQLOutput;
import java.util.HashMap;

public class ManejadorFragmentos {
    private final HashMap<Object, Object> fragmentos;
    private final Object[][] info;

    public ManejadorFragmentos() {
        this.fragmentos = new HashMap<Object, Object>();
        Archivos arch = new Archivos();
        info = arch.cargarConfiguracionFragmentos();
        info();
    }

    private void info() {
        for (int i = 0; i < info.length; i++) {
            fragmentos.put(info[i][4], info[i][info[i].length - 1]);
        }
    }

    public String verificador(String consulta) {
        if (StringUtils.containsIgnoreCase(consulta, "zona=")) {
            String[] partes = consulta.split("zona=");
            for (int i = 1; i < partes.length; i++) {
                String[] zonas = partes[i].split(",");
                for (String zonaNueva : zonas) {
                    zonaNueva = zonaNueva.trim().replace("'", "");
                    zonaNueva = zonaNueva.trim().replace(" or", "");
                    zonaNueva = zonaNueva.trim().replace(" and", "");

                    zonaNueva = zonaNueva.substring(0, 1).toUpperCase() + zonaNueva.substring(1).toLowerCase();
                    if (fragmentos.containsKey(zonaNueva)) {
                        Object valor = fragmentos.get(zonaNueva);
                        if ("Inactivo".equals(valor)) {
                            return "La zona '" + zonaNueva + "' está inactiva.";
                        } else {
                            return "La zona '" + zonaNueva + "' está activa.";
                        }
                    } else {
                        return "La zona '" + zonaNueva + "' no está configurada.";
                    }

                }
            }
        }
        return "Consulta Comprobada";
    }

}

