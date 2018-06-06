package com.noeliaLP.principal.prueba;

import java.util.ArrayList;
import java.util.HashMap;

public class prueba {


    public HashMap split(String color, ArrayList colores) {

        HashMap listaColores= new HashMap();

        String[] algo = color.split("-");

        String temp = "";

        for (int i = 0; i < algo.length; i++) {

            for (int j = 0; j < colores.size(); j++) {

                if (algo[i].equals(colores.get(j))) {
                    colores.remove(j);
                }
            }
        }

        for (int j = 0; j < colores.size(); j++) {
            String temp2 = "";
            temp = (String) colores.get(j);
            temp2+= temp+"-"+color;
            listaColores.put(colores.get(j),temp2);

        }

        return listaColores;

    }

    public HashMap listadoColoresSeleccionados(String color, ArrayList colores){

        HashMap listaColores= new HashMap();
        String[] algo = color.split("-");


        for (int i = 0; i < algo.length; i++) {
            String temp3 = color;
            String temp4 = temp3.replace(algo[i],"");
            String temp5 = temp4.replace("-"," ");
            String temp6 = temp5.trim();
            if( temp6.replaceAll("\\s+", "-").equals("")){
                listaColores.put(algo[i], null);
            }else{
                listaColores.put(algo[i], temp6.replaceAll("\\s+", "-"));
            }
        }

        return listaColores;
    }


    public String consultaColores(String colores){

        String[] color = colores.split("-");

        String consultaColor= "SELECT id_producto,o_color,o_foto, o_fotob FROM otroscolores WHERE id_producto=ID AND o_color=";
        String OR_Color = "OR o_color=";

        consultaColor +=  "? ";
        for (int i = 1; i <color.length; i++){

            consultaColor +=  OR_Color+"? ";

        }
        consultaColor += "GROUP BY id_producto";

        return consultaColor;

    }

//    public static void main(String[] args) {
//    }
}
