package src.Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlServer {
    private String usuario, password, servidor, nombreBaseDeDatos, url;
    private Connection conexion;
    public SqlServer(String usuario, String password, String servidor, String nombreBaseDeDatos) {
        this.usuario = usuario;
        this.password = password;
        this.servidor = servidor;
        this.nombreBaseDeDatos = nombreBaseDeDatos;
        url = "jdbc:sqlserver://" + servidor + ":1433;" + nombreBaseDeDatos;
    }
    private boolean establecerConexion() {
        try {
            conexion = DriverManager.getConnection(url, usuario, password);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    private boolean cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    private void insert(String query) {

    }
    private void delete(String query) {

    }
    private void select(String query) {

    }
    private void update(String query) {

    }
    public boolean ejecutarTransaccion(String query) {
        //insert - update - delete - select
        if(!establecerConexion()) {
            return false;
        }
        String beginTran = "BEGIN TRAN";
        //que pregunte que es lo que se esta haciendo ya sea select update delete o insert
        String commitTran = "COMMIT TRAN";
        cerrarConexion();
        return true;
    }
    public boolean ejecutarConsulta(String query) {
        //select
        if(!establecerConexion()) {
            return false;
        }
        //aqui hacer la consulta //probablemente aqui sea lo de devolver el resultSet de la consulta y que
        //en la vista este el metodo de actualiza la tabla
        //para no ensuciar este metodo con cosas de la vista
        cerrarConexion();
        return true;
    }
}
