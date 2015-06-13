import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameInterface extends JFrame implements ActionListener
{
    private JPanel rootPanel;
    private JPanel oceanPanel;
    private JPanel myOceanPanel;
    private JPanel enemyOceanPanel;
    private JPanel fleetInfoPanel;
    private JPanel myFleetInfoPanel;
    private JPanel enemyFleetInfoPanel;
    private JPanel gameStatsPanel;

    private Square[][] myOcean = new Square[11][11];
    private Square[][] enemyOcean = new Square[11][11];

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
        JButton destroyer = new JButton("DESTROYER");
        JButton submarine = new JButton("SUBMARINE");
        JButton cruiser = new JButton("CRUISER");
        JButton battleship = new JButton("BATTLESHIP");
        JButton carrier = new JButton("CARRIER");
        destroyer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.print("destroyer pressed");
            }
        });
        submarine.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("submarine pressed");
            }
        });
//        destroyer.setPreferredSize(new Dimension(170, 30));
//        submarine.setPreferredSize(new Dimension(170, 30));
//        cruiser.setPreferredSize(new Dimension(170, 30));
//        battleship.setPreferredSize(new Dimension(170, 30));
//        carrier.setPreferredSize(new Dimension(170, 30));



        myFleetInfoPanel.add(new JLabel("placeholder"));
        enemyFleetInfoPanel.add(new JLabel("placeholder"));
        gameStatsPanel.add(new JLabel("placeholder"));
        myFleetInfoPanel.add(destroyer);
        myFleetInfoPanel.add(submarine);
        myFleetInfoPanel.add(cruiser);
        myFleetInfoPanel.add(battleship);
        myFleetInfoPanel.add(carrier);
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

        for(int x = 0; x < 11; x++) // empty oceans for both panels
        {
            for(int y = 0; y < 11; y++)
            {
                myOcean[x][y] = new Square(x, y);
                enemyOcean[x][y] = new Square(x, y);
                myOceanPanel.add(myOcean[x][y]);
                enemyOceanPanel.add(enemyOcean[x][y]);
            }
        }
        // setup our and enemy ocean
//        for(int i = 0; i < 11; i++)
//        {
//            for(int j = 0; j < 11; j++)
//            {
//                myOceanPanel.add(new Square(-1, -1));
//                enemyOceanPanel.add(new Square(-1, -1));
//            }
//        }

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("source: " + e.getSource());

    }
}