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
 * @class Cliente
 */

package cliente;

public class Cliente {
    private String name;
    private GameFrame frame;
    private boolean inGame;

    private ClienteRMI rmi;
    private ClienteMulticast multicast;
    private ClienteTCP tcp;

    public Cliente(String name) {
        this.frame = null;
        this.name = name;
        this.inGame = false;
    }

    public Cliente(GameFrame frame, String name) {
        this.frame = frame;
        this.name = name;
        this.inGame = false;
    }

    public String getName() {
        return name;
    }

    public ClienteRMI getRmi() {
        return rmi;
    }

    public ClienteMulticast getMulticast() {
        return multicast;
    }

    public ClienteTCP getTcp() {
        return tcp;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public int hitTopo(int i) {
        return tcp.golpeaTopo(i);
    }

    public void reportWinner(String winner) {
        if (frame != null)
            frame.reportWinner(winner);
        inGame = false;
        tcp.desconecta();

        this.start();

    }

    public void changeTopo(int id) {
        if (frame != null)
            frame.changeTopo(id);
    }

    public String start() {
        System.setProperty("java.net.preferIPv4Stack", "true"); // Para Multicast

        rmi = new ClienteRMI(this);
        multicast = new ClienteMulticast(this, rmi.getIPMulticast(), rmi.getMulticastSocket());
        tcp = new ClienteTCP(this, rmi.getIPTCP(), rmi.getTCPSocket());

        String puntuacion;
        puntuacion = rmi.registra();
        multicast.start();
        tcp.conecta();

        return puntuacion;
    }
}
