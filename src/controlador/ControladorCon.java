package src.controlador;

import src.Compilador.Analizador;
import src.Configuracion.ManejadorFragmentos;
import src.vista.PanCon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControladorCon implements ActionListener {
    PanCon v;
    ManejadorFragmentos Manejador;
    Analizador analizador;

    public ControladorCon(PanCon v){
        this.v=v;
        Manejador = new ManejadorFragmentos();
        analizador = new Analizador();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String consulta = v.obtenerConsulta().toLowerCase();
        /*if(analizador.realizarAnalisisLexico(consulta)){
            JOptionPane.showMessageDialog(null, Analizador.getTxtAreaErrores(), "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            return;
        }*/
        analizador.realizarAnalisisSintactico();
        String[] encabezados = Manejador.obtenerAtributos(consulta);
        consulta = Manejador.verificadorZonaActiva(consulta);
        if(!Manejador.isContinuar()){
            JOptionPane.showMessageDialog(null, consulta, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        System.out.println(consulta);
        Manejador.ejecutarConsulta(consulta);
        String[][] resultado = Manejador.resultadoConsulta();
        v.mostrarConsulta(resultado, encabezados);
    }


}
