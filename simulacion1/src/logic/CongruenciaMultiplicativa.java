/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;

/**
 *
 * @author Julian
 */
public class CongruenciaMultiplicativa {

    private int xo;
    private int t;
    private int d;

    public CongruenciaMultiplicativa(int xo, int t, int d) {
        this.xo = xo;
        this.t = 8 * t + 3;
        this.d = 2 * d;
    }

    public ArrayList<Double> obtenerNumerosAleatorios(int cantidadNumeros) {
        ArrayList<Double> numerosAleatorios = new ArrayList<>();
        double xi = (t * xo) % d;
        numerosAleatorios.add(xi / (d - 1));
        for (int i = 0; i < cantidadNumeros-1; i++) {
            xi = (t * xi) % d;
            numerosAleatorios.add(xi / (d - 1));
        }
        return numerosAleatorios;
    }
}
