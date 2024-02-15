import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Abstract class to represent a database entity
abstract class DatabaseEntity {
    protected String url = "jdbc:mysql://localhost:3308/thetechcompanydb";
    protected String user = "root";
    protected String password = "";

    abstract boolean insertIntoDatabase();
}

// Class representing a department, extending DatabaseEntity
class Department extends DatabaseEntity {
    private String departmentName;

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    boolean insertIntoDatabase() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO department (DepartmentName) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, departmentName);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}

public class AddNewDepartment {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Add New Department");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel label = new JLabel("Department Name: ");
        label.setBounds(20, 20, 150, 24);
        panel.add(label);

        JTextField textField = new JTextField();
        textField.setBounds(150, 20, 150, 24);
        panel.add(textField);

        JButton addDepartmentButton = new JButton("Add Department");
        addDepartmentButton.setBounds(20, 60, 150, 30);
        panel.add(addDepartmentButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(180, 60, 150, 30);
        panel.add(clearButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(20, 100, 150, 30);
        panel.add(backButton);

        addDepartmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String departmentName = textField.getText();

                if (departmentName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a department name.");
                    return;
                }

                // Using the Department class to handle database operations
                Department department = new Department(departmentName);
                if (department.insertIntoDatabase()) {
                    JOptionPane.showMessageDialog(frame, "Department added successfully");
                    textField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to add department. Please try again.");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                HRManager.main(new String[]{});
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
