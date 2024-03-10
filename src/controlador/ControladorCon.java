package src.controlador;

import src.Configuracion.ManejadorFragmentos;
import src.vista.PanCon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorCon implements ActionListener {
    PanCon v;
    ManejadorFragmentos Manejador;

    public ControladorCon(PanCon v){
        this.v=v;
        Manejador = new ManejadorFragmentos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, Manejador.verificadorZonaActiva(v.obtenerConsulta().toLowerCase()), "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        String [] encabezados = {"ID", "Nombre", "Estado", "Credito", "Deuda"};
        Object [][] datos= {{"Esto","es","una","prueba",":)"}};
        v.mostrarConsulta(datos, encabezados);
    }


}
