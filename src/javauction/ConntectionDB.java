package javauction;

import java.sql.*;

/**
 * Created by jimouris on 4/17/16.
 */

public class ConntectionDB {

    private final String driverName = "com.mysql.jdbc.Driver";
    private final String dbName = "jdbc:mysql://localhost:3306/Auction-Website";
    private final String username = "root";
    private final String password = "root";

    public Connection initializeConnection() throws Exception {
        Class.forName(driverName);
        Connection db = DriverManager.getConnection(dbName, username, password);
        return db;
    }

    public boolean insertUser(User user) {
        try {
            Connection db = initializeConnection();
            String sql = "INSERT INTO User(Username, Password, FirstName, LastName, e-mail, PhoneNumber, AFM, HomeAddress, City, Country) " + "VALUES(?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement stmt = db.prepareStatement(sql); // Use of prepared statements to avoid sql injection
//            stmt.setString(1, user._username);
//            stmt.setString(2, user._password);
//            stmt.setString(3, user._firstName);
//            stmt.setString(4, user._lastName);
//            stmt.setString(5, user._email);
//            stmt.setString(6, user._phoneNumber);
//            stmt.setString(7, user._afm);
//            stmt.setString(8, user._address);
//            stmt.setString(9, user._city);
//            stmt.setString(10, user._country);
            if(stmt.executeUpdate() == 0) {
                stmt.close();
                db.close();
                return false;
            }
            stmt.close();
            db.close();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public void printUser(User u) {
        System.out.println(u._firstName);
    }

}
