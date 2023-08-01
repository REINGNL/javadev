package hms.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class registrationController {

    public static void registerPage(VBox registerLayout) {
        // Font
        Font registerTitleFont = Font.font("Arial", FontWeight.BOLD, 25);
        Font registerLabelFont = Font.font("Arial", 18);

        // Label
        Label registrationTitleLabel = new Label("Patient Registration");
        registrationTitleLabel.setFont(registerTitleFont);
        Label spacing = new Label("");
        Label firstNameLabel = new Label("First Name");
        firstNameLabel.setFont(registerLabelFont);
        Label lastNameLabel = new Label("Last Name");
        lastNameLabel.setFont(registerLabelFont);
        Label phoneNumberLabel = new Label("Phone Number");
        phoneNumberLabel.setFont(registerLabelFont);
        Label dateOfBirthLabel = new Label("Date of Birth");
        dateOfBirthLabel.setFont(registerLabelFont);
        Label personalHealthNumberLabel = new Label("Personal Health Number");
        personalHealthNumberLabel.setFont(registerLabelFont);
        Label addressLabel = new Label("Address");
        addressLabel.setFont(registerLabelFont);
        Label postalCodeLabel = new Label("Postal Code");
        postalCodeLabel.setFont(registerLabelFont);
        Label cityLabel = new Label("City");
        cityLabel.setFont(registerLabelFont);
        Label countryLabel = new Label("Country");
        countryLabel.setFont(registerLabelFont);
        Label patientIdenficationNumberLabel = new Label("Patient Idenfication Number");
        patientIdenficationNumberLabel.setFont(registerLabelFont);

        // Textfield
        TextField firstNameField = new TextField();
        firstNameField.setFont(registerLabelFont);
        TextField lastNameField = new TextField();
        lastNameField.setFont(registerLabelFont);
        TextField dateOfBirthField = new TextField();
        dateOfBirthField.setFont(registerLabelFont);
        dateOfBirthField.setPromptText("eg. yyyy-mm-dd");
        TextField phoneNumberField = new TextField();
        phoneNumberField.setFont(registerLabelFont);
        phoneNumberField.setPromptText("eg. 0123456789");
        TextField personalHealthNumberField = new TextField();
        personalHealthNumberField.setFont(registerLabelFont);
        personalHealthNumberField.setPromptText("eg. 112111");
        TextField addressField = new TextField();
        addressField.setFont(registerLabelFont);
        TextField postalCodeField = new TextField();
        postalCodeField.setFont(registerLabelFont);
        TextField cityField = new TextField();
        cityField.setFont(registerLabelFont);
        TextField countryField = new TextField();
        countryField.setFont(registerLabelFont);
        TextField patientIdenficationNumberField = new TextField();
        patientIdenficationNumberField.setFont(registerLabelFont);
        patientIdenficationNumberField.setPromptText("eg. 023456789900");

        // Button for Registration Page
        Button registerBtn = new Button("Register");
        registerBtn.setPadding(new Insets(10));
        registerBtn.setFont(registerLabelFont);
        HBox btnLayout = new HBox(registerBtn);
        btnLayout.setAlignment(Pos.CENTER_RIGHT);
        btnLayout.setPadding(new Insets(10));

        registerLayout.getChildren().addAll(registrationTitleLabel, spacing, firstNameLabel, firstNameField,
                lastNameLabel, lastNameField, dateOfBirthLabel, dateOfBirthField, phoneNumberLabel,
                phoneNumberField,
                patientIdenficationNumberLabel,
                patientIdenficationNumberField,
                personalHealthNumberLabel, personalHealthNumberField, addressLabel,
                addressField, postalCodeLabel, postalCodeField, cityLabel, cityField, countryLabel, countryField,
                btnLayout);

        registerBtn.setOnAction(e -> {

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

            if (firstName.isEmpty() || lastName.isEmpty() || dateOfBirth.isEmpty() || phoneNumber.isEmpty()
                    || personalHealthNumber.isEmpty() || address.isEmpty() || postalCode.isEmpty() || city.isEmpty()
                    || country.isEmpty() || patientIdentificationNumber.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("All the fields are required to fill in");
                alert.showAndWait();
            } else {
                registration(firstName, lastName, dateOfBirth, phoneNumber, personalHealthNumber, address, postalCode,
                        city, country, patientIdentificationNumber);
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
            }
        });
    }

    static void registration(String firstName, String lastName, String dateOfBirth, String phoneNumber,
            String personalHealthNumber, String address, String postalCode, String city, String country,
            String patientIdentificationNumber) {
        try {
            // Please check your port before run it!!!
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
            Statement stmt = con.createStatement();
            String query = "INSERT INTO patient (FIRSTNAME, LASTNAME, DATEOFBIRTH, PHONENUMBER, PHEALTHNUMBER, ADDRESS, POSTALCODE, CITY, COUNTRY, PIDENTIFICATIONNUMBER, isHospitalized) VALUES ('"
                    +
                    firstName + "', '" + lastName + "', '" + dateOfBirth + "', '" + phoneNumber + "', '"
                    + personalHealthNumber + "', '"
                    + address + "', '" + postalCode + "', '" + city + "', '" + country + "', '"
                    + patientIdentificationNumber + "', 0)";

            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected > 0) {
                // Show registration success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Registration");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Registration successfully!");
                successAlert.showAndWait();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Registration failed! Please try again.");
                errorAlert.showAndWait();
            }

            stmt.close();
            con.close();
        } catch (Exception ex) {
            // Show error message
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
