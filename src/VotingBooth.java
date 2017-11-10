/**
 * Voting Booth that each contains a voter coming in from the central Q.
 *
 * @author David Calkins
 * @version 11.06.2016
 */
public class VotingBooth extends Booth implements ClockListener {

    //The VotingBooth hashmap contains arraylists of each completed voter's TOTAL time from start to finish

    /**
     * Constructor that echoes the super.
     */
    public VotingBooth() {
        super();
    }

    /**
     * Adds a voter to the booth.
     * @param person Voter object.
     * @param tick int variable representing time/loop in simulation.
     * @throws Exception
     */
    public void add(Voter person, int tick) throws Exception {
        if (this.person != null)
            throw new Exception("There is already a voter in this booth " + tick);
        this.person = person;
        System.out.println("new voter admitted to booth " + tick);
        timeOfNextEvent = tick + this.person.boothTime + 1;
    }

    /**
     * Loops through event ticks to send Voters out of the booth and out of the simulation.
     * @param tick int variable representing time.
     */
    @Override
    public void event(int tick) {
        if (tick >= timeOfNextEvent) {
            if (person != null) {

                /** add the finished voter's total time in the sim to the waittimes list **/
                waitTimes.get(person.getClass().getSimpleName()).add(tick - person.getTimeCreated());

                /** forget them (let them leave) **/
                person.setEndX(350);
                person.setEndY(100);
                person = null;
                completed++;
                System.out.println("voter done " + tick);
            }
        }
    }

}