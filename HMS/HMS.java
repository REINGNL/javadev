/*GROUP: Ng Yan Lam, Leong Yung Thai, Desmond Cheng Wen Xuan*/
//Group Assignment
//Title: Hospital System Management
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.*;

public class HMS {
    public static void main(String[] args) 
    {
        loginPage();
    }
    //Login Page for Hospital Manangement System
    public static void loginPage() 
    {
        // Create a panel with two text fields
        JPanel panel = new JPanel(new GridLayout(5, 2, 2, 2));
        JTextField staffIDField = new JTextField();
        JTextField passwordField = new JTextField();
        
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
            System.out.println("Staff ID: " + staffID);
            System.out.println("Password: " + password);
            if (!staffID.isEmpty() && !password.isEmpty()) {
                mainPage(staffID);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Staff ID or Password.", "Error", JOptionPane.ERROR_MESSAGE);
                loginPage();
            }
        } else {
            // User canceled the dialog
            System.out.println("User canceled to Login.");
        }
    }

    //Main Page for Hospital Manangement System
    public static void mainPage(String staffID) {
        // Create a new JFrame for the next page
        JFrame nextPageFrame = new JFrame("Hospital Manangement System");

        // Create a label to display the staff ID
        JLabel label = new JLabel("Welcome! Your Staff ID is: " + staffID);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the label to the frame's content pane
        nextPageFrame.getContentPane().add(label);

        // Set the size and close operation for the frame
        nextPageFrame.setSize(1000, 700);
        nextPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display the frame
        nextPageFrame.setVisible(true);
    }
}
