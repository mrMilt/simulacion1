/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import UI.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lib.RunPoker;
import logic.CuadradosMedios;

/**
 *
 * @author Milton
 */
public class Controlador implements ActionListener {

    private CuadradosMedios cuadradosMedios;
    private RunPoker runPoker;

    private Main main;

    public Controlador() {
        this.cuadradosMedios = new CuadradosMedios();
        this.runPoker = new RunPoker();
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
        }
    }

    private void obtenerCuadradosMedios() {
        main.agregarNumeros(cuadradosMedios.calcular(Integer.parseInt(main.getTxtCantidad()), Integer.parseInt(main.getTxtSemilla()), Integer.parseInt(main.getTxtK())));
    }

    private void pruebasAleatoriedad() {
        if (main.pruebaPokerSeleccionada()) {
            boolean esCorrecta = runPoker.calcular(main.getNumeros());
            main.setLblPoker(esCorrecta ? "<Pasa la prueba>" : "<No pasa la prueba>");
        }
    }

}