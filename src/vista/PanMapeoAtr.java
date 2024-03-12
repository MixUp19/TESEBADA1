package src.vista;

import src.controlador.ControladorMapeoAtr;

import javax.swing.*;
import java.awt.*;

public class PanMapeoAtr extends JPanel {
    private JComboBox[][] atributosFragmentos;
    private JLabel atributosDistribuida[], nombreFrag[], nombreTabla;
    private final JPanel panelConfiguracion;
    private ControladorMapeoAtr c;
    private JButton btnGuardar;
    private boolean creado;
    private String nomTabla;
    private final Font fuente;
    public PanMapeoAtr(){
        fuente = new Font("Verdana", Font.PLAIN, 16);
        creado= false;
        c = new ControladorMapeoAtr(this);
        addComponentListener(c);
        btnGuardar= disenoBoton("Guardar");
        btnGuardar.addActionListener(c);
        panelConfiguracion = new JPanel();
        panelConfiguracion.setBackground(new Color(241, 255, 198));
        setBackground(new Color(241, 255, 198));
        setLayout(new BorderLayout());
        nombreTabla=disenoLabel("Nombre de la tabla: ");

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
    public void hazInterfaz(String[][] atributosFrag, String [] atributosTabla,String[] nombreFrag, String nombreTabla){
        nomTabla= nombreTabla;
        creado= true;
        add(btnGuardar,BorderLayout.SOUTH);
        panelConfiguracion.setLayout(new GridLayout(atributosTabla.length+1, nombreFrag.length+1));
        this.nombreTabla.setText("Nombre de la tabla: "+nombreTabla);
        add(this.nombreTabla, BorderLayout.NORTH);

        atributosDistribuida = new JLabel[atributosTabla.length];
        for (int i = 0; i < atributosTabla.length; i++) {
            atributosDistribuida[i]= disenoLabel(atributosTabla[i]+":");

        }//c

        this.nombreFrag= new JLabel[nombreFrag.length];
        for (int i = 0; i < this.nombreFrag.length; i++) {
            this.nombreFrag[i]= disenoLabel(nombreFrag[i]);

        }//C

        atributosFragmentos = new JComboBox[atributosTabla.length][nombreFrag.length];
        for (int i = 0; i < atributosFragmentos.length; i++) {
            for (int j = 0; j < nombreFrag.length; j++) {
                atributosFragmentos[i][j]= disenoComboBox(atributosFrag[j]);
            }
        }//C

        for (int i = 0; i < atributosTabla.length+1 ; i++) {
            for (int j = 0; j < nombreFrag.length+1; j++) {
                if(i==0&&j==0){
                    panelConfiguracion.add(disenoLabel("Atributos distribuida"));
                    continue;
                }
                if(i==0) {
                    panelConfiguracion.add(this.nombreFrag[j - 1]);
                    continue;
                }
                if(j==0){
                    panelConfiguracion.add(this.atributosDistribuida[i-1]);
                    continue;
                }
                panelConfiguracion.add(atributosFragmentos[i-1][j-1]);
            }
        }
        add(panelConfiguracion,BorderLayout.CENTER);
    }
    private JComboBox<String> disenoComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(fuente);
        comboBox.setBackground(new Color(201, 250, 226));
        comboBox.setForeground(Color.BLACK);
        return comboBox;
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
        for (int i = 1; i < mapeo.length; i++) {
            String [] tablaAtributo= mapeo[i].split("-");
            String[] atributo = tablaAtributo[1].split(",");
            for (int j = 0; j < atributo.length; j++) {
                atributosFragmentos[i-1][j].setSelectedItem(atributo[j]);
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
