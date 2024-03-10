package src.controlador;

import src.Compilador.Analizador;
import src.Configuracion.ManejadorFragmentos;
import src.vista.PanTran;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorTran implements ActionListener {
    PanTran v;
    Analizador Analizador;
    ManejadorFragmentos Manejador;
    public ControladorTran(PanTran v){
        this.v=v;
        Analizador= new Analizador();
        Manejador = new ManejadorFragmentos();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(Analizador.realizarAnalisisLexico(v.obtenerInstruccion().toLowerCase())){
            JOptionPane.showMessageDialog(null, Analizador.getTxtAreaErrores(), "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Analizador.realizarAnalisisSintactico();

        JOptionPane.showMessageDialog(null, Manejador.verificadorZonaActiva(v.obtenerInstruccion().toLowerCase()), "Mensaje", JOptionPane.INFORMATION_MESSAGE);




    }
}
