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
 * @class Estresador
 */

package estres;

import java.util.List;

/**
 * La clase Estresador genera diferentes clientes ClientThread en diferentes hilos para que se pruebe las respuesta
 * de los servidores y se mida su desempeño.
 */
public class Estresador {

    public static double mean(List<Long> arr){
        long sum = 0;
        for (Long v: arr) sum += v;
        return (double) sum /(double) arr.size();
    }

    public static double stdDev(List<Long> arr){
        double num = 0.0;
        double prom = mean(arr);
        for(Long v:arr) num += Math.pow(v - prom,2);
        return Math.sqrt(num / arr.size());
    }

    public static void main(String args[]) {

        System.setProperty("sun.net.maxDatagramSockets", "500");

        int n = 50;
        ClienteThread[] clientes = new ClienteThread[n];

        //Se itera para instanciar a múltiples clientes
        for (int i = 0; i < n; i++) {
            clientes[i] = new ClienteThread("Cliente_" + i);
            clientes[i].start();
        }
    }

}
