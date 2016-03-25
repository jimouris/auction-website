<%-- 
    Document   : auctionsxml
    Created on : Sep 8, 2015, 11:59:23 AM
    Author     : hostAdmin
--%>

<%@page import="myPackage.executeCreateXML"%>
<%@page import="java.sql.ResultSet"%>
<%@include file="connect_db.jsp"%>


<%


   String result = executeCreateXML.GetAuctionsAsXML();
   out.println(result); 

   
%>

  



<%@ page contentType="text/xml" %>
