package src.vista;
import src.controlador.ControladorParticionar;
import javax.swing.*;
import java.awt.*;

public class ParticionarVista extends JPanel {
    private ControladorParticionar c;
    private JButton particionar;
    private JLabel texto;
    public ParticionarVista()  {
        setSize(840, 600);
        setVisible(true);
        this.setLayout(null);
        init();
    }


    private void init() {
        int h = getHeight(), w = getWidth();
        particionar = new JButton("Particionar");
        particionar.setBounds((int) (w * 0.48), (int) (h * 0.7), (int) (w * 0.2), (int) (h * 0.08));
        texto = new JLabel("Particionar tabla");
        texto.setBounds((int) (w * 0.48), (int) (h * 0.41), (int) (w * 0.5), (int) (h * 0.03));
        texto.setFont(new Font("Verdana", Font.PLAIN, 20));


        add(particionar);
        add(texto);
        c = new ControladorParticionar(this);
        particionar.addActionListener(c);
    }
}
