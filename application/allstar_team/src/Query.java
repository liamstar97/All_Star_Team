import java.sql.*;

public class Query {

  private Connection conn;

  public Query(Connection conn) {
    this.conn = conn;
  }

  public void playerRank(Connection conn) throws SQLException {
      Statement getPlayerRank = conn.createStatement();

      String query = "SELECT Name, PLAYER_Rank FROM PLAYERS JOIN ALLSTAR_NOMINEES PLAYERS_SSN ON SSN = PLAYERS_SSN";

      ResultSet result = getPlayerRank.executeQuery(query);

      while(result.next()){
          String name = result.getString(1);
          int rank = result.getInt(2);

          System.out.println(name + " " + rank);
      }

  }
}
