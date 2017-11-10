/**
 * Modified Clock class.
 *
 * @author Kasey Stowell
 * @author Roger Ferguson
 * @version 10.27.2016
 */
public class Clock {
    private ClockListener[] myListeners;
    private int numListeners;
    private final int MAX = 100;
    //Added two additional variables for tracking time.
    private int currentTick;
    private int endingTime;

    /**
     * Original constructor that auto instantiates the sim ending time to 1000.
     */
    public Clock() {
        numListeners = 0;
        myListeners = new ClockListener[MAX];
        //added instantiation of current tick = 0 and a default ending time of 1000.
        currentTick = 0;
        endingTime = 10000;
    }

    /**
     * Loaded constructor that sets the sim ending time to the time parameter.
     * @param time int variable representing time.
     */
    public Clock(int time) {
        numListeners = 0;
        myListeners = new ClockListener[MAX];
        //added instantiation of current tick = 0 and a default ending time of 1000.
        currentTick = 0;
        endingTime = time;
    }

    /**
     * Method that auto runs the clock.
     */
    public void run() {
        for (int currentTime = 0; currentTime <= endingTime; currentTime++) {
            for (int j = 0; j < numListeners; j++)
                myListeners[j].event(currentTime);
        }
    }

    /**
     * Method that increments currentTick and loops through all listeners, firing their event.
     */
    public void clockTick() {
        if (currentTick <= endingTime)
            for (int x = 0; x < numListeners; x++)
                myListeners[x].event(currentTick);
        currentTick++;
    }

    /**
     * Adds a ClockListener object for the clock to cycle through.
     * @param cl ClockListener object.
     */
    public void add(ClockListener cl) {
        myListeners[numListeners] = cl;
        numListeners++;
    }

    /**
     * Getter for myListeners Array.
     * @return Array of ClockListeners.
     */
    public ClockListener[] getMyListeners() {
        return myListeners;
    }

    /**
     * Setter for myListeners Array.
     * @param myListeners Array of ClockListeners.
     */
    public void setMyListeners(ClockListener[] myListeners) {
        this.myListeners = myListeners;
    }

    /**
     * Getter for numListeners int.
     * @return numListeners int.
     */
    public int getNumListeners() {
        return numListeners;
    }

    /**
     * Setter for numListeners int.
     * @param numListeners int value for numListeners.
     */
    public void setNumListeners(int numListeners) {
        this.numListeners = numListeners;
    }

    /**
     * Getter for final int of MAX.
     * @return final int MAX.
     */
    public int getMAX() {
        return MAX;
    }

    /**
     * Getter for currentTick.
     * @return int value of currentTick.
     */
    public int getCurrentTick() {
        return currentTick;
    }

    /**
     * Setter for currentTick.
     * @param currentTick int value of currentTick.
     */
    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
    }

    /**
     * Getter for endingTime.
     * @return int value of endingTime.
     */
    public int getEndingTime() {
        return endingTime;
    }

    /**
     * Setter for endingTime.
     * @param endingTime int value of endingTime.
     */
    public void setEndingTime(int endingTime) {
        this.endingTime = endingTime;
    }
}