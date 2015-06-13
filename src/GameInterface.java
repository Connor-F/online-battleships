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
        setTitle("Battleships Online");
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

        // setup our and enemy ocean
        for(int i = 0; i < 11; i++)
        {
            for(int j = 0; j < 11; j++)
            {
                myOceanPanel.add(new Square(-1, -1));
                enemyOceanPanel.add(new Square(-1, -1));
            }
        }

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}