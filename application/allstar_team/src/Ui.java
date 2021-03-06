/*
 * Name: Ui.java
 * Authors: Kyle White, Mathew Tkachuk, Liam Thompson
 * Date: 12/3/2020
 * --------------------------
 * Description: This class contains all Ui menus and their sub menus which calls query methods in Query.java printing
 *              results to the console.
 */
import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ui {

  /**
   * global private Connection variable
   */
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
    try {
      String url = "jdbc:mysql://cs331.chhxghxty6xs.us-west-2.rds.amazonaws.com:3306/" +
          "Allstar_Team?serverTimezone=UTC&useSSL=TRUE";
      String user, pass;
      user = readEntry("Enter Username: ");
      pass = readEntry("Enter Password: ");
      return DriverManager.getConnection(url, user, pass);
    } catch (SQLException e) { // prompt user for username and password again if incorrect
      println("Incorrect username or password! Press enter to try again, or 'x' to exit!");
      if (readLine().equals("x")) {
        System.exit(0);
        return null;
      } else {
        return login();
      }
    }
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
        case 'x':
          CONNECTION.close();
          System.exit(0);
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
        case 'x':
          CONNECTION.close();
          System.exit(0);
          break;
        default:
          println("not a valid input");
          break;
      }
    } while (!quit);
  }

  /**
   * browse menu that displays a specified teams players and ranks!
   * @param query query object
   * @throws SQLException
   */
  private void browseMenu(Query query) throws SQLException  {
    printBrowseMenu();
    query.listNominees();
    pauseMenu();
  }

  /**
   * statsMenu has switch case that takes in user's input using getOption() then calls the corresponding query
   * @param query query object
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
        case 'x':
          CONNECTION.close();
          System.exit(0);
          break;
      }
    } while (!quit);
  }


  /**
   * The updatesMenu uses a switch-case to handle user input for the 
   * selection of either insertion or deletion methods to update the
   * database.
   * @param query The query object stored as a field in the UI class,
   * which houses all of the query-based functionality of the program.
   */
  
  private void updatesMenu(Query query) throws SQLException{
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
        case 'x':
          CONNECTION.close();
          System.exit(0);
          break;
        default:
          System.out.println("Not an option.");
          break;
      }
    } while (!quit);
  }

  /**
   * The insertMenu uses a switch-case to handle user input for the 
   * selection of the three permissible insert queries: insert player,
   * coach, or team.
   * @param query The query object stored as a field in the UI class,
   * which houses all of the query-based functionality of the program.
   */

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
          case 'x':
            CONNECTION.close();
            System.exit(0);
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

  /**
   * The deleteMenu uses a switch-case to handle user input for the 
   * selection of the three permissible delete queries: delete player,
   * coach, or team.
   * @param query The query object stored as a field in the UI class,
   * which houses all of the query-based functionality of the program.
   */

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
          case 'x':
            CONNECTION.close();
            System.exit(0);
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

  /**
   * header for main menu
   */
  private void printMainMenu() {
    println("***********************************************************");
    println("                       ***********                         ");
    println("            Welcome to Selecting an All-Star Team          ");
    println("                       ***********                         ");
    println("***********************************************************");
    println(" 1. Search & Browse the Database");
    println(" 2. Statistics & Data Mining");
    println(" 3. Updates");
    println(" q. Quit");
  }

  /**
   * header for stats menu
   */
  private void printStatsMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("***********************************************************");
    println(" 1. Score");
    println(" 2. Wins per team");
    println(" 3. Championship participation");
    println(" q. Quit");
    println(" x. To exit program");
  }

  /**
   * header for browse and search menu
   */
  private void printBrowseAndSearchMenu() {
    println("***********************************************************");
    println("                Browse & Search the Database               ");
    println("***********************************************************");
    println(" 1. Browse Nominees");
    println(" 2. Search Nominees");
    println(" q. Back");
    println(" x. To exit program");
  }

  /**
   * header for browse menu
   */
  private void printBrowseMenu() {
    println("***********************************************************");
    println("                       Browse Nominees                     ");
    println("***********************************************************");
  }

  /**
   * header for search menu
   */
  private void printSearchMenu() {
    println("***********************************************************");
    println("                           Search                          ");
    println("***********************************************************");
    println(" 1. Team Info");
    println(" 2. Game Info");
    println(" 3. Coach Info");
    println(" q. Back");
    println(" x. To exit program");
  }

  /**
   * The printUpdatesMenu is a helper function that is called to print menu
   * information when updating information in the database.
   */

  private void printUpdatesMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("                       3. Updates                          ");
    println("***********************************************************");
    println(" 1. Insert New Information");
    println(" 2. Delete Information");
    println(" q. Return to Main Menu");
    println(" x. To exit program");
  }

  /**
   * The printInsertMenu is a helper function that is called to print
   * menu information when inserting information in the database.
   */

  private void printInsertMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("              3. Updates - Insert Information              ");
    println("***********************************************************");
    println(" 1. Add a New Player");
    println(" 2. Add a New Coach");
    println(" 3. Add a New Team");
    println(" q. Return to Updates Menu");
    println(" x. To exit program");
  }

  /**
   * The printDeleteMenu is a helper function that is called to print
   * menu information when deleting information in the database.
   */

  private void printDeleteMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("              3. Updates - Delete Information              ");
    println("***********************************************************");
    println(" 1. Delete a Specific Player");
    println(" 2. Delete a Specific Coach");
    println(" 3. Delete a Specific Team");
    println(" q. Return to Updates Menu");
    println(" x. To exit program");
  }

  /**
   * gets user input for menu options
   * @return user input option
   */
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

  /**
   * pauses menu and waits for user to press enter
   */
  private void pauseMenu() { // TODO: fix bug where enter must be pressed twice if console window looses focus
    print("Press 'enter' to go back");
    readLine();
  }

  /**
   * reads entire line of user input and returns it
   * @return string of user input
   */
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

  /**
   * reads user input and returns it
   * @param prompt takes a string as a prompt for user input
   * @return string of user input
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