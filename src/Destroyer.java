import java.awt.event.ActionEvent;

public class Destroyer extends Ship
{
    public Destroyer()
    {
        setImagePath("images/ships/destroyer_");
        setInitialText("DESTROYER (2)");
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
        System.out.println("in act. per. in Destroyer");
        GameInterface.getInstance().setupShip(this);
    }
}
