package javauction;

/**
 * Created by jimouris on 4/17/16.
 */
public class User {

    long _userID;
    String _username;
    String _password;
    String _firstName;
    String _lastName;
    String _email;
    String _phoneNumber;
    String _afm;
    String _address;
    String _city;
    String _country;


    public User(String username, String password, String firstName, String lastName, String email,
                String phoneNumber, String afm, String address, String city, String country)
    {
        this._username = username;
        this._password = password;
        this._firstName = firstName;
        this._lastName = lastName;
        this._email = email;
        this._phoneNumber = phoneNumber;
        this._afm = afm;
        this._address = address;
        this._city = city;
        this._country = country;
    }


}
