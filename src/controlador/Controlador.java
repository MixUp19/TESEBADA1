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
        if(e.getSource() == menuPantalla.getItemConfigTablaDist()) {
            menuPantalla.anadirPanel(new PanConfigTablaDist());
            return;
        }
        if(e.getSource() ==menuPantalla.getItemConfiguracion()){
            menuPantalla.anadirPanel(new PanConfig());
            return;
        }
        if(e.getSource() == menuPantalla.getItemTransaccion()){
            menuPantalla.anadirPanel(new PanTran());
            return;
        }
        if (e.getSource()== menuPantalla.getItemConsulta()){
            menuPantalla.anadirPanel(new PanCon());
            return;
        }
        if(e.getSource()== menuPantalla.getItemMapaAtributo()){
            menuPantalla.anadirPanel(new PanMapeoAtr());
        }
        if(e.getSource()== menuPantalla.getItemSalir()){
            System.exit(0);
        }
    }
}
