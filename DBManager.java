import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.*;

public class DBManager {
    static DBManager shared;
    String server = "localhost"; // MySQL 서버 주소
    String database = "cau_dbs_dev"; // MySQL DATABASE 이름
    String user_name = "root"; //  MySQL 서버 아이디
    String password = "mysql"; // MySQL 서버 비밀번호
    int blockSizeBit = 512;
    static void initialize() {
        shared = new DBManager();
    }
    private Connection con;
    private DBManager() {
        connectDB();
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
SELECT * FROM cau_dbs_dev.flight ORDER BY id LIMIT ?, ?;
""");
                stmt.setInt(1, i * blockSizeBit);
                stmt.setInt(2, blockSizeBit);
                ResultSet queryResult = stmt.executeQuery();
                if (!queryResult.isBeforeFirst() )
                    break;

                BitSet[] bits = new BitSet[possibleRecords.length];
                for(int j=0; j<bits.length; j++) bits[j] = new BitSet(blockSizeBit);

                for(int j=0; queryResult.next(); j++) {
                    String record = queryResult.getString(column);
                    for(int i_possibleRecords = 0; i_possibleRecords < possibleRecords.length; i_possibleRecords++) {
                        bits[i_possibleRecords].set(j, Objects.equals(record, possibleRecords[i_possibleRecords]));
                    }
                }

                for(int i_possibleRecords = 0; i_possibleRecords < possibleRecords.length; i_possibleRecords++) {
                    saveIndexFile(column, possibleRecords[i_possibleRecords], i, bits[i_possibleRecords]);
                }
            }
        } catch(Exception e) {
            System.err.println("con 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private String[] possibleRecords(String column) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(String.format("SELECT DISTINCT `%s` FROM cau_dbs_dev.flight;", column));

        ResultSet result = stmt.executeQuery();
        ArrayList<String> records = new ArrayList<>();
        while(result.next()) {
            records.add(result.getString(column));
        }
        String[] results = new String[records.size()];
        return records.toArray(results);
    }

    int numOfRecords() {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT MAX(id) FROM cau_dbs_dev.flight;");
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) + 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private int numOfIndexFiles(String column, String value) {
        File dir = new File(String.format("index/%s/%s", column, value));
        return dir.listFiles().length;
    }

    private void saveIndexFile(String column, String record, int index, BitSet data) {
        try {
            new File(String.format("index/%s/%s", column, record)).mkdirs();
            FileOutputStream indexFile = new FileOutputStream(String.format("index/%s/%s/%d.idx", column, record, index));
            indexFile.write(data.toByteArray());
            indexFile.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private byte[] readIndexFile(String column, String record, int index) throws Exception {
        FileInputStream indexFile = new FileInputStream(String.format("index/%s/%s/%d.idx", column, record, index));
        byte[] bytes = indexFile.readAllBytes();
        return bytes;
    }

    public ArrayList<Integer> searchIndex(Map<String, String> parameters) throws Exception {
        String firstKey = parameters.keySet().toArray(new String[parameters.size()])[0];
        String firstValue = parameters.get(firstKey);
        int numOfIndexFilesPerValue = numOfIndexFiles(firstKey, firstValue);

        ArrayList<Integer> searchedIndices = new ArrayList<>();
        for (int i = 0; i < numOfIndexFilesPerValue; i++) {
            BitSet[] bitmapIndices = new BitSet[parameters.size()];
            for(int j=0; j<bitmapIndices.length; j++) bitmapIndices[j] = new BitSet(blockSizeBit);

            for (int i_column = 0; i_column < parameters.size(); i_column++) {
                String parameterKey = parameters.keySet().toArray(new String[parameters.size()])[i_column];
                byte[] indexFileBytes = readIndexFile(parameterKey, parameters.get(parameterKey), i);
                bitmapIndices[i_column] = BitSet.valueOf(indexFileBytes);
            }

            BitSet bitmapIndicesAND = new BitSet(blockSizeBit){{
                set(0, blockSizeBit);
            }};
            for (int j = 0; j < bitmapIndices.length; j++) {
                bitmapIndicesAND.and(bitmapIndices[j]);
            }
            for (int j = bitmapIndicesAND.nextSetBit(0); j != -1; j = bitmapIndicesAND.nextSetBit(j + 1)) {
                searchedIndices.add(i * blockSizeBit + j);
            }


        }
        return searchedIndices;
    }

    public static void main(String[] args) {
        try {
            DBManager.initialize();
            ArrayList<Integer> indices = DBManager.shared.searchIndex(new HashMap<>() {{
                put("airline_id", "ZE");
                put("from", "CJJ");
            }});
            System.out.println(indices);
            System.out.println(indices.size());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
