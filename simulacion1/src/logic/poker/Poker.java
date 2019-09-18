/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.poker;

/**
 *clase para representar cada tipo de probabilidad de poker
 * @author Milton
 */
public class Poker {
    
    //tipo de probabilidad
    protected Tipo tipo;
    //cantidad de veces que aparece
    protected int cantidad;
    //frecuencia obtenida 
    protected double Oi;
    //frecuencia esperada
    protected double Ei;

    
    //metodo constructor
    public Poker(Tipo tipo, double probabilidad) {
        this.tipo = tipo;
        this.cantidad = 0;
        this.Oi = probabilidad;
        this.Ei = 0;
    }
    
    //geters y seters

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getProbabilidad() {
        return Oi;
    }

    public void setProbabilidad(double probabilidad) {
        this.Oi = probabilidad;
    }

    public double getEi() {
        return Ei;
    }

    public void setEi(double Ei) {
        this.Ei = Ei;
    }       

    @Override
    public String toString() {
        return "Poker{" + "tipo=" + tipo + ", cantidad=" + cantidad + '}';
    }
}
