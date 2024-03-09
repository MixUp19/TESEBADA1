package src.vista;

import src.controlador.ControladorConfigTablaDist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;

public class PanConfigTablaDist extends JPanel {
    private JLabel labelNombreTabla;
    private JLabel labelAtributos;
    private JTextField nombreTablaTxt, atributosTxt;
    private JButton btnGuardar;
    private ControladorConfigTablaDist c;
    private Font fuente;
    public PanConfigTablaDist() {
        fuente = new Font("Verdana", Font.PLAIN, 16);
        c= new ControladorConfigTablaDist(this);
        addComponentListener(c);
        labelAtributos = new JLabel("Lista de Atributos:");
        labelAtributos.setFont(fuente);
        labelNombreTabla = new JLabel("Nombre de la tabla:");
        labelNombreTabla.setFont(fuente);
        nombreTablaTxt = new JTextField();
        nombreTablaTxt.setFont(fuente);
        atributosTxt = new JTextField();
        atributosTxt.setFont(fuente);
        btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(fuente);
        setLayout(null);
        setBackground(new Color(241, 255, 198));
        hazInterfaz();
        this.btnGuardar.addActionListener(c);
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
    }
    public String[] obtenerConfiguracion(){
        return new String[]{nombreTablaTxt.getText(), atributosTxt.getText()};
    }
    public JButton getBtnGuardar() {
        return btnGuardar;
    }
    public void plasmarConfiguracion(String tabla, String atributos){
        nombreTablaTxt.setText(tabla);
        atributosTxt.setText(atributos);
    }
}
