import java.awt.event.ActionEvent;

/**
 * Created by connor on 15/06/15.
 */
public class Submarine extends Ship
{
    public Submarine()
    {
        setImagePath("images/ships/submarine_");
        setInitialText("SUBMARINE (3)");
        setShipSize(3);
        setIsPlaced(false);
        addActionListener(this);
        //addToAllShips(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //super.actionPerformed(e);
        //System.out.println("Action performed in: Submarine");
        GameInterface.getInstance().setupShip(this);
        //if(getIsPlaced())
            //setEnabled(false);
        //else
            //setIsPlaced(!getIsPlaced());
    }
}
