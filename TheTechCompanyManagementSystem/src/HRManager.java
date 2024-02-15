import javax.swing.*;
import java.awt.Color;
import java.awt.*;

import java.awt.Font;
public class HRManager {
    public static void main(String[] args) {

        // Frame
        JFrame f =new JFrame("HR Manager System");
        JLabel l1 = new JLabel("WELCOME TO THE HR MANAGER SYSTEM");
        Font font = new Font("Times New Roman", Font.BOLD, 18);

        Container c = f.getContentPane();
        c.setBackground(Color.white);
        l1.setFont(font);
        l1.setBounds(70, 36, 500, 50);
        f.add(l1);

        // Add New Department Button
        JButton btn1 = new JButton("Add New Department");
        btn1.setBounds(40, 100, 300, 50);
        btn1.setFont(font);
        f.add(btn1);
        btn1.addActionListener(e -> AddNewDepartment.main(new String[]{}));

        // Add New Designation Button
        JButton btn2 = new JButton("Add New Designation");
        btn2.setBounds(40, 160, 300, 50);
        btn2.setFont(font);
        f.add(btn2);
        btn2.addActionListener(e -> AddNewDesignation.main(new String[]{}));

        // Add New Employee Button
        JButton btn3 = new JButton("Add New Employee");
        btn3.setBounds(40, 220, 300, 50);
        btn3.setFont(font);
        f.add(btn3);
        btn3.addActionListener(e -> AddNewEmployee.main(new String[]{}));

        // Search Employee Button
        JButton btn4 = new JButton("Search Employee");
        btn4.setBounds(40, 280, 300, 50);
        btn4.setFont(font);
        f.add(btn4);
        btn4.addActionListener(e -> SearchEmployee.main(new String[]{}));

        // Create A New HR Assistant Account Button
        JButton btn5 = new JButton("Create HR Assistant Account");
        btn5.setBounds(40, 340, 300, 50);
        btn5.setFont(font);
        f.add(btn5);
        btn5.addActionListener(e -> CreateHRAssistantAccount.main(new String[]{}));

        // Exit Button
        JButton btn6 = new JButton("Exit");
        btn6.setBounds(40, 400, 300, 50);
        btn6.setFont(font);
        f.add(btn6);
        btn6.addActionListener(e -> System.exit(0));

        f.setSize(500, 500);
        f.setLayout(null);
        f.setVisible(true);
    }
}
