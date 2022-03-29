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
 * @class ServidorTCP
 */

package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    private final int TCP_PORT = 7896;
    private Juego game;

    private class Connection extends Thread {
        DataInputStream in;
        DataOutputStream out;
        Socket clientSocket;
        Juego game;

        public Connection (Socket aClientSocket, Juego game) {
            this.game = game;
            try {
                clientSocket = aClientSocket;
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());
                String nameConnection = in.readUTF();
            } catch(IOException e)  {System.out.println("Connection:"+e.getMessage());}
        }

        @Override
        public void run() {
            try {
                boolean kill = false;
                while (!kill) {
                    String mensaje = in.readUTF();
                    String[] arr = mensaje.split(":");
                    if(arr[1].equals("kill")) {
                        kill = true;
                        out.writeUTF("Kill");
                    } else {
                        String name = arr[0];
                        int pos = Integer.parseInt(arr[1]);
                        //Se actualizan los resultados
                        int puntuacion = game.golpeJugador(name, pos);
                        out.writeInt(puntuacion);
                    }
                }
            }
            catch(EOFException e) {
                System.out.println("EOF:"+e.getMessage());
            }
            catch(IOException e) {
                System.out.println("IO:"+e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e){
                    System.out.println(e);
                }
            }
        }
    }

    public ServidorTCP(Juego game) {
        this.game = game;
    }

    public void despliega() {
        try{
            ServerSocket listenSocket = new ServerSocket(TCP_PORT);
            while(true) {
                System.out.println("TCP esperando...");
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket, this.game);
                c.start();
            }
        } catch(IOException e) {System.out.println("Listen :"+ e.getMessage());}
    }
}
