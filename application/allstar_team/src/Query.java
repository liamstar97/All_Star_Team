import java.sql.*;
import java.io.*;
import java.sql.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

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

          String SSNString = readEntry("Enter the player's SSN: \n");
          int SSN = Integer.parseInt(SSNString);
          p.setInt(1, SSN);

          String teamIDStr = readEntry("Enter the player's Team ID: \n");
          int teamID = Integer.parseInt(teamIDStr);
          p.setInt(2, teamID);

          String allstar = readEntry("Enter the number of times this player has been on an Allstar Team: \n");
          int timeAllstar = Integer.parseInt(allstar);
          p.setInt(10, timeAllstar);

          String years = readEntry("Enter the player's number of years on their team: \n");
          int yearsTeam = Integer.parseInt(years);
          p.setInt(8, yearsTeam);

          String name = readEntry("Enter the player's Name: \n");
          p.setString(3, name);

          String address = readEntry("Enter the player's Address: \n");
          p.setString(4, address);

          String bDate = readEntry("Enter the player's birth date as YYYY-MM-DD: \n");
          Date birthDate = Date.valueOf(bDate);
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
        } catch(SQLException s){
            System.out.println(s.getMessage());
            insertPlayer();
        }
    }

  public void insertCoach() throws SQLException {

      try {
          String query = "INSERT INTO COACH VALUES (?,?,?,?,?,?,?,?,?)";
          PreparedStatement p = conn.prepareStatement(query);
          p.clearParameters();

          String SSNString = readEntry("Enter the coach's SSN: \n");
          int SSN = Integer.parseInt(SSNString);
          p.setInt(1, SSN);

          String yearsTotal = readEntry("Enter the total number of years spent as a coach: \n");
          int years = Integer.parseInt(yearsTotal);
          p.setInt(7, years);

          String stringYearsTeam = readEntry("Enter the coach's number of years on their team: \n");
          int yearsTeam = Integer.parseInt(stringYearsTeam);
          p.setInt(6, yearsTeam);

          String name = readEntry("Enter the coach's Name: \n");
          p.setString(2, name);

          String address = readEntry("Enter the coach's Address: \n");
          p.setString(3, address);

          String bDate = readEntry("Enter the coach's birth date as YYYY-MM-DD: \n");
          Date birthDate = Date.valueOf(bDate);
          p.setDate(4, birthDate);

          String univ = readEntry("Enter the coach's University: \n");
          p.setString(5, univ);

          String wins = readEntry("Enter the number of championship games this coach has won: \n");
          int champWins =Integer.parseInt(wins);
          p.setInt(8, champWins);

          String semi = readEntry("Enter the number of times this coach has brought a team to the semifinals: \n");
          int semiFinals = Integer.parseInt(semi);
          p.setInt(9, semiFinals);

          p.executeUpdate();

      } catch (InputMismatchException e) {
          System.out.println("Invalid input");
          insertPlayer();
      } catch(SQLException s){
          System.out.println(s.getMessage());
          insertPlayer();
      }

  }

  public void insertTeam() throws SQLException {
      try {
          String query = "INSERT INTO CHAMPIONSHIP_TEAM VALUES (?,?,?,?,?,?,?,?,?)";
          PreparedStatement p = conn.prepareStatement(query);
          p.clearParameters();

          String stringID = readEntry("Please enter the Team's ID: \n");
          int id = Integer.parseInt(stringID);
          p.setInt(1,id);

          String name = readEntry("Please enter the Team's name: \n");
          p.setString(2,name);

          String stringCoach = readEntry("Please enter the SSN of an existing coach who is not currently the coach of a team: \n");
          int coachSSN = Integer.parseInt(stringCoach);
          p.setInt(3,coachSSN);

          String stringAssCoach = readEntry("Please enter the SSN of an existing assistant coach who is not currently part of a team, enter q if no assistance coach: \n");
          if(!stringAssCoach.equals("q")) {
              int assCoachSSN = Integer.parseInt(stringAssCoach);
              p.setInt(4,assCoachSSN);
          }else{
              p.setNull(4, Types.INTEGER);
          }

          String univ = readEntry("Please enter the university this team belongs to: \n");
          p.setString(5,univ);

          Statement rankTest = conn.createStatement();
          int rank = 1;
          String rankTestQuery = "SELECT Team_name FROM CHAMPIONSHIP_TEAM";
          ResultSet results = rankTest.executeQuery(rankTestQuery);
          while (results.next()) {
              rank++;
          }
          p.setInt(6,rank);

          String stringWins = readEntry("Please enter the team's wins: \n");
          int wins = Integer.parseInt(stringWins);
          p.setInt(7,wins);

          String stringLosses = readEntry("Please enter the team's losses: \n");
          int losses = Integer.parseInt(stringLosses);
          p.setInt(8,losses);

          String stringTies = readEntry("Please enter the team's ties: \n");
          int ties = Integer.parseInt(stringTies);
          p.setInt(9,ties);

          p.executeUpdate();

      } catch (InputMismatchException e) {
          System.out.println("Invalid input");
          insertCoach();
      } catch(SQLException s){
          System.out.println(s.getMessage());
          insertCoach();
      }
  }

  public void deletePlayer() throws SQLException {
    String query = "DELETE FROM PLAYERS WHERE SSN = ?";
    PreparedStatement p = conn.prepareStatement(query);
    String SSNString = readEntry("Enter Player SSN to Delete: ");
    int SSN = Integer.parseInt(SSNString);
    p.clearParameters();
    p.setInt(1, SSN);
    p.executeUpdate();
  }

  public void deleteCoach() throws SQLException {
    String query = "DELETE FROM COACH WHERE SSN = ?";
    PreparedStatement p = conn.prepareStatement(query);
    String SSNString = readEntry("Enter Coach SSN to Delete: ");
    int SSN = Integer.parseInt(SSNString);
    p.clearParameters();
    p.setInt(1, SSN);
    p.executeUpdate();
  }

  public void deleteTeam() throws SQLException {
    String query = "DELETE FROM CHAMPIONSHIP_TEAM WHERE ID = ?";
    PreparedStatement p = conn.prepareStatement(query);
    String IDString = readEntry("Enter Team ID to Delete: ");
    int ID = Integer.parseInt(IDString);
    p.clearParameters();
    p.setInt(1, ID);
    p.executeUpdate();
  }

  public void playerRank() throws SQLException {
    Statement getPlayerRank = conn.createStatement();

    String query = "SELECT Name, PLAYER_Rank FROM PLAYERS JOIN ALLSTAR_NOMINEES PLAYERS_SSN ON SSN = PLAYERS_SSN";

    ResultSet result = getPlayerRank.executeQuery(query);

    while (result.next()) {
      String name = result.getString(1);
      int rank = result.getInt(2);

      System.out.println(name + " " + rank);
    }
  }

  public void getTeamWins() throws SQLException {
    Statement getTeamWins = conn.createStatement();

    String query = "SELECT Team_name, Win FROM CHAMPIONSHIP_TEAM";

    ResultSet result = getTeamWins.executeQuery(query);

    while (result.next()) {
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
