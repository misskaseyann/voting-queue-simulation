import java.awt.*;

/**
 * @author Roger Ferguson
 * @author Kasey Stowell
 * @author David Calkins
 */
public abstract class Voter {

    //these fields are for keeping track of stats
    private int timeCreated;
    private int timeEnteredCenterQ;

    //fields for animation
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int moveX;
    private int moveY;
    private int table;
    private boolean center;
    private Image voterImage;

    /**
     * these fields are used in the sim
     **/
    private Booth destination;   //this is unused in our sim, but I'm keeping it just in case

    //time needed at the check in table
    int checkInTime;

    //time needed to vote in a VotingBoothbooth
    int boothTime;

    //time elapsed before voter leaves
    int leaveTime;

    /**
     * @return time entered Q.
     */
    public int getTimeEnteredCenterQ() {
        return timeEnteredCenterQ;
    }

    /**
     * Set time entered Q.
     * @param timeEnteredCenterQ
     */
    public void setTimeEnteredCenterQ(int timeEnteredCenterQ) {
        this.timeEnteredCenterQ = timeEnteredCenterQ;
    }

    /**
     * @return time created.
     */
    public int getTimeCreated() {
        return timeCreated;
    }

    /**
     * Set time created.
     * @param timeCreated
     */
    public void setTimeCreated(int timeCreated) {
        this.timeCreated = timeCreated;
    }

    /**
     * @return Booth destination.
     */
    public Booth getDestination() {
        return destination;
    }

    /**
     * Set Booth destination.
     * @param destination
     */
    public void setDestination(Booth destination) {
        this.destination = destination;
    }

    /**
     * @return check in time.
     */
    public int getCheckInTime() {
        return checkInTime;
    }

    /**
     * Set check in time.
     * @param checkInTime
     */
    public void setCheckInTime(int checkInTime) {
        this.checkInTime = checkInTime;
    }

    /**
     * @return booth time.
     */
    public double getBoothTime() {
        return boothTime;
    }

    /**
     * Set booth time.
     * @param boothTime
     */
    public void setBoothTime(int boothTime) {
        this.boothTime = boothTime;
    }

    /**
     * @return leave time.
     */
    public int getLeaveTime() {
        return leaveTime;
    }

    /**
     * Set leave time.
     * @param leaveTime
     */
    public void setLeaveTime(int leaveTime) {
        this.leaveTime = leaveTime;
    }

    /**
     * @return start X position.
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Set start X.
     * @param startX
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }

    /**
     * @return start Y position.
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Set start Y.
     * @param startY
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    /**
     * @return get end X position.
     */
    public int getEndX() {
        return endX;
    }

    /**
     * Set end X.
     * @param endX
     */
    public void setEndX(int endX) {
        this.endX = endX;
    }

    /**
     * @return get end Y position.
     */
    public int getEndY() {
        return endY;
    }

    /**
     * Set end Y position.
     * @param endY
     */
    public void setEndY(int endY) {
        this.endY = endY;
    }

    /**
     * @return moving X position.
     */
    public int getMoveX() {
        return moveX;
    }

    /**
     * Set moving X position.
     * @param moveX
     */
    public void setMoveX(int moveX) {
        this.moveX = moveX;
    }

    /**
     * @return moving Y position.
     */
    public int getMoveY() {
        return moveY;
    }

    /**
     * Set moving Y position.
     * @param moveY
     */
    public void setMoveY(int moveY) {
        this.moveY = moveY;
    }

    /**
     * @return table.
     */
    public int getTable() {
        return table;
    }

    /**
     * Set table.
     * @param table
     */
    public void setTable(int table) {
        this.table = table;
    }

    /**
     * Allows the voter to move in the animation.
     */
    public void takeStep() {
        // If they haven't reached their X destination...
        if (moveX != endX){
            // if they need to move down, move down
            if (moveX < endX){
                moveX++;
            }
            // else move up
            else
                moveX--;
        }
        // If they haven't reached their Y destination...
        if (moveY != endY){
            // if they need to move down, move down
            if (moveY < endY){
                moveY++;
            }
            // else move up
            else moveY--;
        }
    }

    /**
     * @return Voter image.
     */
    public Image getVoterImage() {
        return voterImage;
    }

    /**
     * Set voters image.
     * @param voterImage
     */
    public void setVoterImage(Image voterImage) {
        this.voterImage = voterImage;
    }
}