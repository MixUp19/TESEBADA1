package src.controlador;

import src.archivos.Archivos;
import src.vista.PanConfigTablaDist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ControladorConfigTablaDist implements ComponentListener, ActionListener {
    private PanConfigTablaDist vista;
    private Archivos archivos;
    public ControladorConfigTablaDist(PanConfigTablaDist vista){
        this.vista= vista;
        archivos = new Archivos();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.getBtnGuardar()){
            String[] aux= vista.obtenerConfiguracion();
            archivos.guardarConfiguracionTablaDist(aux[0],aux[1]);
        }
    }
    @Override
    public void componentResized(ComponentEvent e) {
        vista.hazInterfaz();
        try {
            String config[]= archivos.cargarConfiguracionTablaDist();
            vista.plasmarConfiguracion(config[0], config[1]);
        }catch (NullPointerException ex){

        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }


}
