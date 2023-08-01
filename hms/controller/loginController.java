package hms.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import hms.HMS;

public class loginController {

    public static void login(TextField staffIDField, PasswordField passwordField, Stage loginPage) {

        String username = staffIDField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please enter your username and password");
            alert.showAndWait();
        } else {

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM staff WHERE USERNAME='" + username + "' AND PASSWORD ='" + password + "'");
                if (rs.next()) {
                    loginPage.close();
                    HMS mainApp = new HMS();
                    mainApp.mainPage(rs.getString("USERNAME"), rs.getString("EMPLOYEETYPE"));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username or password");
                    alert.showAndWait();
                    passwordField.clear();
                }
                // Clean up resources
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception a) {
                a.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error: " + a.getMessage());
                alert.showAndWait();
                utilities.writeErrorLogs(a);
            }
        }
    }

    public static void tabPane_employeeVerify(String EmployeeType, TabPane tabPane, Tab overviewTab, Tab registerTab,
            Tab consultationTab, Tab patientTab, Tab staffTab,
            Tab logoutTab) {

        if (EmployeeType.equals("Front-desk")) {
            consultationTab.setDisable(true);
            patientTab.setDisable(true);
            staffTab.setDisable(true);
        } else if (EmployeeType.equals("Nurse")) {
            registerTab.setDisable(true);
            staffTab.setDisable(true);
        } else if (EmployeeType.equals("Doctor")) {
            registerTab.setDisable(true);
            staffTab.setDisable(true);
        } else if (EmployeeType.equals("Administrator")) {
            registerTab.setDisable(true);
            // consultationTab.setDisable(true);
        }

        tabPane.getTabs().addAll(overviewTab, registerTab, consultationTab, patientTab, staffTab, logoutTab);
    }
}
