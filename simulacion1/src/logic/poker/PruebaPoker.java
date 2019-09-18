/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.poker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * clase para representar la prueba de poker
 *
 * @author Milton
 */
public class PruebaPoker {

    //contantes para reprensentar las distintas pobabilidades de poker con cinco cartas
    //las posiciones impares de cada arreglo representan las veces en que aparece la carta
    //las podiciones pares de cada arreglo representan el numero de veces que aparece cada probabilidad
    private final int[] DIFERENTE = {1, 5};
    private final int[] UN_PAR = {2, 1};
    private final int[] DOBLE_PAR = {2, 2};
    private final int[] TERCIA = {3, 1};
    private final int[] TERCIA_PAR = {2, 1, 3, 1};
    private final int[] POKER = {4, 1};
    private final int[] QUINTILLA = {5, 1};

    //para representar el numero de cartas
    private int numeroCartas;
    //contantes para representar el numero de probabilidades
    private final int NUMERO_PROBABILIDADES = 7;
    //contante para representar chi2 para el caso de poker
    private double chi2 = 12.59;
    //lista de chi2 dependiendo del numero de cartas
    private double chis2[] = {5.99, 7.81, 9.48, 11.07, 12.59};

    //lista de las diferentes probabilidades
    private Poker[] pokers;

    //metodo constructor
    public PruebaPoker(int numeroCartas) {
        this.numeroCartas = numeroCartas;
        chi2 = chis2[numeroCartas - 1];
        iniciarPoker();
    }

    /**
     * metodo para inicializar las lista de pokers y agregar las probabilidades
     * de aparicion de cada una de ellas
     */
    private void iniciarPoker() {
        this.pokers = new Poker[NUMERO_PROBABILIDADES];
        agregarFrecuenciaObtenida();
    }

    /**
     * metodo para agregar la frecuancia obtenidad de aparicion de cada item de
     * pokers
     */
    private void agregarFrecuenciaObtenida() {
        pokers[0] = new Poker(Tipo.DIFERENTE, 0.3024);
        pokers[1] = new Poker(Tipo.QUINTILLA, 0.0001);
        pokers[2] = new Poker(Tipo.UN_PAR, 0.5040);
        pokers[3] = new Poker(Tipo.DOS_PARES, 0.1080);
        pokers[4] = new Poker(Tipo.TERCIA, 0.0720);
        pokers[5] = new Poker(Tipo.TERCIA_Y_PAR, 0.0090);
        pokers[6] = new Poker(Tipo.POKER, 0.0045);

    }

    /**
     * metodo para pasar un entero a un arreglo
     *
     * @param numero enter
     * @return arreglo del numero
     */
    private int[] aArray(int numero) {
        int[] array = new int[numeroCartas];
        int i = 0;
        while (numero > 0) {
            array[i++] = numero % 10;
            numero /= 10;
        }
        return array;
    }

    /**
     * metodo para encontrar las concidencias de aparacion de cada carta
     *
     * @param numero valor que representa las cartas
     * @return el numero de concidencia de aparicion de cada carta
     */
    private Probabilidad[] obtenerCoicidencias(double numero) {
        BigDecimal formatNumber = new BigDecimal(numero);
        formatNumber = formatNumber.setScale(numeroCartas, RoundingMode.HALF_DOWN);
        numero = formatNumber.doubleValue();
        System.out.println("numero " + numero);
        int n = (int) Math.round(numero * Math.pow(10, numeroCartas));
        System.out.println("n " + n);
        Probabilidad[] probabilidades = new Probabilidad[numeroCartas];
        int[] array = aArray(n);
        for (int i = 0; i < array.length; i++) {
            int cantidad = 0;
            if (array[i] != -1) {
                probabilidades[i] = new Probabilidad(array[i]);
                for (int j = i + 1; j < array.length; j++) {
                    if (array[i] == array[j]) {
                        cantidad++;
                        array[j] = -1;
                    }
                }
                probabilidades[i].setProbabilidad(cantidad);
            }
        }
        return probabilidades;
    }

