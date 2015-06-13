import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Square extends JButton implements ActionListener
{
    /** the different types of ships that can be on a square */
    public enum Ship { EMPTY, DESTROYER, SUBMARINE, CRUISER, BATTLESHIP, CARRIER }
    /** the ship type that is located on this particular square */
    private Ship ship;
    /** if this square has been hit already */
    private boolean hit;
    private static String unknownIconPath = "images/unknown.jpg";
    private static String hitIconPath = "images/hit.jpg";
    private static String missIconPath = "images/miss.jpg";
    private int xPos;
    private int yPos;

    public Square(int x, int y)
    {
        xPos = x;
        yPos = y;
        setIcon(getCorrectHeading(x, y));
        hit = false;
        ship = Ship.EMPTY;
        setPreferredSize(new Dimension(35, 35));
        addActionListener(this);
    }

    /**
     * finds the correct ImageIcon for the button provided. (for headers)
     * @param x the xPos of the button
     * @param y the yPos of the button
     * @return an ImageIcon that is correct for the button at the provided position
     */
    private ImageIcon getCorrectHeading(int x, int y)
    {
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

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("in actionPerformed: x=" + xPos + "    y=" + yPos);
        setIcon(new ImageIcon(hitIconPath));
    }

    public void setShip(Ship ship) { this.ship = ship; }

    public int getxPos() { return xPos; }
    public int getyPos() { return yPos; }
    public Ship getShip() { return ship; }

}