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
        System.out.println("Entra action");
        if(e.getSource() ==menuPantalla.getConf()){
            System.out.println("Entra boton");
            menuPantalla.anadirPanel(new PanConfig());
        }
    }
}
