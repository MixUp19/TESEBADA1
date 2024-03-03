package src.vista;

import src.controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    private JMenuBar menu;
    private JMenuItem conf;
    private JMenuItem tran;
    private JMenuItem con;
    private JMenuItem sal;
    public Menu() {

        menu= new JMenuBar();
        setSize(1000,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        crearMenu();
        setVisible(true);
    }
    private void crearMenu(){
        JMenu opciones = new JMenu("Opciones");
        conf= new JMenuItem ("Configuración.");
        tran = new JMenuItem("Transacción");
        con = new JMenuItem("Consulta");
        sal = new JMenuItem("Salir");
        opciones.add(conf);
        opciones.add(tran);
        opciones.add(con);
        opciones.add(sal);
        menu.add(opciones);
        add(menu, BorderLayout.NORTH);
    }
    public void hazEscuchas(Controlador c){
        System.out.println("Entra");
        conf.addActionListener(c);
        tran.addActionListener(c);
        con.addActionListener(c);
        sal.addActionListener(c);
    }
    public void anadirPanel(JPanel panel){
        getContentPane().removeAll();
        add(menu,BorderLayout.NORTH);
        add(panel,BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public JMenuItem getConf() {
        return conf;
    }

    public JMenuItem getTran() {
        return tran;
    }

    public JMenuItem getCon() {
        return con;
    }

    public JMenuItem getSal() {
        return sal;
    }
}