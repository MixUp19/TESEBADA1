package src.Conexiones;

import src.Configuracion.ManejadorFragmentos;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BaseCentralizada {
    private String url;
    public BaseCentralizada(){
        url= "jdbc:sqlserver:// 25.7.117.176:1433; databasename=Proyecto2;trustServerCertificate=true";
    }

    public static void main(String[] args) {
        BaseCentralizada b = new BaseCentralizada();
        b.cargarTuplas();
    }
    public void cargarTuplas(){
        try{
            Connection conexion = DriverManager.getConnection(url,"sa","sa");
            String query ="Select c.idCliente, c.nombre, c.estado, c.credito, c.deuda, e.zona from clientes c inner join estados e on e.estado= c.estado";
            Statement sentencia = conexion.createStatement();
            ResultSet result = sentencia.executeQuery(query);
            while(result.next()){
                int idCliente = result.getInt("idCliente");
                String nombre = result.getString("nombre");
                String estado = result.getString("estado");
                double credito = result.getDouble("credito");
                double deuda = result.getDouble("deuda");
                String zona = result.getString("zona");
                String insertar ="Insert into clientes(idcliente, nombre, estado, credito, deuda, zona) values"+"("+idCliente+", '"+nombre+"', '"+estado+"', "+credito+", "+deuda+", '"+zona+"')";
                ManejadorFragmentos m = new ManejadorFragmentos();
                m.ejecutarConsulta(insertar);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
