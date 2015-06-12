/**
 * Created by connor on 12/06/15.
 */
public class Player
{
    /** the players grid, including row/column headings */
    private Square[][] ocean = new Square[11][11];
    private int wins;
    private int losses;

    public Player()
    {
        initialiseOcean();
    }

    private void initialiseOcean()
    {
        for(int i = 0; i < 11; i++)
            for(int j = 0; j < 11; j++)
                ocean[i][j] = new Square();
        ocean[0][0].setShip(Square.Ship.DESTROYER);
        ocean[0][1].setShip(Square.Ship.DESTROYER);
        ocean[1][0].setShip(Square.Ship.SUBMARINE);
        ocean[1][1].setShip(Square.Ship.SUBMARINE);
        ocean[1][2].setShip(Square.Ship.SUBMARINE);
        ocean[2][0].setShip(Square.Ship.CRUISER);
        ocean[2][1].setShip(Square.Ship.CRUISER);
        ocean[2][2].setShip(Square.Ship.CRUISER);
        ocean[3][0].setShip(Square.Ship.BATTLESHIP);
        ocean[3][1].setShip(Square.Ship.BATTLESHIP);
        ocean[3][2].setShip(Square.Ship.BATTLESHIP);
        ocean[3][3].setShip(Square.Ship.BATTLESHIP);
        ocean[4][0].setShip(Square.Ship.CARRIER);
        ocean[4][1].setShip(Square.Ship.CARRIER);
        ocean[4][2].setShip(Square.Ship.CARRIER);
        ocean[4][3].setShip(Square.Ship.CARRIER);
        ocean[4][4].setShip(Square.Ship.CARRIER);
    }

    public Square[][] getOcean() { return ocean; }
}
