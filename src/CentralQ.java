/**
 * Central Q that allows Voters to wait for their voting booth.
 * @author David Calkins
 * @version 10.27.2016
 */
public class CentralQ extends Booth implements ClockListener {
    private VotingBoothLine boothLine;

    /**
     * Main constructor for object.
     * @param boothLine VotingBoothLine object.
     */
    public CentralQ(VotingBoothLine boothLine) {
        super();
        this.boothLine = boothLine;
    }

    /**
     * Sends voters to an available voting booth from the main Q.
     * @param tick int variable representing time.
     */
    @Override
    public void event(int tick) {

        /** continue attempting to send voters to open booths while there are people waiting in the queue **/
        while (Q.size() > 0 && !noBoothsOpen()) {

            /** check each booth **/
            for (int i = 0; i < boothLine.getBooths().size(); i++){
                VotingBooth booth = boothLine.getBooths().get(i);

                try {
                    /** if the booth is available **/
                    if (booth.getPerson() == null) {

                        /** add the next person in line's total wait time to the array of wait times for their type of voter **/
                        Voter person = Q.get(0);
                        waitTimes.get(person.getClass().getSimpleName()).add(tick - person.getTimeEnteredCenterQ());

                        /** and send them to the available booth **/
                        System.out.println("Sending voter to booth");
                        person.setEndX(120 + (37 * i));
                        person.setEndY(45);  //TODO might be off
                        booth.add(Q.remove(0), tick);
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        /** Check the time a voter will abandon the simulation. **/
        checkLeaveTime(tick);

        /** Moves voter in animation. **/
        adapt();
    }

    /**
     * Helper method that moves the voter from the Q into their booth.
     */
    public void adapt(){
        for (int i = 0; i < Q.size(); i++){
            Q.get(i).setEndX(180);
            Q.get(i).setEndY(100 + (i*20));
        }
    }

    /**
     * Helper method. Returns true if no booths are available to receive voter
     *
     * @return true if a booth is available, false if not
     */
    private boolean noBoothsOpen() {
        for (VotingBooth booth : boothLine.getBooths())
            if (booth.getPerson() == null)
                return false;
        return true;
    }
}