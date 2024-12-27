import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class JDBC_Connect {
    public static void main(String[] args) {
        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish the connection
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "SYSTEM");

            // Create a Statement object
            Statement smt = con.createStatement();

            // Execute an SQL command to create a table
            smt.executeUpdate("CREATE TABLE Students(name VARCHAR(50), rollno NUMBER)");

            // Close the statement and connection
            smt.close();
            con.close();

            System.out.println("Table created successfully!");

        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}
