/*
 * Name: Query.java
 * Authors: Kyle White, Mathew Tkachuk, Liam Thompson
 * Date: 12/3/2020
 * --------------------------
 * Description: This class contains query methods returning results to the console, some ask for user defined input.
 */
import java.sql.*;
import java.io.*;
import java.sql.Date;
import java.util.InputMismatchException;

public class Query {

  /**
   * private global connection
   */
  private final Connection conn;

  /**
   * constructs query object and sets this connection equal to passes in connection
   * @param conn Connection object
   */
  public Query(Connection conn) {
    this.conn = conn;
  }

  /**
   * lists all-star nominees, their ranks, and positions from user defined team
   * @throws SQLException
   */
  public void listNominees() throws SQLException {
    // array of positions
    String[] strings = {"Center", "Guard", "Wide Receiver", "Inside Receiver",
        "Quarterback", "Running Back", "Tackler"};
    // SQL query
    String query = "" +
        "SELECT Name, PLAYER_Rank " +
        "FROM PLAYERS JOIN ALLSTAR_NOMINEES AN on PLAYERS.SSN = AN.PLAYERS_SSN " +
        "WHERE Position = ? " +
        "ORDER BY PLAYER_Rank";
    PreparedStatement statement = conn.prepareStatement(query);
    println("                   ---------------------");
    // print resulting nominees for each position
    for (String s : strings) {
      statement.setString(1, s.toLowerCase());
      ResultSet results = statement.executeQuery();
      // print results
      if (results.next()) { // print nominees or skip position if no results exist for that position
        println(s + "s:");
        printNomineeResults(results);
        while (results.next()) {
          printNomineeResults(results);
        }
      }
      statement.clearParameters(); // reset parameters for next query
    }
  }

  /**
   * prints formatted all-star nominee query results
   * @param results passed in query resultSet
   * @throws SQLException
   */
  private void printNomineeResults(ResultSet results) throws SQLException {
    String name;
    int playerRank;
    name = results.getString(1);
    playerRank = results.getInt(2);
    println("    " + name + ", Rank: " + playerRank );
  }

  /**
   * lists player names, coach name, and team rank of a user defined team
   * @throws SQLException
   */
  public void searchTeamInfo() throws SQLException {
    // SQL query
    String query = "" +
        "SELECT P.Name, C.Name, Team_rank " +
        "FROM CHAMPIONSHIP_TEAM " +
        "JOIN PLAYERS P on CHAMPIONSHIP_TEAM.ID = P.CHAMPIONSHIP_TEAMS_ID " +
        "JOIN COACH C on CHAMPIONSHIP_TEAM.COACH_SSN = C.SSN " +
        "WHERE Team_name = ? " +
        "ORDER BY P.Name";
    PreparedStatement statement = conn.prepareStatement(query);
    String teamName = readEntry("Enter team name: ");
    statement.setString(1, teamName);
    // get query results, and print them
    ResultSet results = statement.executeQuery();
    if (results.next()) { // if there is a result print results
      printTeamInfo(results);
    } else { // else there are no results to print
      println("there are no results for " + teamName + " or that team does not exits!");
    }
  }

  /**
   * format and print team info results
   * @param results resultSet
   * @throws SQLException
   */
  private void printTeamInfo(ResultSet results) throws SQLException{
    println("                   ---------------------");
    println("Coach: " + results.getString(2));
    println("Rank: " + results.getInt(3));
    println("Players:");
    println("1.   " + results.getString(1));
    int count = 2;
    while (results.next()) { // TODO: add spaces dynamically
      println(count + ".   " + results.getString(1));
      count++;
    }
  }

  /**
   * lists won and lost all-star games date, location, score, coach, and assistant coach for a user defined team
   * @throws SQLException
   */
  public void searchGameInfo() throws SQLException {
    // SQL query for team wins
    String queryWins = "" +
        "SELECT Location, Date, Score, C.Name, AC.Name " +
        "FROM ALLSTAR_GAME " +
        "JOIN CHAMPIONSHIP_TEAM CT on ALLSTAR_GAME.CHAMPIONSHIP_TEAMS_ID_WINNER = CT.ID " +
        "JOIN COACH C on ALLSTAR_GAME.COACH_SSN = C.SSN " +
        "JOIN ASSISTANT_COACH AC on ALLSTAR_GAME.ASSISTANT_COACH_SSN = AC.SSN " +
        "WHERE CT.Team_name = ? " +
        "ORDER BY Date DESC";
    PreparedStatement winStatement = conn.prepareStatement(queryWins);
    // SQL query for team losses
    String queryLosses = "" +
        "SELECT Location, Date, Score, C.Name, AC.Name " +
        "FROM ALLSTAR_GAME " +
        "JOIN CHAMPIONSHIP_TEAM CT on ALLSTAR_GAME.CHAMPIONSHIP_TEAMS_ID_LOSER = CT.ID " +
        "JOIN COACH C on ALLSTAR_GAME.COACH_SSN = C.SSN " +
        "JOIN ASSISTANT_COACH AC on ALLSTAR_GAME.ASSISTANT_COACH_SSN = AC.SSN " +
        "WHERE CT.Team_name = ? " +
        "ORDER BY Date DESC";
    PreparedStatement lossStatement = conn.prepareStatement(queryLosses);
    // set query parameters with user defined team
    String teamName = readEntry("Enter team name: ");
    winStatement.setString(1, teamName);
    lossStatement.setString(1, teamName);
    // get query results
    ResultSet winResults = winStatement.executeQuery();
    ResultSet lossResults = lossStatement.executeQuery();
    // print query results
    println("                   ---------------------");
    boolean hasResults = false;
    if (winResults.next()) { // prints wins if any
      hasResults = true;
      println(teamName + " Wins" + ":");
      printGameInfo(winResults);
    }
    if (lossResults.next()) { // prints losses if any
      hasResults = true;
      println(teamName + " Losses: ");
      printGameInfo(lossResults);
    }
    if (!hasResults) { // if no results exist
      println("There are no recorded wins or losses for " + teamName + ".");
    }
  }

