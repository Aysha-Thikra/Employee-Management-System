import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HRManagerAssistant {
    public static void main(String[] args) {
        // Frame
        JFrame f = new JFrame("HR Assistant System");
        JLabel l1 = new JLabel("WELCOME TO THE HR ASSISTANT SYSTEM");
        Font font = new Font("Times New Roman", Font.BOLD, 18);

        Container c = f.getContentPane();
        c.setBackground(Color.white);
        l1.setFont(font);
        l1.setBounds(70, 36, 500, 50);
        f.add(l1);

        // Search Employee Button
        JButton btn1 = new JButton("Search Employee");
        btn1.setBounds(40, 100, 200, 50);
        btn1.setFont(font);
        f.add(btn1);

        // Exit Button
        JButton btn2 = new JButton("Exit");
        btn2.setBounds(300, 100, 200, 50);
        btn2.setFont(font);
        f.add(btn2);

        f.setSize(600, 200);
        f.setLayout(null);
        f.setVisible(true);

        // ActionListener for Search Employee Button
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchForAssistant.main(new String[]{});
            }
        });

        // ActionListener for Exit Button
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose(); // Close the JFrame
            }
        });
    }
}
