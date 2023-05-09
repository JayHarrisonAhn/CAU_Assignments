import java.util.Scanner;

public class Main {
    static FlightManager flightManager = new FlightManager();

    public static void main(String[] args) {
        DBManager.initialize();
        while(true) {
            int command = readCommand();
            switch(command) {
                case 0:
                    flightManager.initialize();
                case 1:
                    flightManager.insertRandomFlights();
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
                        2. Find the number of flights from an airport.
                        3. Find the number of flights of an airline.
                        """);
        while(true) {
            Scanner sc = new Scanner(System.in);
            try {
                command = sc.nextInt();
                if(command >= 0 && command < 4) {
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