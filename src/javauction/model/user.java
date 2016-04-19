package javauction.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by gpelelis on 17/4/2016.
 * that function doesn't care by who gets called
 */
public class user {
    public String getUsername(String name){
        String username = "username is:" + name;
        return(username);
    }

    public boolean addUser(){
        return(true);
    }

    public String getPassword(String d) {
        return "password is:" + d;
    }

    /* get 1 user name*/
    public String getUser(Connection db) throws SQLException {
        String sql = "SELECT * FROM `auction-website`.user;";
        String data = "";

        System.out.println(data);
        Statement stmt = db.createStatement();
        System.out.println(data);
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            //Retrieve by column name
            data = "username: " + rs.getString("Username");
        }

        stmt.close();
        return data;
    }
}
