package ClienteServidor;

import java.io.*;
import java.net.*;

public class HiloCliente extends Thread {

    private Socket socket;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;

    public HiloCliente(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            entrada = new ObjectInputStream(socket.getInputStream());
            salida = new ObjectOutputStream(socket.getOutputStream());

            String accion = (String) entrada.readObject();

            switch (accion) {
                case "LOGIN":
                    manejarLogin();
                    break;
                case "REGISTRO":
                    manejarRegistro();
                    break;
                case "RANKING":
                    enviarRanking();
                    break;
                case "ACTUALIZAR_PUNTAJE":
                    actualizarPuntaje();
                    break;
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void manejarLogin() throws IOException, ClassNotFoundException {
        String nombre = (String) entrada.readObject();
        String clave = (String) entrada.readObject();

        boolean existe = XMLManager.verificarJugador(nombre, clave);
        salida.writeObject(existe);
    }

    private void manejarRegistro() throws IOException, ClassNotFoundException {
        String nombre = (String) entrada.readObject();
        String clave = (String) entrada.readObject();

        boolean registrado = XMLManager.registrarJugador(nombre, clave);
        salida.writeObject(registrado);
    }

    private void enviarRanking() throws IOException {
        salida.writeObject(Servidor.obtenerRanking());
    }

    private void actualizarPuntaje() throws IOException, ClassNotFoundException {
        String nombre = (String) entrada.readObject();
        int puntaje = (int) entrada.readObject();
        Servidor.actualizarRanking(nombre, puntaje);
    }
}
