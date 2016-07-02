package javauction.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.*;
import java.util.Date;
import javauction.model.OpStatus;
import javauction.model.Database;

/**
 * Created by gpelelis on 17/4/2016.
 * that function doesn't care by who gets called
 */
public class customer {

    public String username;
    public String email;
    public String name;
    public String lastname;
    public String password;
    public String vat;
    public String phonenumber;
    public String homeaddress;
    public String city;
    public String postcode;
    public String latitude;
    public String longitude;
    public String country;
    public Boolean isApproved;
    // those are specific for the appication
    public String type;
    public Boolean valid = false;

    public customer() {}

    public customer(String usrname, String pass) {
        username = usrname;
        password = pass;
    }

    public customer(String username, String name, String lastname) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
    }

    public customer(String username, String email, String name, String lastname, String password,
                    String vat, String phonenumber, String homeaddress, String city,
                    String latitude, String longitude, String country, Boolean isApproved){
    this.username = username;
    this.email = email;
    this.name = name;
    this.lastname = lastname;
    this.password = password;
    this.vat = vat;
    this.phonenumber = phonenumber;
    this.homeaddress = homeaddress;
    this.city = city;
    this.postcode = postcode;
    this.latitude = latitude;
    this.longitude = longitude;
    this.country = country;
    this.isApproved = isApproved;   
    }

    /* registers a new user to the app */
    public OpStatus register(String repeat_password, Connection db) {
        // get access to database
        Database db_access = new Database();

        try {
            // check if the user can register
            if (db_access.exist(this, db))
                return OpStatus.UsernameExist;
            if (!this.validCustomerPass(repeat_password))
                return OpStatus.DiffPass;

            // insert a unique user to the database
            if (db_access.registerUser(this, db))
                return OpStatus.Success;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return OpStatus.Error;
    }


    /* checks if a password is valid for the user */
    public boolean validCustomerPass(String pass) {
        return password.equals(pass);
    }

    public OpStatus loginAdmin(Connection db) {
        // get access to database
        Database db_access = new Database();

        try {
            // check the credentials of the user
            if (db_access.authAdmin(this, db))
                return OpStatus.Success;
            else
                return OpStatus.WrongCredentials;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return OpStatus.Error;
    }


    /* this function will try to validate a customer
     * todo: replace with an actual validation
     */
    public boolean login(Connection database) {
        try {
            // execute the sql select query
            Statement stmt = database.createStatement();
            String sql = "select Password, IsAdmin from user where Username = '" + email + "'";
            ResultSet result = stmt.executeQuery(sql);

            // get the password and if the user is an admin
            result.next();
            String db_pass = result.getString("Password");
            Boolean db_admin =  result.getBoolean("IsAdmin");

            // if this was a valid user, then assign some of the data to the customer object
            if (password.contentEquals(db_pass)) {
                valid = true;
                if (db_admin)
                    type = "admin";
                else
                    type = "simple";
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // will return true if the user is an admin.
    public boolean isAdmin() {
        if (type != null)
            return type.equals("admin") ? true : false;
        return false;
    }

    public boolean isValid() {
        return valid;
    }


    public String getUsername(){
        return username;
    }

    public String setUsername(String username) {
        this.username = username;
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String setEmail(String email) {
        this.email = email;
        return email;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String setLastname(String lastname) {
        this.lastname = lastname;
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        this.password = password;
        return password;
    }

    public String getVat() {
        return vat;
    }

    public String setVat(String vat) {
        this.vat = vat;
        return vat;
    }

    public String getphonenumber() {
        return phonenumber;
    }

    public String setphonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
        return phonenumber;
    }

    public String gethomeaddress() {
        return homeaddress;
    }

    public String sethomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
        return homeaddress;
    }

    public String getCity() {
        return city;
    }

    public String setCity(String city) {
        this.city = city;
        return city;
    }

    public String getPostcode() {
        return postcode;
    }

    public String setPostcode(String postcode) {
        this.postcode = postcode;
        return postcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public String setLatitude(String latitude) {
        this.latitude = latitude;
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String setLongitude(String longitude) {
        this.longitude = longitude;
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String setCountry(String country) {
        this.country = country;
        return country;
    }

    public Boolean setisApproved(Boolean aprove){
        this.isApproved = aprove;
        return isApproved;
    }

    public Boolean isApproved(){
        return isApproved;
    }




}
