import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.Border;

public class HMS extends JFrame implements ActionListener {
    JLabel l1, l2;
    JTextField tf1;
    JPasswordField pf2;
    JButton b1, b2;

    HMS() {
        setTitle("Login Form");
        setVisible(true);
        setSize(500, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));
        l1 = new JLabel("Username", SwingConstants.CENTER);
        l2 = new JLabel("Password", SwingConstants.CENTER);
        tf1 = new JTextField();
        pf2 = new JPasswordField();
        b1 = new JButton("Login");
        b2 = new JButton("Clear");
        add(l1);
        add(tf1);
        add(l2);
        add(pf2);
        add(b1);
        add(b2);
        // Add action listener to buttons
        b1.addActionListener(this);
        b2.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            String username = tf1.getText();
            String password = new String(pf2.getPassword());
            try {
                // Connect to MySQL database
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalmanagement", "root",
                        "");
                // Create statement object
                Statement stmt = con.createStatement();
                // Execute SQL query to verify user
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM login WHERE NAME='" + username + "' AND PASSWORD ='" + password + "'");
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful");
                    dispose(); // Close login window
                    mainPageFrame(username);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password");
                }
                // Clean up resources
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        } else if (ae.getSource() == b2) {
            tf1.setText("");
            pf2.setText("");
        }
    }

    public static void main(String[] args) {
        new HMS();
    }

    public static void mainPageFrame(String staffID) {
        // Create a new JFrame for the next page
        JFrame mainPageFrame = new JFrame("Hospital Management System");
        // Create a current user label to display the staff ID
        JLabel currentUserLabel = new JLabel("Welcome to Hospital Management System!");
        currentUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Create an empty border with padding
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 0, 15, 0);
        // Set the empty border on the label
        currentUserLabel.setBorder(emptyBorder);
        // Create a font with the desired size
        Font labelFont = currentUserLabel.getFont();
        Font newFont = new Font(labelFont.getFontName(), Font.PLAIN, 20);
        // Set the new font for the label
        currentUserLabel.setFont(newFont);
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        // Create panels and contents for each tab
        JPanel registrationPanel = new JPanel();
        JLabel registrationPanel_1 = new JLabel("This is the registration panel");
        registrationPanel.add(registrationPanel_1);
        JPanel consultationPanel = new JPanel();
        JLabel consultationPanel_1 = new JLabel("This is the consultation panel");
        consultationPanel.add(consultationPanel_1);
        JPanel mmmPanel = new JPanel();
        JLabel mmmPanel_1 = new JLabel("This is the medical matter management panel");
        mmmPanel.add(mmmPanel_1);
        JPanel checkoutPanel = new JPanel();
        JLabel checkoutPanel_1 = new JLabel("This is the check out panel");
        checkoutPanel.add(checkoutPanel_1);
        JPanel logoutPanel = new JPanel();
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 4) {
                mainPageFrame.dispose();
                new HMS();
            }
        });
        // Add the panels to the tabbed pane with respective tab titles
        tabbedPane.addTab("Registration", registrationPanel);
        tabbedPane.addTab("Consultation", consultationPanel);
        tabbedPane.addTab("Medical Matter Management", mmmPanel);
        tabbedPane.addTab("Check Out", checkoutPanel);
        tabbedPane.addTab("Log Out", logoutPanel);
        // Create a font with the desired size for the tabbed pane
        Font tabFont = new Font(labelFont.getFontName(), Font.PLAIN, 30);
        tabbedPane.setFont(tabFont);
        // Add the tabbed pane to the main frame's content pane
        mainPageFrame.getContentPane().setLayout(new BorderLayout());
        mainPageFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        mainPageFrame.getContentPane().add(currentUserLabel, BorderLayout.NORTH);
        // Set the size and close operation for the frame
        mainPageFrame.setSize(1000, 700);
        mainPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Display the frame
        mainPageFrame.setVisible(true);
    }
}
