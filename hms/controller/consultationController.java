package hms.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class consultationController {

    public static void consultationPage(VBox consultationLayout) {

        VBox patientDataContainer = new VBox(10);

        // Font
        Font consultationTitleFont = Font.font("Arial", FontWeight.BOLD, 25);
        Font consultationLabelFont = Font.font("Arial", 18);

        // Label for Consulation Page
        Label consultationTitle = new Label("Ward Assignation");
        consultationTitle.setFont(consultationTitleFont);

        Label spacing = new Label("");

        // First row components
        Label PHN = new Label("Patient Health Number");
        PHN.setFont(consultationLabelFont);
        PHN.setPadding(new Insets(5, 0, 0, 0));
        TextField phnTextfield = new TextField();
        phnTextfield.setFont(consultationLabelFont);
        Button searchBtn = new Button("Search");
        searchBtn.setFont(consultationLabelFont);
        searchBtn
                .setOnAction(event -> getPatientData(phnTextfield.getText(), consultationLayout, patientDataContainer));

        // Second row components
        Label des = new Label("Description");
        des.setFont(consultationLabelFont);
        TextArea desTextfield = new TextArea();
        desTextfield.setPromptText("Write something...");
        desTextfield.setPrefHeight(200);
        desTextfield.setWrapText(true);
        desTextfield.setFont(consultationLabelFont);

        // Third row components
        // DropDown Box
        Label wardTypeLabel = new Label("Ward Type: ");
        wardTypeLabel.setFont(consultationLabelFont);
        ComboBox<String> wardType = new ComboBox<>();
        wardTypeDropDown(wardType);

        // Display Available BedID
        Label bedIDLabel = new Label("BedID Assinged: ");
        bedIDLabel.setFont(consultationLabelFont);
        TextField bedIDTextField = new TextField();
        bedIDTextField.setPromptText("Please select ward type");
        bedIDTextField.setEditable(false);
        bedIDTextField.setMouseTransparent(true);
        bedIDTextField.setFocusTraversable(false);

        Label assignedDoctorLabel = new Label("Doctor Assigned");
        assignedDoctorLabel.setFont(consultationLabelFont);
        TextField assignedDoctorTextField = new TextField();
        assignedDoctorTextField.setPromptText("Please select ward type");
        assignedDoctorTextField.setEditable(false);
        assignedDoctorTextField.setMouseTransparent(true);
        assignedDoctorTextField.setFocusTraversable(false);

        Label assignedNurseLabel = new Label("Nurse Assigned");
        assignedNurseLabel.setFont(consultationLabelFont);
        TextField assignedNurseTextField = new TextField();
        assignedNurseTextField.setPromptText("Please select ward type");
        assignedNurseTextField.setEditable(false);
        assignedNurseTextField.setMouseTransparent(true);
        assignedNurseTextField.setFocusTraversable(false);

        wardType.setOnAction(event -> availableBed(wardType.getValue(), bedIDTextField, assignedDoctorTextField,
                assignedNurseTextField));

        // Fourth row
        Button resetBtn = new Button("Reset");
        resetBtn.setFont(consultationLabelFont);
        resetBtn.setOnAction(event -> {
            phnTextfield.clear();
            patientDataContainer.getChildren().clear();
            consultationLayout.getChildren().clear();
            consultationPage(consultationLayout);
        });

        Button assignBtn = new Button("Assign");
        assignBtn.setFont(consultationLabelFont);
        assignBtn.setOnAction(event -> assignBed(consultationLayout, phnTextfield, desTextfield, wardType.getValue(),
                bedIDTextField, assignedDoctorTextField, assignedNurseTextField));

        HBox firstRow = new HBox(10);
        firstRow.getChildren().addAll(PHN, phnTextfield, searchBtn);

        VBox secondRow = new VBox(10);
        secondRow.getChildren().addAll(des, desTextfield);

        HBox thirdRow = new HBox(10);
        thirdRow.setPadding(new Insets(20, 0, 0, 0));
        thirdRow.getChildren().addAll(wardTypeLabel, wardType, bedIDLabel, bedIDTextField, assignedDoctorLabel,
                assignedDoctorTextField, assignedNurseLabel, assignedNurseTextField);

        HBox fourthRow = new HBox(10);
        fourthRow.getChildren().addAll(resetBtn, assignBtn);

        consultationLayout.getChildren().addAll(consultationTitle, spacing, firstRow, patientDataContainer, secondRow,
                thirdRow, fourthRow);
    }

    protected static void assignBed(VBox consultationLayout, TextField phn, TextArea desc, String wardType,
            TextField bedIDTextField, TextField assignedDoctorTextField, TextField assignedNurseTextField) {

        String patientHealthNumber = phn.getText();
        String description = desc.getText();
        String bedID = bedIDTextField.getText();
        String assignedDoctor = assignedDoctorTextField.getText();
        String assignedNurse = assignedNurseTextField.getText();

        if (!patientHealthNumber.isEmpty() || !description.isEmpty()) {
            try {
                // Please check your port before running it!!!
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
                Statement stmt = con.createStatement();

                String query = "UPDATE bed AS b " +
                        "INNER JOIN patient AS p INNER JOIN ward AS w " +
                        "SET b.PHEALTHNUMBER = '" + patientHealthNumber + "', " +
                        "    b.Description = '" + description + "', " +
                        "    b.assigned_Doctor = '" + assignedDoctor + "', " +
                        "    b.assigned_Nurse = '" + assignedNurse + "', " +
                        "    p.isHospitalized = true, " +
                        "    p.WARDTYPE = '" + wardType + "', " +
                        "    w.occupied_bed = w.occupied_bed + 1 " +
                        "WHERE b.WardType = '" + wardType + "' " +
                        "  AND b.BEDID = '" + bedID + "' " +
                        "  AND p.PHEALTHNUMBER = '" + patientHealthNumber + "' " +
                        "  AND w.WARDNAME = '" + wardType + "'";

                int rowsAffected = stmt.executeUpdate(query);

                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Bed Assignment");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Bed assigned successfully.");
                    successAlert.showAndWait();

                    consultationLayout.getChildren().clear();
                    consultationController.consultationPage(consultationLayout);
                }

                stmt.close();
                con.close();
            } catch (Exception ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Error: " + ex.getMessage());
                errorAlert.showAndWait();
                ex.printStackTrace();
                utilities.writeErrorLogs(ex);
            }
        }
    }

    protected static void availableBed(String wardType, TextField bedIDTextField, TextField assignedDoctorTextField,
            TextField assignedNurseTextField) {
        try {
            // Please check your port before run it!!!
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
            Statement stmt = con.createStatement();
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            String query = "SELECT BEDID FROM bed";
            String query1 = "SELECT FIRSTNAME, LASTNAME FROM staff";
            String query2 = "SELECT FIRSTNAME, LASTNAME FROM staff";

            if (wardType != null) {
                query += " WHERE WardType = '" + wardType + "' AND PHealthNumber IS NULL LIMIT 1";
                query1 += " WHERE EMPLOYEETYPE = 'Doctor' AND ward_InCharge = '" + wardType + "'";
                query2 += " WHERE EMPLOYEETYPE = 'Nurse' AND ward_InCharge = '" + wardType + "'";
            }

            ResultSet rs = stmt.executeQuery(query);
            ResultSet rs1 = stmt1.executeQuery(query1);
            ResultSet rs2 = stmt2.executeQuery(query2);

            if (rs.next()) {
                String bedID = rs.getString("BEDID");
                bedIDTextField.setText(bedID);
            } else {
                Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                confirmationAlert.setTitle("Bed Full");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("The bed of the ward is full!");
                confirmationAlert.showAndWait();
                return;
            }
            if (rs1.next()) {
                String DocInCharge = rs1.getString("LASTNAME");
                String DocInCharge1 = rs1.getString("FIRSTNAME");
                assignedDoctorTextField.setText(DocInCharge + " " + DocInCharge1);
            } else {
                assignedDoctorTextField.setText("");
            }
            if (rs2.next()) {
                String NurInCharge = rs2.getString("LASTNAME");
                String NurInCharge1 = rs2.getString("FIRSTNAME");
                assignedNurseTextField.setText(NurInCharge + " " + NurInCharge1);
            } else {
                assignedNurseTextField.setText("");
            }
            rs.close();
            rs1.close();
            rs2.close();
            stmt.close();
            stmt1.close();
            stmt2.close();
            con.close();
        } catch (Exception ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Error: " + ex.getMessage());
            errorAlert.showAndWait();
            ex.printStackTrace();
            utilities.writeErrorLogs(ex);
        }
    }

    protected static void wardTypeDropDown(ComboBox<String> wardType) {
        try {
            // Connect to MySQL database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT WARDNAME FROM ward");

            ArrayList<String> wardTypeList = new ArrayList<>();
            while (rs.next()) {
                String wardName = rs.getString("WARDNAME");
                wardTypeList.add(wardName);
            }

            wardType.setItems(FXCollections.observableArrayList(wardTypeList));

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error: " + ex.getMessage());
            alert.showAndWait();
            utilities.writeErrorLogs(ex);
        }
    }

    protected static void getPatientData(String phn, VBox consultationLayout, VBox patientDataContainer) {
        // Font
        Font consultationLabelFont = Font.font("Arial", 20);

        patientDataContainer.getChildren().clear();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
            Statement stmt = con.createStatement();
            String query = "SELECT FIRSTNAME, LASTNAME, DATEOFBIRTH, PIDENTIFICATIONNUMBER FROM patient";

            if (!phn.isEmpty()) {
                query += " WHERE PHEALTHNUMBER = '" + phn + "'";
            }

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                if (!phn.isEmpty()) {
                    HBox row_1 = new HBox(20);
                    Label firstNameLabel = new Label("First Name: " + rs.getString("FIRSTNAME"));
                    firstNameLabel.setFont(consultationLabelFont);
                    Label lastNameLabel = new Label("Last Name: " + rs.getString("LASTNAME"));
                    lastNameLabel.setFont(consultationLabelFont);
                    Label dateOfBirthLabel = new Label("Date of Birth: " + rs.getString("DATEOFBIRTH"));
                    dateOfBirthLabel.setFont(consultationLabelFont);
                    Label PIdentificationNumLabel = new Label(
                            "Patient Identification Number: " + rs.getString("PIDENTIFICATIONNUMBER"));
                    PIdentificationNumLabel.setFont(consultationLabelFont);

                    VBox column_1 = new VBox(20);
                    VBox column_2 = new VBox(20);
                    column_1.getChildren().addAll(firstNameLabel, dateOfBirthLabel);
                    column_2.getChildren().addAll(lastNameLabel, PIdentificationNumLabel);
                    row_1.getChildren().addAll(column_1, column_2);

                    patientDataContainer.setPadding(new Insets(20, 0, 20, 0));
                    patientDataContainer.getChildren().add(row_1);
                }
            } else {
                HBox row_1 = new HBox(20);
                Label contentLabel = new Label("The PHN is not found.");
                contentLabel.setFont(consultationLabelFont);
                row_1.getChildren().add(contentLabel);
                patientDataContainer.getChildren().add(contentLabel);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Error: " + ex.getMessage());
            errorAlert.showAndWait();
            ex.printStackTrace();
            utilities.writeErrorLogs(ex);
        }
    }
}
