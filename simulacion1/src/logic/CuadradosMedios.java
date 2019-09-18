/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;

/**
 * clase para representar em metodo de cuadrados medios
 *
 * @author Milton
 */
public class CuadradosMedios {

    //Numero semilla necesario para el metodo
    private String semilla;

    /**
     * Metodo para extraer las cifras de la mitad de un numero
     *
     * @param numero numero al cuadrado
     * @param k longitud a izquierda y derecha para extraer las cifras de la
     * mitad
     * @return cifras de la mitad del numero
     */
    private int extraerX(String numero, int k) {
        int longitud = numero.length();
        if (longitud % 2 > 0 || longitud < semilla.length()) {
            numero = rellenar(numero);
        }
        return Integer.parseInt(numero.substring((semilla.length() / 2) - k, (semilla.length() / 2) + k));
    }

    /**
     * Metodo para rellenar los nueros cuando son impares o faltan cifras
     *
     * @param numero
     * @return numero rellenado con cifras faltantes
     */
    private String rellenar(String numero) {
        int diferencia = (semilla.length() - numero.length());
        for (int i = 0; i < diferencia; i++) {
            numero = "0" + numero;
        }
        return numero;
    }

    /**
     * Metodo para calcular los numero pseudoaleatorios por el metodo de
     * cuadrados medios
     *
     * @param cantidadNumeros cantidad de numeros pseudoaleatorios
     * @param semilla numero inicial
     * @param k longitud a izquierda y derecha para extraer las cifras de la
     * mitad
     * @return lista de numeros pseudoaleatorios
     */
    public ArrayList<Double> calcular(int cantidadNumeros, int semilla, int k) {
        String semilla2 = (semilla * semilla) + "";
        this.semilla = (semilla2.length() % 2 > 0) ? "0" + semilla2 : semilla2 + "";
        ArrayList<Double> Ri = new ArrayList<>();
        if (k > 0 && k <= (semilla + "").length() / 2) {
            int x = extraerX(this.semilla, k);
            for (int i = 0; i < cantidadNumeros && x != 0; i++) {
                Ri.add((double) (x / Math.pow(10, k * 2)));
//            System.out.println(C"x " + x);
                x = extraerX((x * x) + "", k);
            }
        }
        return Ri;
    }
}
