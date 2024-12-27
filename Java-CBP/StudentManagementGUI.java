import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import java.math.BigDecimal;

public class StudentManagementGUI {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "SYSTEM";
    private static final String PASSWORD = "SYSTEM";
    private static Connection connection = null;

    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database!");

            SwingUtilities.invokeLater(StudentManagementGUI::createAndShowGUI);

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Failed: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Student Management System", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(Color.decode("#B9E5E8")); // Header background
        headerLabel.setForeground(Color.BLACK); // Header text color
        frame.add(headerLabel, BorderLayout.NORTH);

        // Left Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Vertical button layout
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        buttonPanel.setBackground(Color.decode("#7AB2D3")); // Button panel background

        // Buttons with reduced size and sleek design
        JButton registerButton = createButton("Register Student");
        JButton updateButton = createButton("Update Information");
        JButton viewButton = createButton("View Students");
        JButton attendanceButton = createButton("Manage Attendance");
        JButton reportButton = createButton("View Attendance");
        JButton exitButton = createButton("Exit");

        // Adding buttons to the panel
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(updateButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(viewButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(attendanceButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(reportButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(exitButton);

        // Adding action listeners to buttons
        registerButton.addActionListener(e -> showRegisterStudentDialog(frame));
        updateButton.addActionListener(e -> showUpdateStudentDialog(frame));
        viewButton.addActionListener(e -> showViewStudentDialog(frame));
        attendanceButton.addActionListener(e -> showManageAttendanceDialog(frame));
        reportButton.addActionListener(e -> showGenerateReportsDialog(frame));
        exitButton.addActionListener(e -> {
            try {
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        });

        // Right Panel for image
        ImageIcon icon = new ImageIcon("C:/Users/sripa/Downloads/Project.jpg");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(750, 600, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(icon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        // Split Pane to show buttons on the left and image on the right
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buttonPanel, imageLabel);
        splitPane.setDividerLocation(250); // Divide the pane

        frame.add(splitPane, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setBackground(Color.decode("#B9E5E8")); // Footer background
        JLabel footerLabel = new JLabel("<html><center><font color='black'>Student Management System - Manage student data efficiently.</font></center></html>");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        footerPanel.add(footerLabel, BorderLayout.CENTER);

        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null); // Center window
        frame.setVisible(true);
    }

    // Custom button creation
    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(Color.decode("#4A628A")); // Button background
        button.setForeground(Color.WHITE); // Button text
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setPreferredSize(new Dimension(200, 40)); // Button size

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode("#3B516E")); // Darker shade on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.decode("#4A628A")); // Reset to default
            }
        });

        return button;
    }
    // Register student dialog
    private static void showRegisterStudentDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Register Student", true);
        dialog.setSize(600, 400);
        dialog.setLayout(new BorderLayout());

