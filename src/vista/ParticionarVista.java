package src.vista;
import src.controlador.ControladorParticionar;
import javax.swing.*;
import java.awt.*;

public class ParticionarVista extends JPanel {
    private ControladorParticionar c;
    private JButton particionar;
    private JLabel texto;
    private Font fuente;
    public ParticionarVista()  {
        setSize(840, 600);
        setBackground(new Color(241, 255, 198));
        setVisible(true);
        this.setLayout(null);
        init();
    }


    private void init() {
        fuente = new Font("Verdana", Font.PLAIN, 20);
        int h = getHeight(), w = getWidth();
        particionar = disenoBoton("Particionar");
        particionar.setBounds((int) (w * 0.48), (int) (h * 0.7), (int) (w * 0.2), (int) (h * 0.08));
        texto = disenoLabel("Particionar tabla");
        texto.setBounds((int) (w * 0.48), (int) (h * 0.41), (int) (w * 0.5), (int) (h * 0.03));


        add(particionar);
        add(texto);
        c = new ControladorParticionar(this);
        particionar.addActionListener(c);
    }
    private JButton disenoBoton(String texto) {
        JButton button = new JButton(texto);
        button.setFont(fuente);
        button.setBackground(new Color(123, 200, 164));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }
    private JLabel disenoLabel(String texto) {
        JLabel label = new JLabel();
        label.setFont(fuente);
        label.setText(texto);
        return label;
    }
}
