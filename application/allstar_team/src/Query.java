import java.sql.*;
import java.io.*;
import java.sql.Date;
import java.util.InputMismatchException;

public class Query {

  private Connection conn;

  public Query(Connection conn) {
    this.conn = conn;
  }


  public void insertPlayer() throws SQLException {
    try {
      String query = "INSERT INTO PLAYERS VALUES (?,?,?,?,?,?,?,?,?,?)";
      PreparedStatement p = conn.prepareStatement(query);
      p.clearParameters();

      int SSN = Integer.parseInt(readEntry("Enter the player's SSN: \n"));
      p.setInt(1, SSN);

      int teamID = Integer.parseInt(readEntry("Enter the player's Team ID: \n"));
      p.setInt(2, teamID);

      int timeAllstar = Integer.parseInt(readEntry(
          "Enter the number of times this player has been on an Allstar Team: \n"));
      p.setInt(10, timeAllstar);

      int yearsTeam = Integer.parseInt(readEntry("Enter the player's number of years on their team: \n"));
      p.setInt(8, yearsTeam);

      String name = readEntry("Enter the player's Name: \n");
      p.setString(3, name);

      String address = readEntry("Enter the player's Address: \n");
      p.setString(4, address);

      Date birthDate = Date.valueOf(readEntry("Enter the player's birth date as YYYY-MM-DD: \n"));
      p.setDate(5, birthDate);

      String position = readEntry("Enter the player's Position: \n");
      p.setString(6, position);

      String univ = readEntry("Enter the player's University: \n");
      p.setString(7, univ);

      String collegeClass = readEntry("Enter the player's Class: \n");
      p.setString(9, collegeClass);

      p.executeUpdate();
    } catch (InputMismatchException e) {
      System.out.println("Invalid input");
      insertPlayer();
    } catch (SQLException s) {
      System.out.println(s.getMessage());
      insertPlayer();
    }
  }

  public void insertCoach() throws SQLException {
    try {
      String query = "" +
          "INSERT INTO COACH " +
          "VALUES (?,?,?,?,?,?,?,?,?)";
      PreparedStatement p = conn.prepareStatement(query);
      p.clearParameters();

      int SSN = Integer.parseInt(readEntry("Enter the coach's SSN: \n"));
      p.setInt(1, SSN);

      int years = Integer.parseInt(readEntry("Enter the total number of years spent as a coach: \n"));
      p.setInt(7, years);

      int yearsTeam = Integer.parseInt(readEntry("Enter the coach's number of years on their team: \n"));
      p.setInt(6, yearsTeam);

      String name = readEntry("Enter the coach's Name: \n");
      p.setString(2, name);

      String address = readEntry("Enter the coach's Address: \n");
      p.setString(3, address);

      Date birthDate = Date.valueOf(readEntry("Enter the coach's birth date as YYYY-MM-DD: \n"));
      p.setDate(4, birthDate);

      String univ = readEntry("Enter the coach's University: \n");
      p.setString(5, univ);

      int champWins = Integer.parseInt(readEntry(
          "Enter the number of championship games this coach has won: \n"));
      p.setInt(8, champWins);

      int semiFinals = Integer.parseInt(readEntry(
          "Enter the number of times this coach has brought a team to the semifinals: \n"));
      p.setInt(9, semiFinals);

      p.executeUpdate();
    } catch (InputMismatchException e) {
      System.out.println("Invalid input");
      insertCoach();
    } catch (SQLException s) {
      System.out.println(s.getMessage());
      insertCoach();
    }
  }

