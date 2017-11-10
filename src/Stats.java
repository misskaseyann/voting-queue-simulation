/**
 * Stats class that provides all simulation statistics.
 *
 * @author David Calkins
 * @version 11.5.2016
 */
class Stats {
    private VotingBoothLine boothLine;
    private CheckInTableLine tableLine;
    private CentralQ centralQ;

    private int avgWaitTimeCheckInReg_table1;
    private int avgWaitTimeCheckInLim_table1;
    private int avgWaitTimeCheckInSp_table1;
    private int avgWaitTimeCheckInSupSp_table1;

    private int avgWaitTimeCheckInReg_table2;
    private int avgWaitTimeCheckInLim_table2;
    private int avgWaitTimeCheckInSp_table2;
    private int avgWaitTimeCheckInSupSp_table2;

    private int avgWaitTimeCentralQReg;
    private int avgWaitTimeCentralQLim;
    private int avgWaitTimeCentralQSp;
    private int avgWaitTimeCentralQSupSp;

    private int averageCentralQlength;
    private int averageCheckInQlength;

    private int averageTotalSimTimeReg;
    private int averageTotalSimTimeLim;
    private int averageTotalSimTimeSp;
    private int averageTotalSimTimeSupSp;

    private int throughput;

    private int totalDeserters;

    /**
     * Loaded constructor that sets up objects that are already instantiated in the simulation.
     *
     * @param boothLine voting booth line.
     * @param tableLine table line.
     * @param centralQ central q.
     */
    public Stats(VotingBoothLine boothLine, CheckInTableLine tableLine, CentralQ centralQ) {
        this.boothLine = boothLine;
        this.tableLine = tableLine;
        this.centralQ = centralQ;
    }

    /**
     * Updates the statistics using objects in the simulation.
     */
    public void updateStats() {

        double average;
        int sum;
        int count;

        /** Check in tables - Regular Voter **/
        avgWaitTimeCheckInReg_table1 = (int) Math.round(getAverage(tableLine.getTables().get(0), new RegularVoter()));
        avgWaitTimeCheckInReg_table2 = (int) Math.round(getAverage(tableLine.getTables().get(1), new RegularVoter()));

        /** Check in tables - Limited Time Voter **/
        avgWaitTimeCheckInLim_table1 = (int) Math.round(getAverage(tableLine.getTables().get(0), new LimitedTimeVoter()));
        avgWaitTimeCheckInLim_table2 = (int) Math.round(getAverage(tableLine.getTables().get(1), new LimitedTimeVoter()));

        /** Check in tables - Special Needs Voter **/
        avgWaitTimeCheckInSp_table1 = (int) Math.round(getAverage(tableLine.getTables().get(0), new SpecialNeedsVoter()));
        avgWaitTimeCheckInSp_table2 = (int) Math.round(getAverage(tableLine.getTables().get(1), new SpecialNeedsVoter()));

        /** Check in tables - Super Needs Voter **/
        avgWaitTimeCheckInSupSp_table1 = (int) Math.round(getAverage(tableLine.getTables().get(0), new SuperSpecialNeedsVoter()));
        avgWaitTimeCheckInSupSp_table2 = (int) Math.round(getAverage(tableLine.getTables().get(1), new SuperSpecialNeedsVoter()));

        /** Central Q Voters **/
        avgWaitTimeCentralQReg = (int) Math.round(getAverage(centralQ, new RegularVoter()));
        avgWaitTimeCentralQLim = (int) Math.round(getAverage(centralQ, new LimitedTimeVoter()));
        avgWaitTimeCentralQSp = (int) Math.round(getAverage(centralQ, new SpecialNeedsVoter()));
        avgWaitTimeCentralQSupSp = (int) Math.round(getAverage(centralQ, new SuperSpecialNeedsVoter()));


        /** total sim time for all voter types**/
        averageTotalSimTimeReg = (int) Math.round(getAverageSimTime(boothLine, new RegularVoter()));
        averageTotalSimTimeLim = (int) Math.round(getAverageSimTime(boothLine, new LimitedTimeVoter()));
        averageTotalSimTimeSp = (int) Math.round(getAverageSimTime(boothLine, new SpecialNeedsVoter()));
        averageTotalSimTimeSupSp = (int) Math.round(getAverageSimTime(boothLine, new SuperSpecialNeedsVoter()));

        /** Average Check-in Table queue length **/
        average = 0.0;
        sum = 0;
        count = 0;
        for (CheckInTable table : tableLine.getTables()) {
            sum += table.getLeft();
            count++;
        }
        average = sum / count;
        if (averageCheckInQlength == 0)
            averageCheckInQlength = (int) Math.round(average);
        else {
            if (average != 0)
                averageCheckInQlength = (int) Math.round((averageCheckInQlength + average) / 2);
        }

        /** Average CentralQ queue length **/
        if (averageCentralQlength == 0)
            averageCentralQlength = centralQ.getLeft();
        else if (centralQ.getLeft() != 0)
            averageCentralQlength = Math.round((averageCentralQlength + centralQ.getLeft()) / 2);

        /** Total Deserters **/
        int newSum = 0;
        // from central Q
        newSum += centralQ.getNumDesertedVoters();
        // from check in tables
        for (CheckInTable table : tableLine.getTables()){
            newSum += table.getNumDesertedVoters();
        }
        totalDeserters = newSum;

        newSum = 0;
        for (Booth booth : boothLine.getBooths()) {
            newSum += booth.completed;
        }
        throughput = newSum;
    }

