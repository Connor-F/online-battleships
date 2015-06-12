import javax.swing.*;
import java.awt.*;

public class GameInterface extends JFrame
{
    private JPanel rootPanel;
    private JPanel oceanPanel;
    private JPanel myOceanPanel;
    private JPanel enemyOceanPanel;
    private JPanel fleetInfoPanel;
    private JPanel myFleetInfoPanel;
    private JPanel enemyFleetInfoPanel;
    private JPanel gameStatsPanel;

    public GameInterface()
    {
        initialiseInterface();
    }
    
    private void initialiseInterface()
    {
        rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        oceanPanel = new JPanel();
        myOceanPanel = new JPanel(new GridLayout(0, 11));
        enemyOceanPanel = new JPanel(new GridLayout(0, 11));
        fleetInfoPanel = new JPanel();
        myFleetInfoPanel = new JPanel();
        enemyFleetInfoPanel = new JPanel();
        gameStatsPanel = new JPanel();

        myFleetInfoPanel.setLayout(new BoxLayout(myFleetInfoPanel, BoxLayout.Y_AXIS));
        enemyFleetInfoPanel.setLayout(new BoxLayout(enemyFleetInfoPanel, BoxLayout.Y_AXIS));

        setContentPane(rootPanel);
        rootPanel.add(oceanPanel);
        oceanPanel.add(myOceanPanel);
        oceanPanel.add(enemyOceanPanel);

        rootPanel.add(fleetInfoPanel);
        fleetInfoPanel.add(myFleetInfoPanel);
        fleetInfoPanel.add(enemyFleetInfoPanel);

        //space fill
        myFleetInfoPanel.add(new JLabel("placeholder"));
        enemyFleetInfoPanel.add(new JLabel("placeholder"));
        gameStatsPanel.add(new JLabel("placeholder"));
        myFleetInfoPanel.add(new JButton("placeholder"));
        myFleetInfoPanel.add(new JButton("placeholder"));
        myFleetInfoPanel.add(new JButton("placeholder"));
        myFleetInfoPanel.add(new JButton("placeholder"));
        myFleetInfoPanel.add(new JButton("placeholder"));
        enemyFleetInfoPanel.add(new JButton("placeholder"));
        enemyFleetInfoPanel.add(new JButton("placeholder"));
        enemyFleetInfoPanel.add(new JButton("placeholder"));
        enemyFleetInfoPanel.add(new JButton("placeholder"));
        enemyFleetInfoPanel.add(new JButton("placeholder"));
        enemyFleetInfoPanel.add(new JButton("placeholder"));
        enemyFleetInfoPanel.add(new JButton("placeholder"));
        gameStatsPanel.add(new JButton("placeholder"));
        gameStatsPanel.add(new JButton("placeholder"));
        gameStatsPanel.add(new JButton("placeholder"));


        rootPanel.add(gameStatsPanel);

        Square board[] = new Square[121];
        Square enemy[] = new Square[121];
        for(int i = 0; i < 121; i++)
        {
            board[i] = new Square(Square.Ship.BATTLESHIP);
            enemy[i] = new Square(Square.Ship.BATTLESHIP);
            enemy[i].setPreferredSize(new Dimension(35, 35));
            board[i].setPreferredSize(new Dimension(35, 35));
            myOceanPanel.add(board[i]);
            enemyOceanPanel.add(enemy[i]);
        }
            //myOceanPanel.add(new Square(Square.Ship.BATTLESHIP));
        //for(int i = 0; i < 121; i++)
          //  enemyOceanPanel.add(new Square(Square.Ship.BATTLESHIP));


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }
}