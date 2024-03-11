package src.vista;

import src.controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    private JMenuBar menu;
    private JMenuItem itemConfiguracion;
    private JMenuItem itemTransaccion;
    private JMenuItem itemConsulta;
    private JMenuItem itemSalir;
    private JMenuItem itemMapaAtributo;
    private JMenuItem itemConfigTablaDist;
    private JMenuItem itemParticionar;


    public Menu() {
        menu= new JMenuBar();
        menu.setBackground(new Color(233, 251, 152));
        setSize(1000,700);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon("src/ingres.png");
        Image image = img.getImage();
        Image newimg = image.getScaledInstance(500 ,400,  java.awt.Image.SCALE_SMOOTH);
        img = new ImageIcon(newimg);

        this.setIconImage(img.getImage());
        add(new JLabel(img), BorderLayout.CENTER);
        crearMenu();
        setBackground(new Color(241, 255, 198));
        System.out.println();
        setVisible(true);
    }
    private void crearMenu(){
        JMenu opciones = new JMenu("Opciones");

        itemConfigTablaDist = new JMenuItem("Configuracion De Tabla Distribuida");
        itemConfiguracion = new JMenuItem ("Configuración De Fragmentos");
        itemTransaccion = new JMenuItem("Transacción");
        itemConsulta = new JMenuItem("Consulta");
        itemSalir = new JMenuItem("Salir");
        itemMapaAtributo = new JMenuItem("Mapeo de Atributos");
        itemParticionar= new JMenuItem("Particionar");
        opciones.add(itemConfigTablaDist);
        opciones.add(itemConfiguracion);
        opciones.add(itemMapaAtributo);
        opciones.add(itemTransaccion);
        opciones.add(itemConsulta);
        opciones.add(itemParticionar);
        opciones.add(itemSalir);

        menu.add(opciones);
        add(menu, BorderLayout.NORTH);

    }
    public void hazEscuchas(Controlador c){
        System.out.println("Entra");
        itemConfigTablaDist.addActionListener(c);
        itemMapaAtributo.addActionListener(c);
        itemConfiguracion.addActionListener(c);
        itemTransaccion.addActionListener(c);
        itemConsulta.addActionListener(c);
        itemSalir.addActionListener(c);
        itemParticionar.addActionListener(c);
    }
    public void anadirPanel(JPanel panel){
        getContentPane().removeAll();
        add(menu,BorderLayout.NORTH);
        add(panel,BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    public JMenuItem getItemConfiguracion() {
        return itemConfiguracion;
    }
    public JMenuItem getItemTransaccion() {
        return itemTransaccion;
    }
    public JMenuItem getItemConsulta() {
        return itemConsulta;
    }
    public JMenuItem getItemSalir() {
        return itemSalir;
    }
    public JMenuItem getItemConfigTablaDist() {
        return itemConfigTablaDist;
    }
    public JMenuItem getItemMapaAtributo(){
        return itemMapaAtributo;
    }
    public JMenuItem getItemParticionar(){return  itemParticionar}
}