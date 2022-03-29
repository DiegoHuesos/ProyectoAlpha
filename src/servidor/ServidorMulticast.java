/**
 * @author Diego Hernández Delgado
 * @author Jesús García Moreno
 *
 * @subject Sistemas Distribuidos
 * @professor Octavio Gutiérrez García
 *
 * @version 1.0
 * @since 28/03/2022
 *
 * @class ServidorMulticast
 */

package servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorMulticast extends Thread{
    private final int MULTICAST_PORT = 6868;
    private final String IP_MULTICAST = "239.192.0.1"; //"225.228.225.228";
    private Juego game;
    private MulticastSocket socket;
    private InetAddress direccion;

    private final int CHANGE_POS_TIME = 1500;

    public ServidorMulticast (Juego g) {
        this.game = g;
    }

    public void despliega() {
        try {
            this.socket = new MulticastSocket(MULTICAST_PORT);
            this.direccion = InetAddress.getByName(IP_MULTICAST);
            socket.joinGroup(direccion);
            System.out.println("¡Multicast desplegado!");
            this.start();
        } catch (IOException ex) {
            System.out.println("Servidor Multicast: Problemas para unirse al grupo.");
            Logger.getLogger(ServidorMulticast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            while (!game.getStatus()) {
                // Enviamos la posicion del topo
                game.mueveTopo();
                String mensaje = "P:" + game.obtenPosicion();
                byte[] m = mensaje.getBytes();
                DatagramPacket messageOut = new DatagramPacket(m, m.length, direccion, MULTICAST_PORT);
                try {
                    socket.send(messageOut);
                    Thread.sleep(CHANGE_POS_TIME);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorMulticast.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServidorMulticast.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // Termina el juego y se reporta el ganador
            String mensaje = "W:" + game.getWinner();
            System.out.println("JUEGO TERMINADO");
            byte[] m = mensaje.getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, direccion, MULTICAST_PORT);
            //Reiniciar el juego
            game.restart();
            try {
                socket.send(messageOut);
                Thread.sleep(CHANGE_POS_TIME);
            } catch (IOException ex) {
                Logger.getLogger(ServidorMulticast.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorMulticast.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
