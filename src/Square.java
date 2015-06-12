import javax.swing.*;
import java.awt.*;

public class Square extends JButton
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

    public Square()
    {
        hit = false;
        setPreferredSize(new Dimension(35, 35));
        setIcon(new ImageIcon(unknownIconPath));
    }

    public void setShip(Ship ship) { this.ship = ship; }

}
