import java.awt.event.ActionEvent;

/**
 * Created by connor on 15/06/15.
 */
public class Battleship extends Ship
{
    public Battleship()
    {
        setImagePath("images/ships/battleship_");
        setInitialText("BATTLESHIP (4)");
        setShipSize(4);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //super.actionPerformed(e);
        System.out.println("Action performed in: Battleship");
        GameInterface.getInstance().setupShip(this);
    }
}
