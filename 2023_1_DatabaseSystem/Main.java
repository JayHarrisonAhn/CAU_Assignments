import java.util.Scanner;

public class Main {
    static FlightManager flightManager = new FlightManager();

    public static void main(String[] args) {
        DBManager.initialize();
        while(true) {
            int command = readCommand();
            switch(command) {
                case 0:
                    flightManager.initializeDatabase();
                    break;
                case 1:
                    flightManager.insertRandomFlights();
                    break;
                case 2:
                    DBManager.shared.createIndex("airline_id");
                    DBManager.shared.createIndex("from");
                    DBManager.shared.createIndex("to");
                    break;
                case 3:
                    flightManager.searchAndCountFlights();
                    break;
                case 4:
                    flightManager.searchAndPrintFlights();
                    break;
            }
        }
    }

    private static int readCommand() {
        int command;
        clearConsole();
        System.out.println("""
                        Welcome to ROK Flight Manager.

                        0. Initial database scheme setup.
                        1. Insert random flights to DB.
                        2. Create indices for queries.
                        3. Count flights with airline, airport_from and airport_to.
                        4. Search flights with airline, airport_from and airport_to.
                        """);
        while(true) {
            Scanner sc = new Scanner(System.in);
            try {
                command = sc.nextInt();
                if(command >= 0 && command < 5) {
                    break;
                } else {
                    System.out.println("unreachable command");
                }
            } catch(Exception e) {
                System.out.println("unreachable command");
            }
        }
        clearConsole();
        return command;
    }

    private static void clearConsole() {
        for(int clear = 0; clear < 100; clear++) {
            System.out.println("\b") ;
        }
    }
}