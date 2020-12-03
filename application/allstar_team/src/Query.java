import java.sql.*;
import java.io.*;
import java.sql.Date;
import java.util.Scanner;


public class Query {

  private Connection conn;

  public Query(Connection conn) {
    this.conn = conn;
  }


  public void insertPlayer() throws SQLException {
    Scanner scanner = new Scanner(System.in);
    String query = "INSERT INTO PLAYERS VALUES (?,?,'?','?',?,'?','?',?,'?',?);";
    PreparedStatement p = conn.prepareStatement(query);
    System.out.println("Enter the player's SSN: ");
    int SSN = scanner.nextInt();
    System.out.println("Enter the player's Team ID: ");
    int teamID = scanner.nextInt();
    System.out.println("Enter the player's Name: ");
    String name = scanner.nextLine();
    System.out.println("Enter the player's Address: ");
    String address = scanner.nextLine();
    String bDate = scanner.next("Enter the player's birth date as YYYY-MM-DD: ");
    Date birthDate = Date.valueOf(bDate);
    System.out.println("Enter the player's Position: ");
    String position = scanner.nextLine();
    System.out.println("Enter the player's University: ");
    String univ = scanner.nextLine();
    System.out.println("Enter the player's number of years on their team: ");
    int yearsTeam = scanner.nextInt();
    System.out.println("Enter the player's Class: ");
    String collegeClass = scanner.nextLine();
    System.out.println("Enter the number of times this player has been on an Allstar Team: ");
    int timeAllstar = scanner.nextInt();
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
    ResultSet r = p.executeQuery();
    scanner.close();
    // while (r.next()) {
    //     String fname = r.getString(1);
    //     String lname = r.getString(2);
    //     double salary = r.getDouble(3);
    //     System.out.println(String.format("%-20s %s", fname + " " + lname, "Salary: " + salary));
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

    private boolean validateInt(String input){
        Scanner userInput = new Scanner(input);

        for(int i = 0; i < input.length(); i++){
            if(!userInput.hasNextInt())
                return false;
        }
        return true;
    }
}

