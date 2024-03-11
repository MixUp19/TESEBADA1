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
        String consulta = v.obtenerInstruccion().toLowerCase();
        if(Analizador.realizarAnalisisLexico(consulta)){
            JOptionPane.showMessageDialog(null, Analizador.getTxtAreaErrores(), "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Analizador.realizarAnalisisSintactico();
        consulta = Manejador.verificadorZonaActiva(consulta);
        if(!Manejador.isContinuar()){
            JOptionPane.showMessageDialog(null, consulta, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        System.out.println(consulta);
        Manejador.ejecutarConsulta(consulta);
    }
}
