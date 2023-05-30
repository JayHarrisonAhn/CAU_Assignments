import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class FlightManager {
    Scanner sc = new Scanner(System.in);
    public void initializeDatabase() {
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
        int numOfRecords = DBManager.shared.numOfRecords();
        for(int i=numOfRecords; i<numOfRecords + bound; i++) {
            while(true) {
                try {
                    DBManager.shared.insertData(i, Flight.random());
                    break;
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        waitForAnyKey();
    }

    public void searchAndCountFlights() {
        System.out.print("Airline : ");
        String airline = getInputString();
        System.out.print("Airport from : ");
        String from = getInputString();
        System.out.print("Airport to : ");
        String to = getInputString();
        if(Objects.equals(airline, "")&&Objects.equals(from, "")&&Objects.equals(to, "")) {
            System.out.println("Error : You should set at least 1 search key.");
            waitForAnyKey();
            return;
        }
        int numOfFlights = findFlightIndices(airline, from, to).size();
        System.out.println(String.format("There are %d flights found", numOfFlights));
        waitForAnyKey();
    }

    public void searchAndPrintFlights() {
        System.out.print("Airline : ");
        String airline = getInputString();
        System.out.print("Airport from : ");
        String from = getInputString();
        System.out.print("Airport to : ");
        String to = getInputString();
        if(Objects.equals(airline, "")&&Objects.equals(from, "")&&Objects.equals(to, "")) {
            System.out.println("Error : You should set at least 1 search key.");
            waitForAnyKey();
            return;
        }
        ArrayList<Integer> searchedIndices = findFlightIndices(airline, from, to);
        for(int i: searchedIndices) {
            try {
                Flight flight = DBManager.shared.selectData(i);
                System.out.println(String.format(
                        "%s%d from %s to %s (fuel:%d)",
                        flight.airline_id,
                        flight.flight_id,
                        flight.from,
                        flight.to,
                        flight.fuel
                ));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        waitForAnyKey();
    }

    private ArrayList<Integer> findFlightIndices(String airline, String from, String to) {
        try {
            HashMap<String, String> searchParameters = new HashMap<>(){{
                put("airline_id", airline);
                put("from", from);
                put("to", to);
                while (values().remove(""));
                while (values().remove(null));
            }};
            ArrayList<Integer> searchedIndices = DBManager.shared.searchIndex(searchParameters);
            return searchedIndices;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private int getInputInt() {
        int number = sc.nextInt();
        sc.nextLine();
        return number;
    }

    private String getInputString() {
        String string = sc.nextLine();
        return string;
    }
    private void waitForAnyKey() {
        System.out.println("아무 키나 눌러서 나가기");
        sc.nextLine();
    }

    public static void main(String[] args) {
        DBManager.initialize();
        FlightManager flightManager = new FlightManager();
        flightManager.searchAndCountFlights();
    }
}
