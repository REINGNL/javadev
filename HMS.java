
/*
GROUP: Ng Yan Lam, Leong Yung Thai, Desmond Cheng Wen Xuan
Group Assignment
Title: Hospital Management System
*/
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class HMS extends JFrame implements ActionListener {

    public static void main(String[] args) {
        new HMS();
    }

    // Declaration
    JLabel staffIDLabel, passwordLabel;
    JTextField staffIDField;
    JPasswordField passwordField;
    JButton loginBtn;

    HMS() {
        setTitle("Log In");
        setLayout(null);
        // Staff ID
        staffIDLabel = new JLabel("Username :");
        staffIDLabel.setBounds(45, 30, 300, 25);
        staffIDField = new JTextField();
        staffIDField.setBounds(130, 30, 250, 25);
        // Password
        passwordLabel = new JLabel("Password :");
        passwordLabel.setBounds(45, 80, 300, 25);
        passwordField = new JPasswordField();
        passwordField.setBounds(130, 80, 250, 25);
        // Login Button
        loginBtn = new JButton("Login");
        loginBtn.setBounds(145, 150, 150, 25);
        add(staffIDLabel);
        add(staffIDField);
        add(passwordLabel);
        add(passwordField);
        add(loginBtn);

        // Add action listener to buttons
        loginBtn.addActionListener(this);

        // Set the size and close operation for the frame
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(450, 250);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent Btn) {
        if (Btn.getSource() == loginBtn) {
            String username = staffIDField.getText();
            String password = new String(passwordField.getPassword());
            try {
                // Connect to MySQL database
                // Please check your port before run it!!!
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/hms", "root",
                        "");
                // Create statement object
                Statement stmt = con.createStatement();
                // Execute SQL query to verify user
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM login WHERE NAME='" + username + "' AND PASSWORD ='" + password + "'");
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful");
                    dispose();
                    mainPageFrame(null, username);
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong username or password");
                }
                // Clean up resources
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    // Main Page for Hospital Management System
    public static void mainPageFrame(HMS hsmInstance, String staffID) {
        JFrame mainPageFrame = new JFrame("Hospital Management System");

        JLabel systemTitleLabel = new JLabel("Welcome to Hospital Management System!");
        systemTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create an empty border with padding
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 0, 15, 0);
        systemTitleLabel.setBorder(emptyBorder);

        // Font for Title
        Font labelFont = systemTitleLabel.getFont();
        Font newFont = new Font(labelFont.getFontName(), Font.PLAIN, 20);
        systemTitleLabel.setFont(newFont);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        tabbedPane.setBackground(Color.lightGray);

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
        JLabel dateOfBirthLabel = new JLabel("Date of Birth (yyyy-mm-dd) :");
        dateOfBirthLabel.setBounds(50, 230, 250, 30);
        dateOfBirthLabel.setFont(FontRegistrationLabel);
        JLabel personalHealthNumberLabel = new JLabel("Personal Health Number :");
        personalHealthNumberLabel.setBounds(50, 280, 250, 30);
        personalHealthNumberLabel.setFont(FontRegistrationLabel);
        JLabel addressLabel = new JLabel("Address :");
        addressLabel.setBounds(50, 330, 250, 30);
        addressLabel.setFont(FontRegistrationLabel);
        JLabel postalCodeLabel = new JLabel("Postal Code :");
        postalCodeLabel.setBounds(50, 380, 250, 30);
        postalCodeLabel.setFont(FontRegistrationLabel);
        JLabel cityLabel = new JLabel("City :");
        cityLabel.setBounds(50, 430, 250, 30);
        cityLabel.setFont(FontRegistrationLabel);
        JLabel countryLabel = new JLabel("Country :");
        countryLabel.setBounds(50, 480, 250, 30);
        countryLabel.setFont(FontRegistrationLabel);
        JLabel patientIdenficationNumberLabel = new JLabel("Patient Idenfication Number :");
        patientIdenficationNumberLabel.setBounds(50, 530, 250, 30);
        patientIdenficationNumberLabel.setFont(FontRegistrationLabel);

        // Text Field for Registration Page
        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(300, 80, 500, 30);
        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(300, 130, 500, 30);
        JTextField dateOfBirthField = new JTextField();
        dateOfBirthField.setBounds(300, 230, 500, 30);
        JTextField phoneNumberField = new JTextField();
        phoneNumberField.setBounds(300, 180, 500, 30);
        JTextField personalHealthNumberField = new JTextField();
        personalHealthNumberField.setBounds(300, 280, 500, 30);
        JTextField addressField = new JTextField();
        addressField.setBounds(300, 330, 500, 30);
        JTextField postalCodeField = new JTextField();
        postalCodeField.setBounds(300, 380, 500, 30);
        JTextField cityField = new JTextField();
        cityField.setBounds(300, 430, 500, 30);
        JTextField countryField = new JTextField();
        countryField.setBounds(300, 480, 500, 30);
        JTextField patientIdenficationNumberField = new JTextField();
        patientIdenficationNumberField.setBounds(300, 530, 500, 30);

        // Button for Registration Page
        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(330, 610, 200, 30);

        // Add components to Registration Page
        registrationPanel.add(registrationTitleLabel);
        registrationPanel.add(firstNameLabel);
        registrationPanel.add(firstNameField);
        registrationPanel.add(lastNameLabel);
        registrationPanel.add(lastNameField);
        registrationPanel.add(dateOfBirthLabel);
        registrationPanel.add(dateOfBirthField);
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
        registrationPanel.add(registerBtn);

        registerBtn.addActionListener(e -> {

            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String dateOfBirth = dateOfBirthField.getText();
            String phoneNumber = phoneNumberField.getText();
            String personalHealthNumber = personalHealthNumberField.getText();
            String address = addressField.getText();
            String postalCode = postalCodeField.getText();
            String city = cityField.getText();
            String country = countryField.getText();
            String patientIdentificationNumber = patientIdenficationNumberField.getText();

            // Insert the data into the database
            try {
                // Please check your port before run it!!!
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/hms", "root", "");
                Statement stmt = con.createStatement();

                String query = "INSERT INTO patient (FIRSTNAME, LASTNAME, DATEOFBIRTH, PHONENUMBER, PHEALTHNUMBER, ADDRESS, POSTALCODE, CITY, COUNTRY, PIDENTIFICATIONNUMBER) VALUES ('"
                        +
                        firstName + "', '" + lastName + "', '" + dateOfBirth + "', '" + phoneNumber + "', '"
                        + personalHealthNumber + "', '"
                        + address + "', '" + postalCode + "', '" + city + "', '" + country + "', '"
                        + patientIdentificationNumber + "')";

                int rowsAffected = stmt.executeUpdate(query);

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(mainPageFrame, "Registration successful");
                    // Clear the text fields
                    firstNameField.setText("");
                    lastNameField.setText("");
                    phoneNumberField.setText("");
                    personalHealthNumberField.setText("");
                    addressField.setText("");
                    postalCodeField.setText("");
                    cityField.setText("");
                    countryField.setText("");
                    patientIdenficationNumberField.setText("");
                    dateOfBirthField.setText("");
                } else {
                    JOptionPane.showMessageDialog(mainPageFrame, "Registration failed");
                }

                stmt.close();
                con.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPageFrame, "Error: " + ex.getMessage());
            }
        });

        // Consultation Page
        JPanel consultationPanel = new JPanel();
        tabbedPane.addChangeListener(e1 -> {
            if (tabbedPane.getSelectedIndex() == 1) {
                try {
                    // Establish a database connection
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/hms", "root", "");

                    // Execute the query to select the required columns from the table
                    String query = "SELECT PATIENTID, FIRSTNAME, LASTNAME, PHEALTHNUMBER FROM patient";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    // Create the table model with column names
                    String[] columnNames = { "Patient ID", "First Name", "Last Name", "Personal Health Number" };
                    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                    // Populate the table model with data from the result set
                    while (resultSet.next()) {
                        String patientId = resultSet.getString("PATIENTID");
                        String firstName = resultSet.getString("FIRSTNAME");
                        String lastName = resultSet.getString("LASTNAME");
                        String PHN = resultSet.getString("PHEALTHNUMBER");

                        Object[] rowData = { patientId, firstName, lastName, PHN };
                        tableModel.addRow(rowData);
                    }
                    // Create the table using the table model
                    JTable patientTable = new JTable(tableModel);
                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                    patientTable.setDefaultRenderer(Object.class, centerRenderer);
                    patientTable.setPreferredScrollableViewportSize(new Dimension(860, 100));
                    patientTable.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
                    Font tableFont = new Font(patientTable.getFont().getName(), Font.PLAIN, 17);
                    patientTable.setFont(tableFont);
                    JScrollPane scrollPane = new JScrollPane(patientTable);
                    scrollPane.setPreferredSize(new Dimension(860, 205));
                    consultationPanel.add(scrollPane, BorderLayout.CENTER);

                    // Remove the existing table from the consultationPanel
                    consultationPanel.removeAll();

                    // Add the new scroll pane with the table to the consultationPanel
                    consultationPanel.add(scrollPane, BorderLayout.CENTER);
                    consultationPanel.revalidate();
                    consultationPanel.repaint();

                    // Close the database resources
                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // Medical Matter Management Page
        JPanel mmmPanel = new JPanel();
        JLabel mmmPanel_1 = new JLabel("This is the medical matter management panel");
        mmmPanel.add(mmmPanel_1);

        // Checkout Page
        JPanel checkoutPanel = new JPanel();
        checkoutPanel.setLayout(null);

        // Font for Checkout Page
        Font FontCheckoutTitle = new Font(labelFont.getFontName(), Font.PLAIN, 25);
        Font FontCheckoutLabel = new Font(labelFont.getFontName(), Font.PLAIN, 15);

        // Labels for Checkout Page
        JLabel CheckoutTitleLabel = new JLabel("Patient Checkout");
        CheckoutTitleLabel.setBounds(320, 20, 300, 30);
        CheckoutTitleLabel.setFont(FontCheckoutTitle);
        JLabel patientIDLabel = new JLabel("Patient ID :");
        patientIDLabel.setBounds(50, 80, 250, 30);
        patientIDLabel.setFont(FontCheckoutLabel);

        // Text Field for Checkout Page
        JTextField patientIDField = new JTextField();
        patientIDField.setBounds(300, 80, 500, 30);

        // Checkbox for Checkout Page
        JCheckBox checkBox = new JCheckBox("I had admitted the patient above is ready to check out", false);
        checkBox.setBounds(230, 150, 500, 30);
        checkBox.setFont(FontCheckoutLabel);

        // Button for Checkout Page
        JButton checkoutBtn = new JButton("Check Out");
        checkoutBtn.setBounds(330, 200, 200, 30);

        // Add components to Checkout Page
        checkoutPanel.add(CheckoutTitleLabel);
        checkoutPanel.add(patientIDLabel);
        checkoutPanel.add(patientIDField);
        checkoutPanel.add(checkBox);
        checkoutPanel.add(checkoutBtn);

        checkoutBtn.addActionListener(e -> {
            if (!checkBox.isSelected()) {
                JOptionPane.showMessageDialog(mainPageFrame, "Please confirm patient checkout");
                return;
            }

            String PatientID = patientIDField.getText();

            // Insert the data into the database
            try {
                // Please check your port before run it!!!
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/hms", "root", "");
                Statement stmt = con.createStatement();

                String query = "DELETE FROM patient WHERE PATIENTID = '" + PatientID + "'";

                int rowsAffected = stmt.executeUpdate(query);

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(checkoutPanel, "Checkout successfully");
                    // Clear the text fields
                    patientIDField.setText("");
                    checkBox.setSelected(false);
                } else {
                    JOptionPane.showMessageDialog(checkoutPanel,
                            "Checkout failed, please confirm the patient ID is existing!");
                }

                stmt.close();
                con.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPageFrame, "Error: " + ex.getMessage());
            }
        });

        // Logout Page
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

        Font tabFont = new Font(labelFont.getFontName(), Font.PLAIN, 18);
        tabbedPane.setFont(tabFont);

        mainPageFrame.getContentPane().setLayout(new BorderLayout());
        mainPageFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        mainPageFrame.getContentPane().add(systemTitleLabel, BorderLayout.NORTH);

        // Set the size and close operation for the frame
        mainPageFrame.setSize(1150, 800);
        mainPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPageFrame.setLocationRelativeTo(null);
        mainPageFrame.setResizable(false);
        mainPageFrame.setVisible(true);
    }
}