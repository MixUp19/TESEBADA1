package src.controlador;
import src.Conexiones.BaseCentralizada;
import src.vista.ParticionarVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorParticionar implements ActionListener {
    private ParticionarVista Pan;
    private BaseCentralizada Base;

    public ControladorParticionar(ParticionarVista a) {
        this.Pan=a;
        Base= new BaseCentralizada();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Base.cargarTuplas();
    }
}
