import java.sql.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ui {

  public Ui() {
  }

  public void makeConnection() { //TODO: change to user input before turning in, and demo
    Connection conn = null;
    try {
      // Load the JDBC driver
      Class.forName("com.mysql.cj.jdbc.Driver");
      // Connect to the database
      String url = "jdbc:mysql://cs331.chhxghxty6xs.us-west-2.rds.amazonaws.com:3306/Allstar_Team?serverTimezone=UTC&useSSL=TRUE";
      String user, pass;
      user = "javaApp";
      pass = "GiveUsAnAPlease!100%";
      conn = DriverManager.getConnection(url, user, pass);
      // print menu, and get user input
      mainMenu(conn);
    } catch (ClassNotFoundException e) {
      println("Could not load the driver");
    } catch (SQLException ex) {
      println(ex);
//        } catch (IOException e) {
//            e.printStackTrace();
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          println(e);
        }
      }
    }
  }

  private void mainMenu(Connection conn) throws SQLException {
    boolean quit = false;

    do {
      printMainMenu();
      println("Enter the number of your selection... ");
      System.out.flush();
      String input = readLine();
      char[] inputArray = input.toCharArray();
      char userInputFinal = inputArray[0];
      switch (userInputFinal) {
        default:
          println(" Not a valid option ");
          break;
        case '1':
          println("case 1");
          break;
        case '2':
          statsMenu(conn);
          break;
        case '3':
          updatesMenu(conn);
          break;
        case '4':
          quit = true;
          println("case 4");
          conn.close();
          break;
      }
    } while (!quit);
  }

  private void statsMenu(Connection conn) throws SQLException {
    boolean quit = false;

    printStatsMenu();

    do{

      println("Enter the number of your selection... ");
      System.out.flush();
      String input = readLine();
      char[] inputArray = input.toCharArray();
      char userInputFinal = inputArray[0];

      Query statsQueries = new Query(conn);

      switch (userInputFinal) {
        default:
          println(" Not a valid option ");
          break;
        case '1':
          println("case 1");
          statsQueries.playerRank();
          break;
        case '2':
          println("case 2");
          statsQueries.getTeamWins();
          break;
        case '3':
          println("case 3");
          statsQueries.getParticipation();
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

  private void updatesMenu(Connection conn) {
    boolean quit = false;
    do {
      printUpdatesMenu();
      System.out.println();
      System.out.flush();
      String ch = readLine();
      if (ch.toCharArray().length > 1) {
        ch = "0";
      }
      switch (ch.charAt(0)) {
        case '1':
          boolean quitInsert = false;
          do {
            printInsertMenu();
            System.out.println();
            System.out.flush();
            String insert = readLine();
            if (insert.toCharArray().length > 1) {
              insert = "0";
            }
            Query insertQuery = new Query(conn);
            switch (insert.charAt(0)) {
              case '1':
                try {
                  insertQuery.insertPlayer();
                } catch (SQLException e) {
                  System.out.println("Failed.");
                }
                break;
              case '2':
                try {
                  insertQuery.insertCoach();
                } catch (SQLException e) {
                  System.out.println("Failed.");
                }
                break;
              case '3':
                try {
                  insertQuery.insertTeam();
                } catch (SQLException e) {
                  System.out.println("Failed.");
                }
                break;
              case '4':
                quitInsert = true;
                break;
              default:
                println("Not an option.");
                break;
            }
          } while (!quitInsert);
          break;
        case '2':
          boolean quitDelete = false;
          do {
            System.out.println();
            printDeleteMenu();
            System.out.println();
            System.out.flush();
            String insert = readLine();
            if (insert.toCharArray().length > 1) {
              insert = "0";
            }
            Query deleteQuery = new Query(conn);
            switch (insert.charAt(0)) {
              case '1':
                try {
                  deleteQuery.deletePlayer();
                } catch (SQLException e) {
                  System.out.println("Failed.");
                }
                break;
              case '2':
                try {
                  deleteQuery.deleteCoach();
                } catch (SQLException e) {
                  System.out.println("Failed.");
                }
                break;
              case '3':
                try {
                  deleteQuery.deleteTeam();
                } catch (SQLException e) {
                  System.out.println("Failed.");
                }
                break;
              case '4':
                quitDelete = true;
                break;
              default:
                println("Not an option.");
                break;
            }
          } while (!quitDelete);
          break;
        case '3':
          quit = true;
          break;
        default:
          System.out.println("Not an option.");
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
    println("                         4. Quit");
  }

  private void printStatsMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("***********************************************************");
    println("1. Score");
    println("2. Wins per team");
    println("3. Championship participation");
    println("4. Quit");
  }

  private void printSearchMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("               2. Statistics & Data Mining                 ");
    println("***********************************************************");
    println("1. Score");
    println("2. Wins per team");
    println("3. Championship participation");
    println("4. Quit");
  }

  private void printUpdatesMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("                       3. Updates                          ");
    println("***********************************************************");
    println("1. Insert New Information");
    println("2. Delete Information");
    println("3. Return to Main Menu");
  }

  private void printInsertMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("              3. Updates - Insert Information              ");
    println("***********************************************************");
    println("1. Add a New Player");
    println("2. Add a New Coach");
    println("3. Add a New Team");
    println("4. Return to Updates Menu.");
  }

  private void printDeleteMenu() {
    println("***********************************************************");
    println("            Select an All-Star Team Application            ");
    println("              3. Updates - Delete Information              ");
    println("***********************************************************");
    println("1. Delete a Specific Player");
    println("2. Delete a Specific Coach");
    println("3. Delete a Specific Team");
    println("4. Return to Updates Menu.");
  }

  private void print(Object s) {
    System.out.print(s);
  }

  private void println(Object s) {
    System.out.println(s);
  }
}