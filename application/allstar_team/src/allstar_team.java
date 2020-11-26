import java.sql.*;
import java.io.*;

public class allstar_team {
    public static void main(String args[]) {
        Connection conn = null;
        try {
            //Step 1: Load the JDBC driver(You have to have the connector Jar file in your project Class path)

            Class.forName("com.mysql.cj.jdbc.Driver");

            //Connect to the database(Change the URL)

            String url = "jdbc:mysql://cs331.chhxghxty6xs.us-west-2.rds.amazonaws.com:3306/Company?serverTimezone=UTC&useSSL=TRUE";
            String user, pass;
            user = readEntry("userid : ");
            pass = readEntry("password: ");
            conn = DriverManager.getConnection(url, user, pass);

//                Statement stmt = conn.createStatement();
//                String newPass = "SET PASSWORD FOR 'matt'@'%' = 'password'";
//                stmt.executeQuery(newPass);


            boolean done = false;
            do {
                printMenu();
                System.out.print("Type in your option: ");
                System.out.flush();
                String ch = readLine();
                System.out.println();
                switch (ch.charAt(0)) {
                    case 'a': findHighestPaid(conn);
                        break;
                    case 'b':
                        findMostWorked(conn);
                        break;
                    case 'c':
                        departmentEmployees(conn);
                        break;
                    case 'q': done = true;
                        //Close the statement
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
        } catch (IOException e) {
            e.printStackTrace();
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

    private static void findHighestPaid(Connection conn) throws SQLException, IOException {

            /*In this method your SQL Query should return the ssn, Lname, FirstName and Salay for all employees
            ordered by the highest salary */

        //STEP1: CREATE VARIABLE OF TYPE STATEMENT
        Statement stmt = conn.createStatement();
        // STEP 2 DEFINE A STRING THAT IS = TO YOUR query SQL Statement
        String query = "SELECT lname, fname, salary " + "FROM employee " + "ORDER BY salary DESC " + "LIMIT 10";

        // Step 3: Declare a variable with ResultSet type
        ResultSet rset = stmt.executeQuery(query);
        //Execute your Query and store the return in the declared variable from step 3

        System.out.println("    HIGHEST PAID WORKERS");
        System.out.println("--------------------------------------------------\n");

        // Write a loop to read all the returned rows from the query execution
        while(rset.next()){
            String fname = rset.getString(1);
            String lname = rset.getString(2);
            double salary = rset.getDouble(3);
            System.out.println(fname + " " + lname + " " + salary);
        }
    }


    private static void findMostWorked(Connection conn) throws SQLException, IOException {
            /*In this method your Query should return the employees SSN,fname and Lname and Total work hours
            ordered by the most worked first, limit your answer for the highest 5 employees */

        //STEP1: CREATE VARIABLE OF TYPE STATEMENT
        Statement stmt = conn.createStatement();
        // STEP 2 DEFINE A STRING THAT IS = TO YOUR query SQL Statement
        String query = "SELECT ssn, lname, fname, hours " + "FROM employee JOIN works_on WHERE ssn = essn " + "ORDER BY hours DESC " + "LIMIT 5";

        // Complete this method following the same steps above to return the required information
        // Step 3: Declare a variable with ResultSet type
        ResultSet rset = stmt.executeQuery(query);
        //Execute your Query and store the return in the declared variable from step 3

        System.out.println("    MOST WORKED WORKERS");
        System.out.println("--------------------------------------------------\n");

        // Write a loop to read all the returned rows from the query execution
        while(rset.next()){
            String ssn = rset.getString(1);
            String fname = rset.getString(2);
            String lname = rset.getString(3);
            double hours = rset.getDouble(4);
            System.out.println(ssn + " " + fname + " " + lname + " " + hours);
        }

    }

    private static void departmentEmployees(Connection conn) throws SQLException, IOException {
            /*In this method your Query should
            Prompt the user for a department number information, Given a department number Print the last name and salary
            of all employees working for the department*/

        String query = "SELECT lname, salary FROM employee WHERE dno = ?;";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        String dno = readEntry("Please enter a department number: ");
        preparedStatement.clearParameters();
        preparedStatement.setString(1, dno);

        // Complete this method following the same steps above to return the required information
        ResultSet rset = preparedStatement.executeQuery();
        //Execute your Query and store the return in the declared variable from step 3

        System.out.println("    WORKERS FROM DEPARTMENT " + dno);
        System.out.println("--------------------------------------------------\n");

        // Write a loop to read all the returned rows from the query execution
        while(rset.next()){
            String lname = rset.getString(1);
            double salary = rset.getDouble(2);
            System.out.println(lname + " " + salary);
        }
    }

    static String readEntry(String prompt) {
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
        System.out.println("\n        QUERY OPTIONS ");
        System.out.println("(a) Find Highest paid workers. ");
        System.out.println("(b) Find the most worked workers. ");
        System.out.println("(c) Find the employees information for a given department");
        System.out.println("(q) Quit. \n");
    }

}
