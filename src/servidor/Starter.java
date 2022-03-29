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
 * @class Starter
 */

package servidor;

import estres.Globals;

/**
 * La clase Starter es la clase principal que inicializa y coordina todos los servidores del juego.
 */
public class Starter {

    public static void main(String[] args) {

        //Configuraciones necesarias para los protocolos UDP y Multicast
        System.setProperty("sun.net.maxDatagramSockets", "500");
        System.setProperty("java.net.preferIPv4Stack", "true"); // Multicast configuration

        //Se obtienen la cantidad de topos de las variables/configuraciones globales (Clase Globals) del juego
        int n = Globals.topos;

        //Se inicializa la clase juego con los n topos como parámetro
        Juego game = new Juego(n);

        // Se declaran e inicializan los servidores RMI, Multicast y TCP pasándo el objeto del Juego como parámetro
        ServidorRMI rmi = new ServidorRMI(game);
        ServidorMulticast multicast = new ServidorMulticast(game);
        ServidorTCP tcp = new ServidorTCP(game);

        // Se despliegan/corren los servidores RMI, Multicast y TCP
        rmi.despliega();
        multicast.despliega();
        tcp.despliega();
    }

}
