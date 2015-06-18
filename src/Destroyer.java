import java.awt.event.ActionEvent;

public class Destroyer extends Ship
{
    public Destroyer()
    {
        setImagePath("images/ships/destroyer_");
        setButtonInitialText("DESTROYER (2)");
        setLabelInitialText(" Destroyer waiting to be placed");
        setShipSize(2);
        addActionListener(this);
        setIsPlaced(false);
        //addToAllShips(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //super.actionPerformed(e);
        //GameInterface.s
        //System.out.println("destroyer this: " + this);
        GameInterface.getInstance().setupShip(this);
        //if(getIsPlaced())
            //setEnabled(false);
        //else
            //setEnabled(true);
    }
}
