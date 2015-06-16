import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by connor on 16/06/15.
 */
public class Player
{
    private BufferedReader playerInput;
    private PrintWriter playerOutput;
    private boolean myTurn;
    private Socket playerSocket;
    private Square[][] ocean = new Square[11][11];

    public BufferedReader getPlayerInput()
    {
        return playerInput;
    }

    public void setPlayerInput(BufferedReader playerInput)
    {
        this.playerInput = playerInput;
    }

    public PrintWriter getPlayerOutput()
    {
        return playerOutput;
    }

    public void setPlayerOutput(PrintWriter playerOutput)
    {
        this.playerOutput = playerOutput;
    }

    public boolean isMyTurn()
    {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn)
    {
        this.myTurn = myTurn;
    }

    public Socket getPlayerSocket()
    {
        return playerSocket;
    }

    public void setPlayerSocket(Socket playerSocket)
    {
        this.playerSocket = playerSocket;
    }


    public Square[][] getOcean()
    {
        return ocean;
    }

    public void setOcean(Square[][] ocean)
    {
        this.ocean = ocean;
    }
}
