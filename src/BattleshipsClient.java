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

        initialiseOcean();
        setupServerConnection();
    }


    private void initialiseOcean()
    {
        for(int i = 0; i < 11; i++)
            for(int j = 0; j < 11; j++)
                ocean[i][j] = new Square(i, j);
        ocean[0][0].setShip(Square.Ship.DESTROYER);
        ocean[0][1].setShip(Square.Ship.DESTROYER);
        ocean[1][0].setShip(Square.Ship.SUBMARINE);
        ocean[1][1].setShip(Square.Ship.SUBMARINE);
        ocean[1][2].setShip(Square.Ship.SUBMARINE);
        ocean[2][0].setShip(Square.Ship.CRUISER);
        ocean[2][1].setShip(Square.Ship.CRUISER);
        ocean[2][2].setShip(Square.Ship.CRUISER);
        ocean[3][0].setShip(Square.Ship.BATTLESHIP);
        ocean[3][1].setShip(Square.Ship.BATTLESHIP);
        ocean[3][2].setShip(Square.Ship.BATTLESHIP);
        ocean[3][3].setShip(Square.Ship.BATTLESHIP);
        ocean[4][0].setShip(Square.Ship.CARRIER);
        ocean[4][1].setShip(Square.Ship.CARRIER);
        ocean[4][2].setShip(Square.Ship.CARRIER);
        ocean[4][3].setShip(Square.Ship.CARRIER);
        ocean[4][4].setShip(Square.Ship.CARRIER);
    }

    private void setupServerConnection() throws IOException
    {
        toServer = new Socket(SERVER_ADDRESS, SERVER_PORT);
        inFromServer = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
        outToServer = new PrintWriter(toServer.getOutputStream());
        ObjectOutputStream oos = new ObjectOutputStream(toServer.getOutputStream()); // pass our ocean to the server
        oos.writeObject(ocean);
    }

    public Square[][] getOcean() { return ocean; }
}