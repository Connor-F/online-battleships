import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by connor on 15/06/15.
 */
public abstract class Ship extends JButton implements ActionListener
{
    private String imagePath;
    private String initialText;
    private int shipSize;

    public void setImagePath(String path)
    {
        imagePath = path;
    }

    public void setInitialText(String text)
    {
        initialText = text;
        setText(initialText);
    }

//    @Override
//    public void actionPerformed(ActionEvent e)
//    {
//        System.out.println("ship act. per.");
//    }

    public void setShipSize(int size) { shipSize = size; }

    public int getShipSize() { return shipSize; }
    public String getImagePath() { return imagePath; }
    public String getInitialText() { return initialText; }
}
