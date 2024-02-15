import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchForAssistant {
    private JFrame frame;
    private JTextField epfNumberField, epfNameField, departmentField, designationField;
    private JTable employeeTable;

    public SearchForAssistant() {
        frame = new JFrame("Employee Details");
        frame.setLayout(null);

        // Create and set up the table
        employeeTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBounds(20, 20, 500, 150);
        frame.add(scrollPane);

        // Labels and text fields for search criteria
        JLabel epfNumberLabel = new JLabel("EPF Number:");
        epfNumberField = new JTextField();
        JLabel epfNameLabel = new JLabel("EPF Name:");
        epfNameField = new JTextField();
        JLabel departmentLabel = new JLabel("Department:");
        departmentField = new JTextField();
        JLabel designationLabel = new JLabel("Designation:");
        designationField = new JTextField();

        epfNumberLabel.setBounds(20, 180, 100, 20);
        epfNumberField.setBounds(130, 180, 150, 20);
        epfNameLabel.setBounds(20, 210, 100, 20);
        epfNameField.setBounds(130, 210, 150, 20);
        departmentLabel.setBounds(20, 240, 100, 20);
        departmentField.setBounds(130, 240, 150, 20);
        designationLabel.setBounds(20, 270, 100, 20);
        designationField.setBounds(130, 270, 150, 20);

        frame.add(epfNumberLabel);
        frame.add(epfNumberField);
        frame.add(epfNameLabel);
        frame.add(epfNameField);
        frame.add(departmentLabel);
        frame.add(departmentField);
        frame.add(designationLabel);
        frame.add(designationField);

        // Search button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(20, 300, 100, 30);
        frame.add(searchButton);

	// Clear button
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(240, 300, 100, 30);
        frame.add(clearButton);

	// Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(130, 300, 100, 30);
        frame.add(backButton);

        // Set up action listeners
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAndReload();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                HRManagerAssistant.main(new String[]{});
            }
        });

        // Load all employee records initially
        loadEmployeeRecords();

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void loadEmployeeRecords() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("EPF Number");
        model.addColumn("EPF Name");
        model.addColumn("Department");
        model.addColumn("Designation");

        // Retrieve data from the database and populate the table
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/thetechcompanydb", "root", "")) {
            String query = "SELECT * FROM Employee";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String[] rowData = {
                                resultSet.getString("EPFNumber"),
                                resultSet.getString("EPFName"),
                                resultSet.getString("Department"),
                                resultSet.getString("Designation")
                        };
                        model.addRow(rowData);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        employeeTable.setModel(model);
    }

    private void performSearch() {
        // Retrieve search criteria from text fields
        String epfNumber = epfNumberField.getText();
        String epfName = epfNameField.getText();
        String department = departmentField.getText();
        String designation = designationField.getText();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("EPF Number");
        model.addColumn("EPF Name");
        model.addColumn("Department");
        model.addColumn("Designation");

        // Retrieve search results from the database based on the criteria
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/thetechcompanydb", "root", "")) {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Employee WHERE 1=1");

            if (!epfNumber.isEmpty()) {
                queryBuilder.append(" AND EPFNumber LIKE ?");
            }
            if (!epfName.isEmpty()) {
                queryBuilder.append(" AND EPFName LIKE ?");
            }
            if (!department.isEmpty()) {
                queryBuilder.append(" AND Department LIKE ?");
            }
            if (!designation.isEmpty()) {
                queryBuilder.append(" AND Designation LIKE ?");
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
                int parameterIndex = 1;

                if (!epfNumber.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, "%" + epfNumber + "%");
                }
                if (!epfName.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, "%" + epfName + "%");
                }
                if (!department.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, "%" + department + "%");
                }
                if (!designation.isEmpty()) {
                    preparedStatement.setString(parameterIndex, "%" + designation + "%");
                }

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String[] rowData = {
                                resultSet.getString("EPFNumber"),
                                resultSet.getString("EPFName"),
                                resultSet.getString("Department"),
                                resultSet.getString("Designation")
                        };
                        model.addRow(rowData);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        employeeTable.setModel(model);
    }

    private void clearAndReload() {
        clearTextFields();
        loadEmployeeRecords();
    }

    private void clearTextFields() {
        epfNumberField.setText("");
        epfNameField.setText("");
        departmentField.setText("");
        designationField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SearchForAssistant();
            }
        });
    }
}
