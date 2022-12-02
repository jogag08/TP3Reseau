import java.net.*;
import java.io.*;

public class Client implements Runnable
{
    protected ServerSocket serverSocket; //Établit la connexion entre deux sockets
    protected Socket socket; //Socket connecté à un autre socket (client ou autre serveur)
    protected DataInputStream dataInputStream; //Stream par lequel le socket va recevoir de l'information de l'autre socket
    protected DataOutputStream dataOutputStream; //Stream par lequel le socket va envoyer de l'information à l'autre socket
    protected String address;
    protected int port; //Port sur lequel va établir la connexion

    public Client(String address, int port)
    {
        this.address = address;
        this.port = port;

        try
        {
            socket = new Socket(address,port);
            System.out.println("Client connected");

            dataInputStream = new DataInputStream(System.in);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            String line = "";

            while(!line.equals("Fini"))
            {
                try
                {
                    line = dataInputStream.readLine();
                    dataOutputStream.writeUTF(line);
                }
                catch(IOException e)
                {
                    System.out.println(e);
                }
            }

            try
            {
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    @Override
    public void run()
    {
        try
        {
            serverSocket = new ServerSocket(port); // Ouvre la connexion sur un port
            socket = serverSocket.accept(); // Bloque ici tant que personne est connecté
            dataInputStream = new DataInputStream(socket.getInputStream()); // Récupère la référence vers l'input stream du socket
            dataOutputStream = new DataOutputStream(socket.getOutputStream()); // Récupère la référence vers l'output stream du socket
        }
        catch (Exception e)
        {
            System.out.println(e + Integer.toString(port));
        }
    }
}
