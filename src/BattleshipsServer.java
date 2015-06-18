import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * Created by connor on 13/06/15.
 */
public class BattleshipsServer
{
    private static final int LISTENING_PORT = 0xdead;
    private Player[] players = new Player[2];
    private int playersTurn;

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

    private void notifyOfTurn() throws IOException
    {
        players[playersTurn].getPlayerOutput().println(CommunicationConstants.DATA_READY);
        players[1 - playersTurn].getPlayerOutput().println(CommunicationConstants.DATA_READY);
        flushOutputs();

        players[playersTurn].getPlayerOutput().println(CommunicationConstants.MY_TURN);
        players[1 - playersTurn].getPlayerOutput().println(CommunicationConstants.NOT_MY_TURN);

        flushOutputs();
    }

    private Point findProvidedAttackPoint() throws IOException, InterruptedException
    {
        while(!(players[playersTurn].getPlayerInput().readLine()).equals(CommunicationConstants.DATA_READY))
            Thread.sleep(100); // block until client tells us data is ready to be sent
        int providedX = Integer.valueOf(players[playersTurn].getPlayerInput().readLine());
        int providedY = Integer.valueOf(players[playersTurn].getPlayerInput().readLine());

        System.out.println("points from player " + (1 - playersTurn) + ": " + "x:" + providedX + "    y:" + providedY);

        return new Point(providedX, providedY);
    }

    private boolean checkIfSuccessfulAttack(Point attackCoords)
    {
        return players[1 - playersTurn].getOcean()[(int)attackCoords.getX()][(int)attackCoords.getY()].getShip() != Square.ShipType.EMPTY;
    }

    private void notifyOfMiss(Point missCoords)
    {
        players[playersTurn].getPlayerOutput().println(CommunicationConstants.DATA_READY);
        players[1 - playersTurn].getPlayerOutput().println(CommunicationConstants.DATA_READY);
        flushOutputs();


        players[0].getPlayerOutput().println(CommunicationConstants.MISS);
        players[1].getPlayerOutput().println(CommunicationConstants.MISS);
        flushOutputs(); // todo: only need one flush due to println

        players[1 - playersTurn].getPlayerOutput().println((int)missCoords.getX());
        players[1 - playersTurn].getPlayerOutput().println((int)missCoords.getY());
        flushOutputs();
    }

    private void notifyOfHit(Point hitCoords)
    {
        players[playersTurn].getPlayerOutput().println(CommunicationConstants.DATA_READY);
        players[1 - playersTurn].getPlayerOutput().println(CommunicationConstants.DATA_READY);
        flushOutputs();

        players[0].getPlayerOutput().println(CommunicationConstants.HIT);
        players[1].getPlayerOutput().println(CommunicationConstants.HIT);
        flushOutputs();

        players[1 - playersTurn].incrementHitCounter();
        if(players[1 - playersTurn].checkHitCounter()) // check to see if that last hit was a hit on enemys final ship square
        {
           System.out.println("Round is over!!!!!!!!!!!");
        }



        players[1 - playersTurn].getPlayerOutput().println((int)hitCoords.getX());
        players[1 - playersTurn].getPlayerOutput().println((int)hitCoords.getY());
        flushOutputs();
    }


    private void checkIfRoundOver()
    {

    }

    /**
     * handles the playing of the game
     * @throws IOException
     */
    private void takeTurn() throws IOException, InterruptedException
    {
//        if(isFirstGo) // todo this is temp fix
//        {
//            notifyOfTurn();
//            isFirstGo = false;
//        }

        playersTurn = players[0].isMyTurn() ? 0 : 1;
        players[playersTurn].getPlayerOutput().println("");
        players[1 - playersTurn].getPlayerOutput().println("");
        flushOutputs();

        notifyOfTurn();

        Point attackCoords = findProvidedAttackPoint();
        boolean attackSuccessful = checkIfSuccessfulAttack(attackCoords);
        if(attackSuccessful)
            notifyOfHit(attackCoords);
        else
            notifyOfMiss(attackCoords);

        players[0].setMyTurn(!players[0].isMyTurn());
    }

    /**
     * flushes both players PrintWriters
     */
    private void flushOutputs()
    {
        players[0].getPlayerOutput().flush();
        players[1].getPlayerOutput().flush();
    }

    private void playRound() throws IOException, InterruptedException
    {
        if(new Random().nextInt(2) == 0)
            players[0].setMyTurn(true); // since there is only 2 players we only need to set one player's turn to know whose turn it is
        else
            players[1].setMyTurn(false);

        players[0].setMyTurn(false); // todo remove

        System.out.println("chose p1 first? : " + players[0].isMyTurn());
        while(true) // game loop
        {
            Thread.sleep(300);
            takeTurn();
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

        players[0] = new Player();
        players[1] = new Player();

        while(!playerOneDataRead || !playerTwoDataRead) // read in both players ocean & setup the sockets for each client
        {
            try(ServerSocket serverSocket = new ServerSocket(LISTENING_PORT))
            {
                Socket client = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(client.getInputStream()); // first thing read from client connection is always the client's ocean
                if(!playerOneDataRead)
                {
                    System.out.println("Reading player 1");
                    players[0].setOcean((Square[][]) ois.readObject());
//                    playerOneOcean = (Square[][])ois.readObject();
                    players[0].setPlayerSocket(client);
                    players[0].setPlayerInput(new BufferedReader(new InputStreamReader(players[0].getPlayerSocket().getInputStream())));
                    players[0].setPlayerOutput(new PrintWriter(players[0].getPlayerSocket().getOutputStream()));
//                    playerOneSocket = client;
                    playerOneDataRead = true;
                    serverSocket.close();
                }
                else
                {
                    System.out.println("Reading player 2");
                    players[1].setPlayerSocket(client);
                    players[1].setOcean((Square[][]) ois.readObject());
                    players[1].setPlayerInput(new BufferedReader(new InputStreamReader(players[1].getPlayerSocket().getInputStream())));
                    players[1].setPlayerOutput(new PrintWriter(players[1].getPlayerSocket().getOutputStream()));
//                    playerTwoOcean = (Square[][])ois.readObject();
//                    playerTwoSocket = client;
                    playerTwoDataRead = true;
                    serverSocket.close();
                }
            }
            catch(IOException ioe)
            {
                System.out.println("server listen and setup ioe: " + ioe.getMessage());
                ioe.printStackTrace();
                // todo: reset connections / terminate
            }
            catch(ClassNotFoundException cnfe)
            {
                System.out.println("server listen and setup: class not found: " + cnfe.getMessage());
            }
        }
    }
}