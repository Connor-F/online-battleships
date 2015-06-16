import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Square extends JButton implements ActionListener
{
    /** the different types of ships that can be on a square */
    public enum ShipType { EMPTY, DESTROYER, SUBMARINE, CRUISER, BATTLESHIP, CARRIER }
    /** the ship type that is located on this particular square */
    private ShipType shipType;
    /** if this square has been hit already */
    private boolean hit;
    private static String unknownIconPath = "images/unknown.jpg";
    private static String hitIconPath = "images/hit.jpg";
    private static String missIconPath = "images/miss.jpg";
    private static String selectedIconPath = "images/selected.jpg";
    private Point coordinates;
    private boolean isSelected;
    /** holds a list of all clicked squares. Used when placing ships */
    private static ArrayList<Point> selectedPoints;

    public Square(int x, int y)
    {
        coordinates = new Point(x, y);
        setIcon(getCorrectHeading());
        if(x != 0 && y != 0) // headers always disabled, we dont need an icon for them
            setDisabledIcon(getCorrectHeading());
        hit = false;
        isSelected = false;
        shipType = ShipType.EMPTY;
        setPreferredSize(new Dimension(35, 35));
        selectedPoints = new ArrayList<>();
        addActionListener(this);
    }

    /**
     * finds the correct ImageIcon for the button provided using the Point of this square. (for headers)
     * @return an ImageIcon that is correct for the button at the provided position
     */
    private ImageIcon getCorrectHeading()
    {
        int x = (int)coordinates.getX();
        int y = (int)coordinates.getY();
        if(y == 0) // must be a row heading
        {
            setEnabled(false); // columns / rows headings are not usable
            if(x == 0)
                return new ImageIcon("images/headers/notused.jpg"); // top left square is never used for anything
            return new ImageIcon("images/headers/" + x + ".jpg");
        }
        else if(x == 0) // must be a column heading
        {
            setEnabled(false);
            return new ImageIcon("images/headers/" + y + "_column.jpg");
        }
        return new ImageIcon(unknownIconPath); // normal square
    }

    /**
     * called when a square on the grid is clicked
     * @param e the ActionEvent that caused the function call
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(shipType != ShipType.EMPTY)
            return; // can't select an already used square

        isSelected = !isSelected;
        if(isSelected)
        {
            selectedPoints.add(coordinates);
            setIcon(new ImageIcon(selectedIconPath));
        }
        else
        {
            selectedPoints.remove(coordinates);
            setIcon(new ImageIcon(unknownIconPath));
        }

        //System.out.println("Square act. perf. x=" + coordinates.getX() + "     y=" + coordinates.getY());
    }

    public void defaultIcon() { setIcon(new ImageIcon(unknownIconPath)); }

    public void setShip(ShipType ship) { this.shipType = ship; }
    public void setIsSelected(boolean selected) { isSelected = selected; }

    public boolean getIsSelected() { return isSelected; }
    public ShipType getShip() { return shipType; }
    public static ArrayList<Point> getSelectedPoints() { return selectedPoints; }
    public static void clearSelectedPoints()
    {
        selectedPoints.clear();
    }

}