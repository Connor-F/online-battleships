import javax.swing.*;
import java.awt.*;
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
    private String buttonInitialText;
    private int shipSize;
    private int shipHealth;
    private JLabel shipLabel = new JLabel();
    private String labelInitialText;
    // todo store array of Point[shipSize] here to inidcate the location of the ships
    /** contains every Ship instance, so we can enumerate all the Ships to help with ui functions */
    //private static ArrayList<Ship> allShips = new ArrayList<>()

    private boolean isPlaced; // todo might not need this anymore

    public void setImagePath(String path)
    {
        imagePath = path;
    }

    public void setLabelInitialText(String text)
    {
        labelInitialText = text;
        shipLabel.setText(text);

    }


    public void setButtonInitialText(String text)
    {
        buttonInitialText = text;
        setText(text);
    }

//    public synchronized void addToAllShips(Ship ship) { allShips.add(ship); }
//
//    public static ArrayList<Ship> getAllShips() { return allShips; }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //if(!isPlaced)
            //setEnabled(false);
        //isPlaced = !isPlaced; // second click of the button will make
        System.out.println("ship act. per.");
    }

    public void setShipSize(int size)
    {
        shipSize = size;
        shipHealth = size;
    }

    public void setIsPlaced(boolean placed) { isPlaced = placed; }

    public int getShipSize() { return shipSize; }
    public int getShipHealth() { return shipHealth; }
    public JLabel getShipLabel() { return shipLabel; }
    public String getImagePath() { return imagePath; }
    public String getButtonInitialText() { return buttonInitialText; }
    public boolean getIsPlaced() { return isPlaced; }
}
