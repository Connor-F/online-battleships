import java.awt.event.ActionEvent;

/**
 * Created by connor on 15/06/15.
 */
public class Cruiser extends Ship
{
    public Cruiser()
    {
        setImagePath("images/ships/cruiser_");
        setInitialText("CRUISER (3)");
        setShipSize(3);
        setIsPlaced(false);
        addActionListener(this);
        //addToAllShips(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //super.actionPerformed(e);
        System.out.println("Action performed in: Cruiser");
        GameInterface.getInstance().setupShip(this);
    }
}
