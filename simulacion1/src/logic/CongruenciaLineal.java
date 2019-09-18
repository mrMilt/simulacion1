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
public class CongruenciaLineal {

    private int xo;
    private int k;
    private int c;
    private int g;
    
    /**
     * metodo constructor
     * @param xo es la semilla
     * @param k el multiplicador = 1 + 2k
     * @param c constante aditiva
     * @param g modulo = 2^g
     */
    public CongruenciaLineal(int xo, int k, int c, int g) {
        this.xo = xo;
        this.k = 1 + 2 * k;
        this.c = c;
        this.g = (int) Math.pow(2, g);
    }

    /**
     * genera numeros pseudoaletorios puros empleando el metodo congruencial lineal
     * @param cantidadNumeros la cantidad de numeros pseudoaleatorios que deseamos generar
     * @return ArrayList de numeros pseudoaleatorios puros
     */
    public ArrayList<Double> obtenerRi(int cantidadNumeros) {
        ArrayList<Double> numerosAleatorios = new ArrayList<>();
        double xi = (k * xo + c) % g;
        numerosAleatorios.add(xi / (g - 1));
        for (int i = 0; i < cantidadNumeros - 1; i++) {
            xi = (k * xi + c) % g;
            numerosAleatorios.add(xi / (g - 1));
        }
        return numerosAleatorios;
    }
}