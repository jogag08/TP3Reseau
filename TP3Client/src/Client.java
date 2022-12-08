import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client implements Runnable
{
    protected ServerSocket serverSocket; //Établit la connexion entre deux sockets
    protected Socket socket; //Socket connecté à un autre socket (client ou autre serveur)
    protected DataInputStream consoleIn;
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
            System.out.println("Connection successful");

            consoleIn = new DataInputStream(System.in);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            String line = "";
            String gameMsg = "";
            String gameBoard = "";
            String verifInput = "Denied";
            String winLose = "";
            String rematch = "";

            while(!line.equals("Disconnect"))
            {
                try
                {
                    gameMsg = dataInputStream.readUTF(); //Aller chercher l'info du serveur
                    System.out.println(gameMsg); // Affichage du message du serveur
                    if(gameMsg.equals("It's your turn!"))
                    {
                        gameBoard = dataInputStream.readUTF();
                        System.out.println(gameBoard);
                        verifInput = "Denied";
                        while (verifInput.equals("Denied"))
                        {
                            System.out.println("Enter your move : ");
                            line = consoleIn.readLine(); //Entrée du move
                            dataOutputStream.writeUTF(line); //Communiquer le move au serveur
                            verifInput = dataInputStream.readUTF();
                        }

                        gameBoard = dataInputStream.readUTF();
                        System.out.println(gameBoard);
                    }
                    winLose = dataInputStream.readUTF();
                    System.out.println(winLose);
                    while(!winLose.equals(""))
                    {
                        gameBoard = dataInputStream.readUTF();
                        System.out.println(gameBoard);
                        while(!rematch.equals("YES") && !rematch.equals(("NO")))
                        {
                            System.out.println("Rematch? YES or NO :");
                            rematch = consoleIn.readLine(); //Entrée du move
                        }
                        dataOutputStream.writeUTF(rematch); //Communiquer le move au serveur

                        if(rematch.equals("YES"))
                        {
                            line = "";
                            gameMsg = "";
                            gameBoard = "";
                            verifInput = "Denied";
                            winLose = "";
                            rematch = "";
                        }
                        if(rematch.equals("NO"))
                        {
                            line = "Disconnect";
                        }
                    }
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
