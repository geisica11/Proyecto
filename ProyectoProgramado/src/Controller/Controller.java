package Controller;

import View.Login;
import Model.Jugador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.*;

public class Controller {

    private Login vista;
    private static final String HOST = "localhost";
    private static final int PUERTO = 5555;

    public Controller(Login vista) {
        this.vista = vista;
        initController();
    }

   private void initController() {
    vista.getBtnRegistrar().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            registrarJugador();
        }
    });

    vista.getBtnIngresar().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ingresarJugador();
        }
    });
}

    private void registrarJugador() {
        String nombre = vista.getJtxNombre().getText().trim();
        String clave = vista.getJtxContrasena().getText().trim();

        if (nombre.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe ingresar nombre y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Socket socket = new Socket(HOST, PUERTO); 
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream()); 
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())) {

            salida.writeObject("REGISTRO");
            salida.writeObject(nombre);
            salida.writeObject(clave);

            boolean registrado = (boolean) entrada.readObject();
            if (registrado) {
                JOptionPane.showMessageDialog(vista, "Registro exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista, "El nombre ya está en uso.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(vista, "Error de conexión con el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void ingresarJugador() {
        String nombre = vista.getJtxNombre().getText().trim();
        String clave = vista.getJtxContrasena().getText().trim();

        if (nombre.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe ingresar nombre y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Socket socket = new Socket(HOST, PUERTO); 
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream()); 
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())) {

            salida.writeObject("LOGIN");
            salida.writeObject(nombre);
            salida.writeObject(clave);

            boolean loginExitoso = (boolean) entrada.readObject();
            if (loginExitoso) {
                JOptionPane.showMessageDialog(vista, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                abrirMenuPrincipal();
            } else {
                JOptionPane.showMessageDialog(vista, "Nombre o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(vista, "Error de conexión con el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void abrirMenuPrincipal() {
        // Lógica para abrir el menú del juego
        JOptionPane.showMessageDialog(vista, "Abriendo menú del juego...", "Espere", JOptionPane.INFORMATION_MESSAGE);
    }
}
