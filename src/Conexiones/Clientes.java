package src.Conexiones;

import javax.persistence.Entity;
import java.io.Serializable;
@Entity
public class Clientes implements Serializable {
    private static final long serialVersionUID = 1L;
    int idCliente;
    String nombre;
    String estado;
    double credito;
    double deuda;

    public Clientes(){

    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public double getDeuda() {
        return deuda;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", credito=" + credito +
                ", deuda=" + deuda +
                '}';
    }
}
