import java.awt.event.ActionEvent;

/**
 * Created by connor on 15/06/15.
 */
public class Carrier extends Ship
{
    public Carrier()
    {
        setImagePath("images/ships/carrier_");
        setButtonInitialText("CARRIER (5)");
        setLabelInitialText(" Carrier waiting to be placed");
        setShipSize(5);
        setIsPlaced(false);
        addActionListener(this);
        //addToAllShips(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //super.actionPerformed(e);
        //System.out.println("Action performed in: Carrier");
        GameInterface.getInstance().setupShip(this);
//        if(getIsPlaced())
//            setEnabled(false);
//        else
//            setIsPlaced(!getIsPlaced());
    }
}
