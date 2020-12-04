import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Ui {

  private final Connection CONNECTION;

  /**
   * Constructs a Ui Object
   * @throws SQLException
   */
  public Ui() throws SQLException {
    Connection conn = login();
    CONNECTION = conn;
    Query query = new Query(conn);
    mainMenu(conn, query);
  }

  /**
   * getter for Connection object
   * @return connection
   */
  public Connection getConnection() {
    return CONNECTION;
  }

  /**
   * prompts user for a username and password to login to the database
   * @return returns a connection
   * @throws SQLException
   */
  private Connection login() throws SQLException {
    String url = "jdbc:mysql://cs331.chhxghxty6xs.us-west-2.rds.amazonaws.com:3306/" +
        "Allstar_Team?serverTimezone=UTC&useSSL=TRUE";
    String user, pass;
    user = readEntry("Enter Username: ");
    pass = readEntry("Enter Password: ");
    return DriverManager.getConnection(url, user, pass);
  }

  /**
   * the main menu is the first menu displayed to the user containing three sub menus
   * @param conn connection
   * @param query query object
   * @throws SQLException
   */
  private void mainMenu(Connection conn, Query query) throws SQLException {
    boolean quit = false;
    do {
      printMainMenu();
      switch (getOption()) {
        case '1':
          browseAndSearchMenu(query);
          break;
        case '2':
          statsMenu(query);
          break;
        case '3':
          updatesMenu(query);
          break;
        case 'q':
          quit = true;
          println("Closing connection...");
          conn.close();
          break;
        default:
          println("not a valid input");
          break;
      }
    } while (!quit);
  }

  /**
   * displays browse and search menu containing two sub menus
   * @param query query object
   * @throws SQLException
   */
  private void browseAndSearchMenu(Query query) throws SQLException {
    boolean quit = false;
    do {
      printBrowseAndSearchMenu();
      switch (getOption()) {
        case '1':
          browseMenu(query);
          break;
        case '2':
          searchMenu(query);
          break;
        case 'q':
          quit = true;
          break;
        default:
          println("not a valid input");
          break;
      }
    } while (!quit);
  }

  /**
   * displays search menu containing three submenus
   * @param query query object
   * @throws SQLException
   */
  private void searchMenu(Query query) throws SQLException {
    boolean quit = false;
    do {
      printSearchMenu();
      switch (getOption()) {
        case '1':
          query.searchTeamInfo();
          pauseMenu();
          break;
        case '2':
          query.searchGameInfo();
          pauseMenu();
          break;
        case '3':
          query.searchCoachInfo();
          pauseMenu();
          break;
        case 'q':
          quit = true;
          break;
        default:
          println("not a valid input");
          break;
      }
    } while (!quit);
  }

  /**
   * browse menu that displays a specified teams players and ranks!
   * @param query
   * @throws SQLException
   */
  private void browseMenu(Query query) throws SQLException  {
    printBrowseMenu();
    query.listNominees();
    pauseMenu();
  }

  /**
   * statsMenu has switch case that takes in user's input using getOption() then calls the corresponding query
   * @param query
   * @throws SQLException
   */
  private void statsMenu(Query query) throws SQLException {
    boolean quit = false;
    printStatsMenu();
    do{
      switch (getOption()) {
        default:
          println("Not a valid option\n");
          break;
        case '1':
          query.playerRank();
          break;
        case '2':
          query.getTeamWins();
          break;
        case '3':
          query.getParticipation();
          break;
        case 'q':
          quit = true;
          break;
      }
    } while (!quit);
  }

  private void updatesMenu(Query query) {
    boolean quit = false;
    do {
      printUpdatesMenu();
      switch (getOption()) {
        case '1':
          insertMenu(query);
          break;
        case '2':
          deleteMenu(query);
          break;
        case 'q':
          quit = true;
          break;
        default:
          System.out.println("Not an option.");
          break;
      }
    } while (!quit);
  }

  private void insertMenu(Query query) {
    boolean quit = false;
    do {
      try {
        printInsertMenu();
        switch (getOption()) {
          case '1':
              query.insertPlayer();
            break;
          case '2':
              query.insertCoach();
            break;
          case '3':
              query.insertTeam();
            break;
          case 'q':
            quit = true;
            break;
          default:
            println("Not an option.");
            break;
        }
      } catch (SQLException e) {
        System.out.println("Failed.");
      }
    } while (!quit);
  }

  private void deleteMenu(Query query) {
    boolean quit = false;
    do {
      try {
        printDeleteMenu();
        switch (getOption()) {
          case '1':
              query.deletePlayer();
            break;
          case '2':
              query.deleteCoach();
            break;
          case '3':
              query.deleteTeam();
            break;
          case 'q':
            quit = true;
            break;
          default:
            println("Not an option.");
            break;
        }
      } catch (SQLException e) {
        println("Failed.");
      }
    } while (!quit) ;
  }

  private void printMainMenu() {
    println("***********************************************************");
    println("                       ***********                         ");
    println("            Welcome to Selecting an All-Star Team          ");
    println("                       ***********                         ");
    println("***********************************************************");
    println("             1. Search & Browse the Database");
    println("               2. Statistics & Data Mining");
    println("                        3. Updates");
    println("                         q. Quit");
  }

  private void printStatsMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("***********************************************************");
    println("1. Score");
    println("2. Wins per team");
    println("3. Championship participation");
    println("q. Quit");
  }

  private void printBrowseAndSearchMenu() {
    println("***********************************************************");
    println("                Browse & Search the Database               ");
    println("***********************************************************");
    println("                    1. Browse Nominees");
    println("                    2. Search Nominees");
    println("                         q. Back");
  }

  private void printBrowseMenu() {
    println("***********************************************************");
    println("                       Browse Nominees                     ");
    println("***********************************************************");
    println("                          q. Back ");
  }

  private void printSearchMenu() {
    println("***********************************************************");
    println("                           Search                          ");
    println("***********************************************************");
    println("                       1. Team Info");
    println("                       2. Game Info");
    println("                       3. Coach Info");
    println("                         q. Back");
  }

  private void printUpdatesMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("                       3. Updates                          ");
    println("***********************************************************");
    println("1. Insert New Information");
    println("2. Delete Information");
    println("q. Return to Main Menu");
  }

  private void printInsertMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("              3. Updates - Insert Information              ");
    println("***********************************************************");
    println("1. Add a New Player");
    println("2. Add a New Coach");
    println("3. Add a New Team");
    println("q. Return to Updates Menu");
  }

  private void printDeleteMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("              3. Updates - Delete Information              ");
    println("***********************************************************");
    println("1. Delete a Specific Player");
    println("2. Delete a Specific Coach");
    println("3. Delete a Specific Team");
    println("q. Return to Updates Menu");
  }

  private char getOption() {
    print("Type in your option: ");
    System.out.flush();
    String userInput = readLine();
    println("");
    char option = '0';
    if (userInput.length() == 1) {
      option = userInput.toLowerCase().charAt(0);
    }
    return option;
  }

  private void pauseMenu() { // TODO: fix bug where enter must be pressed twice if console looses focus
    print("Press 'enter' to go back");
    readLine();
  }

  private String readLine() {
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(isr, 1);
    String line = "";
    try {
      line = br.readLine();
    } catch (IOException e) {
      println("Error in SimpleIO.readLine: " +
          "IOException was thrown");
      System.exit(1);
    }
    return line;
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

  /**
   * Utility method for shorthand printing
   * @param s some object to print
   */
  private void print(Object s) {
    System.out.print(s);
  }

  /**
   * Utility method for shorthand printing
   * @param s some object to print
   */
  private void println(Object s) {
    System.out.println(s);
  }
}