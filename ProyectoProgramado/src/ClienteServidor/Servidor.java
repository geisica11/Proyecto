package ClienteServidor;

import Model.Jugador;
import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {

    private static final int PUERTO = 5555;
    private static List<Jugador> jugadores = new ArrayList<>();

    public static void main(String[] args) {
        jugadores = XMLManager.cargarJugadores();

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor en espera de conexiones...");

            while (true) {
                Socket cliente = serverSocket.accept();
                new HiloCliente(cliente).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void actualizarRanking(String nombre, int puntaje) {
        boolean encontrado = false;
        for (Jugador j : jugadores) {
            if (j.getNombre().equals(nombre)) {
                j.setPuntaje(Math.max(j.getPuntaje(), puntaje));
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            jugadores.add(new Jugador(nombre, puntaje));
        }

        jugadores.sort((a, b) -> Integer.compare(b.getPuntaje(), a.getPuntaje()));
        XMLManager.guardarJugadores(jugadores);
    }

    static List<Jugador> obtenerRanking() {
        return jugadores.size() > 10 ? jugadores.subList(0, 10) : jugadores;
    }
}
