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
      String name, address, position, univ, collegeClass;
      Date birthDate;
      int SSN, teamID, allstar, years;
      // get user input
      SSN = Integer.parseInt(readEntry("Enter the player's SSN: \n"));
      teamID = Integer.parseInt(readEntry("Enter the player's Team ID: \n"));
      allstar = Integer.parseInt(readEntry(
          "Enter the number of times this player has been on an Allstar Team: \n"));
      years = Integer.parseInt(readEntry("Enter the player's number of years on their team: \n"));
      name = readEntry("Enter the player's Name: \n");
      address = readEntry("Enter the player's Address: \n");
      birthDate = Date.valueOf(readEntry("Enter the player's birth date as YYYY-MM-DD: \n"));
      position = readEntry("Enter the player's Position: \n");
      univ = readEntry("Enter the player's University: \n");
      collegeClass = readEntry("Enter the player's Class: \n");
      // prepare sql statement
      PreparedStatement p = conn.prepareStatement(query);
      p.clearParameters();
      p.setInt(1, SSN);
      p.setInt(2, teamID);
      p.setString(3, name);
      p.setString(4, address);
      p.setDate(5, birthDate);
      p.setString(6, position);
      p.setString(7, univ);
      p.setInt(8, years);
      p.setString(9, collegeClass);
      p.setInt(10, allstar);
      // execute prepared statement
      p.executeUpdate();
    } catch (InputMismatchException e) { // catch invalid input
      System.out.println("Invalid input");
      insertPlayer();
    } catch (SQLException s) { // catch sql exception
      System.out.println(s.getMessage());
      insertPlayer();
    }
  }

  public void insertCoach() throws SQLException {

    try {
      String query = "INSERT INTO COACH VALUES (?,?,?,?,?,?,?,?,?)";
      String name, address, univ;
      Date birthDate;
      int SSN, yearsTeam, yearsTotal, champWins, semiFinals;
      // get user input
      SSN = Integer.parseInt(readEntry("Enter the coach's SSN: \n"));
      yearsTeam = Integer.parseInt(readEntry("Enter the coach's number of years on their team: \n"));
      yearsTotal = Integer.parseInt(readEntry("Enter the coach's total number of years coaching:"));
      name = readEntry("Enter the coach's Name: \n");
      address = readEntry("Enter the coach's Address: \n");
      birthDate = Date.valueOf(readEntry("Enter the coach's birth date as YYYY-MM-DD: \n"));
      univ = readEntry("Enter the player's University: \n");
      champWins = Integer.parseInt(readEntry("Enter the number of championship games this coach has won: \n"));
      semiFinals = Integer.parseInt(readEntry(
          "Enter the number of times this coach has brought a team to the semifinals: \n"));
      // prepare sql statement
      PreparedStatement p = conn.prepareStatement(query);
      p.clearParameters();
      p.setInt(1, SSN);
      p.setString(2, name);
      p.setString(3, address);
      p.setDate(4, birthDate);
      p.setString(5, univ);
      p.setInt(6, yearsTeam);
      p.setInt(7, yearsTotal);
      p.setInt(8, champWins);
      p.setInt(9, semiFinals);
      // execute prepared statement
      p.executeUpdate();
    } catch (InputMismatchException e) { // catch invalid input
      System.out.println("Invalid input");
      insertPlayer();
    } catch (SQLException s) { // catch sql exception
      System.out.println(s.getMessage());
      insertPlayer();
    }

  }

  public void insertTeam() throws SQLException {
    try {
      String query = "INSERT INTO CHAMPIONSHIP_TEAM VALUES (?,?,?,?,?,?,?,?,?)";
      String name, univ;
      int id, coachSSN, assCoachSSN, wins, losses, ties;
      // get user input
      id = Integer.parseInt(readEntry("Please enter the Team's ID: \n"));
      name = readEntry("Please enter the Team's name: \n");
      coachSSN = Integer.parseInt(readEntry(
          "Please enter the SSN of an existing coach who is not currently the coach of a team: \n"));
      assCoachSSN = Integer.parseInt(readEntry(
          "Please enter the SSN of an existing assistant coach who is not currently part of a team: \n"));
      univ = readEntry("Please enter the university this team belongs to: \n");
      wins = Integer.parseInt(readEntry("Please enter the team's wins: \n"));
      losses = Integer.parseInt(readEntry("Please enter the team's losses: \n"));
      ties = Integer.parseInt(readEntry("Please enter the team's ties: \n"));

      Statement rankTest = conn.createStatement(); // TODO: Can this be a separate method?
      int rank = 1;
      String rankTestQuery = "" +
          "SELECT Team_name " +
          "FROM CHAMPIONSHIP_TEAM";
      ResultSet results = rankTest.executeQuery(rankTestQuery);

      while (results.next()) {
        rank++;
      }
      // prepare sql statement
      PreparedStatement p = conn.prepareStatement(query);
      p.clearParameters();
      p.setInt(1, id);
      p.setString(2, name);
      p.setInt(3, coachSSN);
      p.setInt(4, assCoachSSN);
      p.setString(5, univ);
      p.setInt(6, rank);
      p.setInt(7, wins);
      p.setInt(8, losses);
      p.setInt(9, ties);
      // execute prepared statement
      p.executeUpdate();
    } catch (InputMismatchException e) { // catch invalid input
      println("Invalid input");
      insertPlayer();
    } catch (SQLException s) { // catch sql exception
      println(s.getMessage());
      insertPlayer();
    }
  }

  public void deletePlayer() throws SQLException {
    Scanner scanner = new Scanner(System.in);
    String query = "" +
        "SELECT fname, lname, salary " +
        "FROM employee " +
        "WHERE dno = ? " +
        "ORDER BY salary DESC";
    PreparedStatement p = conn.prepareStatement(query);
    String dno = scanner.next("Enter a department number: ");
    p.clearParameters();
    p.setString(1, dno);
    ResultSet r = p.executeQuery();
    scanner.close();
  }

  public void deleteCoach() throws SQLException {
    Scanner scanner = new Scanner(System.in);
    String query = "" +
        "SELECT fname, lname, salary " +
        "FROM employee " +
        "WHERE dno = ? " +
        "ORDER BY salary DESC";
    PreparedStatement p = conn.prepareStatement(query);
    String dno = scanner.next("Enter a department number: ");
    p.clearParameters();
    p.setString(1, dno);
    ResultSet r = p.executeQuery();
    scanner.close();
  }

  public void deleteTeam() throws SQLException {
    Scanner scanner = new Scanner(System.in);
    String query = "" +
        "SELECT fname, lname, salary " +
        "FROM employee " +
        "WHERE dno = ? " +
        "ORDER BY salary DESC";
    PreparedStatement p = conn.prepareStatement(query);
    String dno = scanner.next("Enter a department number: ");
    p.clearParameters();
    p.setString(1, dno);
    ResultSet r = p.executeQuery();
    scanner.close();
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

  private boolean validateInt(String input) {
    Scanner userInput = new Scanner(input);

    for (int i = 0; i < input.length(); i++) {
      if (!userInput.hasNextInt())
        return false;
    }
    return true;
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
