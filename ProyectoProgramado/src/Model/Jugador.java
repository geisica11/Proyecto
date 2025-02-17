package Model;

import java.io.Serializable;

public class Jugador implements Serializable {

    private String nombre;
    private String clave;
    private int puntaje;

    public Jugador(String nombre, String clave, int puntaje) {
        this.nombre = nombre;
        this.clave = clave;
        this.puntaje = puntaje;
    }

    public Jugador(String nombre, int puntaje) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.clave = "";
    }

    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "Jugador{"
                + "nombre='" + nombre + '\''
                + ", puntaje=" + puntaje
                + '}';
    }
}
