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
 * @class ServidorRMI
 */

package servidor;

import interfaces.Registro;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServidorRMI implements Registro{

    private Juego game;

    public ServidorRMI(Juego g) {
        this.game = g;
    }

    public void despliega() {
        System.setProperty("java.security.policy","file:./src/servidor/server.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            LocateRegistry.createRegistry(1099);

            String name = "RegistroGame";

            Registro stub = (Registro) UnicastRemoteObject.exportObject(this, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("¡RMI desplegado!");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPuntuacion(String name) throws RemoteException {


        boolean registrado = game.agregaJugador(name);
        if (registrado) {
            System.out.println(name + " registrado correctamente.");
        } else {
            System.out.println("Ya hay un " + name + " en la sala");
        }
        String puntuacion = Integer.toString(game.obtenPuntuacion(name));

        return puntuacion;
    }

    @Override
    public boolean registraJugador(String name) throws RemoteException {
        boolean registrado = game.agregaJugador(name);
        if (registrado) {
            System.out.println(name + " registrado correctamente.");
        } else {
            System.out.println("Ya hay un " + name + " en la sala");
        }
        return registrado;
    }

    @Override
    public String enviaIPTCP() throws RemoteException {
        return "localhost";
    }

    @Override
    public String enviaIPMulticast() throws RemoteException {
        return "239.192.0.1";
    }

    @Override
    public int enviaTCPSocket() throws RemoteException {
        return 7896;
    }

    @Override
    public int enviaMulticastSocket() throws RemoteException {
        return 6868;
    }

}
