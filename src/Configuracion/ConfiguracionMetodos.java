package src.Configuracion;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class ConfiguracionMetodos {

    //[nombre en la tabla distribuida - nombre en el fragmento]
    public static HashMap<String,String> mapearAtributos(String nombreFragmento) {
        HashMap<String,String> mapeo = new HashMap<>();
        int fragmentoIndex = 0;
        String path = "src/Configuracion/mapeo_Atributos.txt";
        File archivo;
        try {
            archivo = new File(path);
            FileReader fileReader = new FileReader(archivo);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linea = bufferedReader.readLine();
            String[] fragmentos = linea.split("-");
            for(int i = 0; i < fragmentos.length; i++) {
                if(fragmentos[i].equalsIgnoreCase(nombreFragmento)) {
                    fragmentoIndex = i;
                    break;
                }
            }
            linea = bufferedReader.readLine();
            while(linea != null) {
                fragmentos = linea.split("-");
                String[] fragmentos2 = fragmentos[1].split(",");
                mapeo.put(fragmentos[0],fragmentos2[fragmentoIndex-1]);
                linea = bufferedReader.readLine();
            }
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("Ocurrio un error con el archivo");
        }
        return mapeo;
    }
    public static String getNombreTabla(String nombreFragmento) {
        String nombre = "";
        String path = "src/Configuracion/config_file.txt";
        File archivo;
        try {
            archivo = new File(path);
            FileReader fileReader = new FileReader(archivo);
            BufferedReader buffReader = new BufferedReader(fileReader);
            buffReader.readLine();
            String linea = buffReader.readLine();
            while(linea != null) {
                String[] configuracion = linea.split("-");
                if(configuracion[0].equalsIgnoreCase(nombreFragmento)) {
                    nombre = configuracion[3];
                    break;
                }
                linea = buffReader.readLine();
            }
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("Ocurrio un error con el archivo");
        }
        return nombre;
    }

    public static void main(String[] args) {
        System.out.println("Nombre de la tabla en el fragmento X: " + getNombreTabla("centro"));
        HashMap<String,String> mapeo = mapearAtributos("norte");
        for(Map.Entry<String,String> entry : mapeo.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println("Atributos definidos en mi tabla distribuida");
        for(String x : obtenerAtributosTablaDist()) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

    public static ArrayList<String> obtenerAtributosTablaDist() {
        ArrayList<String> atributos = new ArrayList<>();
        String path = "src/Configuracion/config_tabla_dist_file.txt";
        File archivo;
        try {
            archivo = new File(path);
            BufferedReader buffReader = new BufferedReader(new FileReader(archivo));
            buffReader.readLine();
            String linea = buffReader.readLine();
            String[] arr = linea.split("-");
            for(String atrib : arr[1].split(",")) {
                atributos.add(atrib);
            }
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("Ocurrio un problema con el archivo");
        }
        return atributos;
    }
}
