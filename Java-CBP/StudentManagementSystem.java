import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "SYSTEM";
    private static final String PASSWORD = "SYSTEM";

    private static Connection connection = null;

    public static void main(String[] args) {

        // Try to load Oracle JDBC Driver and establish a connection
        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the Oracle database successfully!");

            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("\n--- Student Management System ---");
                System.out.println("1. Register Student");
                System.out.println("2. Update Student Information");
                System.out.println("3. View Student Details");
                System.out.println("4. Manage Attendance");
                System.out.println("5. View Attendance");
                System.out.println("6. Generate Reports");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        registerStudent(scanner);
                        break;
                    case 2:
                        updateStudentInfo(scanner);
                        break;
                    case 3:
                        viewStudentDetails(scanner);
                        break;
                    case 4:
                        manageAttendance(scanner);
                        break;
                    case 5:
                        gradeAnalysis(scanner);
                        break;
                    case 6:
                        generateReports();
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 7);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close connection after all operations are completed
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void registerStudent(Scanner scanner) {
        try {
            System.out.print("Enter Roll No: ");
            int rollNo = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            String dob = scanner.nextLine();
            System.out.print("Enter Date of Joining (YYYY-MM-DD): ");
            String doj = scanner.nextLine();
            System.out.print("Enter Contact: ");
            String contact = scanner.nextLine();
            System.out.print("Enter Gender (M/F): ");
            String gender = scanner.nextLine();
            System.out.print("Enter Branch: ");
            String branch = scanner.nextLine();
            System.out.print("Enter Address ID: ");
            int addressId = scanner.nextInt();
            System.out.print("Enter Training ID: ");
            int trainingId = scanner.nextInt();
            System.out.print("Enter Year: ");
            int year = scanner.nextInt();

            String sql = "INSERT INTO STUDENT (ROLLNO, NAME, DOB, DOJ, CONTACT, GENDER, BRANCH, ADDRESS_ID, TRAINING_ID, YEAR) " +
                         "VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, rollNo);
            statement.setString(2, name);
            statement.setString(3, dob);
            statement.setString(4, doj);
            statement.setString(5, contact);
            statement.setString(6, gender);
            statement.setString(7, branch);
            statement.setInt(8, addressId);
            statement.setInt(9, trainingId);
            statement.setInt(10, year);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student registered successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateStudentInfo(Scanner scanner) {
        try {
            System.out.print("Enter Roll No to update: ");
            int rollNo = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter field to update (Name/Branch/Year/Contact/Training_ID): ");
            String field = scanner.nextLine();
            System.out.print("Enter new value: ");
            String newValue = scanner.nextLine();

            String sql = "UPDATE STUDENT SET " + field.toUpperCase() + " = ? WHERE ROLLNO = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            if (field.equalsIgnoreCase("Year") || field.equalsIgnoreCase("Training_ID")) {
                statement.setInt(1, Integer.parseInt(newValue));
            } else {
                statement.setString(1, newValue);
            }
            statement.setInt(2, rollNo);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student information updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewStudentDetails(Scanner scanner) {
        try {
            System.out.print("Enter Roll No to view details: ");
            int rollNo = scanner.nextInt();

            String sql = "SELECT NAME, DOB, DOJ, CONTACT, GENDER, BRANCH, ADDRESS_ID, TRAINING_ID, YEAR FROM STUDENT WHERE ROLLNO = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, rollNo);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("\n--- Student Details ---");
                System.out.println("Name: " + resultSet.getString("NAME"));
                System.out.println("Date of Birth: " + resultSet.getDate("DOB"));
                System.out.println("Date of Joining: " + resultSet.getDate("DOJ"));
                System.out.println("Contact: " + resultSet.getString("CONTACT"));
                System.out.println("Gender: " + resultSet.getString("GENDER"));
                System.out.println("Branch: " + resultSet.getString("BRANCH"));
                System.out.println("Address ID: " + resultSet.getInt("ADDRESS_ID"));
                System.out.println("Training ID: " + resultSet.getInt("TRAINING_ID"));
                System.out.println("Year: " + resultSet.getInt("YEAR"));
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void manageAttendance(Scanner scanner) {
        try {
            System.out.print("Enter Roll No to update attendance: ");
            int rollNo = scanner.nextInt();
            System.out.print("Enter new Attendance Percentage: ");
            double percentage = scanner.nextDouble();

            String sql = "UPDATE ATTENDANCE SET PERCENTAGE = ? WHERE ROLLNO = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, percentage);
            statement.setInt(2, rollNo);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Attendance updated successfully!");
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void gradeAnalysis(Scanner scanner) {
        try {
            System.out.print("Enter Roll No to view attendance: ");
            int rollNo = scanner.nextInt();  // Get the roll number from the user
    
            // Use a PreparedStatement with a parameterized query
            String sql = "SELECT s.NAME, s.ROLLNO, a.PERCENTAGE FROM student s " +
                         "JOIN attendance a ON s.ROLLNO = a.ROLLNO " + // Join the tables based on ROLLNO
                         "WHERE s.ROLLNO = ?";  // Use ? as a placeholder for the roll number
    
            // Prepare the statement
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, rollNo);  // Set the roll number parameter to the user input
    
            // Execute the query
            ResultSet resultSet = statement.executeQuery();
    
            System.out.println("\n--- View Attendance ---");
            if (resultSet.next()) {
                // Display the results
                System.out.println("Name: " + resultSet.getString("NAME") +
                                   ", Roll No: " + resultSet.getInt("ROLLNO") +
                                   ", Attendance: " + resultSet.getDouble("PERCENTAGE") + "%");
            } else {
                System.out.println("No attendance record found for Roll No: " + rollNo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private static void generateReports() {
        try {
            String sql = "SELECT * FROM STUDENT";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("\n--- Student Report ---");
            while (resultSet.next()) {
                System.out.println("Roll No: " + resultSet.getInt("ROLLNO") +
                                   ", Name: " + resultSet.getString("NAME") +
                                   ", DOB: " + resultSet.getDate("DOB") +
                                   ", DOJ: " + resultSet.getDate("DOJ") +
                                   ", Contact: " + resultSet.getString("CONTACT") +
                                   ", Gender: " + resultSet.getString("GENDER") +
                                   ", Branch: " + resultSet.getString("BRANCH") +
                                   ", Year: " + resultSet.getInt("YEAR"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