  /**
   * format and print game info for searchGameInfo query results
   * @param results resultSet
   * @throws SQLException
   */
  private void printGameInfo(ResultSet results) throws SQLException {
    do {
      String location = results.getString(1);
      Date date = results.getDate(2);
      String score = results.getString(3);
      String coach = results.getString(4);
      String assistantCoach = results.getString(5);
      println("(" + date + ") " + location +
          ", Score: " + score + ", Coach: " + coach + ", Assistant Coach: " + assistantCoach);
    } while (results.next());
  }

  /**
   * lists teams a user defined coach has coached
   * @throws SQLException
   */
  public void searchCoachInfo() throws SQLException {
    // SQL query
    String query = "" +
        "SELECT Team_name " +
        "FROM CHAMPIONSHIP_TEAM " +
        "JOIN COACH C on C.SSN = CHAMPIONSHIP_TEAM.COACH_SSN " +
        "WHERE C.Name = ? " +
        "ORDER BY Team_name";
    PreparedStatement statement = conn.prepareStatement(query);
    // set query parameters for user defined coach
    String coach = readEntry("Enter coach name: ");
    statement.setString(1, coach);
    // get query results
    ResultSet results = statement.executeQuery();
    // format and print query results
    println("                   ---------------------");
    println("Teams:");
    while (results.next()) {
      String team = results.getString(1);
      println("    " + team);
    }
  }


  /**
   * The insertPlayer method accepts user input for each of the 10 attributes
   * of a player in the Allstar_Team database, creates a PreparedStatement
   * object with each parameter, and uses the PreparedStatement executeUpdate
   * method to initiate the query within the database.
   * @throws SQLException
   */
  public void insertPlayer() throws SQLException {
    try {
      String query = "" +
          "INSERT INTO PLAYERS " +
          "VALUES (?,?,?,?,?,?,?,?,?,?)";
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

//      String univ = readEntry("Enter the player's University: \n");
//      p.setString(7, univ);

      p.setString(7,playerUnivCheck(teamID));

      String collegeClass = readEntry("Enter the player's Class: \n");
      p.setString(9, collegeClass);

      p.executeUpdate();

      //Exception handling for invalid user input and database rejections
    } catch (InputMismatchException e) {
      System.out.println("Invalid input.");
      insertPlayer();
    } catch (SQLException s) {
      System.out.println(s.getMessage());
      insertPlayer();
    } catch (NumberFormatException n) {
      println("Invlaid input.");
    }
  }

  /**
   * The insertCoach method accepts user input for each of the 9 attributes
   * of a coach in the Allstar_Team database, creates a PreparedStatement
   * object with each parameter, and uses the PreparedStatement executeUpdate
   * method to initiate the query within the database.
   * @throws SQLException
   */

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

      //Exception handling for invalid user input and database rejections
    } catch (InputMismatchException e) {
      System.out.println("Invalid input");
      insertCoach();
    } catch (SQLException s) {
      System.out.println(s.getMessage());
      insertCoach();
    } catch (NumberFormatException n) {
      println("Invlaid input.");
    }
  }

  /**
   * The insertTeam method accepts user input for each of the 9 attributes
   * of a team in the Allstar_Team database, creates a PreparedStatement
   * object with each parameter, and uses the PreparedStatement executeUpdate
   * method to initiate the query within the database. This method also handles
   * placing the new team in the correctly ranked position and setting
   * the assistant coach to null if no assistant coach is entered.
   * @throws SQLException
   */

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

      String stringAssCoach = readEntry("Please enter the SSN of an existing assistant coach who is " +
          "not currently part of a team, enter q if no assistance coach: \n");
      if (!stringAssCoach.equals("q")) {
        int assCoachSSN = Integer.parseInt(stringAssCoach);
        p.setInt(4, assCoachSSN);
      } else {
        p.setNull(4, Types.INTEGER);
      }

