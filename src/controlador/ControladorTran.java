package src.controlador;

import src.Compilador.Analizador;
import src.vista.PanTran;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorTran implements ActionListener {
    PanTran v;
    Analizador Analizador;
    public ControladorTran(PanTran v){
        this.v=v;
        Analizador= new Analizador();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, Analizador.realizarAnalisisLexico(v.obtenerInstruccion()),"Mensaje", JOptionPane.INFORMATION_MESSAGE);
        Analizador.realizarAnalisisSintactico();
        v.ponerMensaje(v.obtenerInstruccion());
    }
}
