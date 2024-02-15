import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageWithDB extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Connection connection;

    public LoginPageWithDB() {
        // Set up the frame
        setTitle("Login");
        setSize(400, 200); // Adjusted the frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JLabel welcomeLabel = new JLabel("WELCOME TO THE TECH COMPANY MANAGEMENT SYSTEM");
        welcomeLabel.setBounds(20, 10, 400, 24);
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        // Create a panel to hold components
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Set bounds for components
        welcomeLabel.setBounds(20, 10, 400, 24);
        usernameLabel.setBounds(20, 40, 80, 24);
        passwordLabel.setBounds(20, 70, 80, 24);
        usernameField.setBounds(120, 40, 200, 24);
        passwordField.setBounds(120, 70, 200, 24);
        loginButton.setBounds(120, 110, 100, 30);

        // Add components to the panel
        panel.add(welcomeLabel);
        panel.add(usernameLabel);
        panel.add(passwordLabel);
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(loginButton);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check the credentials against the database
                if (isValidLogin(username, password)) {
                    // Check the first three letters of the UserID
                    String firstThreeLetters = username.substring(0, 3);
                    if (firstThreeLetters.equalsIgnoreCase("MAN")) {
                        openHRManager();
                    } else if (firstThreeLetters.equalsIgnoreCase("ASS")) {
                        openHRManagerAssistant();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid role. Please check your credentials.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Please check your credentials.");
                }
            }
        });

        // Add panel to the frame
        add(panel);

        // Connect to the database
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/thetechcompanydb", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed.");
            System.exit(1);
        }

        // Display the frame
        setVisible(true);
    }

    // Validate the login credentials against the database
    private boolean isValidLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Check if a row was found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Open the HRManager class or perform other actions
    private void openHRManager() {
        // Replace with your code to open the HRManager class or perform other actions
        HRManager.main(new String[]{});
        dispose(); // Close the login frame after opening HRManager
    }

    // Open the HRManagerAssistant class or perform other actions
    private void openHRManagerAssistant() {
        // Replace with your code to open the HRManagerAssistant class or perform other actions
        HRManagerAssistant.main(new String[]{});
        dispose(); // Close the login frame after opening HRManagerAssistant
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginPageWithDB();
            }
        });
    }
}
