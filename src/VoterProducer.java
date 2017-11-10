import java.util.Random;

/**
 * @author kaseystowell
 * @version 10.27.2016
 */
public class VoterProducer implements ClockListener {

    protected int nextPerson = 0;
    private Booth booth;
    protected int numOfTicksNextPerson;
    protected int averageBoothTime;

    protected Random r = new Random();

    VoterProducer(int numOfTicksNextPerson, int averageBoothTime) {
        this.numOfTicksNextPerson = numOfTicksNextPerson;
        this.averageBoothTime = averageBoothTime;
        booth = null;
    }

    VoterProducer(Booth booth, int numOfTicksNextPerson,
                  int averageBoothTime) {

        this.booth = booth;
        this.numOfTicksNextPerson = numOfTicksNextPerson;
        this.averageBoothTime = averageBoothTime;
        //r.setSeed(13);    // This will cause the same random numbers
    }

    public void event(int tick) {
        if (nextPerson <= tick) {
            nextPerson = tick + numOfTicksNextPerson;

            Voter person = new RegularVoter();

            int rNumber = (int) (Math.random() * 100);

            person.setBoothTime((int) (averageBoothTime * 0.5 * r.nextGaussian() + averageBoothTime + .5));
            person.setTimeCreated(tick);
            try {
                assert booth != null;
                booth.add(person);
            } catch (NullPointerException e) {
                //do nothing
            }

            //	person.setDestination(theLocationAfterTheBooth);  // You can save off where the voter should go.
        }
    }
}