import java.util.ArrayList;

/**
 * Voting booth line that holds total number of voting booths.
 *
 * @author David Calkins
 * @version 10.29.2016
 */
public class VotingBoothLine implements ClockListener {
    private ArrayList<VotingBooth> booths;

    /**
     * @return array list of voting booths.
     */
    public ArrayList<VotingBooth> getBooths() {
        return booths;
    }

    /**
     * Setter for voting booth array list.
     * @param booths array list of voting booths.
     */
    public void setBooths(ArrayList<VotingBooth> booths) {
        this.booths = booths;
    }

    /**
     * Arraylist of voting booths.
     * @param n int value of number of booths.
     */
    public VotingBoothLine(int n) {
        booths = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            booths.add(new VotingBooth());
        }
    }

    /**
     * the event of the voting booth line triggers the events of each booth
     **/
    public void event(int tick) {
        for (int i = 0; i < booths.size(); i++) {
            System.out.println("Now running booth " + i + " tick " + tick);
            booths.get(i).event(tick);
        }
    }
}