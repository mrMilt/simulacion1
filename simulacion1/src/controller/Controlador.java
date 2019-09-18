/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import UI.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logic.CongruenciaLineal;
import logic.CongruenciaMultiplicativa;
import logic.CuadradosMedios;
import logic.Distribucion;
import logic.Prueba;
import logic.poker.PruebaPoker;

/**
 *
 * @author Milton
 */
public class Controlador implements ActionListener {

    private CuadradosMedios cuadradosMedios;
    private PruebaPoker pruebaPoker;

    private Main main;

    public Controlador() {
        this.cuadradosMedios = new CuadradosMedios();
        this.pruebaPoker = new PruebaPoker(5);
        this.main = new Main(this);
        this.main.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Acciones.valueOf(e.getActionCommand())) {
            case CUADRADOS_MEDIOS:
                obtenerCuadradosMedios();
                break;
            case PRUEBAS_ALEATORIEDAD:
                pruebasAleatoriedad();
                break;
            case CONGRUENCIA_LINEAL:
                obtenerCOngruencialLineal();
                break;
            case CONGRUENCIA_MULTIPLICATIVA:
                obtenerCongruenciaMultiplicativa();
                break;
            case PRUEBAS_UNIFORMIDAD:
                pruebasUniformidad();
                break;
        }
    }

    private void obtenerCuadradosMedios() {
        switch(main.getDistribucionSeleccionada()){
            case 0:
                main.agregarNumeros(cuadradosMedios.calcular(Integer.parseInt(main.getTxtCantidad()), Integer.parseInt(main.getTxtSemilla()), Integer.parseInt(main.getTxtK())));
                break;
            case 1:
                main.agregarNumeros(Distribucion.obtenerNiNormales(cuadradosMedios.calcular(Integer.parseInt(main.getTxtCantidad()), Integer.parseInt(main.getTxtSemilla()), Integer.parseInt(main.getTxtK()))));
                break;
            case 2:
                main.agregarNumeros(Distribucion.obtenerNiUniformes(cuadradosMedios.calcular(Integer.parseInt(main.getTxtCantidad()), Integer.parseInt(main.getTxtSemilla()), Integer.parseInt(main.getTxtK())), main.getMinimo(), main.getMaximo()));
                break;
        }
    }

    private void pruebasAleatoriedad() {
        if (main.pruebaPokerSeleccionada()) {
            boolean esCorrecta = pruebaPoker.calcular(main.getNumeros());
            main.setLblPoker(esCorrecta ? "<Pasa la prueba>" : "<No pasa la prueba>");
        }
    }
    
    private void pruebasUniformidad() {
        if(main.pruebaChi2()){
            main.setLblChi2(Prueba.ejecutarPruebaChi2(main.getNumeros(), main.getCantidadIntervalosChi2())? "<Pasa la prueba>" : "<No pasa la prueba>");
        }
        if(main.pruebaKS()){
            main.setLblKS(Prueba.ejecutarPruebaKS(main.getNumeros())? "<Pasa la prueba>" : "<No pasa la prueba>");
        }
        if(main.pruebaMedias()){
            main.setLblMedias(Prueba.ejecutarPruebaMedias(main.getNumeros(), 0.05)? "<Pasa la prueba>" : "<No pasa la prueba>");
        }
        if(main.pruebaVarianza()){
            main.setLblVariazas(Prueba.ejecutarPruebaVarianza(main.getNumeros(), 0.05)? "<Pasa la prueba>" : "<No pasa la prueba>");
        }
    }

    private void obtenerCOngruencialLineal() {
        CongruenciaLineal cl = new CongruenciaLineal(main.getTxtX0(), main.getTxtKCL(), main.getTxtC(), main.getG());
        switch(main.getDistribucionSeleccionadaCL()){
            case 0:
                main.agregarNumeros(cl.obtenerRi(main.getCantidadCL()));
                break;
            case 1:
                main.agregarNumeros(Distribucion.obtenerNiNormales(cl.obtenerRi(main.getCantidadCL())));
                break;
            case 2:
                main.agregarNumeros(Distribucion.obtenerNiUniformes(cl.obtenerRi(main.getCantidadCL()), main.getMinimo1(), main.getMaximo1()));
                break;
        }
    }

    private void obtenerCongruenciaMultiplicativa() {
        CongruenciaMultiplicativa cm = new CongruenciaMultiplicativa(main.getXoCM(), main.getT(), main.getD());
        switch(main.getDistribucionSeleccionadaCM()){
            case 0:
                main.agregarNumeros(cm.obtenerNumerosAleatorios(main.getCantidadCM()));
                break;
            case 1:
                main.agregarNumeros(Distribucion.obtenerNiNormales(cm.obtenerNumerosAleatorios(main.getCantidadCM())));
                break;
            case 2:
                main.agregarNumeros(Distribucion.obtenerNiUniformes(cm.obtenerNumerosAleatorios(main.getCantidadCM()),main.getMinimo2(), main.getMaximo2()));
                break;
        }
    }
}
