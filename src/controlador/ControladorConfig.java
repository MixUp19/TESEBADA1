package src.controlador;

import src.archivos.Archivos;
import src.vista.PanConfig;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ControladorConfig implements ActionListener, ComponentListener {
    private PanConfig vista;
    private Archivos archivos;
    public ControladorConfig(PanConfig vista){
        this.vista= vista;
        archivos = new Archivos();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.getBtnAnadirFila()){
            vista.anadirFila();
            return;
        }
        if(e.getSource() == vista.getGuardarConfiguracionBtn()) {
            archivos.guardarConfiguracionFragmentos(vista.obtenerConfiguracion());
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        vista.hazInterfaz();
        if(vista.isCreada())
            return;
        vista.llenarTabla(archivos.cargarConfiguracionFragmentos());
        vista.setCreada(true);
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
