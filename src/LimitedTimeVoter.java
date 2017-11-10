/**
 * Limited Voter with shorter leave time.
 *
 * @author Natalie Rodriguez
 * @author David Calkins
 * @version 10.29.2016
 */
public class LimitedTimeVoter extends Voter {
    /**
     * Setter for leave time that cuts the voters time in half.
     * @param leaveTime int value that represents time.
     */
    @Override
    public void setLeaveTime(int leaveTime) {
        this.leaveTime = (int) Math.round(leaveTime * .5);
    }

    /**
     * Setter for booth time that cuts the voters time in half.
     * @param boothTime int value that represents time.
     */
    @Override
    public void setBoothTime(int boothTime) {
        this.boothTime = (int) Math.round(boothTime * .5);
    }
}