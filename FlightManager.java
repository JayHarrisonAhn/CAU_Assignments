import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class FlightManager {
    Scanner sc = new Scanner(System.in);
    public void initialize() {
        try {
            DBManager.shared.createTable();
            System.out.println("Success");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        waitForAnyKey();
    }

    public void insertRandomFlights() {
        System.out.println("How many flights to add?");
        int bound = getInputInt();
        for(int i=0; i<bound; i++) {
            while(true) {
                try {
                    DBManager.shared.insertData(i+1, Flight.random());
                    break;
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        waitForAnyKey();
    }

    public void findFlights(String airline) {

    }

    private int getInputInt() {
        int number = sc.nextInt();
        sc.nextLine();
        return number;
    }
    private void waitForAnyKey() {
        System.out.println("아무 키나 눌러서 나가기");
        sc.nextLine();
    }

    public static void main(String[] args) {
        FlightManager flightManager = new FlightManager();
        flightManager.initialize();
    }
}