    /**
     * metodo para saber si una combinacion de cartas es tercia y para
     *
     * @param probabilidades lista de apariciones de las cartas
     * @return si es tercia y par
     */
    private boolean esTerciaPar(Probabilidad[] probabilidades) {
        int cantidad = 0;
        for (int i = 0; i < probabilidades.length; i++) {
            Probabilidad p = probabilidades[i];
            if (p != null) {
                System.out.println("p " + p.probabilidad);
            }
            if (p != null && (p.probabilidad == TERCIA_PAR[2] && esOtra(probabilidades, UN_PAR))) {
                return true;
            }
        }
        return false;
    }

    /**
     * metodo para saber si una combinacion de cartas es un par, doble para,
     * tercia, poker, diferentes o quintilla
     *
     * @param probabilidades ista de apariciones de las cartas
     * @param probabilidad tipo de probabilidad
     * @return si es el tipo de probabilidad especificada
     */
    private boolean esOtra(Probabilidad[] probabilidades, int[] probabilidad) {
        int cantidad = 0;
        for (int i = 0; i < probabilidades.length; i++) {
            Probabilidad p = probabilidades[i];
            if (p != null && p.probabilidad == probabilidad[0]) {
                cantidad++;
            }
        }
        return cantidad == probabilidad[1];
    }

    /**
     * metodo para un tipo de poker
     *
     * @param tipo tipo de poker
     * @return poker que conicide con el tipo especificado
     */
    private Poker buscarPoker(Tipo tipo) {
        for (int i = 0; i < pokers.length; i++) {
            Poker poker = pokers[i];
            if (poker.tipo == tipo) {
                return poker;
            }
        }
        return null;
    }

    /**
     * metodo para obtener la frecuencia esperada
     *
     * @param elementos numero de elementos de prueba
     */
    private void obtenerEi(int elementos) {
        for (int i = 0; i < pokers.length; i++) {
            Poker p = pokers[i];
            p.Ei = p.Oi * elementos;
            System.out.println("e1 " + p.Ei);
        }
    }

    /**
     * metodo para determinar a que tipo de poker corresponde cada una de las
     * coicidencias encontradas
     *
     * @param numeros lista de numeros de prueba
     * @return lista de pokers
     */
    private Poker[] obtenerProbabilidad(ArrayList<Double> numeros) {
        for (int i = 0; i < numeros.size(); i++) {
            double n = numeros.get(i);
            String probabilidad = "";
            System.out.println("n " + n);
            Probabilidad[] probabilidades = obtenerCoicidencias(n);
            if (esOtra(probabilidades, QUINTILLA)) {
                buscarPoker(Tipo.QUINTILLA).cantidad++;
                System.out.println("quintilla");
            } else if (esOtra(probabilidades, POKER)) {
                buscarPoker(Tipo.POKER).cantidad++;
                System.out.println("poker");
            } else if (esTerciaPar(probabilidades)) {
                buscarPoker(Tipo.TERCIA_Y_PAR).cantidad++;
                System.out.println("tercia y par");
            } else if (esOtra(probabilidades, DOBLE_PAR)) {
                buscarPoker(Tipo.DOS_PARES).cantidad++;
                System.out.println("doble par");
            } else if (esOtra(probabilidades, TERCIA)) {
                buscarPoker(Tipo.TERCIA).cantidad++;
                System.out.println("tercia");
            } else if (esOtra(probabilidades, UN_PAR)) {
                buscarPoker(Tipo.UN_PAR).cantidad++;
                System.out.println("par");
            } else if (esOtra(probabilidades, DIFERENTE)) {
                buscarPoker(Tipo.DIFERENTE).cantidad++;
                System.out.println("diferente");
            }
        }
        imprimirResultados();
        return pokers;
    }

    /**
     * metodo para imprir los resultados de los pokers
     */
    private void imprimirResultados() {
        for (int i = 0; i < pokers.length; i++) {
            Poker poker = pokers[i];
            System.out.println("" + poker);
        }
    }

    /**
     * metodo para normalizar lso numeros de entrada
     *
     * @param numeros lista de numeros de prueba
     * @return lista de numeros normalizados
     */
    public ArrayList<Double> obtenerRiNormalizados(ArrayList<Double> numeros) {
        double max = obtenerMax(numeros);
        double min = obtenerMin(numeros);
        ArrayList<Double> listaNormalizados = new ArrayList<>();
        for (double ni : numeros) {
            double rn = (ni - min) / (max - min);
            if (rn != 1 && rn != 0) {
                listaNormalizados.add(rn);
            }
        }
        return listaNormalizados;
    }

