import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateHRAssistantAccount {
    public static void main(String[] args) {
        JFrame frame = new JFrame("HR Assistant Account Creation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setBounds(50, 10, 100, 30);
        frame.add(userIdLabel);

        JTextField userIdField = new JTextField();
        userIdField.setBounds(150, 10, 200, 30);
        frame.add(userIdField);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 50, 100, 30);
        frame.add(firstNameLabel);

        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(150, 50, 200, 30);
        frame.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 90, 100, 30);
        frame.add(lastNameLabel);

        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(150, 90, 200, 30);
        frame.add(lastNameField);

        JLabel usernameLabel = new JLabel("User Name:");
        usernameLabel.setBounds(50, 130, 100, 30);
        frame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 130, 200, 30);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 170, 100, 30);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 170, 200, 30);
        frame.add(passwordField);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(50, 210, 150, 30);
        frame.add(createAccountButton);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save user details to the database
                String enteredUserId = userIdField.getText();
                String enteredFirstName = firstNameField.getText();
                String enteredLastName = lastNameField.getText();
                String enteredUsername = usernameField.getText();
                String enteredPassword = new String(passwordField.getPassword());

                if (saveAccountToDatabase(enteredUserId, enteredFirstName, enteredLastName, enteredUsername, enteredPassword)) {
                    JOptionPane.showMessageDialog(frame, "Account created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to create account. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(220, 210, 150, 30);
        frame.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handles the back action here (e.g., open the HRManager class)
                frame.dispose();
                HRManager.main(new String[]{});
            }
        });

        frame.setVisible(true);
    }

    private static boolean saveAccountToDatabase(String userId, String firstName, String lastName, String username, String password) {
        String url = "jdbc:mysql://localhost:3308/thetechcompanydb";
        String user = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            String query = "INSERT INTO users (UserID, FirstName, LastName, UserName, Password) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, userId);
                preparedStatement.setString(2, firstName);
                preparedStatement.setString(3, lastName);
                preparedStatement.setString(4, username);
                preparedStatement.setString(5, password);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
