import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * Created by connor on 13/06/15.
 */
public class BattleshipsServer
{
    private static final int LISTENING_PORT = 0xdead+1; // todo
    private Square[][] playerOneOcean = new Square[11][11];
    private Square[][] playerTwoOcean = new Square[11][11];
    private Socket playerOneSocket;
    private Socket playerTwoSocket;


    public static void main(String[] args)
    {
        new BattleshipsServer();
    }

    public BattleshipsServer()
    {
        listenAndSetup();
        System.out.println("p1: " + playerOneOcean + "\n" + "p2: " + playerTwoOcean);

        for(int y = 0; y < 11; y++)
        {
            for(int x = 0; x < 11; x++)
            {
                if(x == 0 || y == 0) // we want to ignore headings
                    continue;
                System.out.println("p1 ship[" + x + "][" + y + "]: " + playerOneOcean[x][y].getShip());
                System.out.println("p2 ship[" + x + "][" + y + "]: " + playerTwoOcean[x][y].getShip());
            }
        }


        try
        {
            playRound();
        }
        catch(IOException ioe)
        {
            // todo refactor this catch
        }
    }

    private void playRound() throws IOException
    {
        Random ran = new Random();
        int turn = ran.nextInt(2);
        BufferedReader playerOneInput = new BufferedReader(new InputStreamReader(playerOneSocket.getInputStream()));
        BufferedReader playerTwoInput = new BufferedReader(new InputStreamReader(playerTwoSocket.getInputStream()));
        PrintWriter playerOneOutput = new PrintWriter(playerOneSocket.getOutputStream());
        PrintWriter playerTwoOutput = new PrintWriter(playerTwoSocket.getOutputStream());

        if(turn == 0) // let playerOne go first
        {

        }
        else // playerTwo
        {

        }

    }

    /**
     * waits for 2 connections from the clients, then reads in each clients ocean and sets up the
     * necessary sockets to allow for communication between the clients and server
     */
    private void listenAndSetup()
    {
        boolean playerOneDataRead = false;
        boolean playerTwoDataRead = false;

        while(!playerOneDataRead || !playerTwoDataRead) // read in both players ocean & setup the sockets for each client
        {
            try(ServerSocket serverSocket = new ServerSocket(LISTENING_PORT))
            {
                Socket client = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(client.getInputStream()); // first thing read from client connection is always the client's ocean
                if(!playerOneDataRead)
                {
                    System.out.println("Reading player 1");
                    playerOneOcean = (Square[][])ois.readObject();
                    playerOneSocket = client;
                    playerOneDataRead = true;
                    serverSocket.close();
                }
                else
                {
                    System.out.println("Reading player 2");
                    playerTwoOcean = (Square[][])ois.readObject();
                    playerTwoSocket = client;
                    playerTwoDataRead = true;
                    serverSocket.close();
                }
            }
            catch(Exception ioe)
            {
                System.out.println("ioe: " + ioe.getMessage());
                // todo: reset connections / terminate
            }
        }
    }
}