    /**
     * metodo par obtener el numero mas grande de una lista de numeros
     *
     * @param numeros lista de numeros
     * @return numero mas grande
     */
    private double obtenerMax(ArrayList<Double> numeros) {
        double max = numeros.get(0);
        for (int i = 1; i < numeros.size(); i++) {
            if (numeros.get(i) > max) {
                max = numeros.get(i);
            }
        }
        return max;
    }

    /**
     * metodo para obtener el numero mas pequeño de una lista de numeros
     *
     * @param numeros lista de numeros
     * @return numero mas pequeño
     */
    private double obtenerMin(ArrayList<Double> numeros) {
        double min = numeros.get(0);
        for (int i = 1; i < numeros.size(); i++) {
            if (numeros.get(i) < min) {
                min = numeros.get(i);
            }
        }
        return min;
    }

    /**
     * metodo par verificar la prueba de poker
     *
     * @param numeros lista de numeros de prueba
     * @return si pasa la prueba de poker o no
     */
    public boolean calcular(ArrayList<Double> numeros) {
        numeros = obtenerRiNormalizados(numeros);
        iniciarPoker();
        obtenerProbabilidad(numeros);
        obtenerEi(numeros.size());
        double estadisticoChi2 = 0;
        for (int i = 0; i < pokers.length; i++) {
            Poker poker = pokers[i];
            System.out.println("---- " + (Math.pow(poker.Ei - poker.cantidad, 2) / poker.Ei));
            estadisticoChi2 += (Math.pow(poker.Ei - poker.cantidad, 2) / poker.Ei);
        }
        System.out.println("estchi2 " + estadisticoChi2 + "  chi2 " + chi2);
        return estadisticoChi2 < chi2;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("" + 0.14411 * 100000);
        PruebaPoker p = new PruebaPoker(5);

        BigDecimal formatNumber = new BigDecimal(0.0754);
        formatNumber = formatNumber.setScale(5, RoundingMode.HALF_DOWN);
        System.out.println(formatNumber.doubleValue());

//        BigDecimal formatNumber = new BigDecimal(0.02029);
//        formatNumber = formatNumber.setScale(5, RoundingMode.DOWN);
//        System.out.println(formatNumber.doubleValue());
//        String numero = "82818";
//        System.out.println("numero: " + numero);
//        System.out.println("Probabilidad: " + p.obtenerProbabilidades(numero));
//        System.out.println(p.obtenerCoicidencias(0.14411)[0]);
//        System.out.println(p.obtenerCoicidencias(0.14411)[1]);
//        System.out.println(p.obtenerCoicidencias(0.14411)[2]);
//        System.out.println(p.obtenerCoicidencias(0.14411)[3]);
//        System.out.println(p.obtenerCoicidencias(0.14411)[4]);
//        for (int i = 0; i < p.aArray(14411).length; i++) {
//            System.out.println(p.aArray(14411)[i]);
//
//        }
        double li[] = {10.0, 9.0, 10.0, 9.0, 10.0, 10.0, 9.0, 9.0, 9.0, 10.0, 9.0, 9.0, 9.0, 9.0, 10.0, 10.0, 10.0, 10.0, 10.0, 9.0, 10.0, 9.0, 9.0, 10.0, 10.0, 10.0, 10.0};
        ArrayList<Double> list = new ArrayList<>();
        list.add(0.06141);
        list.add(0.72484);
        list.add(0.94107);
        list.add(0.56766);
        list.add(0.14411);
        list.add(0.87648);
        list.add(0.81792);
        list.add(0.48999);
        list.add(0.18590);
        list.add(0.06060);
        list.add(0.11223);
        list.add(0.64794);
        list.add(0.52953);
        list.add(0.50502);
        list.add(0.30444);
        list.add(0.70688);
        list.add(0.25357);
        list.add(0.31555);
        list.add(0.04127);
        list.add(0.67347);
        list.add(0.28103);
        list.add(0.99367);
        list.add(0.44598);
        list.add(0.73997);
        list.add(0.27813);
        list.add(0.62182);
        list.add(0.82578);
        list.add(0.85923);
        list.add(0.51483);
        list.add(0.09099);

        System.out.println("list " + list);

        ArrayList<Double> l = p.obtenerRiNormalizados(list);
        System.out.println("normailza");
        System.out.println(l);

        System.out.println(p.calcular(list));
    }

}
