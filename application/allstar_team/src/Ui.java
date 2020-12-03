import java.sql.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ui {

  Connection CONNECTION = null;

  public Ui() throws SQLException {
    Connection conn = login();
    CONNECTION = conn;
    Query query = new Query(conn);
    mainMenu(conn, query);
  }

  public Connection getConnection() {
    return CONNECTION;
  }

  private Connection login() throws SQLException { //TODO: change to user input before turning in, and demo
    String url = "jdbc:mysql://cs331.chhxghxty6xs.us-west-2.rds.amazonaws.com:3306/Allstar_Team?serverTimezone=UTC&useSSL=TRUE";
    String user, pass;
    user = "javaApp"; //TODO: remove hardcoded credentials, and remove user from db
    pass = "GiveUsAnAPlease!100%"; //TODO: Create demo user and sara user
    return DriverManager.getConnection(url, user, pass);
  }

  private void mainMenu(Connection conn, Query query) throws SQLException {
    boolean quit = false;
    do {
      printMainMenu();
      switch (getOption()) {
        case '1':
          browseAndSearchMenu(conn, query);
          break;
        case '2':
          statsMenu(conn, query);
          break;
        case '3':
          updatesMenu(conn, query);
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

  private void browseAndSearchMenu(Connection conn, Query query) throws SQLException {
    boolean quit = false;
    do {
      printBrowseAndSearchMenu();
      switch (getOption()) {
        case '1':
          browseMenu(conn, query);
          break;
        case '2':
          searchMenu(conn, query);
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

  private void searchMenu(Connection conn, Query query) throws SQLException {
    boolean quit = false;
    do {
      printSearchMenu();
      switch (getOption()) {
        case '1':
          println("case 1");
          break;
        case '2':
          statsMenu(conn, query);
          break;
        case '3':
          updatesMenu(conn, query);
          break;
        case 'q':
          quit = true;
          println("case 4");
          break;
        default:
          println("not a valid input");
          break;
      }
    } while (!quit);
  }

  private void browseMenu(Connection conn, Query query) throws SQLException  {
    boolean quit = false;
    do {
      printBrowseMenu();
      if (getOption() ==  'q') {
        quit = true;
      } else {
        println("not a valid input");
      }
    } while (!quit);
  }

  private void statsMenu(Connection conn, Query query) throws SQLException {
    boolean quit = false;
    printStatsMenu();
    do{
      switch (getOption()) {
        default:
          println(" Not a valid option ");
          break;
        case '1':
          println("case 1");
          query.playerRank();
          break;
        case '2':
          println("case 2");
          query.getTeamWins();
          break;
        case '3':
          println("case 3");
          query.getParticipation();
          break;
        case '4':
          quit = true;
          println("case 4");
  //          conn.close();   Not closing connection until user quits program in main menu
          break;
      }
  } while (!quit);

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

  private void updatesMenu(Connection conn, Query query) {
    boolean quit = false;
    do {
      printUpdatesMenu();
      switch (getOption()) {
        case '1':
          insertMenu(conn, query);
          break;
        case '2':
          deleteMenu(conn, query);
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

  private void insertMenu(Connection conn, Query query) {
    boolean quit = false;
    do {
      printInsertMenu();
      switch (getOption()) {
        case '1':
          try {
            query.insertPlayer();
          } catch (SQLException e) {
            System.out.println("Failed.");
          }
          break;
        case '2':
          try {
            query.insertCoach();
          } catch (SQLException e) {
            System.out.println("Failed.");
          }
          break;
        case '3':
          try {
            query.insertTeam();
          } catch (SQLException e) {
            System.out.println("Failed.");
          }
          break;
        case 'q':
          quit = true;
          break;
        default:
          println("Not an option.");
          break;
      }
    } while (!quit);
  }

  private void deleteMenu(Connection conn, Query query) {
    boolean quit = false;
    do {
      printDeleteMenu();
      switch (getOption()) {
        case '1':
          try {
            query.deletePlayer();
          } catch (SQLException e) {
            System.out.println("Failed.");
          }
          break;
        case '2':
          try {
            query.deleteCoach();
          } catch (SQLException e) {
            System.out.println("Failed.");
          }
          break;
        case '3':
          try {
            query.deleteTeam();
          } catch (SQLException e) {
            System.out.println("Failed.");
          }
          break;
        case 'q':
          quit = true;
          break;
        default:
          println("Not an option.");
          break;
      }
    } while (!quit);
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
    println("                   1. Browse Nominees");
    println("                   2. Search Nominees");
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
    println("                          q. Back");
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

  private void print(Object s) {
    System.out.print(s);
  }

  private void println(Object s) {
    System.out.println(s);
  }
}