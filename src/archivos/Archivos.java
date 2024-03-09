package src.archivos;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class Archivos {
    public Archivos(){

    }
    public Object[][] cargarConfiguracionFragmentos() {
        Object[][] datos = new Object[1][1];
        Object[][] aux ;
        String path = "src/Configuracion/config_file.txt";
        File archivo;
        try {
            archivo = new File(path);
            BufferedReader buffReader = new BufferedReader(new FileReader(archivo));
            String linea;
            buffReader.readLine();
            linea = buffReader.readLine();
            int row = 0;
            while(linea != null) {
                String[] renglon = linea.split("-");
                aux=datos;
                datos = new Object[row+1][renglon.length];
                for (int i = 0; i < renglon.length; i++) {
                    datos[row][i]= renglon[i];
                }
                if(row==0){
                    linea = buffReader.readLine();
                    row++;
                    continue;
                }
                for (int i = 0; i < aux.length; i++) {
                    datos[i] =aux[i];
                }
                linea = buffReader.readLine();
                row++;
            }
        }catch(IOException e) {
            System.out.println("Ha ocurrido un error con el archivo");
        }
        return datos;
    }
    public void guardarConfiguracionFragmentos(String[] datos) {
        String path = "src/Configuracion/config_file.txt";
        File archivo;
        try {
            archivo = new File(path);
            archivo.createNewFile();
            FileWriter writer = new FileWriter(archivo);
            writer.write("");
            writer.write("NombreFragmento-FRAGMENTO-BASE DE DATOS-TABLA-CRITERIO-ATRIBUTOS-ESTADO\n");

            for (int i = 0; i < datos.length ; i++) {
                if(datos[i] == null){
                    continue;
                }
                writer.write(datos[i]);
            }
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("Ha ocurrido un error con el archivo");
        }
    }
    public String[] cargarConfiguracionTablaDist() {
        String path = "src/Configuracion/config_tabla_dist_file.txt";
        File archivo;
        try {
            archivo = new File(path);
            if(!archivo.exists()) {
                return null;
            }
            BufferedReader buffReader = new BufferedReader(new FileReader(archivo));
            String linea = buffReader.readLine();
            linea = buffReader.readLine();
            return linea.split("-");
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("Ha ocurrido un error con el archivo");
        }
        return null;
    }

    public void guardarConfiguracionTablaDist(String nombreTabla, String atributos) {
        String path = "src/Configuracion/config_tabla_dist_file.txt";
        File archivo;
        try {
            archivo = new File(path);
            archivo.createNewFile();
            FileWriter writer = new FileWriter(archivo);
            writer.write("");
            writer.write("NOMBRE DE TABLA-ATRIBUTOS\n");
            writer.write(nombreTabla + "-" + atributos);
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("Ha ocurrido un error con el archivo");
        }
    }
    public void guardarMapeoAtributos(String [] mapeoAtr, String encabezado){
        String path = "src/Configuracion/mapeo_Atributos.txt";
        File archivo;
        try{
            archivo = new File(path);
            FileWriter writer = new FileWriter(archivo);
            writer.write("");
            writer.write(encabezado+"\n");
            for (int i = 0; i < mapeoAtr.length; i++) {
                writer.write(mapeoAtr[i]+"\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public String[] cargarMapeoAtributos(){
        ArrayList<String> mapeo = new ArrayList<>();
        String path = "src/Configuracion/mapeo_Atributos.txt";
        FileReader archivo;
        try{
            archivo = new FileReader(path);
            BufferedReader reader = new BufferedReader(archivo);
            String linea;
            linea= reader.readLine();
            while (linea!=null){
                mapeo.add(linea);
                linea= reader.readLine();
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return mapeo.toArray(new String[0]);
    }
}
