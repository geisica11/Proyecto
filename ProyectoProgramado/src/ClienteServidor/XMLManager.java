package ClienteServidor;

import Model.Jugador;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.*;

public class XMLManager {

    private static final String FILE_NAME = "jugadores.xml";

    public static List<Jugador> cargarJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        try {
            File archivo = new File(FILE_NAME);
            if (!archivo.exists()) {
                return jugadores;
            }

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(archivo);
            NodeList lista = doc.getElementsByTagName("jugador");

            for (int i = 0; i < lista.getLength(); i++) {
                Element elemento = (Element) lista.item(i);
                String nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
                String clave = elemento.getElementsByTagName("clave").item(0).getTextContent();
                int puntaje = Integer.parseInt(elemento.getElementsByTagName("puntaje").item(0).getTextContent());
                jugadores.add(new Jugador(nombre, clave, puntaje));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jugadores;
    }

    public static void guardarJugadores(List<Jugador> jugadores) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = doc.createElement("jugadores");
            doc.appendChild(root);

            for (Jugador j : jugadores) {
                Element jugador = doc.createElement("jugador");

                Element nombre = doc.createElement("nombre");
                nombre.appendChild(doc.createTextNode(j.getNombre()));

                Element clave = doc.createElement("clave");
                clave.appendChild(doc.createTextNode(j.getClave()));

                Element puntaje = doc.createElement("puntaje");
                puntaje.appendChild(doc.createTextNode(String.valueOf(j.getPuntaje())));

                jugador.appendChild(nombre);
                jugador.appendChild(clave);
                jugador.appendChild(puntaje);
                root.appendChild(jugador);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(FILE_NAME)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean verificarJugador(String nombre, String clave) {
        return cargarJugadores().stream().anyMatch(j -> j.getNombre().equals(nombre) && j.getClave().equals(clave));
    }

    public static boolean registrarJugador(String nombre, String clave) {
        List<Jugador> jugadores = cargarJugadores();
        if (jugadores.stream().anyMatch(j -> j.getNombre().equals(nombre))) {
            return false;
        }
        jugadores.add(new Jugador(nombre, clave, 0));
        guardarJugadores(jugadores);
        return true;
    }
}
