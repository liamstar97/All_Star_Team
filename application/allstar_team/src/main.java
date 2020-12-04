/*
 * Name: main.java
 * Authors: Kyle White, Mathew Tkachuk, Liam Thompson
 * Date: 12/3/2020
 * --------------------------
 * Description: This class contains the main method for our application which creates a new UI object and catches
 *              exceptions thrown by it.
 */
import java.sql.*;

public class main {
  public static void main(String args[]) {
    Ui allstarTeam = null;
    try {
      // load the JDBC driver
      Class.forName("com.mysql.cj.jdbc.Driver");
      // connect to the database
      allstarTeam = new Ui();
      // catch possibly thrown exceptions
    } catch (ClassNotFoundException e) {
      System.out.println("Could not load the driver");
    } catch (SQLException ex) {
      System.out.println(ex);
    } finally {
      if (allstarTeam.getConnection() != null) {
        try {
          allstarTeam.getConnection().close();
        } catch (SQLException e) {
          System.out.println(e);
        }
      } else {
        System.out.println("Error NullPointerException, allstarTeam is Null");
        System.exit(1);
      }
    }
  }
}