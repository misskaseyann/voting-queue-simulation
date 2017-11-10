import java.util.ArrayList;

/**
 * Simulation class for running a full voter simulation.
 *
 * @author David Calkins
 * @author Kasey Stowell
 * @version 11.06.2016
 */
public class Simulation {
    private Clock clock;
    private VotingBoothLine boothLine;
    private CentralQ centralQ;
    private CheckInTableLine tableLine;
    private SimVoterProducer voterProducer;
    private ArrayList<Voter> voters;

    /**
     * Standard constructor that sets:
     * Booths: 4
     * Tables: 2
     * Ticks to Next Voter: 5
     * Average Booth Time: 50
     * Average Leave Time: 100
     * Average Check-in Time: 10
     * % Regular Voters: 60
     * % Limited Voters: 25
     * % Special Voters: 10
     * % Super Special Voters: 5
     */
    public Simulation() {
        clock = new Clock();

        //sets standard four booths
        boothLine = new VotingBoothLine(4);

        //sets centralq with a line
        centralQ = new CentralQ(boothLine);

        //sets standard 2 checkintables with a centralq for voters
        tableLine = new CheckInTableLine(2, centralQ);

        //ticks to next, avg booth time, avg leave time, avg check-in time, % reg, % lim, % sp needs, % super sp needs,
        // check in table
        voterProducer = new SimVoterProducer(5, 50, 100, 10, 60, 25, 10, 5, tableLine);

        clock.add(boothLine);
        clock.add(centralQ);
        clock.add(tableLine);
        clock.add(voterProducer);

        voters = voterProducer.getVoters();
    }

    /**
     * Loaded constructor that allows user input of variables.
     * @param ticks int time until next voter is produced.
     * @param boothTime int time spent at a booth.
     * @param leaveTime int time when voter abandons simulation.
     * @param checkIn int time it takes for check-in.
     * @param totalTime int total time of simulation.
     * @param nBooths int number of booths.
     */
    public Simulation(int ticks, int boothTime, int leaveTime, int checkIn, int totalTime, int nBooths) {
        clock = new Clock(totalTime);
        //sets standard four booths
        boothLine = new VotingBoothLine(nBooths);
        //sets centralq with a line
        centralQ = new CentralQ(boothLine);
        //sets standard 2 checkintables with a centralq for voters
        tableLine = new CheckInTableLine(2, centralQ);
        //ticks to next, avg booth time, avg leave time, avg check-in time, % reg, % lim, % sp needs, % super sp needs, check in table
        voterProducer = new SimVoterProducer(ticks, boothTime, leaveTime, checkIn, 60, 25, 10, 5, tableLine);

        clock.add(boothLine);
        clock.add(centralQ);
        clock.add(tableLine);
        clock.add(voterProducer);

        voters = voterProducer.getVoters();
    }

    /**
     * Getter for Clock object.
     * @return Clock
     */
    public Clock getClock() {
        return clock;
    }

    /**
     * Setter for Clock object.
     * @param clock Clock object.
     */
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    /**
     * Getter for VotingBoothLine object.
     * @return VotingBoothLine object.
     */
    public VotingBoothLine getBoothLine() {
        return boothLine;
    }

    /**
     * Setter for VotingBoothLine object.
     * @param boothLine VotingBoothLine object.
     */
    public void setBoothLine(VotingBoothLine boothLine) {
        this.boothLine = boothLine;
    }

    /**
     * Getter for CentralQ object.
     * @return CentralQ object.
     */
    public CentralQ getCentralQ() {
        return centralQ;
    }

    /**
     * Setter for CentralQ object.
     * @param centralQ CentralQ object.
     */
    public void setCentralQ(CentralQ centralQ) {
        this.centralQ = centralQ;
    }

    /**
     * Getter for CheckInTableLine object.
     * @return CheckInTableLine object.
     */
    public CheckInTableLine getTableLine() {
        return tableLine;
    }

    /**
     * Setter for CheckInTableLine object.
     * @param tableLine CheckInTableLine object.
     */
    public void setTableLine(CheckInTableLine tableLine) {
        this.tableLine = tableLine;
    }

    /**
     * Getter for SimVoterProducer object.
     * @return SimVoterProducer object.
     */
    public SimVoterProducer getVoterProducer() {
        return voterProducer;
    }

    /**
     * Setter for SimVoterProducer object.
     * @param voterProducer SimVoterProducer object.
     */
    public void setVoterProducer(SimVoterProducer voterProducer) {
        this.voterProducer = voterProducer;
    }

    /**
     * Getter for ArrayList of Voters.
     * @return ArrayList of Voters.
     */
    public ArrayList<Voter> getVoters() {
        return voters;
    }

    /**
     * Method that ticks the clock and allows the simulation to loop one "turn".
     */
    public void tick() {
        clock.clockTick();
    }
}
