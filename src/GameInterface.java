import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;

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

    private Ship[] allShips = new Ship[5];

    private Square[][] myOcean = new Square[11][11];
    private Square[][] enemyOcean = new Square[11][11];

    private static boolean allShipsPlaced = false;
    public GameInterface()
    {
        ui = this;
        initialiseInterface();

        while(!areAllShipsPlaced()) // block until all ships placed by the player
        {
            try
            {
                Thread.sleep(250);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            new BattleshipsClient(); // now all ships are placed we open up the Client to connect to the server
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private boolean areAllShipsPlaced()
    {
        for(int i = 0; i < allShips.length; i++)
            if(!allShips[i].getIsPlaced())
                return false;
        return true;
    }

    /**
     * singleton allows for sending of data to this class
     * @return the GameInterface instance that is running
     */
    public static GameInterface getInstance()
    {
        return ui;
    }

    private void initialiseInterface()
    {
        allShips[0] = new Destroyer();
        allShips[1] = new Submarine();
        allShips[2] = new Cruiser();
        allShips[3] = new Battleship();
        allShips[4] = new Carrier();

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


        //myFleetInfoPanel.add(new Destroyer());
        for(int i = 0; i < allShips.length; i++)
            myFleetInfoPanel.add(allShips[i]);
//        myFleetInfoPanel.add(destroyerShip);
//        myFleetInfoPanel.add(submarineShip);
//        myFleetInfoPanel.add(cruiserShip);
//        myFleetInfoPanel.add(battleShip);
//        myFleetInfoPanel.add(carrierShip);
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
                myOcean[x][y].setEnabled(false);
                enemyOcean[x][y].setEnabled(false);
                myOceanPanel.add(myOcean[x][y]);
                enemyOceanPanel.add(enemyOcean[x][y]);
            }
        }

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setEnabledMyOcean(boolean option)
    {
        for(int y = 0; y < 11; y++)
        {
            for(int x = 0; x < 11; x++)
            {
                if(x == 0 || y == 0) // we want to ignore headings
                    continue;
                myOcean[x][y].setEnabled(option);
            }
        }
    }

    private void setEnabledAllShips(boolean option)
    {
        for(int i = 0; i < allShips.length; i++)
            allShips[i].setEnabled(option);
    }

    /**
     * used by setupShip to find the passed in Ship's position in the allShips array
     *
     * @param shipToAdd the Ship that needs to be added
     * @return the position in the allShips array that the Ship is located
     */
    private int findShipsIndex(Ship shipToAdd)
    {
        for(int i = 0; i < allShips.length; i++)
            if(allShips[i] == shipToAdd)
                return i;
        return -1; // todo make return exception
    }

    protected void setupShip(Ship shipToAdd) // protected so can still use with singleton
    {
        int shipToAddIndex = findShipsIndex(shipToAdd);
        if(!allShips[shipToAddIndex].getIsPlaced())
        {
            allShips[shipToAddIndex].setEnabled(true); //@@@@@@@@@@@@@@new

            setEnabledMyOcean(true);
            allShips[shipToAddIndex].setText("Done (" + allShips[shipToAddIndex].getShipSize() + ")");

            for(int i = 0; i < allShips.length; i++) // disable every other ship button
                if(allShips[i] != shipToAdd)
                    allShips[i].setEnabled(false);

            if(Square.getSelectedPoints().size() == allShips[shipToAddIndex].getShipSize()) // all our squares for the ship have been clicked
                allShips[shipToAddIndex].setIsPlaced(true);

            // todo if the amount of squares is not == to ship size then the button sticks on "done"
            //else
                //return;
        }

        if(allShips[shipToAddIndex].getIsPlaced())
        {
            setEnabledMyOcean(false);
            allShips[shipToAddIndex].setText(allShips[shipToAddIndex].getInitialText());

            for(int i = 0; i < allShips.length; i++) // enable all unplaced ship buttons
                if(!allShips[i].getIsPlaced())
                    allShips[i].setEnabled(true);

            ArrayList<Point> coordsSelected = new ArrayList<>(new HashSet<>(Square.getSelectedPoints())); // coordsSelected now contains no duplicates
            try
            {
                allShips[shipToAddIndex].setEnabled(false); // @@@@@@@@@@@@@@@new
                processShip(coordsSelected, allShips[shipToAddIndex]);

                Square.ShipType type = findShipType(allShips[shipToAddIndex]); // need to know our ships type
                for(Point p : coordsSelected)
                    myOcean[(int)p.getX()][(int)p.getY()].setShip(type); // since the ship adding was successful, we now add the ship type to the squares clicked
            }
            catch(InvalidPlacementException | NoSuchShipException exc)
            {
                System.out.println("Invalid ship placement. For: " + shipToAdd.getImagePath());
                setEnabledMyOcean(false);
                allShips[shipToAddIndex].setEnabled(true); // @@@@@@@@@@@@@@@new

                //@@@@@@@@@@@@@@@@@@ uncomment
                //for(int i = 0; i < allShips.length; i++)
                   // if(allShips[i] == shipToAdd)
                       // allShips[i].setEnabled(true);

                allShips[shipToAddIndex].setIsPlaced(false);
                //allShips[shipToAddIndex].setEnabled(true);
                //shipToAdd.setIsPlaced(false);

                // need to reset any selected squares to default icons if they were selected
                for(Point p : coordsSelected) // todo maybe refactor
                    if(myOcean[(int) p.getX()][(int) p.getY()].getIsSelected())
                        myOcean[(int) p.getX()][(int) p.getY()].defaultIcon();
            }
            finally
            {
                Square.clearSelectedPoints();
            }
        }
    }

    /**
     * finds the type of the Ship provided
     * @param ship the Ship to find the type of
     * @return the type of the Ship or null if no type found (this should never happen)
     */
    private Square.ShipType findShipType(Ship ship) throws NoSuchShipException
    {
        if(ship instanceof Destroyer)
            return Square.ShipType.DESTROYER;
        else if(ship instanceof Submarine)
            return Square.ShipType.SUBMARINE;
        else if(ship instanceof Cruiser)
            return Square.ShipType.CRUISER;
        else if(ship instanceof Battleship)
            return Square.ShipType.BATTLESHIP;
        else if(ship instanceof Carrier)
            return Square.ShipType.CARRIER;
        throw new NoSuchShipException("No type exists for the provided Ship.");
    }

    /**
     * uses multiple methods to verify the coordinates provided are valid, then draws the correct ship image to the screen.
     * This is the only method that should be called when a ship needs to be drawn to the screen
     *
     * @param coords the coords the user selected the ship to be at
     * @param ship   the Ship instance we want to add
     */
    private void processShip(ArrayList<Point> coords, Ship ship) throws InvalidPlacementException
    {
        if(checkIfHorizontal(coords, ship.getShipSize()))
            drawShipToOcean(sortByXCoords(coords), ship.getImagePath() + "horizontal_", ship.getShipSize());
        else if(checkIfVertical(coords, ship.getShipSize()))
            drawShipToOcean(sortByYCoords(coords), ship.getImagePath() + "vertical_", ship.getShipSize());
        else
            throw new InvalidPlacementException("Invalid ship placement.");
//            System.out.println("Invalid placement for the " + ship.getImagePath()); // todo cleanup after error, make new exception
    }

    /**
     * draws the correct image that makes up the ship to the correct buttons. This shouldn't be called directly. Use processShip instead.
     * This sets the icon and disabled icon for the ship's squares
     *
     * @param coords    the coordinates to draw to. These must be sorted before being passed in
     * @param imagePath String to the images that make up the desired ship
     * @param shipSize  the length of the ship
     */
    private void drawShipToOcean(ArrayList<Point> coords, String imagePath, int shipSize)
    {
        for(int i = 0; i < shipSize; i++)
        {
            myOcean[(int) coords.get(i).getX()][(int) coords.get(i).getY()].setIcon(new ImageIcon(imagePath + (i + 1) + ".jpg"));
            myOcean[(int) coords.get(i).getX()][(int) coords.get(i).getY()].setDisabledIcon(new ImageIcon(imagePath + (i + 1) + ".jpg"));
        }
        Square.clearSelectedPoints(); // once we have drawn the ship we need to reset the arraylist that holds the clicked squares
    }

    private boolean checkIfVertical(ArrayList<Point> coords, int shipSize)
    {
        if(coords.size() != shipSize)
            return false;

        ArrayList<Point> sortedXCoords = sortByXCoords(coords);
        ArrayList<Point> sortedYCoords = sortByYCoords(coords);

        if((int) sortedXCoords.get(0).getX() == (int) sortedXCoords.get(1).getX()) // since the coords are sorted, if the x coords are equal then we know the ship has been placed vertically
        {
            for(int i = 0; i < shipSize - 1; i++)
            {
                if((int) sortedYCoords.get(i).getY() == (int) (sortedYCoords.get(i + 1).getY() - 1)) // checks to see if the y coordinates are next to each other, otherwise the ship has been placed incorrectly
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
        if((int) sortedYCoords.get(0).getY() == (int) sortedYCoords.get(1).getY()) // if the y coords are the same then we know the ship has been placed horizontally
        {
            for(int i = 0; i < shipSize - 1; i++)
            {
                if((int) sortedXCoords.get(i).getX() == (int) (sortedXCoords.get(i + 1).getX() - 1)) // checks to see if the x coordinates are next to each other, otherwise the ship has been placed incorrectly
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
                if((int) o1.getX() > (int) o2.getX())
                    return 1;
                else if((int) o1.getX() < (int) o2.getX())
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
                if((int) o1.getY() > (int) o2.getY())
                    return 1;
                else if((int) o1.getY() < (int) o2.getY())
                    return -1;
                return 0;
            }
        });
        return coords;
    }

    public Square[][] getMyOcean() { return myOcean.clone(); }

}