import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddNewDesignation {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Add New Designation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel label = new JLabel("Designation Name: ");
        label.setBounds(20, 20, 150, 24);
        panel.add(label);

        JTextField textField = new JTextField();
        textField.setBounds(150, 20, 150, 24);
        panel.add(textField);

        JButton addDesignationButton = new JButton("Add Designation");
        addDesignationButton.setBounds(20, 60, 150, 30);
        panel.add(addDesignationButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(180, 60, 150, 30);
        panel.add(clearButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(20, 100, 150, 30);
        panel.add(backButton);

        addDesignationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the entered designation name
                String designationName = textField.getText();

                // Validate that the designation name is not empty
                if (designationName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a designation name.");
                    return;
                }

                // Insert the designation name into the database
                if (insertDesignation(designationName)) {
                    JOptionPane.showMessageDialog(frame, "Designation added successfully");
                    // Clear the text field after successful insertion
                    textField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to add designation. Please try again.");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear all the filled text fields
                textField.setText("");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the JFrame
                HRManager.main(new String[]{}); // Open the HRManager class
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    // Insert designation into the database
    private static boolean insertDesignation(String designationName) {
        String url = "jdbc:mysql://localhost:3308/thetechcompanydb";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO designation (DesignationName) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, designationName);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