  public void insertTeam() throws SQLException {
    try {
      String query = "" +
          "INSERT INTO CHAMPIONSHIP_TEAM " +
          "VALUES (?,?,?,?,?,?,?,?,?)";
      PreparedStatement p = conn.prepareStatement(query);
      p.clearParameters();

      int id = Integer.parseInt(readEntry("Please enter the Team's ID: \n"));
      p.setInt(1, id);

      String name = readEntry("Please enter the Team's name: \n");
      p.setString(2, name);

      int coachSSN = Integer.parseInt(readEntry(
          "Please enter the SSN of an existing coach who is not currently the coach of a team: \n"));
      p.setInt(3, coachSSN);

      String stringAssCoach = readEntry("Please enter the SSN of an existing assistant coach who is not currently part of a team, enter q if no assistance coach: \n");
      if (!stringAssCoach.equals("q")) {
        int assCoachSSN = Integer.parseInt(stringAssCoach);
        p.setInt(4, assCoachSSN);
      } else {
        p.setNull(4, Types.INTEGER);
      }

      String univ = readEntry("Please enter the university this team belongs to: \n");
      p.setString(5, univ);

      Statement rankTest = conn.createStatement(); // TODO: can this be its own function?
      int rank = 1;
      String rankTestQuery = "" +
          "SELECT Team_name " +
          "FROM CHAMPIONSHIP_TEAM";
      ResultSet results = rankTest.executeQuery(rankTestQuery);
      while (results.next()) {
        rank++;
      }
      p.setInt(6, rank);

      String stringWins = readEntry("Please enter the team's wins: \n");
      int wins = Integer.parseInt(stringWins);
      p.setInt(7, wins);

      String stringLosses = readEntry("Please enter the team's losses: \n");
      int losses = Integer.parseInt(stringLosses);
      p.setInt(8, losses);

      String stringTies = readEntry("Please enter the team's ties: \n");
      int ties = Integer.parseInt(stringTies);
      p.setInt(9, ties);

      p.executeUpdate();
    } catch (InputMismatchException e) {
      System.out.println("Invalid input");
      insertTeam();
    } catch (SQLException s) {
      System.out.println(s.getMessage());
      insertTeam();
    }
  }

  public void deletePlayer() throws SQLException {
    String query = "" +
        "DELETE FROM PLAYERS " +
        "WHERE SSN = ?";
    PreparedStatement p = conn.prepareStatement(query);
    int SSN = Integer.parseInt(readEntry("Enter Player SSN to Delete: "));
    p.clearParameters();
    p.setInt(1, SSN);
    p.executeUpdate();
  }

  public void deleteCoach() throws SQLException {
    String query = "" +
        "DELETE FROM COACH " +
        "WHERE SSN = ?";
    PreparedStatement p = conn.prepareStatement(query);
    int SSN = Integer.parseInt(readEntry("Enter Coach SSN to Delete: "));
    p.clearParameters();
    p.setInt(1, SSN);
    p.executeUpdate();
  }

  public void deleteTeam() throws SQLException {
    String query = "" +
        "DELETE FROM CHAMPIONSHIP_TEAM " +
        "WHERE ID = ?";
    PreparedStatement p = conn.prepareStatement(query);
    int ID = Integer.parseInt(readEntry("Enter Team ID to Delete: "));
    p.clearParameters();
    p.setInt(1, ID);
    p.executeUpdate();
  }

  public void playerRank() throws SQLException {
    Statement getPlayerRank = conn.createStatement();

    String query = "" +
        "SELECT Name, PLAYER_Rank " +
        "FROM PLAYERS JOIN ALLSTAR_NOMINEES PLAYERS_SSN " +
        "ON SSN = PLAYERS_SSN";

    ResultSet result = getPlayerRank.executeQuery(query);

    while (result.next()) {
      String name = result.getString(1);
      int rank = result.getInt(2);

      System.out.println(name + " " + rank);
    }
  }

  public void getTeamWins() throws SQLException {

    Statement getTeamWins = conn.createStatement();
    String query = "" +
        "SELECT Team_name, Win " +
        "FROM CHAMPIONSHIP_TEAM";
    ResultSet result = getTeamWins.executeQuery(query);

    while (result.next()) {
      String teamName = result.getString(1);
      int wins = result.getInt(2);

      println(teamName + " " + wins);
    }
  }

  public void getParticipation() throws SQLException {
    int count = 0;

    Statement getParticipation = conn.createStatement();
    String query = "" +
        "SELECT Team_name " +
        "FROM CHAMPIONSHIP_TEAM";
    ResultSet results = getParticipation.executeQuery(query);

    while (results.next()) {
      count++;
    }
    println(count);
  }

  private void print(Object s) {
    System.out.print(s);
  }

  private void println(Object s) {
    System.out.println(s);
  }

  private String readEntry(String prompt) {
    try {
      StringBuffer buffer = new StringBuffer();
      print(prompt);
      System.out.flush();
      int c = System.in.read();
      while (c != '\n' && c != -1) {
        buffer.append((char) c);
        c = System.in.read();
      }
      return buffer.toString().trim();
    } catch (IOException e) {
      return "";
    }
  }
}
