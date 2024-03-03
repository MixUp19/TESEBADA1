package src.controlador;

import src.vista.PanTran;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorTran implements ActionListener {
    PanTran v;
    public ControladorTran(PanTran v){
        this.v=v;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        v.ponerMensaje(v.obtenerInstruccion());
    }
}
