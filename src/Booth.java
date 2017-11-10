import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Booth holds an event tick while holding/sending a voter and containing a Q of voters.
 * @author Roger Ferguson
 * @author David Calkins
 */
public class Booth implements ClockListener {
    protected ArrayList<Voter> Q = new ArrayList<>();
    protected int numDesertedVoters = 0;
    protected int timeOfNextEvent = 0;
    protected int maxQlength = 0;
    protected int completed = 0;
    protected Voter person = null;   // this is the person at the booth.
    protected HashMap<String, ArrayList<Integer>> waitTimes = new HashMap<>();
    protected Voter newestVoter;

    /**
     * Main construction of Booth.
     */
    @SuppressWarnings("InstantiatingObjectToGetClassObject")
    Booth() {
        waitTimes.put(new RegularVoter().getClass().getSimpleName(), new ArrayList<>());
        waitTimes.put(new LimitedTimeVoter().getClass().getSimpleName(), new ArrayList<>());
        waitTimes.put(new SpecialNeedsVoter().getClass().getSimpleName(), new ArrayList<>());
        waitTimes.put(new SuperSpecialNeedsVoter().getClass().getSimpleName(), new ArrayList<>());
    }

    /**
     * Getter for Voter person.
     * @return Voter object.
     */
    Voter getPerson() {
        return person;
    }

    /**
     * Setter for Voter person.
     * @param person Voter object.
     */
    public void setPerson(Voter person) {
        this.person = person;
    }

    /**
     * Getter for Q ArrayList of type Voter object.
     * @return ArrayList of Voter objects.
     */
    public ArrayList<Voter> getQ() {
        return Q;
    }

    /**
     * Getter for newestVoter object.
     * @return Voter object.
     */
    public Voter getNewestVoter() {
        return newestVoter;
    }

    /**
     * Add a Voter object to the Booth's Q. Set the voter as the newestVoter.
     * If the size of the Q is larger than the recorded length, the length is updated.
     * @param person Voter object.
     */
    public void add(Voter person) {
        Q.add(person);
        //add as newest vote
        newestVoter = person;
//		System.out.println("voter added to Q");
        if (Q.size() > maxQlength)
            maxQlength = Q.size();
    }

    /**
     * Event tick that removes a voter from the Q. Sets next time of event by the Voters booth time.
     * @param tick int variable representing time.
     */
    public void event(int tick) {
        if (tick >= timeOfNextEvent) {
//			if (person != null) { 			// Notice the delay that takes place here
//				person.getDestination().add(person);    // take this person to the next station.
//			person = null;				// I have send the person on.
//			}

            if (Q.size() >= 1) {
                person = Q.remove(0);        // do not send this person as of yet, make them wait.
                timeOfNextEvent = tick + (int) (person.getBoothTime() + 1);
                completed++;
            }
        }
    }

    /**
     * Checks the leave time for a voter.
     * If the time created plus the voters leave time is less than the current tick, the voter deserts the sim.
     * @param tick int variable representing time.
     */
    void checkLeaveTime(int tick) {
        Iterator<Voter> iter = Q.iterator();
        while (iter.hasNext()) {
            Voter person = iter.next();
            if (person.getTimeCreated() + person.getLeaveTime() <= tick) {
                person.setEndX(350);
                person.setEndY(350);
                iter.remove();
                numDesertedVoters++;
            }
        }
    }

    /**
     * @return Size of Q.
     */
    public int getLeft() {
        return Q.size();
    }

    /**
     * @return Max size of Q.
     */
    public int getMaxQlength() {
        return maxQlength;
    }

    /**
     * @return Amount of Voters who have completed simulation.
     */
    public int getThroughPut() {
        return completed;
    }

    /**
     * @return Amount of Voters who have deserted the simulation.
     */
    public int getNumDesertedVoters() {
        return numDesertedVoters;
    }
}
