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
 * Además, obtiene los tiempos de respuesta de cada una de las comunicaciones remotas, con las cuales calcula
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

        rmiText = "RMI," + cliente.getName() + "," + cliente.getRmi().getRegisterTime();
        tcpText = "TCP," + cliente.getName() + "," + cliente.getTcp().getResponseTime().get(0);
        tcpMeanText = "TCP-mean," + cliente.getName() + "," + Estresador.mean(cliente.getTcp().getResponseTime());
        tcpStdDevText = "TCP-stdDev," + cliente.getName() + "," + Estresador.stdDev(cliente.getTcp().getResponseTime());
        erroresTcpText = "Errores-TCP," + cliente.getName() + "," + cliente.getTcp().getErroresTCP()/cliente.getTcp().getResponseTime().size();

        System.out.println(rmiText);
        System.out.println(tcpText);
        System.out.println(tcpMeanText);
        System.out.println(tcpStdDevText);
        System.out.println(erroresTcpText);



        if(cliente.getRmi().isError()){
            System.out.println("Errores-RMI," + cliente.getName() + "," + 1);
        }else{
            System.out.println("Errores-RMI," + cliente.getName() + "," + 0);
        }
    }

}
