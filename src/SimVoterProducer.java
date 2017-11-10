import javax.swing.*;
import java.util.ArrayList;

/**
 * Modified VoterProducer for project simulation.
 *
 * @author David Calkins
 * @version 11.06.2016
 */
public class SimVoterProducer extends VoterProducer {
    private int averageLeaveTime;
    private int averageCheckInTime;
    private CheckInTableLine tableLine;
    private int regVoterLimit;
    private int limVoterLimit;
    private int spNeedsVoterLimit;
    private int supSpNeedsVoterLimit;
    private int tableNumber;
    private Voter person;
    private ArrayList<Voter> voters;

    /**
     * Constructor
     * @param numOfTicksNextPerson   frequency for voter production
     * @param averageBoothTime    avg. time each voter spends in booth voting
     * @param averageLeaveTime    avg. time voter will wait before leaving
     * @param averageCheckInTime    avg. time voter needs to check in
     * @param percentRegVoters   chance of producing a regular voter
     * @param percentLimVoters    chance of producing a limited voter
     * @param percentSpNeedsVoters    chance of producing a sp. needs voter
     * @param percentSupSpNeedsVoters    chance of producing sup. sp. nds voter
     * @param tableLine   check in tables where producer can send new voters
     */
    public SimVoterProducer(int numOfTicksNextPerson,
                            int averageBoothTime, int averageLeaveTime, int averageCheckInTime,
                            int percentRegVoters, int percentLimVoters, int percentSpNeedsVoters,
                            int percentSupSpNeedsVoters, CheckInTableLine tableLine) {

        /** first, uses the original constructor, then assigns other values **/
        super(numOfTicksNextPerson, averageBoothTime);
        this.averageCheckInTime = averageCheckInTime;
        this.averageLeaveTime = averageLeaveTime;
        this.tableLine = tableLine;
        /** all percentages must sum to exactly 100 **/
        if (percentRegVoters + percentLimVoters + percentSpNeedsVoters + percentSupSpNeedsVoters != 100) {
            throw new IllegalArgumentException("Bad voter ratio");
        }

        /** a random number between 0 and 100 is generated during voter producer's event
         * to determine what kind of voter to make.
         * Thus, each passed value for "percent X voter" must be converted to represent
         * upper limit of a range of values 0 to 100
         *
         *  Example:
         *      70% regular voters, 20% limited, 10% special needs, 0% super special.
         *      Resulting logic:
         *          if random number <= 70, make regular voter
         *          if > 70 and <= 90, (this is 20%) make limited voter
         *          if > 90 and <= 100, (this is 10%) make sp needs voter
         *          if any percent value param is 0, then for that type of voter, test if random # is <= -1
         *              (which it will never be; random number is between 0 and 100) **/
        if (percentRegVoters == 0)
            regVoterLimit = -1;
        else
            regVoterLimit = percentRegVoters;
        if (percentLimVoters == 0)
            limVoterLimit = -1;
        else
            limVoterLimit = regVoterLimit + percentLimVoters;
        if (percentSpNeedsVoters == 0)
            spNeedsVoterLimit = -1;
        else
            spNeedsVoterLimit = limVoterLimit + percentSpNeedsVoters;
        if (percentSupSpNeedsVoters == 0)
            supSpNeedsVoterLimit = -1;
        else
            supSpNeedsVoterLimit = spNeedsVoterLimit + percentSupSpNeedsVoters;

        /** initialize table number to 0
         * Voterproducer will distribute voters evenly, moving from table 0 to table n in order **/
        tableNumber = 0;

        voters = new ArrayList<>();

    }

    /**
     * Event loop for each tick which produces a voter and sends them to a table.
     * @param tick int value representing time.
     */
    @Override
    public void event(int tick) {

        /** if it's time to have an event **/
        if (nextPerson <= tick) {

            /** set the next time an event should occur **/
            nextPerson = tick + numOfTicksNextPerson;

            /** generate random number **/
            int rNumber = (int) (Math.random() * 100);

            /** use limit values initialized in constructor to determine what kind of voter to make **/
            if (rNumber <= regVoterLimit) {
                ImageIcon i = new ImageIcon(System.class.getResource("/icons/votericonregular.png"));
                person = new RegularVoter();
                person.setVoterImage(i.getImage());
                System.out.println("new Regular Voter produced");
            } else if (rNumber <= limVoterLimit) {
                ImageIcon i = new ImageIcon(System.class.getResource("/icons/votericonlimited.png"));
                person = new LimitedTimeVoter();
                person.setVoterImage(i.getImage());
                System.out.println("new Limited Time Voter produced");
            } else if (rNumber <= spNeedsVoterLimit) {
                ImageIcon i = new ImageIcon(System.class.getResource("/icons/votericonspecial.png"));
                person = new SpecialNeedsVoter();
                person.setVoterImage(i.getImage());
                System.out.println("new Special Needs Voter produced");
            } else if (rNumber <= supSpNeedsVoterLimit) {
                ImageIcon i = new ImageIcon(System.class.getResource("/icons/votericonsuperspecial.png"));
                person = new SuperSpecialNeedsVoter();
                person.setVoterImage(i.getImage());
                System.out.println("new Super Special Needs Voter produced");
            }

            /** set the newly created voter's specs
             * All values will have a bell curve distribution
             * grouped around the average time provided during voterproducer initialization **/
            person.setBoothTime((int) Math.round(standardDeviationDistrib(averageBoothTime)));
            person.setLeaveTime((int) Math.round(standardDeviationDistrib(averageLeaveTime)));
            person.setCheckInTime((int) Math.round(standardDeviationDistrib(averageCheckInTime)));

            person.setTimeCreated(tick);

            /** make sure voter won't be sent to non-existant table **/
            if (tableNumber >= tableLine.getTables().size())
                tableNumber = 0;

            /** send voter to check in table **/
            tableLine.getTables().get(tableNumber).add(person);
            System.out.println("voter sent to table " + tableNumber);

            person.setTable(tableNumber);
            person.setMoveX(0);
            person.setMoveY(100);
            person.setEndX(20 + tableNumber * 40);
            person.setEndY(50);

            try {
                voters.add(person);
            } catch (NullPointerException e) {
                //do nothing
            }

            /** when the next voter is produced, send them to the next check in table **/
            tableNumber++;
        }
    }

    /**
     * Helper method to generate a random value with a bell curve distribution
     * grouped around the parameter value. Random value has 70% chance of
     * being one standard deviation away from parameter.
     *
     * @param averageVal the average value to group around
     * @return double new random number
     */
    private double standardDeviationDistrib(int averageVal) {
        return (averageVal * 0.5 * r.nextGaussian() + averageVal + .5);
    }

    /**
     * Getter for ArrayList of Voters.
     * @return ArrayList of Voters.
     */
    public ArrayList<Voter> getVoters() {
        return voters;
    }

}
