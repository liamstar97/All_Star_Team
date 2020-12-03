import java.sql.*;

public class Query {

  private Connection conn;

  public Query(Connection conn) {
    this.conn = conn;
  }

  public void playerRank() throws SQLException {
      Statement getPlayerRank = conn.createStatement();

      String query = "SELECT Name, PLAYER_Rank FROM PLAYERS JOIN ALLSTAR_NOMINEES PLAYERS_SSN ON SSN = PLAYERS_SSN";

      ResultSet result = getPlayerRank.executeQuery(query);

      while(result.next()){
          String name = result.getString(1);
          int rank = result.getInt(2);

          System.out.println(name + " " + rank);
      }
  }

  public void getTeamWins() throws SQLException {
      Statement getTeamWins = conn.createStatement();

      String query = "SELECT Team_name, Win FROM CHAMPIONSHIP_TEAM";

      ResultSet result = getTeamWins.executeQuery(query);

      while(result.next()){
          String teamName = result.getString(1);
          int wins = result.getInt(2);

          System.out.println(teamName + " " + wins);
      }
  }

  public void getParticipation() throws SQLException {

      int count = 0;
      Statement getParticipation = conn.createStatement();
      String query = "SELECT Team_name FROM CHAMPIONSHIP_TEAM";
      ResultSet results = getParticipation.executeQuery(query);


      while (results.next()) {
          count++;
      }
      System.out.println(count);
  }
}
