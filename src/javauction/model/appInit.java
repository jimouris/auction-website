package javauction.model; /**
 * Created by gpelelis on 19/4/2016.
 */

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener()
public class appInit implements ServletContextListener {

    // Public constructor is required by servlet spec
    public appInit() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        ServletContext sc = sce.getServletContext(); // context contains info from the web.xml

        /* get the db info from context */
        String driverName = sc.getInitParameter("db driver");
        String dbName = sc.getInitParameter("db url");
        String username = sc.getInitParameter("db user");
        String password = sc.getInitParameter("db pass");

        /* create the object to be used across the project */
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /* try to establish a db connection. */
//        Connection db = null;
//        try {
//            db = DriverManager.getConnection(dbName, username, password);
//        } catch (SQLException e) {
//            System.out.println("couldn't connect :" + dbName + username + password);
//            e.printStackTrace();
//        }

        /* make the object accessible from every jsp and seervlet */
//        sc.setAttribute("db", db);
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        // just to be sure that we clean up our db resources
        ServletContext sc = sce.getServletContext();
        Connection db = (Connection) sc.getAttribute("db"); // cast because getAttribute returns object
        try {
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
