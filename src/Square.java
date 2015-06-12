import javax.swing.*;

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

    public Square(Ship ship)
    {
        this.ship = ship;
        hit = false;
        setIcon(new ImageIcon(unknownIconPath));
    }

}
