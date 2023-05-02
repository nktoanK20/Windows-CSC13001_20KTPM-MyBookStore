package DAO;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    public static Connection createConnection(){
        Connection connection = null;
        try {
            Driver myDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(myDriver);

//            String DB_URL = "jdbc:mysql://localhost:3306/book-store-management-db";
//            String USER = "root";
//            String PASS = "280502";

            String DB_URL = "jdbc:mysql://localhost/book-store-management-db";
            String USER = "root";
            String PASS = "root";

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
}