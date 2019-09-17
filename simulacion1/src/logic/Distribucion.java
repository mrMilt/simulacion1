/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;

/**
 * @author Julian
 */
public class Distribucion {
    
    /**
     * Funcion que calcula la normal invertida
     * @param v el valor a calcular la normal invertida
     * @return el valor de la normal invertida
     */
    public static double calculaNormInv(double v) {  //Funcion de distribucion de probabilidad normal inversa
        double acumulador = 0.00000028666;
        double i;
        for (i = -5; acumulador < v; i = i + 0.00001) {
            acumulador = acumulador + (0.00001 * calculaz(i - 0.000005));
        }
        return i;
    }

    /**
     * Calcula el valor de z de la normal
     * @param v el valor a calcular la z
     * @return el valor de la z de la normal
     */
    public static double calculaz(double v) { //funcion de densidad de probabilidad normal
        double N = Math.exp(-Math.pow(v, 2) / 2) / Math.sqrt(2 * Math.PI);
        return N;
    }
    /**
     * 
     * @param listaRi
     * @param min
     * @param max
     * @return 
     */
    public static  ArrayList<Double> obtenerNiNormales(ArrayList<Double> listaRi) {
        ArrayList<Double> lN = new ArrayList<>();
         for (int i = 0; i < listaRi.size(); i++) {
             lN.add(i, calculaNormInv(listaRi.get(i)));
         }
        return lN;
    }
     
    public static ArrayList<Double> obtenerNiUniformes(ArrayList<Double> listaRi, int min, int max) {
        ArrayList<Double> lN = new ArrayList<>();
        for (int i = 0; i < listaRi.size(); i++) {
            lN.add(i, min+(max-min)*listaRi.get(i));
        }
        return lN;
    }
}