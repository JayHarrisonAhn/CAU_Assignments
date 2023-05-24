import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;

public class DBManager {
    static DBManager shared;
    String server = "localhost"; // MySQL 서버 주소
    String database = "cau_dbs_dev"; // MySQL DATABASE 이름
    String user_name = "root"; //  MySQL 서버 아이디
    String password = "mysql"; // MySQL 서버 비밀번호
    static void initialize() {
        shared = new DBManager();
    }
    private Connection con;
    private DBManager() {
        connectDB();
    }

    public void insertData(Flight flight) throws Exception {
        PreparedStatement preparedStatement = con.prepareStatement("""
INSERT INTO `flight` (`flight_id`, `airline_id`, `departure`, `from`, `to`, `fuel`) VALUES (?, ?, ?, ?, ?, ?);
""");
        preparedStatement.setInt(1, flight.flight_id);
        preparedStatement.setString(2, flight.airline_id);
        preparedStatement.setString(3, flight.from);
        preparedStatement.setString(4, flight.to);
        preparedStatement.setInt(5, flight.fuel);
        preparedStatement.execute();
    }

    public void insertData(int id, Flight flight) throws Exception {
        PreparedStatement preparedStatement = con.prepareStatement("""
INSERT INTO `flight` (`id`, `flight_id`, `airline_id`, `from`, `to`, `fuel`) VALUES (?, ?, ?, ?, ?, ?);
""");
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, flight.flight_id);
        preparedStatement.setString(3, flight.airline_id);
        preparedStatement.setString(4, flight.from);
        preparedStatement.setString(5, flight.to);
        preparedStatement.setInt(6, flight.fuel);
        preparedStatement.execute();
    }

    public void readData() throws Exception {

    }

    public void createTable() throws Exception {
        PreparedStatement preparedStatement = con.prepareStatement("""
                CREATE TABLE `flight` (
                  `id` int NOT NULL,
                  `flight_id` int NOT NULL,
                  `airline_id` char(2) NOT NULL,
                  `from` char(3) NOT NULL,
                  `to` char(3) NOT NULL,
                  `fuel` int NOT NULL,
                  PRIMARY KEY (`flight_id`,`airline_id`),
                  UNIQUE KEY `id_UNIQUE` (`id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
""");
        preparedStatement.execute();
    }
    private void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(" !! <JDBC 오류> Driver load 오류: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true", user_name, password);
            System.out.println("정상적으로 연결되었습니다.");
        } catch(SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createIndex(String column) {
        try {
            String[] possibleRecords = possibleRecords(column);

            for(int i=0; true; i++) {
                PreparedStatement stmt = con.prepareStatement("""
SELECT * FROM cau_dbs_dev.flight ORDER BY id LIMIT ?, 128;
""");
                stmt.setInt(1, i * 128);
                ResultSet queryResult = stmt.executeQuery();
                if (!queryResult.isBeforeFirst() )
                    break;

                boolean[][] bitIndices = new boolean[possibleRecords.length][128];
                for(boolean[] bitIndex : bitIndices) {
                    Arrays.fill(bitIndex, false);
                }
                for(int j=0; queryResult.next(); j++) {
                    String record = queryResult.getString(column);
                    for(int i_possibleRecords = 0; i_possibleRecords < possibleRecords.length; i_possibleRecords++) {
                        bitIndices[i_possibleRecords][j] = Objects.equals(record, possibleRecords[i_possibleRecords]);
                    }
                }

                for(int i_possibleRecords = 0; i_possibleRecords < possibleRecords.length; i_possibleRecords++) {
                    saveIndexFile(column, possibleRecords[i_possibleRecords], i, bitIndices[i_possibleRecords]);
                }
            }
        } catch(SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private String[] possibleRecords(String column) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(String.format("SELECT DISTINCT `%s` FROM cau_dbs_dev.flight;", column));

        ResultSet result = stmt.executeQuery();
        ArrayList<String> records = new ArrayList<String>();
        while(result.next()) {
            records.add(result.getString(column));
        }
        String[] results = new String[records.size()];
        return records.toArray(results);
    }

    private void saveIndexFile(String column, String record, int index, boolean[] data) {
        try {
            new File(String.format("index/%s/%s", column, record)).mkdirs();
            FileOutputStream index1 = new FileOutputStream(String.format("index/%s/%s/%d.idx", column, record, index));
            boolean[] testbools = new boolean[]{false, false, false, false, false, false, false, true};
            byte[] testbytes = toByteArray(testbools);
            boolean[] testresults = toBooleanArray(testbytes);

            byte[] bytes = toByteArray(data);
            index1.write(bytes);
            index1.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    boolean[] toBooleanArray(byte[] bytes) {
        BitSet bits = BitSet.valueOf(bytes);
        boolean[] bools = new boolean[bytes.length * 8];
        for (int i = bits.nextSetBit(0); i != -1; i = bits.nextSetBit(i+1)) {
            bools[i] = true;
        }
        return bools;
    }

    byte[] toByteArray(boolean[] bools) {
        BitSet bits = new BitSet(bools.length);
        for (int i = 0; i < bools.length; i++) {
            if (bools[i]) {
                bits.set(i);
            }
        }

        byte[] bytes = bits.toByteArray();
        if (bytes.length * 8 >= bools.length) {
            return bytes;
        } else {
            return Arrays.copyOf(bytes, bools.length / 8 + (bools.length % 8 == 0 ? 0 : 1));
        }
    }

    public static void main(String[] args) {
        try {
            DBManager.initialize();
            DBManager.shared.createIndex("to");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
