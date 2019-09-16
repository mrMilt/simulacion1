/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

/**
 *
 * @author Milton
 */
public class Probabilidad {
    
    protected int numero;
    protected int probabilidad;

    public Probabilidad(int numero) {
        this.numero = numero;
        this.probabilidad = 1;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(int probabilidad) {
        this.probabilidad += probabilidad;
    }       

    @Override
    public String toString() {
        return "Probabilidad{" + "numero=" + numero + ", probabilidad=" + probabilidad + '}';
    }
}
