import java.util.ArrayList;

/**
 * @author davidcalkins
 * @version 10.29.2016
 */
public class CheckInTableLine implements ClockListener {
    private ArrayList<CheckInTable> tables;

    public ArrayList<CheckInTable> getTables() {
        return tables;
    }

    public void setTables(ArrayList<CheckInTable> tables) {
        this.tables = tables;
    }

    public CheckInTableLine(int n, CentralQ centerQ) {
        tables = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            tables.add(new CheckInTable(centerQ));
            tables.get(i).setTableNumber(i);
        }
    }

    public void event(int tick) {
        for (int i = 0; i < tables.size(); i++) {
            System.out.println("Now running table " + i + " tick " + tick);
            tables.get(i).event(tick);
        }
    }
}
