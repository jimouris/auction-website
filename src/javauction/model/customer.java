package javauction.model;

import java.sql.*;

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
    public String address;
    public String city;
    public String postcode;
    public String latitude;
    public String longitude;

    /* insert a new customer to the database
    * todo: actually insert a new user
    **/
    public boolean registerCustomer(Connection db) {

        String sql = "INSERT INTO user"
            + "(Username, Password, Firstname, lastname, mail, AFM, PhoneNumber, HomeAddress, City, Country, SignUpDate) VALUES"
            + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        int affected = 0;

        // create the object which will "send the data"
        try {
            pstmt = db.prepareStatement(sql);
            Date date = new Date(0);


            // bind the values into the parameter
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.setString(4, lastname);
            pstmt.setString(5, vat);
            pstmt.setString(6, email);
            pstmt.setString(7, latitude);
            pstmt.setString(8, vat);
            pstmt.setString(9, address);
            pstmt.setString(10, city);
            pstmt.setDate(11, Date.valueOf(date.toString()));


            // try to insert the values to db
            affected = pstmt.executeUpdate();
            System.out.println(pstmt.toString() + affected);

            if(affected == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return true;
    }

    /* gets two passwords and check if this would be a valid password
     * for a customer of the javauction
     * todo: replace with an actual check of the passwords. something like pass1 == pass2
     */
    public boolean validCustomerPass(String pass1, String pass2){
        return true;
    }
}
