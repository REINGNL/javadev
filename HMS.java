/*
GROUP: Ng Yan Lam, Leong Yung Thai, Desmond Cheng Wen Xuan
Group Assignment
Title: Hospital Management System
*/
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class HMS {
    public static void main(String[] args) 
    {
        loginPage();
    }
    
    //Login Page for Hospital Management System
    public static void loginPage() 
    {
        // Create a panel with two text fields
        JPanel panel = new JPanel(new GridLayout(5, 2, 2, 2));
        JTextField staffIDField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        
        // Remove the border around the text fields
        staffIDField.setBorder(null);
        passwordField.setBorder(null);
        
        // Create labels for staff ID and password
        JTextField staffIDLabel = new JTextField("Staff ID: ");
        staffIDLabel.setEditable(false);
        staffIDLabel.setBorder(null);
        JTextField passwordLabel = new JTextField("Password: ");
        passwordLabel.setEditable(false);
        passwordLabel.setBorder(null);
        
        panel.add(staffIDLabel);
        panel.add(staffIDField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        // Show the custom dialog
        int result = JOptionPane.showOptionDialog(null, panel, "Log In",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        // Check if the user clicked "OK"
        if (result == JOptionPane.OK_OPTION) {
            String staffID = staffIDField.getText();
            String password = passwordField.getText();
            System.out.println("staffID: " + staffID);
            System.out.println("password: " + password);
            if (!staffID.isEmpty() && !password.isEmpty()) {
                mainPageFrame(staffID);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Staff ID or Password.", "Error", JOptionPane.ERROR_MESSAGE);
                loginPage();
            }
        } else {
            // User canceled the dialog
            System.out.println("User canceled to Login.");
        }
    }

    //Main Page for Hospital Management System
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
                loginPage();
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