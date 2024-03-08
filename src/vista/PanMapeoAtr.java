package src.vista;

import src.controlador.ControladorMapeoAtr;

import javax.swing.*;
import java.awt.*;

public class PanMapeoAtr extends JPanel {
    private JComboBox[][] atributosFragmentos;
    private JLabel atributosDistribuida[], nombreFrag[], nombreTabla;
    private JPanel config;
    private ControladorMapeoAtr c;
    private JButton btnGuardar;
    private boolean creado;
    private String nomTabla;
    public PanMapeoAtr(){
        this.setBackground(new Color(201,250,246));
        creado= false;
        c = new ControladorMapeoAtr(this);
        addComponentListener(c);
        btnGuardar= new JButton("Guardar");
        btnGuardar.addActionListener(c);
        config= new JPanel();
        setLayout(new BorderLayout());
        nombreTabla= new JLabel("Nombre de la tabla: ");
    }
    public void hazInterfaz(String[][] atributosFrag, String [] atributosTabla,String[] nombreFrag, String nombreTabla){
        nomTabla= nombreTabla;
        config.setBackground(new Color(201,250,246));;
        creado= true;
        add(btnGuardar,BorderLayout.SOUTH);
        config.setLayout(new GridLayout(atributosTabla.length+1, nombreFrag.length+1));
        this.nombreTabla.setText("Nombre de la tabla: "+nombreTabla);
        add(this.nombreTabla, BorderLayout.NORTH);

        atributosDistribuida = new JLabel[atributosTabla.length];
        for (int i = 0; i < atributosTabla.length; i++) {
            atributosDistribuida[i]= new JLabel(atributosTabla[i]+":");
        }//c

        this.nombreFrag= new JLabel[nombreFrag.length];
        for (int i = 0; i < this.nombreFrag.length; i++) {
            this.nombreFrag[i]= new JLabel(nombreFrag[i]);
        }//C

        atributosFragmentos = new JComboBox[atributosTabla.length][nombreFrag.length];
        for (int i = 0; i < atributosFragmentos.length; i++) {
            for (int j = 0; j < nombreFrag.length; j++) {
                atributosFragmentos[i][j]= new JComboBox<>(atributosFrag[j]);
            }
        }//C

        for (int i = 0; i < atributosTabla.length+1 ; i++) {
            for (int j = 0; j < nombreFrag.length+1; j++) {
                if(i==0&&j==0){
                    config.add(new JLabel("Atributos distribuida"));
                    continue;
                }
                if(i==0) {
                    config.add(this.nombreFrag[j - 1]);
                    continue;
                }
                if(j==0){
                    config.add(this.atributosDistribuida[i-1]);
                    continue;
                }
                config.add(atributosFragmentos[i-1][j-1]);
            }
        }
        add(config,BorderLayout.CENTER);
    }

    public String[] getMapeo(){
        String [] mapeo= new String[atributosFragmentos.length];
        StringBuilder linea;
        for (int i = 0; i < atributosFragmentos.length; i++) {
            linea = new StringBuilder();
            linea.append(atributosDistribuida[i].getText(), 0, atributosDistribuida[i].getText().length()-1).append("-");
            for (int j = 0; j < atributosFragmentos[i].length; j++) {
                linea.append(atributosFragmentos[i][j].getSelectedItem()).append(",");
            }
            mapeo[i]= linea.substring(0, linea.length()-1);
        }
        return mapeo;
    }
    public String getEncabezado(){
        StringBuilder encabezado= new StringBuilder();
        encabezado.append(nomTabla).append("-");
        for (JLabel jLabel : nombreFrag) {
            encabezado.append(jLabel.getText()).append("-");
        }
        return encabezado.substring(0,encabezado.length()-1);
    }
    public void colocarDecisiones(String[] mapeo){
        if(mapeo.length==0){
            return;
        }
        for (int i = 0; i < mapeo.length; i++) {
            String [] tablaAtributo= mapeo[i].split("-");
            String[] atributo = tablaAtributo[1].split(",");
            for (int j = 0; j < atributo.length; j++) {
               atributosFragmentos[i][j].setSelectedItem(atributo[j]);
            }
        }
    }
    public boolean isCreado() {
        return creado;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }
}
