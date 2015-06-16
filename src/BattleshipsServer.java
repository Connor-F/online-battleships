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
    private BufferedReader playerOneInput;
    private BufferedReader playerTwoInput;
    private PrintWriter playerOneOutput;
    private PrintWriter playerTwoOutput;
    private boolean playerOneTurn;


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
        catch(Exception ioe)
        {
            System.out.println("playRound exception: " + ioe.getMessage());
            // todo refactor this catch
        }
    }

    private void playRound() throws IOException, InterruptedException
    {
        if(new Random().nextInt(2) == 0)
            playerOneTurn = true;
        else
            playerOneTurn = false;

        System.out.println("chose p1 first? : " + playerOneTurn);
        playerOneInput = new BufferedReader(new InputStreamReader(playerOneSocket.getInputStream()));
        playerTwoInput = new BufferedReader(new InputStreamReader(playerTwoSocket.getInputStream()));
        playerOneOutput = new PrintWriter(playerOneSocket.getOutputStream());
        playerTwoOutput = new PrintWriter(playerTwoSocket.getOutputStream());

        while(true) // game loop
        {
            Thread.sleep(300);
            if(playerOneTurn)
            {
                playerOneOutput.println(CommunicationConstants.MY_TURN);
                playerTwoOutput.println(CommunicationConstants.NOT_MY_TURN);
                playerOneOutput.flush();
                playerTwoOutput.flush();
                String xCoord = playerOneInput.readLine();
                String yCoord = playerOneInput.readLine();
                int x = Integer.valueOf(xCoord);
                int y = Integer.valueOf(yCoord);
                if(playerTwoOcean[x][y].getShip() == Square.ShipType.EMPTY) // p1 missed all of p2 ships
                {
                    playerOneOutput.println(CommunicationConstants.MISS);
                    playerTwoOutput.println(CommunicationConstants.MISS);
                    playerOneOutput.flush();
                    playerTwoOutput.flush();
                }
                else
                {
                    playerOneOutput.println(CommunicationConstants.HIT);
                    playerTwoOutput.println(CommunicationConstants.HIT);
                    playerTwoOutput.println(x); // notify of hit, then of the coords the hit took place
                    playerTwoOutput.println(y);
                    playerOneOutput.flush();
                    playerTwoOutput.flush();
                }
            }
            else
            {
                playerTwoOutput.println(CommunicationConstants.MY_TURN);
                playerOneOutput.println(CommunicationConstants.NOT_MY_TURN);
                playerTwoOutput.flush();
                playerOneOutput.flush();
                String xCoord = playerOneInput.readLine();
                String yCoord = playerOneInput.readLine();
                int x = Integer.valueOf(xCoord);
                int y = Integer.valueOf(yCoord);

                if(playerOneOcean[x][y].getShip() == Square.ShipType.EMPTY) // p2 missed all of p1 ships
                {
                    playerOneOutput.println(CommunicationConstants.MISS);
                    playerTwoOutput.println(CommunicationConstants.MISS);
                    playerOneOutput.flush();
                    playerTwoOutput.flush();
                }
                else
                {
                    playerOneOutput.println(CommunicationConstants.HIT);
                    playerTwoOutput.println(CommunicationConstants.HIT);
                    playerOneOutput.println(x); // notify of hit, then of the coords the hit took place
                    playerOneOutput.println(y);
                    playerOneOutput.flush();
                    playerTwoOutput.flush();
                }
            }

            playerOneTurn = !playerOneTurn;
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