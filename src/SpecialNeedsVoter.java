/**
 * Special Needs Voter that requires extra time compared to a Regular Voter.
 *
 * @author Natalie Rodriguez
 * @author David Calkins
 * @version 10.29.2016
 */
public class SpecialNeedsVoter extends Voter {
    /**
     * Sets voters check in time * 1.5.
     * @param checkInTime int value representing time.
     */
    @Override
    public void setCheckInTime(int checkInTime) {
        this.checkInTime = (int) Math.round(checkInTime * 1.5);
    }

    /**
     * Sets voters leave time * 2.
     * @param leaveTime int value representing time.
     */
    @Override
    public void setLeaveTime(int leaveTime) {
        this.leaveTime = leaveTime * 2;
    }

    /**
     * Sets voters booth time * 3.
     * @param boothTime int value representing time.
     */
    @Override
    public void setBoothTime(int boothTime) {
        this.boothTime = boothTime * 3;
    }
}