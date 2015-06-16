import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

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

    public BattleshipsClient() throws IOException, InterruptedException
    {
        SERVER_ADDRESS = InetAddress.getByName("127.0.0.1");
        myTurn = false;
        ocean = removeUnnecessaryInfo(GameInterface.getInstance().getMyOcean());
        setupServerConnection();
        readAndSend();
    }

    private void readAndSend() throws IOException, InterruptedException
    {
        GameInterface ui = GameInterface.getInstance();
        while(true)
        {
            String fromServer = inFromServer.readLine();
            switch(fromServer)
            {
                case CommunicationConstants.MY_TURN:
                    ui.setMyOceanPanelBorder(CommunicationConstants.COLOUR_MY_TURN);
                    ui.setEnemyOceanPanelBorder(CommunicationConstants.COLOUR_NOT_MY_TURN);

                    ArrayList<Point> selectedPoints;
                    while((selectedPoints = Square.getSelectedPoints()).size() != 1) // todo: probably better to make .getSelectedPoints block
                        Thread.sleep(200); // now we have the one clicked point, we can send the coords to the server to see if it was a hit / miss
                    int x = (int)selectedPoints.get(0).getX();
                    int y = (int)selectedPoints.get(0).getY();
                    outToServer.println(x); // send the coords
                    outToServer.println(y);
                    outToServer.flush();

                    String reply = inFromServer.readLine();
                    if(reply.equals(CommunicationConstants.MISS))
                        ui.setEnemyOceanSquareMiss(x, y);

                    break;
                case CommunicationConstants.NOT_MY_TURN:
                    ui.setEnemyOceanPanelBorder(CommunicationConstants.COLOUR_MY_TURN);
                    ui.setMyOceanPanelBorder(CommunicationConstants.COLOUR_NOT_MY_TURN);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * the server doesn't need icons, filepaths, etc. so we just remove them before sending the data
     * @param originalOcean the players ocean
     * @return a Square[][] with any unneeded data removed
     */
    private Square[][] removeUnnecessaryInfo(Square[][] originalOcean)
    {
        Square[][] tempOcean = new Square[11][11];
        for(int y = 0; y < 11; y++)
        {
            for(int x = 0; x < 11; x++)
            {
                tempOcean[x][y] = new Square(x, y);
                tempOcean[x][y].setIcon(null);
                tempOcean[x][y].setShip(originalOcean[x][y].getShip());
            }
        }
        return tempOcean;
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