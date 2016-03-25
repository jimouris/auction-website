package myPackage;




import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hostAdmin
 */
public class executeCreateXML {
    
    /**
     *
     * @return
     */
    public static String GetAuctionsAsXML() throws Exception
    {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.newDocument();
    Element results = doc.createElement("Items");
    doc.appendChild(results);

    Connection con = DBConnection.getDBConnection();
    
    
    ResultSet rs = con.createStatement().executeQuery("select * from auction");

    ResultSetMetaData rsmd = rs.getMetaData();
    int colCount = rsmd.getColumnCount();

    try
    {
            while (rs.next()) {
         Element row = doc.createElement("Item");
         results.appendChild(row);
         for (int i = 1; i <= colCount; i++) {
           String columnName = rsmd.getColumnName(i);
           Object value = rs.getObject(i);
           Element node = doc.createElement(columnName);
           if(value ==  null)
           {
               value = "";
           }
           node.appendChild(doc.createTextNode(value.toString()));
           row.appendChild(node);
         }
       }
    }
    catch(SQLException | DOMException ex)
    {
        int i = 0;
    }
   
    DOMSource domSource = new DOMSource(doc);
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
    StringWriter sw = new StringWriter();
    StreamResult sr = new StreamResult(sw);
    transformer.transform(domSource, sr);

   
    con.close();
    rs.close();
    
    return sw.toString();
    
    }
    
}
