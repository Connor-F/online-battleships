import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * Created by connor on 13/06/15.
 */
public class BattleshipsServer
{
    private static final int LISTENING_PORT = 0xdead+1; // todo
    private Player playerOne;
    private Player playerTwo;

    public static void main(String[] args)
    {
        new BattleshipsServer();
    }

    public BattleshipsServer()
    {
        listenAndSetup();
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
            playerOne.setMyTurn(true); // since there is only 2 players we only need to set one player's turn to know whose turn it is
        else
            playerOne.setMyTurn(false);

        System.out.println("chose p1 first? : " + playerOne.isMyTurn());

        while(true) // game loop
        {
            Thread.sleep(300);
            if(playerOne.isMyTurn())
            {
                playerOne.getPlayerOutput().println(CommunicationConstants.MY_TURN);
                playerTwo.getPlayerOutput().println(CommunicationConstants.NOT_MY_TURN);
                playerOne.getPlayerOutput().flush();
                playerTwo.getPlayerOutput().flush();
                String xCoord = playerOne.getPlayerInput().readLine();
                String yCoord = playerOne.getPlayerInput().readLine();
                int x = Integer.valueOf(xCoord);
                int y = Integer.valueOf(yCoord);
                if(playerTwo.getOcean()[x][y].getShip() == Square.ShipType.EMPTY) // p1 missed all of p2 ships
                {
                    playerOne.getPlayerOutput().println(CommunicationConstants.MISS);
                    playerTwo.getPlayerOutput().println(CommunicationConstants.MISS);
                    playerOne.getPlayerOutput().flush();
                    playerTwo.getPlayerOutput().flush();
                }
                else
                {
                    playerOne.getPlayerOutput().println(CommunicationConstants.HIT);
                    playerTwo.getPlayerOutput().println(CommunicationConstants.HIT);
                    playerTwo.getPlayerOutput().println(x); // notify of hit, then of the coords the hit took place
                    playerTwo.getPlayerOutput().println(y);
                    playerOne.getPlayerOutput().flush();
                    playerTwo.getPlayerOutput().flush();
                }
            }
            else
            {
                playerTwo.getPlayerOutput().println(CommunicationConstants.MY_TURN);
                playerOne.getPlayerOutput().println(CommunicationConstants.NOT_MY_TURN);
                playerTwo.getPlayerOutput().flush();
                playerOne.getPlayerOutput().flush();
                String xCoord = playerOne.getPlayerInput().readLine();
                String yCoord = playerOne.getPlayerInput().readLine();
                int x = Integer.valueOf(xCoord);
                int y = Integer.valueOf(yCoord);

                if(playerOne.getOcean()[x][y].getShip() == Square.ShipType.EMPTY) // p2 missed all of p1 ships
                {
                    playerOne.getPlayerOutput().println(CommunicationConstants.MISS);
                    playerTwo.getPlayerOutput().println(CommunicationConstants.MISS);
                    playerOne.getPlayerOutput().flush();
                    playerTwo.getPlayerOutput().flush();
                }
                else
                {
                    playerOne.getPlayerOutput().println(CommunicationConstants.HIT);
                    playerTwo.getPlayerOutput().println(CommunicationConstants.HIT);
                    playerOne.getPlayerOutput().println(x); // notify of hit, then of the coords the hit took place
                    playerOne.getPlayerOutput().println(y);
                    playerOne.getPlayerOutput().flush();
                    playerTwo.getPlayerOutput().flush();
                }
            }

            playerOne.setMyTurn(!playerOne.isMyTurn());
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
                    playerOne.setOcean((Square[][]) ois.readObject());
//                    playerOneOcean = (Square[][])ois.readObject();
                    playerOne.setPlayerSocket(client);
                    playerOne.setPlayerInput(new BufferedReader(new InputStreamReader(playerOne.getPlayerSocket().getInputStream())));
                    playerOne.setPlayerOutput(new PrintWriter(playerOne.getPlayerSocket().getOutputStream()));
//                    playerOneSocket = client;
                    playerOneDataRead = true;
                    serverSocket.close();
                }
                else
                {
                    System.out.println("Reading player 2");
                    playerTwo.setPlayerSocket(client);
                    playerTwo.setOcean((Square[][])ois.readObject());
                    playerTwo.setPlayerInput(new BufferedReader(new InputStreamReader(playerTwo.getPlayerSocket().getInputStream())));
                    playerTwo.setPlayerOutput(new PrintWriter(playerTwo.getPlayerSocket().getOutputStream()));
//                    playerTwoOcean = (Square[][])ois.readObject();
//                    playerTwoSocket = client;
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