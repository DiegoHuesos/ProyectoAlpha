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
 * @class ClienteThread
 */

package estres;

import cliente.Cliente;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase ClientThread que extiende la Superclase Thread y funge como un Cliente con todos los clientes
 * necesarios, genera tiros aleatorios mientras no se haya terminado la partida.
 * Además, obtiene los tiempos de respuesta de cada una de las comunicaciones remotas, con las cuales calculará
 * la desviación estándar.
 */

public class ClienteThread extends Thread{

    private Cliente cliente;
    private static int CLIENT_SLEEP_TIME = 100;
    String rmiText;
    String tcpText;
    String tcpMeanText;
    String tcpStdDevText;
    String erroresTcpText;
    String text;

    public ClienteThread(String name) {
        cliente = new Cliente(name);
    }

    @Override
    public void run() {
        cliente.start();
        Random rand = new Random();
        while (cliente.isInGame()) {
            int x = rand.nextInt(Globals.topos); // Tiros aleatorios para las pruebas
            cliente.hitTopo(x);
            try {
                Thread.sleep(CLIENT_SLEEP_TIME);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        rmiText = "Tiempo de respuesta RMI" + cliente.getRmi().getRegisterTime();
        System.out.println(rmiText);

    }

}
