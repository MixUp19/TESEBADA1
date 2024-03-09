package src.vista;

import src.controlador.ControladorCon;
import src.controlador.ControladorTran;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class PanCon extends JPanel implements ComponentListener {
    private JLabel lblConsulta;
    private  JTextField txtConsulta;
    private JButton btnConsulta;
    private JLabel lblMsg;
    private ControladorCon c;
    private DefaultTableModel modelo;
    private JTable tablaConsulta;
    private JScrollPane tablaPanel;
    private Font fuente;
    public PanCon(){
        fuente = new Font("Verdana", Font.PLAIN, 16);
        setBackground(new Color(241, 255, 198));
        setLayout(null);
        addComponentListener(this);
        c = new ControladorCon(this);
        tablaPanel= new JScrollPane();
        lblConsulta = new JLabel("Consultar: ");
        lblConsulta.setFont(fuente);
        txtConsulta = new JTextField();
        txtConsulta.setFont(fuente);
        btnConsulta = new JButton("Consultar");
        lblMsg= new JLabel();
        lblMsg.setFont(fuente);
        hazInterfaz();
        hazEscuchas();
    }
    private void hazEscuchas(){
        txtConsulta.addActionListener(c);
        btnConsulta.addActionListener(c);
    }
    private void hazInterfaz(){
        int h = getHeight(), w = getWidth();
        lblConsulta.setBounds((int)(w*0.2), (int)(h*0.21),(int)(w*0.1), (int)(h*0.03));
        txtConsulta.setBounds((int)(w*0.3), (int)(h*0.2),(int)(w*0.3), (int)(h*0.05));
        btnConsulta.setBounds((int)(w*0.62), (int)(h*0.2),(int)(w*0.2), (int)(h*0.05));
        lblMsg.setBounds((int)(w*0.01), (int)(h*0.3),(int)(w*0.9), (int)(h*0.03));
        tablaPanel.setBounds((int)(w*0.01), (int)(h*0.35),(int)(w*0.98), (int)(h*0.5));
        removeAll();
        add(lblConsulta);
        add(txtConsulta);
        add(btnConsulta);
        add(lblMsg);
        add(tablaPanel);
    }
    public String obtenerConsulta(){
        return txtConsulta.getText();
    }
    public void mostrarConsulta(Object[][] datos, String[]encabezados){
        modelo = new DefaultTableModel(datos,encabezados){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaConsulta =new JTable(modelo);
        tablaPanel =new JScrollPane(tablaConsulta);
        hazInterfaz();
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