    /**
     * Provides average time of booth.
     *
     * @param booth Booth object.
     * @param proxy Voter object.
     * @return double value of average time.
     */
    private double getAverage(Booth booth, Voter proxy) {
        double average = 0.0;
        int sum = 0;
        int count = 0;

        for (int i : booth.waitTimes.get(proxy.getClass().getSimpleName())) {
            sum += i;
            count++;
        }
        if (count != 0)
            average = sum / count;

        return average;
    }

    /**
     * Provides average sim time of Q.
     *
     * @param boothLine VotingBoothLine object.
     * @param proxy Voter object.
     * @return double value of the average sim time.
     */
    private double getAverageSimTime(VotingBoothLine boothLine, Voter proxy) {
        double average = 0.0;
        int sum = 0;
        int count = 0;

        for (Booth booth : boothLine.getBooths()) {
            for (int i : booth.waitTimes.get(proxy.getClass().getSimpleName())) {
                sum += i;
                count++;
            }
        }
        if (count != 0)
            average = sum / count;

        return average;
    }

    /**
     * @return average wait time checking in regular voter table 1.
     */
    public int getAvgWaitTimeCheckInReg_table1() {
        return avgWaitTimeCheckInReg_table1;
    }

    /**
     * @return average wait time checking in limited voter table 1.
     */
    public int getAvgWaitTimeCheckInLim_table1() {
        return avgWaitTimeCheckInLim_table1;
    }

    /**
     * @return average wait time checking in special voter table 1.
     */
    public int getAvgWaitTimeCheckInSp_table1() {
        return avgWaitTimeCheckInSp_table1;
    }

    /**
     * @return average wait time check in super special voter table 1.
     */
    public int getAvgWaitTimeCheckInSupSp_table1() {
        return avgWaitTimeCheckInSupSp_table1;
    }

    /**
     * @return average wait time check in regular voter table 2.
     */
    public int getAvgWaitTimeCheckInReg_table2() {
        return avgWaitTimeCheckInReg_table2;
    }

    /**
     * @return average wait time check in limited voter table 2.
     */
    public int getAvgWaitTimeCheckInLim_table2() {
        return avgWaitTimeCheckInLim_table2;
    }

    /**
     * @return average wait time check in special voter table 2.
     */
    public int getAvgWaitTimeCheckInSp_table2() {
        return avgWaitTimeCheckInSp_table2;
    }

    /**
     * @return average wait time check in super special voter table 2.
     */
    public int getAvgWaitTimeCheckInSupSp_table2() {
        return avgWaitTimeCheckInSupSp_table2;
    }

    /**
     * @return average wait time in central q for a regular voter.
     */
    public int getAvgWaitTimeCentralQReg() {
        return avgWaitTimeCentralQReg;
    }

    /**
     * @return average wait time in central q for a limited voter.
     */
    public int getAvgWaitTimeCentralQLim() {
        return avgWaitTimeCentralQLim;
    }

    /**
     * @return average wait time in central q for a special voter.
     */
    public int getAvgWaitTimeCentralQSp() {
        return avgWaitTimeCentralQSp;
    }

    /**
     * @return average wait time in central q for a super special voter.
     */
    public int getAvgWaitTimeCentralQSupSp() {
        return avgWaitTimeCentralQSupSp;
    }

    /**
     * @return average check in Q length.
     */
    public int getAverageCheckInQlength() {
        return averageCheckInQlength;
    }

    /**
     * @return average total sim time for a regular voter.
     */
    public int getAverageTotalSimTimeReg() {
        return averageTotalSimTimeReg;
    }

    /**
     * @return average total sim time for a limited voter.
     */
    public int getAverageTotalSimTimeLim() {
        return averageTotalSimTimeLim;
    }

    /**
     * @return average total sim time for a special voter.
     */
    public int getAverageTotalSimTimeSp() {
        return averageTotalSimTimeSp;
    }

    /**
     * @return average total sim time for a super special voter.
     */
    public int getAverageTotalSimTimeSupSp() {
        return averageTotalSimTimeSupSp;
    }

    /**
     * @return total throughput of simulation.
     */
    public int getThroughput() {
        return throughput;
    }

    /**
     * @return total deserters in simulation.
     */
    public int getTotalDeserters() {
        return totalDeserters;
    }
}