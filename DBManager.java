import java.sql.*;

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
    Connection getCon() {
        return con;
    }
    private Statement stmt;
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
            stmt = con.createStatement();
            System.out.println("정상적으로 연결되었습니다.");
        } catch(SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createIndex() {

    }

    public static void main(String[] args) {
        try {
            DBManager.initialize();
            DBManager.shared.createTable();
            DBManager.shared.insertData(new Flight(126, "OZ", "ICN", "CJU", 314));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
