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
        Scanner scanner = new Scanner(System.in);
        try {
            String query = "INSERT INTO PLAYERS VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(query);

            String SSNString = readEntry("Enter the player's SSN: \n");
            int SSN = Integer.parseInt(SSNString);

            String teamIDStr = readEntry("Enter the player's Team ID: \n");
            int teamID = Integer.parseInt(teamIDStr);

            String allstar = readEntry("Enter the number of times this player has been on an Allstar Team: \n");
            int timeAllstar = Integer.parseInt(allstar);

            String years = readEntry("Enter the player's number of years on their team: \n");
            int yearsTeam = Integer.parseInt(years);

            String name = readEntry("Enter the player's Name: \n");

            String address = readEntry("Enter the player's Address: \n");

            String bDate = readEntry("Enter the player's birth date as YYYY-MM-DD: \n");
            Date birthDate = Date.valueOf(bDate);

            String position = readEntry("Enter the player's Position: \n");

            String univ = readEntry("Enter the player's University: \n");

            String collegeClass = readEntry("Enter the player's Class: \n");

            p.clearParameters();
            p.setInt(1, SSN);
            p.setInt(2, teamID);
            p.setString(3, name);
            p.setString(4, address);
            p.setDate(5, birthDate);
            p.setString(6, position);
            p.setString(7, univ);
            p.setInt(8, yearsTeam);
            p.setString(9, collegeClass);
            p.setInt(10, timeAllstar);

        } catch (InputMismatchException e) {
        System.out.println("Invalid input");
        insertPlayer();
        } catch(NumberFormatException n){
        System.out.println("Invalid input");
        insertPlayer();
        } catch(SQLException s){
        System.out.println(s.getErrorCode());
        insertPlayer();
        }
        // ResultSet r = p.executeQuery();
        scanner.close();
        // while (r.next()) {
        // String fname = r.getString(1);
        // String lname = r.getString(2);
        // double salary = r.getDouble(3);
        // System.out.println(String.format("%-20s %s", fname + " " + lname, "Salary: "
        // + salary));
        // }
    }

  public void insertCoach() throws SQLException {
    Scanner scanner = new Scanner(System.in);
    String query = "SELECT fname, lname, salary FROM employee WHERE dno = ? ORDER BY salary DESC;";
    PreparedStatement p = conn.prepareStatement(query);
    String dno = scanner.next("Enter a department number: ");
    p.clearParameters();
    p.setString(1, dno);
    ResultSet r = p.executeQuery();
    scanner.close();
  }

  public void insertTeam() throws SQLException {
    Scanner scanner = new Scanner(System.in);
    String query = "SELECT fname, lname, salary FROM employee WHERE dno = ? ORDER BY salary DESC;";
    PreparedStatement p = conn.prepareStatement(query);
    String dno = scanner.next("Enter a department number: ");
    p.clearParameters();
    p.setString(1, dno);
    ResultSet r = p.executeQuery();
    scanner.close();
  }

  public void deletePlayer() throws SQLException {
    Scanner scanner = new Scanner(System.in);
    String query = "SELECT fname, lname, salary FROM employee WHERE dno = ? ORDER BY salary DESC;";
    PreparedStatement p = conn.prepareStatement(query);
    String dno = scanner.next("Enter a department number: ");
    p.clearParameters();
    p.setString(1, dno);
    ResultSet r = p.executeQuery();
    scanner.close();
  }

  public void deleteCoach() throws SQLException {
    Scanner scanner = new Scanner(System.in);
    String query = "SELECT fname, lname, salary FROM employee WHERE dno = ? ORDER BY salary DESC;";
    PreparedStatement p = conn.prepareStatement(query);
    String dno = scanner.next("Enter a department number: ");
    p.clearParameters();
    p.setString(1, dno);
    ResultSet r = p.executeQuery();
    scanner.close();
  }

  public void deleteTeam() throws SQLException {
    Scanner scanner = new Scanner(System.in);
    String query = "SELECT fname, lname, salary FROM employee WHERE dno = ? ORDER BY salary DESC;";
    PreparedStatement p = conn.prepareStatement(query);
    String dno = scanner.next("Enter a department number: ");
    p.clearParameters();
    p.setString(1, dno);
    ResultSet r = p.executeQuery();
    scanner.close();
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
