package src;

import src.vista.Menu;
import src.controlador.Controlador;
import src.vista.PanConfig;

public class Main{
    public static void main(String[] args) {
        Menu m = new Menu();
        Controlador c = new Controlador(m);
        m.hazEscuchas(c);
        PanConfig p = new PanConfig();
    }
}