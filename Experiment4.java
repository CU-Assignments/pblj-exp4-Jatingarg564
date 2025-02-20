import java.util.*;

class RecordManager {
    private List<String> records = Collections.synchronizedList(new ArrayList<>());

    public void addRecord(String record) {
        records.add(record);
        System.out.println("Record added: " + record);
    }

    public List<String> getRecords() {
        return new ArrayList<>(records);
    }

    public boolean searchRecord(String record) {
        return records.contains(record);
    }

    public void displayRecords() {
        synchronized (records) {
            System.out.println("Current Records:");
            for (String record : records) {
                System.out.println(record);
            }
        }
    }
}

class DataWorker extends Thread {
    private RecordManager manager;
    private String record;

    public DataWorker(RecordManager manager, String record) {
        this.manager = manager;
        this.record = record;
    }

    @Override
    public void run() {
        manager.addRecord(record);
        manager.displayRecords();
    }
}

public class DataManagementApp {
    public static void main(String[] args) {
        RecordManager manager = new RecordManager();
        Thread t1 = new DataWorker(manager, "Record 1");
        Thread t2 = new DataWorker(manager, "Record 2");
        Thread t3 = new DataWorker(manager, "Record 3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Search for 'Record 2': " + manager.searchRecord("Record 2"));
    }
}
