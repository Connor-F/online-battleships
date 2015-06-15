import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GameInterface extends JFrame implements ActionListener
{
    private JPanel rootPanel = new JPanel();
    private JPanel oceanPanel = new JPanel();
    private JPanel myOceanPanel = new JPanel(new GridLayout(11, 11));
    private JPanel enemyOceanPanel = new JPanel(new GridLayout(11, 11));
    private JPanel fleetInfoPanel = new JPanel();
    private JPanel myFleetInfoPanel = new JPanel();
    private JPanel enemyFleetInfoPanel = new JPanel();
    private JPanel gameStatsPanel = new JPanel();

    // buttons for placing the ships in the players ocean
    private JButton destroyerBtn = new JButton("DESTROYER");
    private JButton submarineBtn = new JButton("SUBMARINE");
    private JButton cruiserBtn = new JButton("CRUISER");
    private JButton battleshipBtn = new JButton("BATTLESHIP");
    private JButton carrierBtn = new JButton("CARRIER");
    /** used to indicate when a player has finished selecting the squares for their ship position */
    private boolean doneSelecting = false;

    private Square[][] myOcean = new Square[11][11];
    private Square[][] enemyOcean = new Square[11][11];

    public GameInterface()
    {
        initialiseInterface();
    }

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

        //space fill
        destroyerBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("destroyer pressed");
                setupDestroyer();
            }
        });
        submarineBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("submarineBtn pressed");
            }
        });

        myFleetInfoPanel.add(new JLabel("placeholder"));
        enemyFleetInfoPanel.add(new JLabel("placeholder"));
        gameStatsPanel.add(new JLabel("placeholder"));
        myFleetInfoPanel.add(destroyerBtn);
        myFleetInfoPanel.add(submarineBtn);
        myFleetInfoPanel.add(cruiserBtn);
        myFleetInfoPanel.add(battleshipBtn);
        myFleetInfoPanel.add(carrierBtn);
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

    private void setupDestroyer()
    {
        if(!doneSelecting) // the ship is being placed
        {
            destroyerBtn.setText("Done");
            submarineBtn.setEnabled(false);
            cruiserBtn.setEnabled(false);
            battleshipBtn.setEnabled(false);
            carrierBtn.setEnabled(false);

            doneSelecting = true;
        }
        else // the ship has been placed
        {
            ArrayList<Point> coordsSelected = Square.getSelectedPoints(); // the points the have been selected for this ship

            if(checkIfHorizontal(coordsSelected, 2)) // we must draw the ship horizontally at the selected coords
            {
                coordsSelected = sortByXCoords(coordsSelected);
                for(int i = 0; i < 2; i++)
                    myOcean[(int)coordsSelected.get(i).getX()][(int)coordsSelected.get(i).getY()].setIcon(new ImageIcon("images/ships/destroyer_horizontal_" + (i + 1) + ".jpg")); // todo: rename images starting from 0
            }
            else if(checkIfVertical(coordsSelected, 2))
            {
                coordsSelected = sortByYCoords(coordsSelected);
                for(int i = 0; i < 2; i++)
                    myOcean[(int)coordsSelected.get(i).getX()][(int)coordsSelected.get(i).getY()].setIcon(new ImageIcon("images/ships/destroyer_vertical_" + (i + 1) + ".jpg"));
            }
            else
            {
                System.out.println("Invalid placement for the destroyer.");
                // todo: cleanup
            }

            destroyerBtn.setText("DESTROYER");
            submarineBtn.setEnabled(true);
            cruiserBtn.setEnabled(true);
            battleshipBtn.setEnabled(true);
            carrierBtn.setEnabled(true);

            destroyerBtn.setEnabled(false);

        }
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

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("source: " + e.getSource());

    }
}