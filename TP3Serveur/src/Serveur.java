import java.net.*;
import java.io.*;
public class Serveur implements Runnable
{
    protected ServerSocket serverSocket; //Établit la connexion entre deux sockets
    protected Socket socketPlayer1; //Socket connecté à un autre socket (client ou autre serveur)
    protected DataInputStream dataInputStream1; //Stream par lequel le socket va recevoir de l'information de l'autre socket
    protected DataOutputStream dataOutputStream1; //Stream par lequel le socket va envoyer de l'information à l'autre socket

    protected Socket socketPlayer2; //Socket connecté à un autre socket (client ou autre serveur)
    protected DataInputStream dataInputStream2; //Stream par lequel le socket va recevoir de l'information de l'autre socket
    protected DataOutputStream dataOutputStream2; //Stream par lequel le socket va envoyer de l'information à l'autre socket

    protected int port; //Port sur lequel va établir la connexion

    public Serveur(int port)
    {
        this.port = port; //this est la classe Serveur
    }

    @Override
    public void run()
    {
        try
        {
            serverSocket = new ServerSocket(port); // Ouvre la connexion sur un port
            System.out.println("Waiting for connection");

            socketPlayer1 = serverSocket.accept(); // Bloque ici tant que personne est connecté
            System.out.println("Player One Connected, waiting for Player Two");
            socketPlayer2 = serverSocket.accept();
            System.out.println("Player Two Connected");

            dataInputStream1 = new DataInputStream(socketPlayer1.getInputStream()); // Récupère la référence vers l'input stream du socket
            dataOutputStream1 = new DataOutputStream(socketPlayer1.getOutputStream()); // Récupère la référence vers l'output stream du socket
            dataInputStream2 = new DataInputStream(socketPlayer2.getInputStream()); // Récupère la référence vers l'input stream du socket
            dataOutputStream2 = new DataOutputStream(socketPlayer2.getOutputStream()); // Récupère la référence vers l'output stream du socket
        }
        catch (Exception e)
        {
            System.out.println(e + Integer.toString(port));
        }
    }
}


