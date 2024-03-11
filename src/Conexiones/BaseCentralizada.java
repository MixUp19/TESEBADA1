package src.Conexiones;

import src.Configuracion.ManejadorFragmentos;


import java.sql.*;

public class BaseCentralizada {
    private String url;
    public BaseCentralizada(){
        url= "jdbc:sqlserver:// 25.7.117.176:1433; databasename=Proyecto2;trustServerCertificate=true";
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
                String insertar ="insert into clientes(idcliente,nombre,estado,credito,deuda,zona) values("+idCliente+",'"+nombre+"','"+estado+"',"+credito+","+deuda+",'"+zona+"')";
                ManejadorFragmentos m = new ManejadorFragmentos();
                System.out.println(insertar);
                insertar =  m.verificadorZonaActiva(insertar);
                m.ejecutarConsulta(insertar);
            }
            query = "select cuenta = count(*) from clientes";
            sentencia = conexion.createStatement();
            result = sentencia.executeQuery(query);
            int maximo=0;
            while (result.next()){
                maximo = result.getInt("cuenta");
            }
            conexion.close();
            actualizarNumero(maximo);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void actualizarNumero (int maximo){
        String url ="jdbc:sqlserver:// 25.7.117.176:1433; databasename=fragmentoSQL;trustServerCertificate=true";
        String query= "Insert into numero_maximo(numero) values ("+(maximo+1)+")";
        try {
            Connection c = DriverManager.getConnection(url, "sa", "sa");
            PreparedStatement sentencia = c.prepareStatement(query);
            sentencia.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
