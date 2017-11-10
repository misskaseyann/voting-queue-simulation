/**
 * Class representing a Check In Table.
 * @author David Calkins
 * @version 10.27.2016
 */
public class CheckInTable extends Booth implements ClockListener {
    private CentralQ centralDestination;
    private int tableNumber;
    private int xPos;
    private int yPos;

    /**
     * Getter for the table number.
     * @return int table number.
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Setter for the table number that also instantiates its X Y coordinates.
     * @param tableNumber int variable representing the number table.
     */
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
        xPos = 10 + (40 * tableNumber);
        yPos = 10;
    }

    /**
     * Sets the destination for every voter exiting the Q of the CheckInTable.
     * @param centerQ CentralQ object for the final Q.
     */
    public CheckInTable(CentralQ centerQ) {
        centralDestination = centerQ;
    }

    /**
     * Cycles through the Q for the Check In Table while sending Voters to the Central Q.
     * @param tick int variable representing time.
     */
    @Override
    public void event(int tick) {

        /** if it's time to have an event **/
        if (tick >= timeOfNextEvent) {

            /** if a person was checking in **/
            if (person != null) {

                /** add their total wait time at the table to the array of wait times for their type of voter **/
                waitTimes.get(person.getClass().getSimpleName()).add(tick - person.getTimeCreated());

                /** send them to the central queue **/
                centralDestination.add(person);
                person.setTimeEnteredCenterQ(tick);
                System.out.println("voter moved to central Q " + tick);
                person = null;
            }

            /** if someone is next in line, start processing them **/
            if (!Q.isEmpty()) {
                person = Q.remove(0);
                System.out.println("new voter checking in " + tick);

                /** set time to have next event (when current voter is done) **/
                timeOfNextEvent = tick + person.getCheckInTime() + 1;
            }
        }
        /** Checks if a voter should desert the simulation. **/
        checkLeaveTime(tick);

        /** Sets the voter's X Y coordinate to move in animation. **/
        adapt();
    }

    /**
     * Sets the Voter object's moving X Y coordinate for the animation.
     */
    public void adapt(){
        if (person != null) {
            person.setEndX(20 + tableNumber * 40);
            person.setEndY(20);
        }
        for (int i = 0; i < Q.size(); i++){
            Q.get(i).setEndX(xPos + 10);
            Q.get(i).setEndY(yPos + 40 + (i+1)*20);
        }
    }
}