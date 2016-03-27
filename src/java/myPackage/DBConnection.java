package myPackage;


import java.sql.Connection;
import java.sql.DriverManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ermes
 */
public class DBConnection {
     public static Connection getDBConnection() throws Exception{
       // setting connection & statement vars
            Connection conn;
            
            // JDBC driver name and database URL
            final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
            final String DB_URL = "jdbc:mysql://eu-cdbr-azure-west-c.cloudapp.net/TedDatabase?zeroDateTimeBehavior=convertToNull";

            // Database credentials
            final String USER = "b492e801a76a72";
            final String PASS = "65319820f913394";
           
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return conn;
    }
}
