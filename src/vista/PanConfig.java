package src.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;

public class PanConfig extends JPanel implements ComponentListener, ActionListener {
    private JTable tableConfig;
    private DefaultTableModel modelo;
    private JButton guardarConfiguracionBtn;
    private String[] columnNames = {"Fragmento", "Base De Datos", "Tabla", "Criterio", "Atributos", "Estado"};
    private String[][] data ={
            {"","","","","",""},
            {"","","","","",""},
            {"","","","","",""},
            {"","","","","",""},
    };

    public PanConfig(){
        addComponentListener(this);
        modelo = new DefaultTableModel(data, columnNames);
        guardarConfiguracionBtn = new JButton("Guardar");
        setLayout(null);
        tableConfig = new JTable(modelo);
        hazInterfaz();

        guardarConfiguracionBtn.addActionListener(this);
    }

    public void hazInterfaz() {
        int h = getHeight(), w = getWidth();
        JScrollPane tablapane = new JScrollPane(tableConfig);
        tablapane.setBounds((int)(w*0.09), (int)(h*0.09), (int)(w*0.8), (int)(h*0.5));
        guardarConfiguracionBtn.setBounds((int)(w*0.38), (int)(h*0.7), (int)(w*0.2), (int)(h*0.08));

        removeAll();

        add(guardarConfiguracionBtn);

        cargarConfiguracion(tableConfig);

        add(tablapane);
    }

    public void cargarConfiguracion(JTable tablaConfiguracion) {
        String path = "src/Configuracion/config_file.txt";
        File archivo;
        try {
            archivo = new File(path);
            BufferedReader buffReader = new BufferedReader(new FileReader(archivo));
            String linea = buffReader.readLine();
            linea = buffReader.readLine();
            int row = 0;
            while(linea != null) {
                String[] renglon = linea.split("-");
                tablaConfiguracion.getModel().setValueAt(renglon[0],row,0);
                tablaConfiguracion.getModel().setValueAt(renglon[1],row,1);
                tablaConfiguracion.getModel().setValueAt(renglon[2],row,2);
                tablaConfiguracion.getModel().setValueAt(renglon[3],row,3);
                tablaConfiguracion.getModel().setValueAt(renglon[4],row,4);
                tablaConfiguracion.getModel().setValueAt(renglon[5],row,5);
                linea = buffReader.readLine();
                row++;
            }
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("Ha ocurrido un error con el archivo");
        }
    }
    public static void guardarConfiguracion(JTable tablaConfiguracion) {
        String path = "src/Configuracion/config_file.txt";
        File archivo;
        try {
            archivo = new File(path);
            archivo.createNewFile();
            FileWriter writer = new FileWriter(archivo);
            writer.write("");
            writer.write("FRAGMENTO-BASE DE DATOS-TABLA-CRITERIO-ATRIBUTOS-ESTADO\n");

            if (tablaConfiguracion.getCellEditor() != null) {
                tablaConfiguracion.getCellEditor().stopCellEditing();
            }

            for(int i = 0; i < tablaConfiguracion.getRowCount(); i++) {
                String fragmento, baseDeDatos, criterio, atributos, estado, tabla;
                fragmento = tablaConfiguracion.getModel().getValueAt(i,0).toString();
                baseDeDatos = tablaConfiguracion.getModel().getValueAt(i,1).toString();
                criterio = tablaConfiguracion.getModel().getValueAt(i,2).toString();
                tabla = tablaConfiguracion.getModel().getValueAt(i,3).toString();
                atributos = tablaConfiguracion.getModel().getValueAt(i,4).toString();
                estado = tablaConfiguracion.getModel().getValueAt(i,5).toString();
                if(fragmento.isEmpty() || baseDeDatos.isEmpty() || criterio.isEmpty() || atributos.isEmpty() || estado.isEmpty() || tabla.isEmpty()) {
                    continue;
                }
                String renglon = fragmento + "-" + baseDeDatos + "-" + tabla + "-" + criterio + "-" + atributos + "-" +  estado + "\n";
                writer.write(renglon);
            }
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
        if(e.getSource() == guardarConfiguracionBtn) {
            guardarConfiguracion(tableConfig);
        }
    }
}
