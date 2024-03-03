package src.controlador;

import src.vista.PanCon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorCon implements ActionListener {
    PanCon v;
    public ControladorCon(PanCon v){
        this.v=v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String [] encabezados = {"ID", "Nombre", "Estado", "Credito", "Deuda"};
        Object [][] datos= {{"Esto","es","una","prueba",":)"}};
        v.mostrarConsulta(datos, encabezados);
    }
}
