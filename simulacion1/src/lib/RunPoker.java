/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.util.ArrayList;

/**
 *
 * @author Milton
 */
public class RunPoker {

    private final int[] DIFERENTE = {1, 5};
    private final int[] UN_PAR = {2, 1};
    private final int[] DOBLE_PAR = {2, 2};
    private final int[] TERCIA = {3, 1};
    private final int[] TERCIA_PAR = {2, 1, 3, 1};
    private final int[] POKER = {4, 1};
    private final int[] QUINTILLA = {5, 1};

    private final int NUMERO_CARTAS = 5;
    private final int NUMERO_PROBABILIDADES = 7;

    private final double CHI2 = 12.59;

    private Poker[] pokers;

    public RunPoker() {
        iniciarPoker();
    }

    private void iniciarPoker() {
        this.pokers = new Poker[NUMERO_PROBABILIDADES];
        agregarPoker();
    }
    
    private void agregarPoker() {
        pokers[0] = new Poker(Tipo.DIFERENTE, 0.3024);
        pokers[1] = new Poker(Tipo.QUINTILLA, 0.0001);
        pokers[2] = new Poker(Tipo.UN_PAR, 0.5040);
        pokers[3] = new Poker(Tipo.DOS_PARES, 0.1080);
        pokers[4] = new Poker(Tipo.TERCIA, 0.0720);
        pokers[5] = new Poker(Tipo.TERCIA_Y_PAR, 0.0090);
        pokers[6] = new Poker(Tipo.POKER, 0.0045);

    }

    private int[] aArray(int numero) {
        int[] array = new int[NUMERO_CARTAS];
        int i = 0;
        while (numero > 0) {
            array[i++] = numero % 10;
            numero /= 10;
        }
        return array;
    }

    private Probabilidad[] obtenerCoicidencias(double numero) {
        int n = (int) Math.round(numero * Math.pow(10, NUMERO_CARTAS));
//        System.out.println(" n " + numero * Math.pow(10, NUMERO_CARTAS));
        Probabilidad[] probabilidades = new Probabilidad[NUMERO_CARTAS];
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

    private Poker buscarPoker(Tipo tipo) {
        for (int i = 0; i < pokers.length; i++) {
            Poker poker = pokers[i];
            if (poker.tipo == tipo) {
                return poker;
            }
        }
        return null;
    }

    private void obtenerEi(int elementos) {
        for (int i = 0; i < pokers.length; i++) {
            Poker p = pokers[i];
            p.Ei = p.Oi * elementos;
            System.out.println("e1 " + p.Ei);
        }
    }

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
        for (int i = 0; i < pokers.length; i++) {
            Poker poker = pokers[i];
            System.out.println("" + poker);
        }
        return pokers;
    }

    /*
    estadistico chi2
    */
    public boolean calcular(ArrayList<Double> numeros) {
        iniciarPoker();
        obtenerProbabilidad(numeros);
        obtenerEi(numeros.size());
        double estadisticoChi2 = 0;
        for (int i = 0; i < pokers.length; i++) {
            Poker poker = pokers[i];
            System.out.println("---- " + (Math.pow(poker.Ei - poker.cantidad, 2) / poker.Ei));
            estadisticoChi2 += (Math.pow(poker.Ei - poker.cantidad, 2) / poker.Ei);
        }
        System.out.println("estchi2 " + estadisticoChi2 + "  chi2 " + CHI2);
        return estadisticoChi2 < CHI2;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("" + 0.14411*100000);
        RunPoker p = new RunPoker();
//        String numero = "82818";
//        System.out.println("numero: " + numero);
//        System.out.println("Probabilidad: " + p.obtenerProbabilidades(numero));
        System.out.println(p.obtenerCoicidencias(0.14411)[0]);
        System.out.println(p.obtenerCoicidencias(0.14411)[1]);
        System.out.println(p.obtenerCoicidencias(0.14411)[2]);
        System.out.println(p.obtenerCoicidencias(0.14411)[3]);
        System.out.println(p.obtenerCoicidencias(0.14411)[4]);
        for (int i = 0; i < p.aArray(14411).length; i++) {
            System.out.println(p.aArray(14411)[i]);
            
        }
        
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

        System.out.println(p.calcular(list));
    }

}
