package src.vista;

import java.util.*;
import java.awt.Image;
import javax.swing.*;
public class Rutinas {
    static Random r=new Random();

    static String []VN ={"Zoila","Daniel","Yessenia","Luis","Anastacia","Plutarco","Alicia","Maria","Sofia","Antonio","Nereida","Carolina",
            "Rebeca","Javier","Luis"};
    static String [] VA={"Garcia","Lopez","Perez","Urias","Mendoza","Coppel","Diaz"};
    static boolean [] Sexo={false,true,false,true,false,true,false,false,false,true,false,false,false,true,true};

    public static void Mensaje(String texto) {
        JOptionPane.showMessageDialog(null,texto, " ******** ",JOptionPane.ERROR_MESSAGE);
        //int resp = JOptionPane.showConfirmDialog(null, "�Est� seguro?");
    }


    public static String nextNombre(int Numero){
        String Nom="",NomTra="";
        int Pos;
        boolean Genero=true;

        for(int i=0;i<Numero;i++){
            Pos=nextInt(VN.length);

            NomTra=VN[Pos]+" ";

            if (i==0){
                Genero=Sexo[Pos];

            }

            if( Genero != Sexo[Pos]  || i>0 && Nom.indexOf(NomTra)>-1    ){
                i--;
                continue;
            }

            Nom+=NomTra;

        }
        for(byte i=0;i<2;i++){
            Nom+=VA[nextInt(VA.length)]+" ";
        }
        return Nom.trim();
    }

    public static int nextInt(int li, int ls) {
        return  r.nextInt(ls-li+1)+li;

    }
    public static int nextInt(int numero) {
        return r.nextInt(numero);
    }

    /*
        public static String PonComas(long cantidad) {
            String texto=cantidad+"";
            int cont=0;
            char car;
            String res="";
            for(int i=texto.length()-1; i>=0 ; i--) {
                car=texto.charAt(i);
                cont++;
                if(cont ==4 ) {
                    res=","+res;
                    cont=1;
                }
                res=car+res;
            }
            return res;
        }
    */
    public static String PonComas(long cantidad) {
        String texto=cantidad+"";

        String res="";
        int l;
        while(texto.length()>3) {
            l=texto.length();
            res=","+texto.substring(l-3)+res;
            texto=texto.substring(0,l-3);


        }
        if(texto.length()>0)
            res=texto+res;
        return res;
    }
    public static String Color() {
        String [] color= {"Azul","Amarillo","Negro","Blanco","Rojo","Verde","Purpura"};
        return color[Rutinas.nextInt(0,color.length-1)];
    }
    public static String PonBlancos(String texto, int largo){
        while(texto.length()< largo)
            texto=texto+" ";
        return texto;

    }
    public static ImageIcon AjustarImagen(String ico,int Ancho,int Alto)
    {
        ImageIcon tmpIconAux = new ImageIcon(ico);
        //Escalar Imagen
        ImageIcon tmpIcon = new ImageIcon(tmpIconAux.getImage().getScaledInstance(Ancho,
                Alto, Image.SCALE_SMOOTH));//SCALE_DEFAULT
        return tmpIcon;
    }
}
