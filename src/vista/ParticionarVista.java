package src.vista;
import src.controlador.ControladorParticionar;
import javax.swing.*;
import java.awt.*;



public class ParticionarVista extends JPanel {
    private ControladorParticionar c;
    private Font fuente;
    private JButton particionar;

    public ParticionarVista()  {
        fuente = new Font("Verdana", Font.PLAIN, 16);
        setSize(840, 600);
        setVisible(true);
        this.setLayout(new BorderLayout());
        init();
    }


    private void init() {
        particionar = new JButton("Particionar");
        add(particionar, BorderLayout.CENTER);
        particionar.addActionListener(c);
        c= new ControladorParticionar(this);
    }


}
