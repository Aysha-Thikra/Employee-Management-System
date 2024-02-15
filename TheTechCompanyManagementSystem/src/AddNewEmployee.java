import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class AddNewEmployee {
    private JFrame frame;
    private JTextField epfNumberField, epfNameField, epfAddressField, epfTelephoneField, emailField;
    private JComboBox<String> departmentComboBox, designationComboBox;

    public AddNewEmployee() {
        frame = new JFrame("Add New Employee");
        frame.setLayout(null);

        JLabel epfNumberLabel = new JLabel("EPF Number:");
        epfNumberLabel.setBounds(20, 20, 100, 25);
        frame.add(epfNumberLabel);

        epfNumberField = new JTextField();
        epfNumberField.setBounds(150, 20, 200, 25);
        frame.add(epfNumberField);

        JLabel epfNameLabel = new JLabel("EPF Name:");
        epfNameLabel.setBounds(20, 60, 100, 25);
        frame.add(epfNameLabel);

        epfNameField = new JTextField();
        epfNameField.setBounds(150, 60, 200, 25);
        frame.add(epfNameField);

        JLabel epfAddressLabel = new JLabel("EPF Address:");
        epfAddressLabel.setBounds(20, 100, 100, 25);
        frame.add(epfAddressLabel);

        epfAddressField = new JTextField();
        epfAddressField.setBounds(150, 100, 200, 25);
        frame.add(epfAddressField);

        JLabel epfTelephoneLabel = new JLabel("EPF Telephone:");
        epfTelephoneLabel.setBounds(20, 140, 120, 25);
        frame.add(epfTelephoneLabel);

        epfTelephoneField = new JTextField();
        epfTelephoneField.setBounds(150, 140, 200, 25);
        frame.add(epfTelephoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 180, 100, 25);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 180, 200, 25);
        frame.add(emailField);

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(20, 220, 100, 25);
        frame.add(departmentLabel);

        List<String> departmentOptions = retrieveOptions("jdbc:mysql://localhost:3308/thetechcompanydb", "department", "DepartmentName");
        String[] departments = departmentOptions.toArray(new String[0]);
        departmentComboBox = new JComboBox<>(departments);
        departmentComboBox.setBounds(150, 220, 200, 25);
        frame.add(departmentComboBox);

        JLabel designationLabel = new JLabel("Designation:");
        designationLabel.setBounds(20, 260, 100, 25);
        frame.add(designationLabel);

        List<String> designationOptions = retrieveOptions("jdbc:mysql://localhost:3308/thetechcompanydb", "designation", "DesignationName");
        String[] designations = designationOptions.toArray(new String[0]);
        designationComboBox = new JComboBox<>(designations);
        designationComboBox.setBounds(150, 260, 200, 25);
        frame.add(designationComboBox);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(20, 300, 100, 30);
        frame.add(submitButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(150, 300, 100, 30);
        frame.add(clearButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(280, 300, 100, 30);
        frame.add(backButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handles the submit action here
                if (saveEmployeeData()) {
                    JOptionPane.showMessageDialog(frame, "Employee data saved successfully");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to save employee data. Please try again.");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handles the clear action here
                clearTextFields();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handles the back action here (e.g., open HRManager)
                frame.dispose();
                HRManager.main(new String[]{});
            }
        });

        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private boolean saveEmployeeData() {
        String epfNumber = epfNumberField.getText();
        String epfName = epfNameField.getText();
        String epfAddress = epfAddressField.getText();
        String epfTelephone = epfTelephoneField.getText();
        String email = emailField.getText();
        String department = (String) departmentComboBox.getSelectedItem();
        String designation = (String) designationComboBox.getSelectedItem();

        // Validate that the required fields are not empty
        if (epfNumber.isEmpty() || epfName.isEmpty() || epfAddress.isEmpty() || epfTelephone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all required fields.");
            return false;
        }

        // Insert the employee data into the database
        String url = "jdbc:mysql://localhost:3308/thetechcompanydb";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Check if the department already exists, if not, add it
            if (!isDepartmentExists(connection, department)) {
                addDepartment(connection, department);
            }

            // Check if the designation already exists, if not, add it
            if (!isDesignationExists(connection, designation)) {
                addDesignation(connection, designation);
            }

            // Insert the new employee
            String query = "INSERT INTO employee (EPFNumber, EPFName, EPFAddress, EPFTelephoneNumber, EMail, Department, Designation) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, epfNumber);
                preparedStatement.setString(2, epfName);
                preparedStatement.setString(3, epfAddress);
                preparedStatement.setString(4, epfTelephone);
                preparedStatement.setString(5, email);
                preparedStatement.setString(6, department);
                preparedStatement.setString(7, designation);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean isDepartmentExists(Connection connection, String department) throws SQLException {
        String query = "SELECT * FROM department WHERE DepartmentName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, department);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void addDepartment(Connection connection, String department) throws SQLException {
        String insertQuery = "INSERT INTO department (DepartmentName) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, department);
            preparedStatement.executeUpdate();
        }
    }

    private boolean isDesignationExists(Connection connection, String designation) throws SQLException {
        String query = "SELECT * FROM designation WHERE DesignationName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, designation);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void addDesignation(Connection connection, String designation) throws SQLException {
        String insertQuery = "INSERT INTO designation (DesignationName) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, designation);
            preparedStatement.executeUpdate();
        }
    }

    private List<String> retrieveOptions(String url, String tableName, String columnName) {
        List<String> options = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, "root", "")) {
            String query = "SELECT " + columnName + " FROM " + tableName;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        options.add(resultSet.getString(columnName));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return options;
    }

    private void clearTextFields() {
        epfNumberField.setText("");
        epfNameField.setText("");
        epfAddressField.setText("");
        epfTelephoneField.setText("");
        emailField.setText("");
        departmentComboBox.setSelectedIndex(0); // Set to the first item
        designationComboBox.setSelectedIndex(0); // Set to the first item
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddNewEmployee();
            }
        });
    }
}