//      String univ = readEntry("Please enter the university this team belongs to: \n");
//      p.setString(5, univ);
      p.setString(5,coachUnivCheck(coachSSN));

      int rank = rankCounter();
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

      //Exception handling for invalid user input and database rejections
    } catch (InputMismatchException e) {
      System.out.println("Invalid input");
      insertTeam();
    } catch (SQLException s) {
      System.out.println(s.getMessage());
      insertTeam();
    } catch (NumberFormatException n) {
      println("Invlaid input.");
    }
  }

  /**
   * The deletePlayer method accepts the SSN of a player that the user wishes
   * to delete from the database, creates a PreparedStatement object with the 
   * input parameters, and executes the update on the database.
   * @throws SQLException
   */

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

  /**
   * The deleteCoach method accepts the SSN of a coach that the user wishes
   * to delete from the database, creates a PreparedStatement object with the 
   * input parameters, and executes the update on the database.
   * @throws SQLException
   */

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

  /**
   * The deleteTeam method accepts the Team ID of a team that the user wishes
   * to delete from the database, creates a PreparedStatement object with the 
   * input parameters, and executes the update on the database.
   * @throws SQLException
   */

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

  /**
   * Prints out the name and rank of the players who have been nominated as allstars
   * @throws SQLException
   */
  public void playerRank() throws SQLException {
    Statement getPlayerRank = conn.createStatement();

    String query = "" +
        "SELECT Name, PLAYER_Rank " +
        "FROM PLAYERS JOIN ALLSTAR_NOMINEES PLAYERS_SSN " +
        "ON SSN = PLAYERS_SSN";

    ResultSet result = getPlayerRank.executeQuery(query);

    //Loops through results and prints them out
    while (result.next()) {
      String name = result.getString(1);
      int rank = result.getInt(2);

      System.out.println(name + " " + rank);
    }
  }

  /**
   * Prints out all team names and their number of wins
   * @throws SQLException
   */
  public void getTeamWins() throws SQLException {

    Statement getTeamWins = conn.createStatement();
    String query = "" +
        "SELECT Team_name, Win " +
        "FROM CHAMPIONSHIP_TEAM";
    ResultSet result = getTeamWins.executeQuery(query);

    //Loops through results and prints them out
    while (result.next()) {
      String teamName = result.getString(1);
      int wins = result.getInt(2);

      println(teamName + " " + wins);
    }
  }

  /**
   * Prints out all teams that have participated in an all-star game
   * @throws SQLException
   */
  public void getParticipation() throws SQLException {
    int count = 0;

    Statement getParticipation = conn.createStatement();
    String query = "" +
        "SELECT DISTINCT Team_name " +
        "FROM CHAMPIONSHIP_TEAM JOIN ALLSTAR_GAME " +
        "ON CHAMPIONSHIP_TEAMS_ID_WINNER = ID OR CHAMPIONSHIP_TEAMS_ID_LOSER = ID";
    ResultSet results = getParticipation.executeQuery(query);

    while (results.next()) {
      count++;
    }
    println(count);
  }

  /**
   * Utility method for shorthand printing
   * @param s
   */
  private void print(Object s) {
    System.out.print(s);
  }

  /**
   * Utility method for shorthand printing
   * @param s
   */
  private void println(Object s) {
    System.out.println(s);
  }

  /**
   * Taken from provided code in worksheet09 of CS331
   * @param prompt
   * @return
   */
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

  /**
   * Helper function that counts the current number of teams in the database, which sets
   * the new team to last place.
   * @return
   * @throws SQLException
   */
  private int rankCounter() throws SQLException{
    Statement rankTest = conn.createStatement();
    int rank = 1;
    String rankTestQuery = "" +
            "SELECT Team_name " +
            "FROM CHAMPIONSHIP_TEAM";
    ResultSet results = rankTest.executeQuery(rankTestQuery);
    while (results.next()) {
      rank++;
    }
    return rank;
  }

  /**
   * Helper function that finds the university for a particular team
   * @param id
   * @return
   * @throws SQLException
   */
  private String playerUnivCheck(int id) throws SQLException{

    String query = "SELECT University " +
            "FROM CHAMPIONSHIP_TEAM " +
            "WHERE ID = ?";

    PreparedStatement univCheck = conn.prepareStatement(query);
    univCheck.setInt(1, id);

    ResultSet result = univCheck.executeQuery();

    return result.getString(1);
  }

  /**
   * Helper function that finds the university tied to a particular coach
   * @param ssn
   * @return
   * @throws SQLException
   */
  private String coachUnivCheck(int ssn) throws SQLException{
    String query = "SELECT University " +
            "FROM COACH " +
            "WHERE SSN = ?";

    PreparedStatement univCheck = conn.prepareStatement(query);
    univCheck.setInt(1, ssn);

    ResultSet result = univCheck.executeQuery();

    return result.getString(1);
  }

}
