import java.awt.event.ActionEvent;

/**
 * represents the data to do with the Battleship in the game, including it's placement button, size, image path etc.
 */
public class Battleship extends Ship
{
    public Battleship()
    {
        setImagePath("images/ships/battleship_");
        setButtonInitialText("BATTLESHIP (4)");
        setLabelInitialText(" Battleship waiting to be placed");
        setShipSize(4);
        setIsPlaced(false);
        addActionListener(this);
    }

    /**
     * when the Battleship button is pressed the setup procedure is called for this instance
     * @param e the ActionEvent that caused this method to be called
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        GameInterface.getInstance().setupShip(this);
    }
}
