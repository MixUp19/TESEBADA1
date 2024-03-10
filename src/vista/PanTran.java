package src.vista;

import src.controlador.ControladorTran;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class PanTran extends JPanel implements ComponentListener {
    private JLabel lbltransaccion;
    private  JTextField txtTransaccion;
    private JButton btnTransaccion;
    private JLabel lblMsg;
    private ControladorTran c;
    private Font fuente;
    public PanTran(){
        fuente = new Font("Verdana", Font.PLAIN, 16);
        setLayout(null);
        setBackground(new Color(241, 255, 198));
        c= new ControladorTran(this);
        lbltransaccion = new JLabel("Transacción:");
        lbltransaccion.setFont(fuente);
        txtTransaccion = new JTextField();
        txtTransaccion.setFont(fuente);
        btnTransaccion = new JButton("Ejecutar.");
        btnTransaccion.setFont(fuente);
        lblMsg = new JLabel("Esto es un mensaje de prueba");
        addComponentListener(this);
        hazInterfaz();
        hazEscuchas();
    }
    private void hazEscuchas(){
        txtTransaccion.addActionListener(c);
        btnTransaccion.addActionListener(c);
    }
    private void hazInterfaz(){
        int h = getHeight(), w = getWidth();
        lbltransaccion.setBounds((int)(w*0.2), (int)(h*0.41),(int)(w*0.1), (int)(h*0.03));
        txtTransaccion.setBounds((int)(w*0.3), (int)(h*0.4),(int)(w*0.3), (int)(h*0.05));
        btnTransaccion.setBounds((int)(w*0.62), (int)(h*0.4),(int)(w*0.2), (int)(h*0.05));
        lblMsg.setBounds((int)(w*0.01), (int)(h*0.6),(int)(w*0.9), (int)(h*0.03));

        lblMsg.setVisible(false);
        add(lbltransaccion);
        add(txtTransaccion);
        add(btnTransaccion);
        add(lblMsg);
    }
    public String obtenerInstruccion(){
        return txtTransaccion.getText();
    }
    public void ponerMensaje(String msg){
        lblMsg.setText(msg);
        lblMsg.setVisible(false);
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
