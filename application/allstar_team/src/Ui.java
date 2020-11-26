import java.sql.*;
import java.io.*;

public class Ui {

    public Ui(){}

    public static void makeConnection(){
        Connection conn = null;
        try {
            //Step 1: Load the JDBC driver(You have to have the connector Jar file in your project Class path)

            Class.forName("com.mysql.cj.jdbc.Driver");

            //Connect to the database(Change the URL)

            String url = "jdbc:mysql://cs331.chhxghxty6xs.us-west-2.rds.amazonaws.com:3306/Allstar_Team?serverTimezone=UTC&useSSL=TRUE";
            String user, pass;
            user = "javaApp";
            pass = "GiveUsAnAPlease!100%";
            conn = DriverManager.getConnection(url, user, pass);

            boolean done = false;
            do {
                printMenu();
                System.out.print("Type in your option: ");
                System.out.flush();
                String ch = readLine();
                System.out.println();
                switch (ch.charAt(0)) {
                    case '1':;
                        System.out.println("case 1");
                        break;
                    case '2':
                        System.out.println("case 2");
                        break;
                    case '3':
                        System.out.println("case 3");
                        break;
                    case '4': done = true;
                        System.out.println("case 4");
                        conn.close();
                        break;
                    default:
                        System.out.println(" Not a valid option ");
                } //switch
            } while (!done);


        } catch (ClassNotFoundException e) {
            System.out.println("Could not load the driver");
        } catch (SQLException ex) {
            System.out.println(ex);
//        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        }
    }

    private static String readEntry(String prompt) {
        try {
            StringBuffer buffer = new StringBuffer();
            System.out.print(prompt);
            System.out.flush();
            int c = System.in.read();
            while(c != '\n' && c != -1) {
                buffer.append((char)c);
                c = System.in.read();
            }
            return buffer.toString().trim();
        } catch (IOException e) {
            return "";
        }
    }

    private static String readLine() {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr, 1);
        String line = "";

        try {
            line = br.readLine();
        } catch (IOException e) {
            System.out.println("Error in SimpleIO.readLine: " +
                    "IOException was thrown");
            System.exit(1);
        }
        return line;
    }

    private static void printMenu() {
        System.out.println("***********************************************************");
        System.out.println("   Welcome to Selecting an All-Star Team Application   ");
        System.out.println("***********************************************************");
        System.out.println("1. Score");
        System.out.println("2. Wins per team");
        System.out.println("3. Championship participation");
        System.out.println("4. Quit");
    }
}
