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
 * @class ClienteMulticast
 */

package cliente;

import estres.Globals;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteMulticast extends Thread {
    private Cliente cliente;

    private MulticastSocket socket;
    private InetAddress direccion;

    public ClienteMulticast(Cliente cliente, String IP_MULTICAST, int MULTICAST_PORT) {
        this.cliente = cliente;
        try {
            direccion = InetAddress.getByName(IP_MULTICAST);
            socket = new MulticastSocket(MULTICAST_PORT);
            socket.joinGroup(direccion);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteMulticast.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteMulticast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run(){
        boolean ganador = false;
        while (!ganador) {
            try {
                byte[] buffer = new byte[1000];
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                socket.receive(messageIn);

                String mensaje = (new String(messageIn.getData())).trim();
                System.out.println(mensaje);
                String arr[] = mensaje.split(":");
                if (arr[0].equals("W")) { // Se registra winner
                    ganador = true;
                    cliente.reportWinner(arr[1]);
                } else if (arr[0].equals("P")) {
                    cliente.changeTopo((int) Integer.parseInt(arr[1]));
                }
            } catch (IOException ex) {
                Logger.getLogger(ClienteMulticast.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        socket.close();
    }
}
