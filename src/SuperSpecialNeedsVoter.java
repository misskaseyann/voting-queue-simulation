/**
 * @author natalierodriguez
 * @version 10.29.2016
 */
public class SuperSpecialNeedsVoter extends SpecialNeedsVoter {

    @Override
    public void setCheckInTime(int checkInTime) {
        this.checkInTime = checkInTime * 2;
    }

    @Override
    public void setLeaveTime(int leaveTime) {
        this.leaveTime = (int) Math.round(leaveTime * 1.5);
    }

    @Override
    public void setBoothTime(int boothTime) {
        this.boothTime = (int) Math.round(boothTime * 1.5);
    }
}