import java.sql.*;
import java.io.*;

public class allstar_team {
  public static void main(String args[]) {
    Ui allstarTeam = new Ui();
    allstarTeam.makeConnection();
  }
}
//
//    private static void findHighestPaid(Connection conn) throws SQLException, IOException {
//
//            /*In this method your SQL Query should return the ssn, Lname, FirstName and Salay for all employees
//            ordered by the highest salary */
//
//        //STEP1: CREATE VARIABLE OF TYPE STATEMENT
//        Statement stmt = conn.createStatement();
//        // STEP 2 DEFINE A STRING THAT IS = TO YOUR query SQL Statement
//        String query = "SELECT lname, fname, salary " + "FROM employee " + "ORDER BY salary DESC " + "LIMIT 10";
//
//        // Step 3: Declare a variable with ResultSet type
//        ResultSet rset = stmt.executeQuery(query);
//        //Execute your Query and store the return in the declared variable from step 3
//
//        System.out.println("    HIGHEST PAID WORKERS");
//        System.out.println("--------------------------------------------------\n");
//
//        // Write a loop to read all the returned rows from the query execution
//        while(rset.next()){
//            String fname = rset.getString(1);
//            String lname = rset.getString(2);
//            double salary = rset.getDouble(3);
//            System.out.println(fname + " " + lname + " " + salary);
//        }
//    }
//
//
//    private static void findMostWorked(Connection conn) throws SQLException, IOException {
//            /*In this method your Query should return the employees SSN,fname and Lname and Total work hours
//            ordered by the most worked first, limit your answer for the highest 5 employees */
//
//        //STEP1: CREATE VARIABLE OF TYPE STATEMENT
//        Statement stmt = conn.createStatement();
//        // STEP 2 DEFINE A STRING THAT IS = TO YOUR query SQL Statement
//        String query = "SELECT ssn, lname, fname, hours " + "FROM employee JOIN works_on WHERE ssn = essn " + "ORDER BY hours DESC " + "LIMIT 5";
//
//        // Complete this method following the same steps above to return the required information
//        // Step 3: Declare a variable with ResultSet type
//        ResultSet rset = stmt.executeQuery(query);
//        //Execute your Query and store the return in the declared variable from step 3
//
//        System.out.println("    MOST WORKED WORKERS");
//        System.out.println("--------------------------------------------------\n");
//
//        // Write a loop to read all the returned rows from the query execution
//        while(rset.next()){
//            String ssn = rset.getString(1);
//            String fname = rset.getString(2);
//            String lname = rset.getString(3);
//            double hours = rset.getDouble(4);
//            System.out.println(ssn + " " + fname + " " + lname + " " + hours);
//        }
//
//    }
//
//    private static void departmentEmployees(Connection conn) throws SQLException, IOException {
//            /*In this method your Query should
//            Prompt the user for a department number information, Given a department number Print the last name and salary
//            of all employees working for the department*/
//
//        String query = "SELECT lname, salary FROM employee WHERE dno = ?;";
//        PreparedStatement preparedStatement = conn.prepareStatement(query);
//
//        String dno = "5";//readEntry("Please enter a department number: ");
//        preparedStatement.clearParameters();
//        preparedStatement.setString(1, dno);
//
//        // Complete this method following the same steps above to return the required information
//        ResultSet rset = preparedStatement.executeQuery();
//        //Execute your Query and store the return in the declared variable from step 3
//
//        System.out.println("    WORKERS FROM DEPARTMENT " + dno);
//        System.out.println("--------------------------------------------------\n");
//
//        // Write a loop to read all the returned rows from the query execution
//        while(rset.next()){
//            String lname = rset.getString(1);
//            double salary = rset.getDouble(2);
//            System.out.println(lname + " " + salary);
//        }
//    }
//
//
//}
