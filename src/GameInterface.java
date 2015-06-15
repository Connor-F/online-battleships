import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameInterface extends JFrame
{
    private static GameInterface ui = null;
    private JPanel rootPanel = new JPanel();
    private JPanel oceanPanel = new JPanel();
    private JPanel myOceanPanel = new JPanel(new GridLayout(11, 11));
    private JPanel enemyOceanPanel = new JPanel(new GridLayout(11, 11));
    private JPanel fleetInfoPanel = new JPanel();
    private JPanel myFleetInfoPanel = new JPanel();
    private JPanel enemyFleetInfoPanel = new JPanel();
    private JPanel gameStatsPanel = new JPanel();

    private Ship destroyerShip = new Destroyer();
    private Ship submarineShip = new Submarine();
    private Ship cruiserShip = new Cruiser();
    private Ship battleShip = new Battleship();
    private Ship carrierShip = new Carrier();

    /** used to indicate when a player has finished selecting the squares for their ship position */
    private boolean doneSelecting = false;

    private Square[][] myOcean = new Square[11][11];
    private Square[][] enemyOcean = new Square[11][11];

    public GameInterface()
    {
        ui = this;
        initialiseInterface();
    }

    /**
     * singleton allows for sending of data to this class
     * @return the GameInterface instance that is running
     */
    public static GameInterface getInstance() { return ui; }

    private void initialiseInterface()
    {

        setTitle("Battleships Online");
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        myFleetInfoPanel.setLayout(new BoxLayout(myFleetInfoPanel, BoxLayout.Y_AXIS));
        enemyFleetInfoPanel.setLayout(new BoxLayout(enemyFleetInfoPanel, BoxLayout.Y_AXIS));

        setContentPane(rootPanel);
        rootPanel.add(oceanPanel);
        oceanPanel.add(myOceanPanel);
        oceanPanel.add(enemyOceanPanel);

        rootPanel.add(fleetInfoPanel);
        fleetInfoPanel.add(myFleetInfoPanel);
        fleetInfoPanel.add(enemyFleetInfoPanel);

        myFleetInfoPanel.add(new JLabel("placeholder"));
        enemyFleetInfoPanel.add(new JLabel("placeholder"));
        gameStatsPanel.add(new JLabel("placeholder"));


        myFleetInfoPanel.add(new Destroyer());
        myFleetInfoPanel.add(new Submarine());
        myFleetInfoPanel.add(new Cruiser());
        myFleetInfoPanel.add(new Battleship());
        myFleetInfoPanel.add(new Carrier());
        //addAllActionListeners();

        enemyFleetInfoPanel.add(new JButton("placeholder"));
        enemyFleetInfoPanel.add(new JButton("placeholder"));
        enemyFleetInfoPanel.add(new JButton("placeholder"));
        enemyFleetInfoPanel.add(new JButton("placeholder"));
        enemyFleetInfoPanel.add(new JButton("placeholder"));
        enemyFleetInfoPanel.add(new JButton("placeholder"));
        enemyFleetInfoPanel.add(new JButton("placeholder"));
        gameStatsPanel.add(new JButton("placeholder"));
        gameStatsPanel.add(new JButton("placeholder"));
        gameStatsPanel.add(new JButton("placeholder"));

        rootPanel.add(gameStatsPanel);

        for(int y = 0; y < 11; y++) // empty oceans for both panels
        {
            for(int x = 0; x < 11; x++)
            {
                myOcean[x][y] = new Square(x, y);
                enemyOcean[x][y] = new Square(x, y);
                myOceanPanel.add(myOcean[x][y]);
                enemyOceanPanel.add(enemyOcean[x][y]);
            }
        }

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    protected void setupShip(Ship shipToAdd)
    {
//        System.out.println("adding: " + shipToAdd);
        if(!doneSelecting) // the ship is being placed
        {
            shipToAdd.setText("Done");
            disableAllShipButtons();
            shipToAdd.setEnabled(true);
            doneSelecting = true;
        }
        else // the ships squares have been chosen
        {
 //           System.out.println("setupShip trying to add: " + shipToAdd.getInitialText());
            ArrayList<Point> coordsSelected = Square.getSelectedPoints(); // the points that have been selected for this ship
            processShip(coordsSelected, shipToAdd.getImagePath(), shipToAdd.getShipSize()); // get the ship drawn to the screen (if coords valid)
            shipToAdd.setText(shipToAdd.getInitialText());
            enableAllShipButtons();
            shipToAdd.setEnabled(false);
            doneSelecting = false;
        }
    }

    private void disableAllShipButtons()
    {
        carrierShip.setEnabled(false);
        battleShip.setEnabled(false);
        cruiserShip.setEnabled(false);
        submarineShip.setEnabled(false);
        destroyerShip.setEnabled(false);
    }

    private void enableAllShipButtons()
    {
        carrierShip.setEnabled(true);
        battleShip.setEnabled(true);
        cruiserShip.setEnabled(true);
        submarineShip.setEnabled(true);
        destroyerShip.setEnabled(true);
    }

    /**
     * uses multiple methods to verify the coordinates provided are valid, then draws the correct ship image to the screen.
     * This is the only method that should be called when a ship needs to be drawn to the screen
     * @param coords the coords the user selected the ship to be at
     * @param imagePath the path to the type of ship being placed
     * @param shipSize the length of the ship to be placed
     */
    private void processShip(ArrayList<Point> coords, String imagePath, int shipSize)
    {
        if(checkIfHorizontal(coords, shipSize))
            drawShipToOcean(sortByXCoords(coords), imagePath + "horizontal_", shipSize);
        else if(checkIfVertical(coords, shipSize))
            drawShipToOcean(sortByYCoords(coords), imagePath + "vertical_", shipSize);
        else
            System.out.println("Invalid placement for the " + imagePath); // todo cleanup after error, make new exception
    }

    /**
     * draws the correct image that makes up the ship to the correct buttons. This shouldn't be called directly. Use processShip instead.
     * @param coords the coordinates to draw to. These must be sorted before being passed in
     * @param imagePath String to the images that make up the desired ship
     * @param shipSize the length of the ship
     */
    private void drawShipToOcean(ArrayList<Point> coords, String imagePath, int shipSize)
    {
        for(int i = 0; i < shipSize; i++)
            myOcean[(int)coords.get(i).getX()][(int)coords.get(i).getY()].setIcon(new ImageIcon(imagePath + (i + 1) + ".jpg"));
        Square.clearSelectedPoints();
    }

    private boolean checkIfVertical(ArrayList<Point> coords, int shipSize)
    {
        if(coords.size() != shipSize)
            return false;

        ArrayList<Point> sortedXCoords = sortByXCoords(coords);
        ArrayList<Point> sortedYCoords = sortByYCoords(coords);

        if((int)sortedXCoords.get(0).getX() == (int)sortedXCoords.get(1).getX()) // since the coords are sorted, if the x coords are equal then we know the ship has been placed vertically
        {
            for(int i = 0; i < shipSize - 1; i++)
            {
                if((int)sortedYCoords.get(i).getY() == (int)(sortedYCoords.get(i + 1).getY() - 1)) // checks to see if the y coordinates are next to each other, otherwise the ship has been placed incorrectly
                    continue;
                return false; // the y coords do not form a horizontal line, therefore the placement is invalid
            }
            return true; // the y coords formed a straight line as the for loop completed
        }
        return false; // the sortXCoords did not form a straight line on the grid, so the placement must be invalid
    }

    private boolean checkIfHorizontal(ArrayList<Point> coords, int shipSize)
    {
        if(coords.size() != shipSize)
            return false;

        ArrayList<Point> sortedXCoords = sortByXCoords(coords);
        ArrayList<Point> sortedYCoords = sortByYCoords(coords);
        if((int)sortedYCoords.get(0).getY() == (int)sortedYCoords.get(1).getY()) // if the y coords are the same then we know the ship has been placed horizontally
        {
            for(int i = 0; i < shipSize - 1; i++)
            {
                if((int)sortedXCoords.get(i).getX() == (int)(sortedXCoords.get(i + 1).getX() - 1)) // checks to see if the x coordinates are next to each other, otherwise the ship has been placed incorrectly
                    continue;
                return false; // the x coords do not form a horizontal line, therefore the placement is invalid
            }
            return true; // for loop completed with the x coords in a straigh line
        }
        return false; // the sortedYCoords did not form a straight line on the grid, therefore the placement must be invalid
    }

    private ArrayList<Point> sortByXCoords(ArrayList<Point> coords)
    {
        Collections.sort(coords, new Comparator<Point>()
        {
            @Override
            public int compare(Point o1, Point o2)
            {
                if((int)o1.getX() > (int)o2.getX())
                    return 1;
                else if((int)o1.getX() < (int)o2.getX())
                    return -1;
                return 0;
            }
        });
        return coords;
    }

    private ArrayList<Point> sortByYCoords(ArrayList<Point> coords)
    {
        Collections.sort(coords, new Comparator<Point>()
        {
            @Override
            public int compare(Point o1, Point o2)
            {
                if((int)o1.getY() > (int)o2.getY())
                    return 1;
                else if((int)o1.getY() < (int)o2.getY())
                    return -1;
                return 0;
            }
        });
        return coords;
    }

}