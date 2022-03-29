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
 * @interface Cliente
 */

package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * La interface Registro que extiende la Clase Remote para poder ser implementada por el servidor RMI,
 * define las firmas de los métodos a emplear en la clase del servidor RMI
 */
public interface Registro extends Remote {

    public boolean registraJugador(String name) throws RemoteException;
    public String enviaIPTCP() throws RemoteException;
    public String enviaIPMulticast() throws RemoteException;
    public int enviaTCPSocket() throws RemoteException;
    public int enviaMulticastSocket() throws RemoteException;
    public String getPuntuacion(String name) throws RemoteException;

}
