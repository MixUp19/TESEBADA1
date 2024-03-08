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
    private JMenuItem mapAtr;
    private JMenuItem conf_tabla_dist;
    public Menu() {

        menu= new JMenuBar();
        setSize(1000,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon("src/ingres.png");

        this.setIconImage(img.getImage());
        add(new JLabel(img), BorderLayout.CENTER);
        crearMenu();
        setVisible(true);
    }
    private void crearMenu(){
        JMenu opciones = new JMenu("Opciones");
        conf_tabla_dist = new JMenuItem("Configuracion De Tabla Distribuida");
        conf= new JMenuItem ("Configuración De Fragmentos");
        tran = new JMenuItem("Transacción");
        con = new JMenuItem("Consulta");
        sal = new JMenuItem("Salir");
        mapAtr= new JMenuItem("Mapeo de Atributos");
        opciones.add(conf_tabla_dist);
        opciones.add(conf);
        opciones.add(mapAtr);
        opciones.add(tran);
        opciones.add(con);
        opciones.add(sal);
        menu.add(opciones);
        add(menu, BorderLayout.NORTH);
    }
    public void hazEscuchas(Controlador c){
        System.out.println("Entra");
        conf_tabla_dist.addActionListener(c);
        mapAtr.addActionListener(c);
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

    public JMenuItem getConf_tabla_dist() {
        return conf_tabla_dist;
    }
    public JMenuItem getMapAtr(){
        return mapAtr;
    }
}