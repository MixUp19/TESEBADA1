package src.vista;

import src.controlador.ControladorConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;

public class PanConfig extends JPanel{
    private JTable tableConfig;
    private DefaultTableModel modelo;
    private JButton guardarConfiguracionBtn, btnAnadirFila;
    private String[] columnNames = {"Nombre Fragmento","Fragmento", "Base De Datos", "Tabla", "Criterio", "Atributos", "Estado"};
    private Object[][] data ={
            {"","","","","","",""},
            {"","","","","","",""},
            {"","","","","","",""},
            {"","","","","","",""},
    };
    private ControladorConfig c;
    private boolean creada;

    public PanConfig(){
        c = new ControladorConfig(this);
        addComponentListener(c);
        modelo = new DefaultTableModel(data, columnNames);
        guardarConfiguracionBtn = new JButton("Guardar");
        btnAnadirFila = new JButton("+");
        setLayout(null);
        setBackground(new Color(241, 255, 198));
        tableConfig = new JTable(modelo);
        hazInterfaz();
        creada= false;
        guardarConfiguracionBtn.addActionListener(c);
        btnAnadirFila.addActionListener(c);
    }

    public void hazInterfaz() {
        int h = getHeight(), w = getWidth();
        JScrollPane tablapane = new JScrollPane(tableConfig);
        tablapane.setBounds((int)(w*0.09), (int)(h*0.09), (int)(w*0.8), (int)(h*0.5));
        btnAnadirFila.setBounds((int)(w*0.9), (int)(h*0.09), (int)(w*0.09), (int)(h*0.05));
        guardarConfiguracionBtn.setBounds((int)(w*0.38), (int)(h*0.7), (int)(w*0.2), (int)(h*0.08));
        removeAll();

        add(guardarConfiguracionBtn);
        add(btnAnadirFila);
        add(tablapane);
    }
    public void anadirFila(){
        data = new Object[tableConfig.getRowCount()+1][tableConfig.getColumnCount()];
        if (tableConfig.getCellEditor() != null) {
            tableConfig.getCellEditor().stopCellEditing();
        }
        for (int i = 0; i < tableConfig.getRowCount() ; i++) {
            for (int j = 0; j < tableConfig.getColumnCount(); j++) {
                data[i][j]=tableConfig.getModel().getValueAt(i,j);
            }
        }
        modelo= new DefaultTableModel(data,columnNames);
        tableConfig.setModel(modelo);
        revalidate();
        repaint();
    }
    public JButton getGuardarConfiguracionBtn() {
        return guardarConfiguracionBtn;
    }

    public void llenarTabla(Object[][]datos){
        System.out.println("0?");
        if(datos.length!= 0){
            data= datos;
        }
        modelo= new DefaultTableModel(data,columnNames);
        tableConfig.setModel(modelo);
        revalidate();
        repaint();
    }

    public JButton getBtnAnadirFila() {
        return btnAnadirFila;
    }
     public String[] obtenerConfiguracion(){
        String[] datos = new String[tableConfig.getRowCount()];
         if (tableConfig.getCellEditor() != null) {
             tableConfig.getCellEditor().stopCellEditing();
         }
         int blancos;
         StringBuilder renglon;
         for(int i = 0; i < tableConfig.getRowCount(); i++) {
             renglon = new StringBuilder();
             blancos=0;
             for (int j = 0; j < tableConfig.getColumnCount(); j++) {
                 String linea= tableConfig.getModel().getValueAt(i, j).toString();
                 if(linea.isEmpty()){
                     blancos++;
                 }
                 renglon.append(linea).append("-");
             }

             if(blancos== tableConfig.getColumnCount()) {
                 continue;
             }
             datos[i] = renglon.substring(0,renglon.length()-1)+"\n";
         }
         return datos;
     }

    public boolean isCreada() {
        return creada;
    }

    public void setCreada(boolean creada){
        this.creada= creada;
    }
}
