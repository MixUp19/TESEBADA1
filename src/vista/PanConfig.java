package src.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class PanConfig extends JPanel implements ComponentListener {
    private JLabel lblNombreTabla;
    private JTextField txtNombreTabla;
    private JTable tableConfig;
    private DefaultTableModel modelo;
    private String[] columnNames = {"Fragmento", "Base Datos", "Criterio", "Atributos"};
    private String[][] data ={
            {"","","",""},
            {"","","",""},
            {"","","",""},
            {"","","",""}
    };

    public PanConfig(){
        addComponentListener(this);
        modelo = new DefaultTableModel(data, columnNames) ;
        setLayout(null);
        tableConfig = new JTable(modelo);
        lblNombreTabla= new JLabel("Nombre de la tabla distribuida:");
        txtNombreTabla = new JTextField();
        hazInterfaz();
    }

    public void hazInterfaz() {
        int h = getHeight(), w = getWidth();
        JScrollPane tablapane = new JScrollPane(tableConfig);
        lblNombreTabla.setBounds((int)(w*0.2), (int)(h*0.03), (int)(w*0.2), (int)(h*0.04));
        txtNombreTabla.setBounds((int)(w*0.45), (int)(h*0.03), (int)(w*0.2), (int)(h*0.07));
        tablapane.setBounds((int)(w*0.05), (int)(h*0.15), (int)(w*0.8), (int)(h*0.5));

        add(lblNombreTabla);
        add(txtNombreTabla);
        add(tablapane);
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
}
