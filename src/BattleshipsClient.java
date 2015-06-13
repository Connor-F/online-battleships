import java.net.*;
import java.io.*;

public class BattleshipsClient
{
    private static InetAddress SERVER_ADDRESS = null;
    private static final int SERVER_PORT = 0xdead+1; //todo
    private Socket toServer;
    private PrintWriter outToServer;
    private BufferedReader inFromServer;
    /** the players grid, including row/column headings */
    private Square[][] ocean = new Square[11][11];
    private boolean myTurn;

    public BattleshipsClient() throws IOException
    {
        SERVER_ADDRESS = InetAddress.getByName("127.0.0.1");
        myTurn = false;
        setupServerConnection(); // must be done after ocean initialised, otherwise ocean doesn't contain any ships
    }

    private void setupServerConnection() throws IOException
    {
        toServer = new Socket(SERVER_ADDRESS, SERVER_PORT);
        inFromServer = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
        outToServer = new PrintWriter(toServer.getOutputStream());
        ObjectOutputStream oos = new ObjectOutputStream(toServer.getOutputStream()); // pass our ocean to the server
        oos.writeObject(ocean);
    }
}