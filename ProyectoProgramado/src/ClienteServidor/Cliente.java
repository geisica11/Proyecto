package ClienteServidor;

import java.io.*;
import java.net.*;

public class Cliente {
    private static final String HOST = "localhost";
    private static final int PUERTO = 12345;
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    public Cliente() {
        try {
            socket = new Socket(HOST, PUERTO);
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }

    public boolean login(String nombre, String clave) {
        try {
            salida.writeObject("LOGIN");
            salida.writeObject(nombre);
            salida.writeObject(clave);
            return (boolean) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registrar(String nombre, String clave) {
        try {
            salida.writeObject("REGISTRO");
            salida.writeObject(nombre);
            salida.writeObject(clave);
            return (boolean) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cerrarConexion() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
