package javauction.model;

import java.sql.*;
import java.util.Date;

/**
 * Created by gpelelis on 17/4/2016.
 * that function doesn't care by who gets called
 */
public class customer {

    public String email;
    public String name;
    public String lastname;
    public String password;
    public String vat;
    public String phone;
    public String address;
    public String city;
    public String postcode;
    public String latitude;
    public String longitude;
    public String country;

    /* insert a new customer to the database
    *  returns true if the addition was succesfull
    *  returns false if the customer couldn't be added
    **/
    public boolean registerCustomer(Connection db) {

        String sql = "INSERT INTO user"
                + "(Username, Password, Firstname, lastname, mail, AFM, HomeAddress, City, Country, SignUpDate, PhoneNumber, Latitude, Longitude) VALUES"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        int affected = -1;

        // create the object which will "send the data"
        try {
            pstmt = db.prepareStatement(sql);
            Date currentDate = new Date(System.currentTimeMillis());
            java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());


            // bind the values into the parameter
            pstmt.setString(1, email); // use as username
            pstmt.setString(2, password); // Password
            pstmt.setString(3, name); // the firstname
            pstmt.setString(4, lastname); // lastname
            pstmt.setString(5, email); // mail
            pstmt.setString(6, vat); // AFM
            pstmt.setString(7, address); // HomeAddress
            pstmt.setString(8, city); // City
            pstmt.setString(9, country); // Country
            pstmt.setDate(10, sqlDate); // SignUpDate
            pstmt.setString(11, phone); // Phone Number
            pstmt.setString(12, latitude); // Latitude
            pstmt.setString(13, longitude); // Longitude

            // try to insert the values to db
            affected = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println(pstmt.toString() + affected);
            try {
                pstmt.close();
                if (affected > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /* gets two passwords and check if this would be a valid password
     * for a customer of the javauction
     * todo: replace with an actual check of the passwords. something like pass1 == pass2
     */
    public boolean validCustomerPass(String pass1, String pass2) {
        return true;
    }

    /* this function will try to validate a customer
     * todo: replace with an actual validation
     */
    public boolean login() {
        if (email.contentEquals("root")) {
            return true;
        } else {
            return false;
        }
    }
}
