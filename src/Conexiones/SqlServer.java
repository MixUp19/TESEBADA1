package src.Conexiones;

import src.Configuracion.ConfiguracionMetodos;

import java.io.ObjectInputFilter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SqlServer extends BD implements Runnable{
    private String usuario, password, ip, nombreBaseDeDatos, url, nombreFragmento;
    private String sentenciaAEjecutar;
    private Connection conexion;
    public SqlServer(String sentenciaAEjecutar, String usuario, String password, String ip, String nombreFragmento, String nombreBaseDeDatos) {
        super(ip, nombreFragmento);
        this.usuario = usuario;
        this.sentenciaAEjecutar = sentenciaAEjecutar;
        this.nombreFragmento = nombreFragmento;
        this.password = password;
        this.nombreBaseDeDatos = nombreBaseDeDatos;
        url = "jdbc:sqlserver://" + ip + ":1433;" + nombreBaseDeDatos;
    }

    @Override
    public void crearConexion() throws Exception {
        try {
            conexion = DriverManager.getConnection(url, usuario, password);
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ocurrio algun error en al conexion");
        }
    }

    @Override
    public void cerrarConexion() throws Exception {
        conexion.close();
    }

    /*public static void main(String[] args) {
        //aqui calar que lo convierta bien
        SqlServer aux = new SqlServer("","","","Norte","");
        aux.insert("insert into Clientes(IdCliente, Nombre, Estado, Credito, Deuda) values (12, 'yosef', 'durango', 10000, 5000)");
        aux.update("update Clientes set nombre = 'yosef' where IdCliente = 23");
        aux.select("select IdCliente, Nombre, Estado from Clientes where Deuda < 12333");
        aux.delete("delete Clientes where IdCliente = 1");
    }*/

    public String insert(String query) {
        HashMap<String,String> mapeo = ConfiguracionMetodos.mapearAtributos(nombreFragmento);
        Query q = new Query(query);
        String nombreTabla = ConfiguracionMetodos.getNombreTabla(nombreFragmento);
        String sqlCodigo = "INSERT INTO " + nombreTabla + "(";
        ArrayList<String> atributos = q.getAtributosUsados();
        for(int i = 0; i < atributos.size(); i++) {
            if(i < atributos.size()-1) {
                sqlCodigo += mapeo.get(atributos.get(i)) + ",";
                continue;
            }
            sqlCodigo += mapeo.get(atributos.get(i));
        }
        sqlCodigo += ") values (";
        ArrayList<String> expresiones = q.getExpresiones();
        for(int i = 0; i < expresiones.size(); i++) {
            if(i < expresiones.size()-1) {
                sqlCodigo += expresiones.get(i) + ",";
                continue;
            }
            sqlCodigo += expresiones.get(i);
        }
        sqlCodigo += ")";
        return sqlCodigo;
    }
    public String delete(String query) {
        HashMap<String,String> mapeo = ConfiguracionMetodos.mapearAtributos(nombreFragmento);
        Query q = new Query(query);
        String nombreTabla = ConfiguracionMetodos.getNombreTabla(nombreFragmento);
        //delete from clientes where nombre = 'yosef'
        String sqlCodigo = "DELETE FROM " + nombreTabla + " ";
        String[] condicion = q.getCondicion().split(" ");
        for(int i = 0; i < condicion.length; i++) {
            if(mapeo.containsKey(condicion[i])) {
                sqlCodigo += mapeo.get(condicion[i]);
            }else {
                sqlCodigo += condicion[i];
            }
        }
        return sqlCodigo;
    }

    public static void main(String[] args) {

    }

    public String select(String query) {
        Query q = new Query(query);
        String nombreTabla = ConfiguracionMetodos.getNombreTabla(nombreFragmento);
        HashMap<String,String> mapeo = ConfiguracionMetodos.mapearAtributos(nombreFragmento);
        String sqlCodigo = "SELECT ";
        ArrayList<String> atributos = q.getAtributosUsados();
        if(!atributos.get(0).equals("*")) {
            for(int i = 0; i < atributos.size(); i++) {
                if(i < atributos.size()-1) {
                    sqlCodigo += mapeo.get(atributos.get(i)) + ",";
                    continue;
                }
                sqlCodigo += mapeo.get(atributos.get(i));
            }
        }else {
            sqlCodigo += "*";
        }
        sqlCodigo += " FROM " + nombreTabla + " ";
        String[] condicion = q.getCondicion().split(" ");
        for(String cond : condicion) {
            if(mapeo.containsKey(cond)) {
                sqlCodigo += mapeo.get(cond);
            }else {
                sqlCodigo += cond;
            }
        }
        return sqlCodigo;
    }
    public String update(String query) {
        HashMap<String,String> mapeo = ConfiguracionMetodos.mapearAtributos(nombreFragmento);
        Query q = new Query(query);
        String nombreTabla = ConfiguracionMetodos.getNombreTabla(nombreFragmento);
        String sqlCodigo = "UPDATE " + nombreTabla + " SET ";
        sqlCodigo += q.getAtributosUsados().get(0) + " = " + q.getExpresiones().get(0) + " ";
        String[] condicion = q.getCondicion().split(" ");
        for(String str : condicion) {
            if(mapeo.containsKey(str)) {
                sqlCodigo += mapeo.get(str) + " ";
                continue;
            }
            sqlCodigo += str + " ";
        }
        return sqlCodigo;
    }

    @Override
    public void commit() throws Exception {
        PreparedStatement statement = conexion.prepareStatement("COMMIT TRAN");
        statement.executeUpdate();
    }

    @Override
    public void rollback() throws Exception {
        PreparedStatement statement = conexion.prepareStatement("ROLLBACK TRAN");
        statement.executeUpdate();
    }

    @Override
    public void ejecutarTransaccion() throws Exception {
        String sqlCodigo;
        if(sentenciaAEjecutar.contains("insert")) {
            sqlCodigo = insert(sentenciaAEjecutar);
        }else if(sentenciaAEjecutar.contains("delete")) {
            sqlCodigo = delete(sentenciaAEjecutar);
        }else if(sentenciaAEjecutar.contains("update")) {
            sqlCodigo = update(sentenciaAEjecutar);
        }else {
            sqlCodigo = select(sentenciaAEjecutar);
        }
        crearConexion();

        cerrarConexion();
    }

    @Override
    public void run() {
        try {
            ejecutarTransaccion();
            this.TERMINADO = true;
        } catch (Exception e) {
            this.TERMINADO = false;
            System.out.println("Ocurrio un error en el fragmento: " + nombreFragmento);
        }
    }
    /*public boolean ejecutarTransaccion(String query) {
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
    }*/
}
