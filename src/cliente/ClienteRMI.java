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
 * @class ClienteRMI
 */

package cliente;

import interfaces.Registro;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteRMI {
    private Cliente cliente;
    private Registro reg;
    private long registerTime;
    private boolean error = false;

    private String IPTCP;
    private String IPMulticast;
    private int TCPSocket;
    private int MulticastSocket;

    public ClienteRMI(Cliente cliente) {
        this.cliente = cliente;
        System.setProperty("java.security.policy","file:./src/cliente/client.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        String name = "RegistroGame";
        Registry registry = null; // server's ip address
        try {
            registry = LocateRegistry.getRegistry("localhost");
            reg = (Registro) registry.lookup(name);
            IPTCP = reg.enviaIPTCP();
            IPMulticast = reg.enviaIPMulticast();
            TCPSocket = reg.enviaTCPSocket();
            MulticastSocket = reg.enviaMulticastSocket();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public String registra() {
        String puntuacion = "0";
        long tiempo = System.currentTimeMillis();
        try {
            cliente.setInGame(reg.registraJugador(cliente.getName()));
            registerTime = System.currentTimeMillis() - tiempo;
            puntuacion = reg.getPuntuacion(cliente.getName());
        } catch (RemoteException ex) {
            error = true;
            Logger.getLogger(ClienteRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return puntuacion;
    }

    public boolean isError() {
        return error;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public String getIPTCP() {
        return this.IPTCP;
    }

    public String getIPMulticast() {
        return this.IPMulticast;
    }

    public int getTCPSocket() {
        return this.TCPSocket;
    }

    public int getMulticastSocket() {
        return this.MulticastSocket;
    }

}
