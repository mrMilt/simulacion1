/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;

/**
 *
 * @author Milton
 */
public class CuadradosMedios {

    private String semilla;

    private int extraerX(String numero, int k) {
        int longitud = numero.length();
        if (longitud % 2 > 0) {
            if (!semilla.equals(numero)) {
                numero = rellenar(numero);
            } else {
                numero = "0" + numero;
            }
            longitud++;
        }

        System.out.println("n " + numero);
//        System.out.println("" + numero.substring((longitud / 2) - k, (longitud / 2) + k));
        return Integer.parseInt(numero.substring((longitud / 2) - k, (longitud / 2) + k));
    }

    private String rellenar(String numero) {
        System.out.println("" + semilla.length() + "  " + numero.length());
        for (int i = 0; i < (semilla.length() - numero.length()); i++) {
            numero = "0" + numero;
        }
        return numero;
    }

    public ArrayList<Double> calcular(int cantidadNumeros, int semilla, int k) {
        this.semilla = (semilla * semilla) + "";
        ArrayList<Double> Ri = new ArrayList<>();
        if (k > 0 && k <= (semilla + "").length() / 2) {
            int x = extraerX((semilla * semilla) + "", k);
            for (int i = 0; i < cantidadNumeros && x != 0; i++) {
                Ri.add((double) (x / Math.pow(10, k * 2)));
//            System.out.println(C"x " + x);
                x = extraerX((x * x) + "", k);
            }
        }
        return Ri;
    }
}
