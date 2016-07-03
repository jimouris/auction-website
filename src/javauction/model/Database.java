package javauction.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
* Created by gpelelis on 2/6/2016.
*/
public class Database {

	public Database() {}

	/* check if a user exist in the database */
	public Boolean exist(customer user, Connection db) throws SQLException {
		Boolean status = null;

		// execute the sql select query
		String sql = "SELECT COUNT(*) as occurs FROM user where username = ?";
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setString(1, user.username);
		ResultSet result = pstmt.executeQuery();

		// get the number of duplicates
		result.next();
		int occurs = result.getInt("occurs");
		result.close();
		pstmt.close();

		// if occurs is 0 this is a unique username
		if (occurs != 0)
			status = true; // username exist = true
		else
			status = false; // username exist = false

		return status;
	}


	/* tries to authenticate an admin */
//	public Boolean authAdmin(customer user, Connection db) throws SQLException {
//		Boolean status = null;
//
//		// execute the sql select query
//		String sql = "SELECT COUNT(*) as occurs FROM user where username = ? and password = ? and isAdmin = 1";
//		PreparedStatement pstmt = db.prepareStatement(sql);
//		pstmt.setString(1, user.username);
//		pstmt.setString(2, user.password);
//		ResultSet result = pstmt.executeQuery();
//
//		// get the number of duplicates
//		result.next();
//		int occurs = result.getInt("occurs");
//		result.close();
//		pstmt.close();
//
//
//		// there should be only one admin with those credentials
//		if (occurs == 1)
//			status = true; // authAdmin = true
//		else
//			status = false; // authAdmin = false
//
//		return status;
//	}


	/* tries to authenticate an admin */
	public OpStatus getEverybody(ArrayList users, Connection db) throws SQLException {
		OpStatus status = OpStatus.Error;

		// execute the sql select query
		String sql = "SELECT * FROM user where isAdmin = 0 order by isApproved";
		PreparedStatement pstmt = db.prepareStatement(sql);
		ResultSet result = pstmt.executeQuery();

		// get the number of duplicates
		while (result.next()) {
			users.add(new customer(
			              result.getString("username"),
			              result.getString("email"),
			              result.getString("Firstname"),
			              result.getString("lastname"),
			              result.getString("password"),
			              result.getString("vat"),
			              result.getString("phonenumber"),
			              result.getString("homeaddress"),
			              result.getString("city"),
			              result.getString("latitude"),
			              result.getString("longitude"),
			              result.getString("country"),
			              result.getBoolean("isApproved")));
			status = OpStatus.Success; // authAdmin = true
		}
		result.close();
		pstmt.close();

		return status;
	}


//	public Boolean registerUser(customer user, Connection db) throws SQLException {
//		Boolean status = null;
//
//		// prepare the query
//		String sql 	= "INSERT INTO user"
//		              + "(Username, Password, Firstname, Lastname, Email, PhoneNumber, Vat, HomeAddress, Latitude, " +
//						"Longitude, City, Country, SignUpDate,isAdmin, isApproved) VALUES"
//		              + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//		PreparedStatement pstmt = db.prepareStatement(sql);
//
//		// prepare the values to be inserted
//		Date currentDate = new Date(System.currentTimeMillis());
//		java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
//		pstmt.setString(1, user.username); // use as username
//		pstmt.setString(2, user.password); // Password
//		pstmt.setString(3, user.name); // the firstname
//		pstmt.setString(4, user.lastname); // lastname
//		pstmt.setString(5, user.email); // mail
//		pstmt.setString(6, user.phonenumber); // Phone Number
//		pstmt.setString(7, user.vat); // AFM
//		pstmt.setString(8, user.homeaddress); // HomeAddress
//		pstmt.setString(9, user.latitude); // Latitude
//		pstmt.setString(10, user.longitude); // Longitude
//		pstmt.setString(11, user.city); // City
//		pstmt.setString(12, user.country); // Country
//		pstmt.setDate(13, sqlDate); // SignUpDate
//		pstmt.setInt(14, 0); // Country
//		pstmt.setInt(15, 0); // Country
//
//		// try to insert the values to db
//		int affected = -1;
//		affected = pstmt.executeUpdate();
//		pstmt.close();
//
//		if (affected > 0)
//			status = true;
//		else
//			status = false;
//		return status;
//	}

}
