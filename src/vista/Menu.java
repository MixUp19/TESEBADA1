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
        conf= new JMenuItem ("Configuración.");
        tran = new JMenuItem("Transacción");
        con = new JMenuItem("Consulta");
        sal = new JMenuItem("Salir");
        menu.add(conf);
        menu.add(tran);
        menu.add(con);
        menu.add(sal);
        add(menu, BorderLayout.NORTH);
    }
    public void hazEscuchas(Controlador c){
        System.out.println("Entra");
        conf.addActionListener(c);
        tran.addActionListener(c);
        con.addActionListener(c);
        sal.addActionListener(c);
    }
    public void anadirPanel(PanConfig panel){
        add(panel,BorderLayout.CENTER);
        revalidate(); // Revalidar el contenido para que se actualice
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