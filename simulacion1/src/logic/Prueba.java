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
public class Prueba {
    
    /**
     * normaliza una lista de numeros 
     * @param listaNi numeros pseudoaleatorios que se quieren normalizar
     * @return lista de numeros normalizados
     */
    private static ArrayList<Double> obtenerRiNormalizados(ArrayList<Double> listaNi){
        double max = obtenerMax(listaNi);
        double min = obtenerMin(listaNi);
        ArrayList<Double> listaNormalizados = new ArrayList<>();
        for (double ni : listaNi) {
            listaNormalizados.add((ni-min)/(max-min));
        }
        return listaNormalizados;
    }
    /**
     * Obtiene el numero mayor de una lista de numeros
     * @param listaNi lista de numeros a evaluar
     * @return valor mayor de la lista
     */
    private static double obtenerMax(ArrayList<Double> listaNi) {
        double max = listaNi.get(0);
        for (int i = 1; i < listaNi.size(); i++) {
            if(listaNi.get(i) > max){
                max = listaNi.get(i);
            }
        }
        return max;
    }

    /**
     * Obtiene el numero menor de una lista de numeros
     * @param listaNi lista de numeros a evaluar
     * @return valor menor de la lista
     */
    private static double obtenerMin(ArrayList<Double> listaNi) {
        double min = listaNi.get(0);
        for (int i = 1; i < listaNi.size(); i++) {
            if(listaNi.get(i) < min){
                min = listaNi.get(i);
            }
        }
        return min;
    }
    
    /**
     * obtiene el promedio de una lista de numero
     * @param listaNi lista de numeros a evaluar
     * @return promedio de la lista
     */
    private static double obtenerPromedio(ArrayList<Double> listaNi){
        double promedio = 0;
        for (double ni : listaNi) {
            promedio += ni;
        }
        return promedio/listaNi.size();
    }
    
    /**
     * Ejecuta la prueba de medias
     * @param listaNi numeros pseudoaleatorios a los que se les va a aplicar la prueba
     * @param error valor entre 0 y 1 que indica la tolerancia o margen de error que se va a admitir en la prueba
     * @return true si pasa la prueba de lo contrario false
     */
    public static boolean ejecutarPruebaMedias(ArrayList<Double> listaNi, double error){
        ArrayList<Double> listaRi = obtenerRiNormalizados(listaNi);
        double r = obtenerPromedio(listaRi);
        int n = listaRi.size();
        double z = Distribucion.calculaNormInv(1-(error/2));
        double limiteInfe = 0.5-(z*(1/Math.sqrt(12*n)));
        double limiteSupe = 0.5+(z*(1/Math.sqrt(12*n)));
        return (r >= limiteInfe && r <= limiteSupe);
    }
    
    /**
     * Aplica la formula chi2 y retorna su valor
     * @param probabilidad el valor de la probabilidad
     * @param gradosLivertad los grados de libertad
     * @return el valor de la chi2
     */
    public static double chi2Invert(double probabilidad, double gradosLivertad) {
        double raiz = Math.sqrt(2 / (9 * gradosLivertad));
        double z = Distribucion.calculaNormInv(1 - probabilidad);
        return gradosLivertad * Math.pow(1 - (2 / (9 * gradosLivertad)) + (z * raiz), 3);
    }
    
    /**
     * aplica la prueba de varianza
     * @param listaNi numeros pseudoaleatorios a los que se les va a aplicar la prueba
     * @param error valor entre 0 y 1 que indica la tolerancia o margen de error que se va a admitir en la prueba
     * @return true si pasa la prueba de lo contrario false
     */
    public static boolean ejecutarPruebaVarianza(ArrayList<Double> listaNi, double error){
        ArrayList<Double> listaRi = obtenerRiNormalizados(listaNi);
        int n = listaRi.size();
        double varianza = calcularVarianza(listaRi);
        double chiInfe = chi2Invert(error/2, n-1);
        double chiSup = chi2Invert(1-(error/2), n-1);
        double limitInfe = chiInfe/(12*(n-1));
        double limitSupe = chiSup/(12*n-1);
        return (varianza >= limitSupe && varianza <= limitInfe);
    }
    /**
     * Calcula la varianza de una lista de numeros
     * @param listaRi lista de numeros a evaluar
     * @return valor de la varianza 
     */
    private static double calcularVarianza(ArrayList<Double> listaRi) {
        double promedio = obtenerPromedio(listaRi);
        double varianza = 0;
        for (double v : listaRi) {
            varianza += Math.pow(v - promedio, 2);
        }
        return (varianza) / (listaRi.size() - 1);
    }
    /**
     * ejecuta la prueba Kolmogórov-Smirnov
     * @param listaNi numeros pseudoaleatorios a los que se les va a aplicar la prueba
     * @return true si pasa la prueba de lo contrario false
     */
    public static boolean ejecutarPruebaKS(ArrayList<Double> listaNi){
        ArrayList<Double> listaRi = obtenerRiNormalizados(listaNi);
        int n = listaRi.size();
        double[] intervalos = new double[10];
        int[] frecuencias = new int[10];
        double tamanioIntervalo = 0;
        int cantidad =0;
        for (int i = 0; i < intervalos.length; i++) {
            intervalos[i] = tamanioIntervalo+=0.1;
            for (double ri : listaRi) {
                if(ri >= (intervalos[i]-0.1) && ri < intervalos[i]){
                    frecuencias[i] ++;
                }
            }
            cantidad += frecuencias[i];
        }
        if(cantidad > n){
            frecuencias[8] = frecuencias[8]-1;
        }else if(cantidad < n){
            frecuencias[8] = frecuencias[8]+1;
        }
        double frecuenciaAcomulada = frecuencias[0];
        double frecuenciaEsperada = n/10;
        double diferencia = Math.abs((frecuenciaEsperada/n)-(frecuenciaAcomulada/n));
        for (int i = 1; i < 10; i++) {
            frecuenciaAcomulada += frecuencias[i];
            frecuenciaEsperada += n/10;
            if(Math.abs((frecuenciaEsperada/n)-(frecuenciaAcomulada/n)) > diferencia){
                diferencia = Math.abs((frecuenciaEsperada/n)-(frecuenciaAcomulada/n));
            }
        }
        return diferencia < (1.36) / Math.sqrt(n);
    }
    /**
     * aplica la prueba chi2
     * @param listaNi numeros pseudoaleatorios a los que se les va a aplicar la prueba
     * @param cantidadIntervalos numero de intervalos para la prueba
     * @return true si pasa la prueba de lo contrario false
     */
    public static boolean ejecutarPruebaChi2(ArrayList<Double> listaNi, int cantidadIntervalos){
        double[] intervalos = new double[cantidadIntervalos];
        double[] frecuencias = new double[cantidadIntervalos];
        double max = obtenerMax(listaNi);
        double min = obtenerMin(listaNi);
        double tamanioIntervalo = min;
        double salto = (min+(max-min)/cantidadIntervalos)- min;
        double frecuenciaEsperada = listaNi.size()/(cantidadIntervalos+0.0);
        double chi2 =0;
        for (int i = 0; i < cantidadIntervalos; i++) {
            intervalos[i] = tamanioIntervalo+=salto;
            for (double ri : listaNi) {
                if(ri >= (intervalos[i]-salto) && ri < intervalos[i]){
                    frecuencias[i] ++;
                }
            }
            chi2 += Math.pow(frecuencias[i]-frecuenciaEsperada, 2)/frecuenciaEsperada;
        }
        return chi2Invert(0.05, cantidadIntervalos-1) > chi2;
    }
}