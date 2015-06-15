import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by connor on 15/06/15.
 */
public class Ship extends JButton implements ActionListener
{
    private String imagePath;
    private String initialText;
    private int shipSize;
    // todo store array of Point[shipSize] here to inidcate the location of the ships
    /** contains every Ship instance, so we can enumerate all the Ships to help with ui functions */
    //private static ArrayList<Ship> allShips = new ArrayList<>();


    private boolean isPlaced; // todo might not need this anymore

    public void setImagePath(String path)
    {
        imagePath = path;
    }

    public void setInitialText(String text)
    {
        initialText = text;
        setText(initialText);
    }

//    public synchronized void addToAllShips(Ship ship) { allShips.add(ship); }
//
//    public static ArrayList<Ship> getAllShips() { return allShips; }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("ship act. per.");
    }

    public void setShipSize(int size) { shipSize = size; }
    public void setIsPlaced(boolean placed) { isPlaced = placed; }

    public int getShipSize() { return shipSize; }
    public String getImagePath() { return imagePath; }
    public String getInitialText() { return initialText; }
    public boolean getIsPlaced() { return isPlaced; }
}
