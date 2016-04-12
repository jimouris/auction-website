<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>

<%

// setting connection & statement vars
Connection conn = null;
Statement stmt = null;

// JDBC driver name and database URL
final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
final String DB_URL = "jdbc:mysql://eu-cdbr-azure-west-c.cloudapp.net/TedDatabase";

// Database credentials
final String USER = "b492e801a76a72";
final String PASS = "65319820f913394";

// Register JDBC driver
Class.forName(JDBC_DRIVER);


%>