package src.controlador;

import src.vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener {
   src.vista.Menu menuPantalla ;
   public Controlador(Menu menuPantalla){
       this.menuPantalla = menuPantalla;
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() ==menuPantalla.getConf()){
            menuPantalla.anadirPanel(new PanConfig());
            return;
        }
        if(e.getSource() == menuPantalla.getTran()){
            menuPantalla.anadirPanel(new PanTran());
            return;
        }
        if (e.getSource()== menuPantalla.getCon()){
            menuPantalla.anadirPanel(new PanCon());
        }
    }
}
