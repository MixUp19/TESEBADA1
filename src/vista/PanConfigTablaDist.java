package src.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;

public class PanConfigTablaDist extends JPanel implements ComponentListener, ActionListener {
    private JLabel labelNombreTabla;
    private JLabel labelAtributos;
    private JTextField nombreTablaTxt, atributosTxt;
    private JButton btnGuardar;
    public PanConfigTablaDist() {
        addComponentListener(this);
        labelAtributos = new JLabel("Lista de Atributos:");
        labelNombreTabla = new JLabel("Nombre de la tabla:");
        nombreTablaTxt = new JTextField();
        atributosTxt = new JTextField();
        btnGuardar = new JButton("Guardar");
        setLayout(null);
        hazInterfaz();
        this.btnGuardar.addActionListener(this);
    }

    public void hazInterfaz() {
        int w = getWidth();
        int h = getHeight();

        labelAtributos.setBounds((int)(w*0.2), (int)(h*0.25), (int)(w*0.50), (int)(h*0.34));
        labelNombreTabla.setBounds((int)(w*0.2), (int)(h*0.08), (int)(w*0.33), (int)(h*0.34));
        nombreTablaTxt.setBounds((int)(w*0.38), (int)(h*0.20), (int)(w*0.33), (int)(h*0.10));
        atributosTxt.setBounds((int)(w*0.38), (int)(h*0.38), (int)(w*0.33), (int)(h*0.10));
        btnGuardar.setBounds((int)(w*0.38), (int)(h*0.7), (int)(w*0.2), (int)(h*0.08));

        add(labelAtributos);
        add(labelNombreTabla);
        add(nombreTablaTxt);
        add(atributosTxt);
        add(btnGuardar);
        cargarConfiguracion();
    }

    public void cargarConfiguracion() {
        String path = "src/Configuracion/config_tabla_dist_file.txt";
        File archivo;
        try {
            archivo = new File(path);
            if(!archivo.exists()) {
                return;
            }
            BufferedReader buffReader = new BufferedReader(new FileReader(archivo));
            String linea = buffReader.readLine();
            linea = buffReader.readLine();
            while(linea != null) {
                String[] renglon = linea.split("-");
                this.nombreTablaTxt.setText(renglon[0]);
                this.atributosTxt.setText(renglon[1]);
                linea = buffReader.readLine();
            }
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("Ha ocurrido un error con el archivo");
        }
    }

    public void guardarConfiguracion() {
        String path = "src/Configuracion/config_tabla_dist_file.txt";
        File archivo;
        try {
            archivo = new File(path);
            archivo.createNewFile();
            FileWriter writer = new FileWriter(archivo);
            writer.write("");
            writer.write("NOMBRE DE TABLA-ATRIBUTOS\n");
            String nombreTabla, atributos;
            nombreTabla = nombreTablaTxt.getText();
            atributos = atributosTxt.getText();
            writer.write(nombreTabla + "-" + atributos);
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("Ha ocurrido un error con el archivo");
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        hazInterfaz();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnGuardar){
            guardarConfiguracion();
        }
    }
}
