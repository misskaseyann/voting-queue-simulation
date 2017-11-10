import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

/**
 * Main GUI for simulation.
 *
 * @author Kasey Stowell
 * @version 11.06.2016
 */
public class GUI {
    private JButton start, pause, reset;
    private JMenuItem quit;
    private Timer simTimer;
    private JTextField sNextField, aCheckInField, tTimeField, aVoteField, sLeaveField, nBoothsField;
    private ButtonListener listener;
    private Simulation sim;
    private Stats stats;
    private JPanel animator;
    private JFrame frame;

    private final int PANEL_DIMENSION = 400;
    private int timerIncrement = 0;

    private JLabel avgWaitCheckInReg1, avgWaitCheckInLim1, avgWaitCheckInSp1, avgWaitCheckInSS1;
    private JLabel avgWaitCheckInReg2, avgWaitCheckInLim2, avgWaitCheckInSp2, avgWaitCheckInSS2;
    private JLabel avgQReg, avgQLim, avgQSp, avgQSS;
    private JLabel avgTotReg, avgTotLim, avgTotSp, avgTotSS;
    private JLabel avgQLength, totDeserters, throughput;

    /**
     * main method for launching GUI.
     * @param args
     */
    public static void main(String[] args) {
        new GUI();
    }

    /**
     * Main constructor.
     */
    public GUI() {
        //Instantiate objects for simulation.
        sim = new Simulation();
        stats = new Stats(sim.getBoothLine(), sim.getTableLine(), sim.getCentralQ());
        listener = new ButtonListener();

        //Instantiate JFrame and add components.
        frame = new JFrame("Voting Simulation");
        frame.setLayout(new GridBagLayout());
        frame.setJMenuBar(setupMenu());
        frame.add(leftPanel(), setConstraints(0,0,2));
        frame.add(centerPanel(), setConstraints(2,0,0));
        frame.add(animator = new Painter(), setConstraints(1,0,0));

        //Timer that fires an event every 1/10 of a second.
        simTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Increment timer to ensure that a clock tick is made every second.
                timerIncrement++;
                if (timerIncrement == 10){
                    //Tick the clock and update all stats.
                    sim.tick();
                    stats.updateStats();
                    updateStats();
                    frame.pack();
                    timerIncrement = 0;
                }
                Iterator<Voter> iter = sim.getVoters().iterator();
                //Animate the voters and move them across the board.
                while (iter.hasNext()) {
                    Voter person = iter.next();
                    person.takeStep();
                    if (person.getMoveX() >= 350){
                        iter.remove();
                    }
                }
                //Repaint and pack the frame.
                animator.repaint();
                frame.pack();
            }

        });

        //Ensure the timer repeats firing.
        simTimer.setRepeats(true);

        //Set frame as visible and that it will close properly.
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

    /**
     * Updates the stats for the simulation.
     */
    private void updateStats() {
        //Standard base stats.
        throughput.setText(stats.getThroughput() + " people.");
        avgQLength.setText(stats.getAverageCheckInQlength() + " people.");
        totDeserters.setText(stats.getTotalDeserters() + " people.");

        //Average wait for check in table 1.
        avgWaitCheckInReg1.setText(stats.getAvgWaitTimeCheckInReg_table1() + " seconds.");
        avgWaitCheckInLim1.setText(stats.getAvgWaitTimeCheckInLim_table1() + " seconds.");
        avgWaitCheckInSp1.setText(stats.getAvgWaitTimeCheckInSp_table1() + " seconds.");
        avgWaitCheckInSS1.setText(stats.getAvgWaitTimeCheckInSupSp_table1() + " seconds.");

        //Average wait for check in table 2.
        avgWaitCheckInReg2.setText(stats.getAvgWaitTimeCheckInReg_table2() + " seconds.");
        avgWaitCheckInLim2.setText(stats.getAvgWaitTimeCheckInLim_table2() + " seconds.");
        avgWaitCheckInSp2.setText(stats.getAvgWaitTimeCheckInSp_table2() + " seconds.");
        avgWaitCheckInSS2.setText(stats.getAvgWaitTimeCheckInSupSp_table2() + " seconds.");

        //Average wait for central Q.
        avgQReg.setText(stats.getAvgWaitTimeCentralQReg() + " seconds.");
        avgQLim.setText(stats.getAvgWaitTimeCentralQLim() + " seconds.");
        avgQSp.setText(stats.getAvgWaitTimeCentralQSp() + " seconds.");
        avgQSS.setText(stats.getAvgWaitTimeCentralQSupSp() + " seconds.");

        //Average total wait.
        avgTotReg.setText(stats.getAverageTotalSimTimeReg() + " seconds.");
        avgTotLim.setText(stats.getAverageTotalSimTimeLim() + " seconds.");
        avgTotSp.setText(stats.getAverageTotalSimTimeSp() + " seconds.");
        avgTotSS.setText(stats.getAverageTotalSimTimeSupSp() + " seconds.");
    }

    /**
     * Left most panel of the GUI that provides a spot for user input and some stats.
     * @return JPanel
     */
    private JPanel leftPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());

        //Upper area of panel.
        JLabel inputInfo = new JLabel("- - - - Input Information - - - -");
        inputPanel.add(inputInfo, setConstraints(1, 0, 0));

        //Left column with labels.
        JLabel l2 = new JLabel("Seconds to Next Person");
        inputPanel.add(l2, setConstraints(0, 1, -1));
        JLabel l3 = new JLabel("Average Seconds for Check-In");
        inputPanel.add(l3, setConstraints(0, 2, -1));
        JLabel l4 = new JLabel("Total Time in Seconds");
        inputPanel.add(l4, setConstraints(0, 3, -1));
        JLabel l5 = new JLabel("Average Seconds for Voting");
        inputPanel.add(l5, setConstraints(0, 4, -1));
        JLabel l6 = new JLabel("Seconds Before Person Leaves");
        inputPanel.add(l6, setConstraints(0, 5, -1));
        JLabel l7 = new JLabel("Number of Booths");
        inputPanel.add(l7, setConstraints(0, 6, -1));

        //Right column text fields.
        sNextField = new JTextField("5", 10);
        inputPanel.add(sNextField, setConstraints(2, 1, 1));
        aCheckInField = new JTextField("10", 10);
        inputPanel.add(aCheckInField, setConstraints(2, 2, 1));
        tTimeField = new JTextField("1000", 10);
        inputPanel.add(tTimeField, setConstraints(2, 3, 1));
        aVoteField = new JTextField("50", 10);
        inputPanel.add(aVoteField, setConstraints(2, 4, 1));
        sLeaveField = new JTextField("100", 10);
        inputPanel.add(sLeaveField, setConstraints(2, 5, 1));
        nBoothsField = new JTextField("4", 10);
        inputPanel.add(nBoothsField, setConstraints(2, 6, 1));

        //Buttons.
        start = new JButton("Start");
        start.addActionListener(listener);
        inputPanel.add(start, setConstraints(0, 7, 1));
        pause = new JButton("Pause");
        pause.addActionListener(listener);
        inputPanel.add(pause, setConstraints(1, 7, 0));
        reset = new JButton("Reset");
        reset.addActionListener(listener);
        inputPanel.add(reset, setConstraints(2, 7, -1));

        //Lower area of panel.
        JLabel outputInfo = new JLabel("- - - - General Output Information - - - -");
        inputPanel.add(outputInfo, setConstraints(1, 8, 0));
        inputPanel.add(outputLabelPanel(), setConstraints(0, 9, -1));
        inputPanel.add(outputDynamicPanel(), setConstraints(2, 9, 1));

        return inputPanel;
    }

    /**
     * Center panel for GUI containing stats.
     * @return Panel
     */
    private JPanel centerPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());

        //Labels for stats.
        JLabel avg1 = new JLabel("- - - - Average Wait Table 1 - - - -");
        inputPanel.add(avg1, setConstraints(0,0,0));
        inputPanel.add(outputAdvancedInfo(), setConstraints(0, 1, -1));
        inputPanel.add(outputAdvancedDynamic1(), setConstraints(1, 1, 1));
        JLabel avg2 = new JLabel("- - - - Average Wait Table 2 - - - -");
        inputPanel.add(avg2, setConstraints(0, 2, 0));
        inputPanel.add(outputAdvancedInfo(), setConstraints(0, 3, -1));
        inputPanel.add(outputAdvancedDynamic2(), setConstraints(1, 3, 1));
        JLabel avg3 = new JLabel("- - - - Average Wait Central Q - - - -");
        inputPanel.add(avg3, setConstraints(0, 4, 0));
        inputPanel.add(outputAdvancedInfo(), setConstraints(0, 5, -1));
        inputPanel.add(outputAdvancedDynamic3(), setConstraints(1, 5, 1));
        JLabel avg4 = new JLabel("- - - - Average Total Wait - - - -");
        inputPanel.add(avg4, setConstraints(0, 6, 0));
        inputPanel.add(outputAdvancedInfo(), setConstraints(0, 7, -1));
        inputPanel.add(outputAdvancedDynamic4(), setConstraints(1, 7, 1));

        return inputPanel;
    }

    /**
     * Panel that contains labels for stats.
     * @return JPanel
     */
    private JPanel outputLabelPanel() {
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new GridBagLayout());

        //Left column with static labels.
        JLabel l2 = new JLabel("Throughput");
        outputPanel.add(l2, setConstraints(0, 1, -1));
        JLabel l3 = new JLabel("Average Length in Central Q");
        outputPanel.add(l3, setConstraints(0, 2, -1));
        JLabel l4 = new JLabel("Number of Deserters");
        outputPanel.add(l4, setConstraints(0, 3, -1));

        return outputPanel;
    }

    /**
     * Dynamic labels for the stats.
     * @return JPanel
     */
    private JPanel outputDynamicPanel() {
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new GridBagLayout());

        //Right column with dynamic labels.
        throughput = new JLabel("0 people.");
        outputPanel.add(throughput, setConstraints(1, 1, 1));
        avgQLength = new JLabel("0 seconds");
        outputPanel.add(avgQLength, setConstraints(1, 2, 1));
        totDeserters = new JLabel("0 people");
        outputPanel.add(totDeserters, setConstraints(1, 3, 1));

        return outputPanel;
    }

    /**
     * Advanced stats labels.
     * @return JPanel
     */
    private JPanel outputAdvancedInfo() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());

        //Labels for the stats per type of voter.
        JLabel l8 = new JLabel("Regular Voter");
        inputPanel.add(l8, setConstraints(0, 0, -1));
        JLabel l9 = new JLabel("Limited Voter");
        inputPanel.add(l9, setConstraints(0, 1, -1));
        JLabel l10 = new JLabel("Special Voter");
        inputPanel.add(l10, setConstraints(0, 2, -1));
        JLabel l11 = new JLabel("Super Special Voter");
        inputPanel.add(l11, setConstraints(0, 3, -1));

        return inputPanel;
    }

    /**
     * Dynamic labels for the center panel part 1.
     * @return JPanel
     */
    private JPanel outputAdvancedDynamic1() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());

        avgWaitCheckInReg1 = new JLabel("0 seconds");
        inputPanel.add(avgWaitCheckInReg1, setConstraints(0, 0, 1));
        avgWaitCheckInLim1 = new JLabel("0 seconds");
        inputPanel.add(avgWaitCheckInLim1, setConstraints(0, 1, 1));
        avgWaitCheckInSp1 = new JLabel("0 seconds");
        inputPanel.add(avgWaitCheckInSp1, setConstraints(0, 2, 1));
        avgWaitCheckInSS1 = new JLabel("0 seconds");
        inputPanel.add(avgWaitCheckInSS1, setConstraints(0, 3, 1));

        return inputPanel;
    }

    /**
     * Dynamic labels for the center panel part 2.
     * @return JPanel
     */
    private JPanel outputAdvancedDynamic2() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());

        avgWaitCheckInReg2 = new JLabel("0 seconds");
        inputPanel.add(avgWaitCheckInReg2, setConstraints(0, 0, 1));
        avgWaitCheckInLim2 = new JLabel("0 seconds");
        inputPanel.add(avgWaitCheckInLim2, setConstraints(0, 1, 1));
        avgWaitCheckInSp2 = new JLabel("0 seconds");
        inputPanel.add(avgWaitCheckInSp2, setConstraints(0, 2, 1));
        avgWaitCheckInSS2 = new JLabel("0 seconds");
        inputPanel.add(avgWaitCheckInSS2, setConstraints(0, 3, 1));

        return inputPanel;
    }

    /**
     * Dynamic labels for the center panel part 3.
     * @return JPanel
     */
    private JPanel outputAdvancedDynamic3() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());

        avgQReg = new JLabel("0 seconds");
        inputPanel.add(avgQReg, setConstraints(0, 8, 1));
        avgQLim = new JLabel("0 seconds");
        inputPanel.add(avgQLim, setConstraints(0, 9, 1));
        avgQSp = new JLabel("0 seconds");
        inputPanel.add(avgQSp, setConstraints(0, 10, 1));
        avgQSS = new JLabel("0 seconds");
        inputPanel.add(avgQSS, setConstraints(0, 11, 1));

        return inputPanel;
    }

    /**
     * Dynamic labels for the center panel part 4.
     * @return JPanel
     */
    private JPanel outputAdvancedDynamic4() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());

        avgTotReg = new JLabel("0 seconds");
        inputPanel.add(avgTotReg, setConstraints(0, 12, -1));
        avgTotLim = new JLabel("0 seconds");
        inputPanel.add(avgTotLim, setConstraints(0, 13, -1));
        avgTotSp = new JLabel("0 seconds");
        inputPanel.add(avgTotSp, setConstraints(0, 14, -1));
        avgTotSS = new JLabel("0 seconds");
        inputPanel.add(avgTotSS, setConstraints(0, 15, -1));

        return inputPanel;
    }

    /**
     * Helper method for setting GridBagConstraints.
     * @param x Grid X
     * @param y Grid Y
     * @param d 0 = Center Alignment, -1 = Left Alignment, 1 = Right Alignment
     * @return GridBagConstraints with default padding of 5x5.
     */
    private static GridBagConstraints setConstraints(int x, int y, int d) {
        GridBagConstraints c = new GridBagConstraints();

        //Column and row.
        c.gridx = x;
        c.gridy = y;

        //Width and height.
        //c.gridwidth = 1;
        //c.gridheight = 1;

        //Default padding.
        c.ipadx = 5;
        c.ipady = 5;

        //Set alignment.
        if (d == 0)
            c.anchor = GridBagConstraints.CENTER;
        if (d == 1)
            c.anchor = GridBagConstraints.EAST;
        if (d == -1)
            c.anchor = GridBagConstraints.WEST;
        if (d == 2)
            c.fill = GridBagConstraints.HORIZONTAL;

        return c;
    }

    /**
     * Sets up the menu bar for the GUI.
     * @return JMenuBar
     */
    private JMenuBar setupMenu() {
        JMenu fileMenu = new JMenu("File");

        quit = new JMenuItem("Quit");
        quit.addActionListener(listener);
        fileMenu.add(quit);

        JMenuBar menu = new JMenuBar();
        menu.add(fileMenu);
        return menu;
    }

    /**
     * Private Painter class that acts as a helper for the GUI animation.
     */
    private class Painter extends JPanel {

        /**
         * Painter constructor that sets background color to white and the dimensions.
         */
        public Painter() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(PANEL_DIMENSION, PANEL_DIMENSION));
            setDoubleBuffered(true);
        }

        /**
         * Draws the image background, tables, booths, and voters.
         * @see Painter Java Doc
         * @param g Graphics
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            //Loads background image from resource folder.
            ImageIcon i = new ImageIcon(System.class.getResource("/icons/background.png"));
            Image backgroundImage = i.getImage();

            //Draws images on component.
            g.drawImage(backgroundImage, 0, 0, this);
            drawTables(g);
            drawBooths(g);
            drawVoters(g);
        }

        /**
         * Draws all voters onto the component.
         * @param g Graphics
         */
        public void drawVoters(Graphics g) {
            //Checks for all Voters that are currently in the Simulation.
            try {
                for (Voter current : sim.getVoters()) {
                    Image i = current.getVoterImage();
                    g.drawImage(i, current.getMoveX(), current.getMoveY(), this);
                    Toolkit.getDefaultToolkit().sync();
                }
            }
            catch (NullPointerException e) {
                //Syncs and updates component even if the Array is empty.
                Toolkit.getDefaultToolkit().sync();
            }
        }

        /**
         * Draws all tables onto the component.
         * @param g Graphics
         */
        private void drawTables(Graphics g) {
            //Padding.
            int x = 10;
            //Loads image from resource folder.
            ImageIcon i = new ImageIcon(System.class.getResource("/icons/checkintableicon.png"));
            Image tableImage = i.getImage();
            //Checks for all tables that are in simulation and draws them.
            for (int num = 0; num < sim.getTableLine().getTables().size(); num++) {
                g.drawImage(tableImage, x, 10, this);
                Toolkit.getDefaultToolkit().sync();
                //Adjusting padding.
                x += 40;
            }
        }

        /**
         * Draws all booths onto the component.
         * @param g Graphics
         */
        private void drawBooths(Graphics g) {
            //Padding.
            int x = 110;
            //Loads booth icon from resource folder.
            ImageIcon i = new ImageIcon(System.class.getResource("/icons/boothicon.png"));
            Image boothImage = i.getImage();
            //Checks for all booths that are in simulation and draws them.
            for (int num = 0; num < sim.getBoothLine().getBooths().size(); num++) {
                g.drawImage(boothImage, x, 10, this);
                Toolkit.getDefaultToolkit().sync();
                //Adjusting padding.
                x += 37;
            }
        }
    }

    /**
     * Helper method to check for number format of text boxes.
     * @throws NumberFormatException
     */
    private void checkValue() throws NumberFormatException {
        //Regex value calling for only numbers 0-9, up to 10 digits total.
        String regex = "^[0-9]{1,10}$";

        //Checks each field for proper values.
        try {
            if (!sNextField.getText().matches(regex) && Integer.parseInt(sNextField.getText()) != 0)
                throw new NumberFormatException();
            if (!aCheckInField.getText().matches(regex) && Integer.parseInt(aCheckInField.getText()) != 0)
                throw new NumberFormatException();
            if (!tTimeField.getText().matches(regex) && Integer.parseInt(tTimeField.getText()) != 0)
                throw new NumberFormatException();
            if (!aVoteField.getText().matches(regex) && Integer.parseInt(aVoteField.getText()) != 0)
                throw new NumberFormatException();
            if (!sLeaveField.getText().matches(regex) && Integer.parseInt(sLeaveField.getText()) != 0)
                throw new NumberFormatException();
            if (!nBoothsField.getText().matches(regex) && Integer.parseInt(nBoothsField.getText()) != 0)
                throw new NumberFormatException();
            if (Integer.parseInt(nBoothsField.getText()) > 7)
                throw new NumberFormatException();
        } catch (NullPointerException ex) {
            throw new NumberFormatException();
        }
    }

    /**
     * Private ActionListener helper class for GUI buttons.
     */
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //If start button is pressed, the simulation starts.
            if (e.getSource() == start)
                simTimer.start();

            //If the pause button is pressed, the simulation pauses.
            if (e.getSource() == pause)
                simTimer.stop();

            //If the reset button is pressed, the simulation resets to the text box values.
            if (e.getSource() == reset) {
                try {
                    //Checks the values in the boxes.
                    checkValue();

                    //Sets simulation to the values.
                    sim = new Simulation(Integer.parseInt(sNextField.getText()), Integer.parseInt(aVoteField.getText()),
                            Integer.parseInt(sLeaveField.getText()), Integer.parseInt(aCheckInField.getText()),
                            Integer.parseInt(tTimeField.getText()), Integer.parseInt(nBoothsField.getText()));
                    stats = new Stats(sim.getBoothLine(), sim.getTableLine(), sim.getCentralQ());
                } catch (NumberFormatException ex) {
                    //If values don't apply to simulation, a pop-up alert tells the user it is invalid.
                    JOptionPane.showMessageDialog(frame, "Invalid number for simulation.", "Alert",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    //Resets timer.
                    simTimer.restart();
                }
            }
            //If the quit menu button is selected, then the frame closes.
            if (e.getSource() == quit)
                System.exit(0);
        }
    }
}
