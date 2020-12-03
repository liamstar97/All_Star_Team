import java.sql.*;
import java.io.*;

public class Ui {

  Connection CONNECTION = null;

  public Ui() throws SQLException{
    CONNECTION = login();
    mainMenu();
  }

  public Connection getConnection() {
    return CONNECTION;
  }

  private Connection login() throws SQLException{ //TODO: change to user input before turning in, and demo
    String url = "jdbc:mysql://cs331.chhxghxty6xs.us-west-2.rds.amazonaws.com:3306/Allstar_Team?serverTimezone=UTC&useSSL=TRUE";
    String user, pass;
    user = "javaApp";
    pass = "GiveUsAnAPlease!100%";
    return DriverManager.getConnection(url, user, pass);
  }

  private void mainMenu() throws SQLException {
    Connection conn = CONNECTION;
    boolean quit = false;
    do {
      printMainMenu();
      switch (getOption()) {
        case '1':
          browseAndSearchMenu(conn);
          break;
        case '2':
          printSearchMenu();
          break;
        case '3':
          println("case 3");
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

  private void browseAndSearchMenu(Connection conn) throws SQLException {
    boolean quit = false;
    do {
      printBrowseAndSearchMenu();
      switch (getOption()) {
        case '1':
          browseMenu(conn);
          break;
        case '2':
          searchMenu(conn);
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

  private void searchMenu(Connection conn) throws SQLException {
    boolean quit = false;
    do {
      printSearchMenu();
      switch (getOption()) {
        case '1':
          println("case 1");
          break;
        case '2':
          printSearchMenu();
          break;
        case '3':
          println("case 3");
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

  private void browseMenu(Connection conn) throws SQLException  {
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

  private void printMainMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("***********************************************************");
    println("1. Score");
    println("2. Wins per team");
    println("3. Championship participation");
    println("q. Quit");
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

  private void print(Object s) {
    System.out.print(s);
  }

  private void println(Object s) {
    System.out.println(s);
  }
}