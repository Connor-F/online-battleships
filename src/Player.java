import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * represents a client that is playing the game
 */
public class Player
{
    /** the actual connection between the client and server */
    private Socket playerSocket;
    /** the input to the server from the client */
    private BufferedReader playerInput;
    /** the output to the client from the server */
    private PrintWriter playerOutput;
    /** used so the server can keep track of whose turn it is */
    private boolean myTurn;
    /** the playeres Ocean */
    private Square[][] ocean = new Square[11][11];
    /** counts how many times this player has been successfully hit by the other players attacks */
    private int shipHitCounter = 0;

    /**
     * used to see if the round is over
     * @return if the number of squares hit is 17, then we know this player has had all their ships destroyed,
     * therefore the round is over
     */
    public boolean checkHitCounter()
    {
        return shipHitCounter == 17;
    }

    /**
     * add 1 to the value of the shipHitCounter
     */
    public void incrementHitCounter()
    {
        ++shipHitCounter;
    }

    // getters
    public BufferedReader getPlayerInput()
    {
        return playerInput;
    }

    public PrintWriter getPlayerOutput()
    {
        return playerOutput;
    }

    public boolean isMyTurn()
    {
        return myTurn;
    }

    public Socket getPlayerSocket()
    {
        return playerSocket;
    }

    public Square[][] getOcean()
    {
        return ocean;
    }

    // setters
    public void setPlayerInput(BufferedReader playerInput)
    {
        this.playerInput = playerInput;
    }

    public void setPlayerOutput(PrintWriter playerOutput)
    {
        this.playerOutput = playerOutput;
    }

    public void setMyTurn(boolean myTurn)
    {
        this.myTurn = myTurn;
    }

    public void setPlayerSocket(Socket playerSocket)
    {
        this.playerSocket = playerSocket;
    }

    public void setOcean(Square[][] ocean)
    {
        this.ocean = ocean;
    }
}
