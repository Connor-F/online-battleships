import java.awt.event.ActionEvent;

/**
 * Created by connor on 15/06/15.
 */
public class Cruiser extends Ship
{
    public Cruiser()
    {
        setImagePath("images/ships/cruiser_");
        setButtonInitialText("CRUISER (3)");
        setLabelInitialText(" Cruiser waiting to be placed");
        setShipSize(3);
        setIsPlaced(false);
        addActionListener(this);
        //addToAllShips(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //super.actionPerformed(e);
        //System.out.println("Action performed in: Cruiser");
        GameInterface.getInstance().setupShip(this);
//        if(getIsPlaced())
//            setEnabled(false);
//        else
//            setIsPlaced(!getIsPlaced());
    }
}
