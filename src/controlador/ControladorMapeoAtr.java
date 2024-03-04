package src.controlador;

import src.archivos.Archivos;
import src.vista.PanMapeoAtr;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ControladorMapeoAtr implements ActionListener, ComponentListener {
    private PanMapeoAtr vista;
    private Archivos archivos;
    public ControladorMapeoAtr (PanMapeoAtr vista){
        this.vista = vista;
        archivos = new Archivos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== vista.getBtnGuardar()){
            archivos.guardarMapeoAtributos(vista.getMapeo(), vista.getEncabezado());
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if(vista.isCreado())
            return;
        Object [][] datos= archivos.cargarConfiguracionFragmentos();
        String [][] atributos = new String[datos.length][];
        String [] nombreFrag= new String[datos.length];
        for (int i = 0; i < datos.length; i++) {
            atributos[i] = datos[i][5].toString().split(",");
            nombreFrag[i]= datos[i][0].toString();
        }
        String[] tablaDist= archivos.cargarConfiguracionTablaDist();
        String[] atributosDist = tablaDist[1].split(",");
        vista.hazInterfaz(atributos,atributosDist,nombreFrag,tablaDist[0]);

        String[] mapeo= archivos.cargarMapeoAtributos();
        vista.colocarDecisiones(mapeo);
        vista.revalidate();
        vista.repaint();
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