        // Input fields for student details
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nameField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField dojField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField branchField = new JTextField();
        JTextField yearField = new JTextField();

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("DOB (YYYY-MM-DD):"));
        inputPanel.add(dobField);
        inputPanel.add(new JLabel("DOJ (YYYY-MM-DD):"));
        inputPanel.add(dojField);
        inputPanel.add(new JLabel("Contact:"));
        inputPanel.add(contactField);
        inputPanel.add(new JLabel("Branch:"));
        inputPanel.add(branchField);
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);

        dialog.add(inputPanel, BorderLayout.CENTER);

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            try {
                String sql = "INSERT INTO STUDENT (NAME, DOB, DOJ, CONTACT, BRANCH, YEAR) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, nameField.getText());
                stmt.setString(2, dobField.getText());
                stmt.setString(3, dojField.getText());
                stmt.setString(4, contactField.getText());
                stmt.setString(5, branchField.getText());
                stmt.setInt(6, Integer.parseInt(yearField.getText()));

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(dialog, "Student Registered Successfully!");
                    dialog.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(registerButton, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    // Update student dialog
    private static void showUpdateStudentDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Update Student Information", true);
        dialog.setSize(600, 400);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField rollNoField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField yearField = new JTextField();

        inputPanel.add(new JLabel("Roll No:"));
        inputPanel.add(rollNoField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Contact:"));
        inputPanel.add(contactField);
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);

        dialog.add(inputPanel, BorderLayout.CENTER);

        // Update button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            try {
                String sql = "UPDATE STUDENT SET NAME = ?, CONTACT = ?, YEAR = ? WHERE ROLLNO = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, nameField.getText());
                stmt.setString(2, contactField.getText());
                stmt.setInt(3, Integer.parseInt(yearField.getText()));
                stmt.setInt(4, Integer.parseInt(rollNoField.getText()));

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(dialog, "Student Information Updated Successfully!");
                    dialog.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(updateButton, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    // View students dialog
    private static void showViewStudentDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "View Student Details", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new BorderLayout());
    
        // Input panel for entering roll number or name
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        JLabel rollNoLabel = new JLabel("Enter Roll No:");
        JTextField rollNoField = new JTextField();
        JLabel nameLabel = new JLabel("Enter Name:");
        JTextField nameField = new JTextField();
    
        inputPanel.add(rollNoLabel);
        inputPanel.add(rollNoField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
    
        dialog.add(inputPanel, BorderLayout.NORTH);
    
        // Panel to display student details
        JTextArea studentDetailsArea = new JTextArea();
        studentDetailsArea.setEditable(false);
        studentDetailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        studentDetailsArea.setBorder(BorderFactory.createTitledBorder("Student Details"));
    
        dialog.add(new JScrollPane(studentDetailsArea), BorderLayout.CENTER);
    
        // Button to fetch data
        JButton fetchButton = new JButton("Fetch Details");
        fetchButton.addActionListener(e -> {
            String rollNo = rollNoField.getText().trim();
            String name = nameField.getText().trim();
    
            if (rollNo.isEmpty() && name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter either Roll No or Name.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            String sql = "SELECT * FROM STUDENT WHERE ";
            if (!rollNo.isEmpty()) {
                sql += "ROLLNO = ?";
            } else {
                sql += "NAME = ?";
            }
    
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, !rollNo.isEmpty() ? rollNo : name);
    
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        StringBuilder details = new StringBuilder();
                        details.append("Roll No: ").append(rs.getInt("ROLLNO")).append("\n");
                        details.append("Name: ").append(rs.getString("NAME")).append("\n");
                        details.append("DOB: ").append(rs.getDate("DOB")).append("\n");
                        details.append("DOJ: ").append(rs.getDate("DOJ")).append("\n");
                        details.append("Contact: ").append(rs.getString("CONTACT")).append("\n");
                        details.append("Branch: ").append(rs.getString("BRANCH")).append("\n");
                        details.append("Year: ").append(rs.getInt("YEAR")).append("\n");
    
                        studentDetailsArea.setText(details.toString());
                    } else {
                        studentDetailsArea.setText("No student found with the given Roll No or Name.");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        dialog.add(fetchButton, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
    

    // Manage attendance dialog
    private static void showManageAttendanceDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Manage Attendance", true);
        dialog.setSize(600, 400);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField rollNoField = new JTextField();
        JTextField percentageField = new JTextField();

        inputPanel.add(new JLabel("Roll No:"));
        inputPanel.add(rollNoField);
        inputPanel.add(new JLabel("Attendance Percentage:"));
        inputPanel.add(percentageField);

        dialog.add(inputPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Update Attendance");
        submitButton.addActionListener(e -> {
            try {
                String sql = "UPDATE ATTENDANCE SET PERCENTAGE = ? WHERE ROLLNO = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setBigDecimal(1, new BigDecimal(percentageField.getText()));
                stmt.setInt(2, Integer.parseInt(rollNoField.getText()));
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(dialog, "Attendance Updated Successfully!");
                }
            } catch (SQLException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(submitButton, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    // Generate reports dialog
    private static void showGenerateReportsDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Generate Reports", true);
        dialog.setSize(800, 600);
    
        String[] columnNames = {"Roll No", "Name", "Attendance Percentage"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    
        try {
            // SQL query to retrieve Roll No, Name, and Attendance percentage
            String sql = "SELECT S.ROLLNO, S.NAME, A.PERCENTAGE " +
                         "FROM STUDENT S " +
                         "JOIN ATTENDANCE A ON S.ROLLNO = A.ROLLNO";
            
            // Execute the query
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
    
            // Process the result set
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("ROLLNO"));             // Roll number
                row.add(rs.getString("NAME"));            // Name
                row.add(rs.getBigDecimal("PERCENTAGE"));  // Attendance percentage
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    
        // Create the table and wrap it in a scroll pane
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);
    
        dialog.setLocationRelativeTo(parent);  // Center the dialog relative to the parent window
        dialog.setVisible(true);               // Show the dialog
    }
    

}
