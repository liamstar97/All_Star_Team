import java.sql.*;

public class allstar_team {
  public static void main(String args[]) {
    Ui allstarTeam = null;
    try {
      // Load the JDBC driver
      Class.forName("com.mysql.cj.jdbc.Driver");
      // Connect to the database
      allstarTeam = new Ui();
    } catch (ClassNotFoundException e) {
      System.out.println("Could not load the driver");
    } catch (SQLException ex) {
      System.out.println(ex);
//        } catch (IOException e) {
//            e.printStackTrace();
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