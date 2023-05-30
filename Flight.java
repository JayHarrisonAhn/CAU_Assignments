import java.util.Random;

public class Flight {
    int flight_id;
    String airline_id;
    String from;
    String to;
    int fuel;

    Flight(int flight_id, String airline_id, String from, String to, int fuel) {
        this.flight_id = flight_id;
        this.airline_id = airline_id;
        this.from = from;
        this.to = to;
        this.fuel = fuel;
    }

    static Flight random() {
        Random random = new Random();
        int id = random.nextInt(10000);
        String airline_id = airlines[random.nextInt(airlines.length)];
        String from = airports[random.nextInt(airports.length)];
        String to = airports[random.nextInt(airports.length)];
        int fuel = random.nextInt(10000);
        return new Flight(id, airline_id, from, to, fuel);
    }

    static String[] airlines = {"KE", "OZ", "7Z", "LJ", "BX", "ZE", "TW", "RS"};
    static String[] airports = {"ICN", "GMP", "PUS", "CJU", "MWX", "YNY", "CJJ", "TAE", "USN", "RSU", "HIN", "KPO", "KUV", "WJU", "KWJ"};
}
