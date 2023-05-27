
/*
GROUP: Ng Yan Lam, Leong Yung Thai, Desmond Cheng Wen Xuan
Group Assignment
Title: Hospital Management System
*/
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class HMS {
    public static void main(String[] args) {
        loginPage();
    }

    // Login Page for Hospital Management System
    public static void loginPage() {
        // Create a panel with two text fields
        JPanel panel = new JPanel(new GridLayout(5, 2, 2, 2));
        JTextField staffIDField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        // Create labels for staff ID and password
        JLabel staffIDLabel = new JLabel("Staff ID: ");
        JLabel passwordLabel = new JLabel("Password: ");

        // Add the components to panel
        panel.add(staffIDLabel);
        panel.add(staffIDField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        // Show the custom dialog
        String[] options = { "Login", "Cancel" };
        int result = JOptionPane.showOptionDialog(null, panel, "Log In",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        // Check if the user clicked "OK"
        if (result == JOptionPane.OK_OPTION) {
            String staffID = staffIDField.getText();
            String password = passwordField.getText();
            System.out.println("staffID: " + staffID);
            System.out.println("password: " + password);
            if (!staffID.isEmpty() && !password.isEmpty()) {
                mainPageFrame(staffID);
            } else if (staffID.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pleese enter your staff ID.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                loginPage();
            } else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pleese enter your password.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                loginPage();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Login.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                loginPage();
            }
        } else {
            // User canceled the dialog
            System.out.println("User canceled to Login.");
        }
    }

    // Main Page for Hospital Management System
    public static void mainPageFrame(String staffID) {
        JFrame mainPageFrame = new JFrame("Hospital Management System");

        JLabel systemTitleLabel = new JLabel("Welcome to Hospital Management System!");
        systemTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create an empty border with padding
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 0, 15, 0);
        systemTitleLabel.setBorder(emptyBorder);

        Font labelFont = systemTitleLabel.getFont();
        Font newFont = new Font(labelFont.getFontName(), Font.PLAIN, 20);
        systemTitleLabel.setFont(newFont);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

        // Registration Page
        JPanel registrationPanel = new JPanel();
        registrationPanel.setLayout(null);
        // Font for Registration Page
        Font FontRegistrationTitle = new Font(labelFont.getFontName(), Font.PLAIN, 25);
        Font FontRegistrationLabel = new Font(labelFont.getFontName(), Font.PLAIN, 15);
        // Labels for Registration Page
        JLabel registrationTitleLabel = new JLabel("Patient Registration");
        registrationTitleLabel.setBounds(320, 20, 300, 30);
        registrationTitleLabel.setFont(FontRegistrationTitle);
        JLabel firstNameLabel = new JLabel("First Name :");
        firstNameLabel.setBounds(50, 80, 250, 30);
        firstNameLabel.setFont(FontRegistrationLabel);
        JLabel lastNameLabel = new JLabel("Last Name :");
        lastNameLabel.setBounds(50, 130, 250, 30);
        lastNameLabel.setFont(FontRegistrationLabel);
        JLabel phoneNumberLabel = new JLabel("Phone Number :");
        phoneNumberLabel.setBounds(50, 180, 250, 30);
        phoneNumberLabel.setFont(FontRegistrationLabel);
        JLabel personalHealthNumberLabel = new JLabel("Personal Health Number :");
        personalHealthNumberLabel.setBounds(50, 230, 250, 30);
        personalHealthNumberLabel.setFont(FontRegistrationLabel);
        JLabel addressLabel = new JLabel("Address :");
        addressLabel.setBounds(50, 280, 250, 30);
        addressLabel.setFont(FontRegistrationLabel);
        JLabel postalCodeLabel = new JLabel("Postal Code :");
        postalCodeLabel.setBounds(50, 330, 250, 30);
        postalCodeLabel.setFont(FontRegistrationLabel);
        JLabel cityLabel = new JLabel("City :");
        cityLabel.setBounds(50, 380, 250, 30);
        cityLabel.setFont(FontRegistrationLabel);
        JLabel countryLabel = new JLabel("Country :");
        countryLabel.setBounds(50, 430, 250, 30);
        countryLabel.setFont(FontRegistrationLabel);
        JLabel patientIdenficationNumberLabel = new JLabel("Patient Idenfication Number :");
        patientIdenficationNumberLabel.setBounds(50, 480, 250, 30);
        patientIdenficationNumberLabel.setFont(FontRegistrationLabel);
        // Text Field for Registration Page
        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(300, 80, 500, 30);
        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(300, 130, 500, 30);
        JTextField phoneNumberField = new JTextField();
        phoneNumberField.setBounds(300, 180, 500, 30);
        JTextField personalHealthNumberField = new JTextField();
        personalHealthNumberField.setBounds(300, 230, 500, 30);
        JTextField addressField = new JTextField();
        addressField.setBounds(300, 280, 500, 30);
        JTextField postalCodeField = new JTextField();
        postalCodeField.setBounds(300, 330, 500, 30);
        JTextField cityField = new JTextField();
        cityField.setBounds(300, 380, 500, 30);
        JTextField countryField = new JTextField();
        countryField.setBounds(300, 430, 500, 30);
        JTextField patientIdenficationNumberField = new JTextField();
        patientIdenficationNumberField.setBounds(300, 480, 500, 30);
        // Button for Registration Page
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(330, 560, 200, 30);
        // Add components to Registration Page
        registrationPanel.add(registrationTitleLabel);
        registrationPanel.add(firstNameLabel);
        registrationPanel.add(firstNameField);
        registrationPanel.add(lastNameLabel);
        registrationPanel.add(lastNameField);
        registrationPanel.add(phoneNumberLabel);
        registrationPanel.add(phoneNumberField);
        registrationPanel.add(personalHealthNumberLabel);
        registrationPanel.add(personalHealthNumberField);
        registrationPanel.add(addressLabel);
        registrationPanel.add(addressField);
        registrationPanel.add(postalCodeLabel);
        registrationPanel.add(postalCodeField);
        registrationPanel.add(cityLabel);
        registrationPanel.add(cityField);
        registrationPanel.add(countryLabel);
        registrationPanel.add(countryField);
        registrationPanel.add(patientIdenficationNumberLabel);
        registrationPanel.add(patientIdenficationNumberField);
        registrationPanel.add(registerButton);

        // Add an action listener to the register button
        registerButton.addActionListener(e -> {
            // Perform registration logic here
            System.out.println("First Name: " + firstNameField.getText());
            System.out.println("Last Name: " + lastNameField.getText());
            System.out.println("Phone Number: " + phoneNumberField.getText());
            System.out.println("Personal Health Number: " + personalHealthNumberField.getText());
            System.out.println("Address: " + addressField.getText());
            System.out.println("Postal Code: " + postalCodeField.getText());
            System.out.println("City: " + cityField.getText());
            System.out.println("Country: " + countryField.getText());
            System.out.println("Patient Idenfication Number: " + patientIdenficationNumberField.getText());
            mainPageFrame.dispose();
            mainPageFrame(staffID);
        });

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

        Font tabFont = new Font(labelFont.getFontName(), Font.PLAIN, 18);
        tabbedPane.setFont(tabFont);

        mainPageFrame.getContentPane().setLayout(new BorderLayout());
        mainPageFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        mainPageFrame.getContentPane().add(systemTitleLabel, BorderLayout.NORTH);

        // Set the size and close operation for the frame
        mainPageFrame.setSize(1150, 750);
        mainPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPageFrame.setLocationRelativeTo(null);
        mainPageFrame.setResizable(false);
        mainPageFrame.setVisible(true);
    }
}