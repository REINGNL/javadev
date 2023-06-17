/*
GROUP: Ng Yan Lam, Leong Yung Thai, Desmond Cheng Wen Xuan
Group Assignment
Title: Hospital Management System
*/

package hms;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class HMS extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage loginPage) {
        loginPage.setTitle("Login");

        // Load the image
        Image userImg = new Image("assets/icon/username-icon.png");
        Image passImg = new Image("assets/icon/password-icon.png");

        // Create an ImageView to display the image
        ImageView userImgView = new ImageView(userImg);
        userImgView.setFitWidth(50);
        userImgView.setFitHeight(50);
        ImageView passImgView = new ImageView(passImg);
        passImgView.setFitWidth(50);
        passImgView.setFitHeight(50);

        // Staff ID
        Label staffIDLabel = new Label();
        staffIDLabel.setGraphic(userImgView);
        TextField staffIDField = new TextField();
        staffIDField.setFocusTraversable(false);
        staffIDField.setPromptText("Username");

        // Password
        Label passwordLabel = new Label();
        passwordLabel.setGraphic(passImgView);
        PasswordField passwordField = new PasswordField();
        passwordField.setFocusTraversable(false);
        passwordField.setPromptText("Password");

        // Login Button
        Button loginBtn = new Button("Login");

        // Font for Login Page
        Font loginFont = Font.font("Arial", 20);

        // Create the login layout
        Pane loginLayout = new Pane();
        loginLayout.setPadding(new Insets(20));
        loginLayout.getChildren().add(staffIDLabel);
        staffIDLabel.setLayoutX(40);
        staffIDLabel.setLayoutY(40);
        loginLayout.getChildren().add(staffIDField);
        staffIDField.setLayoutX(110);
        staffIDField.setLayoutY(50);
        staffIDField.setFont(loginFont);
        loginLayout.getChildren().add(passwordLabel);
        passwordLabel.setLayoutX(40);
        passwordLabel.setLayoutY(120);
        loginLayout.getChildren().add(passwordField);
        passwordField.setLayoutX(110);
        passwordField.setLayoutY(132);
        passwordField.setFont(loginFont);
        loginLayout.getChildren().add(loginBtn);
        loginBtn.setLayoutX(130);
        loginBtn.setLayoutY(230);
        loginBtn.setPrefWidth(150);

        Scene loginScene = new Scene(loginLayout, 400, 300);
        loginPage.setScene(loginScene);
        loginPage.setResizable(false);
        loginPage.show();

        // Login function
        loginBtn.setOnAction(e -> {
            String username = staffIDField.getText();
            String password = passwordField.getText();

            try {
                // Connect to MySQL database
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");

                // Create statement object
                Statement stmt = con.createStatement();

                // Execute SQL query to verify user
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM staff WHERE USERNAME='" + username + "' AND PASSWORD ='" + password + "'");
                if (rs.next()) {
                    // Show a successful login message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login");
                    alert.setHeaderText(null);
                    alert.setContentText("Login successfully!");
                    alert.showAndWait();
                    loginPage.close();

                    // Open the main page
                    mainPage(rs.getString("USERNAME"));
                } else {
                    // Show an error message for invalid credentials
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username or password");
                    alert.showAndWait();
                }
                // Clean up resources
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception a) {
                a.printStackTrace();
                // Show an error message for any exceptions
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error: " + a.getMessage());
                alert.showAndWait();
            }
        });
    }

    // Main Page
    private void mainPage(String staffID) {
        Stage mainPageStage = new Stage();
        mainPageStage.setTitle("Hospital Management System");

        Label systemTitleLabel = new Label("Welcome, " + staffID);
        systemTitleLabel.setStyle("-fx-font-size: 30;");
        systemTitleLabel.setPadding(new Insets(20));

        // Tab Pane
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.LEFT);
        tabPane.getStyleClass().add("horizontal-tabs");

        // Overview Page
        Tab overviewTab = new Tab("Overview");
        VBox overviewLayout = new VBox();
        overviewLayout.setSpacing(10);
        overviewLayout.setPadding(new Insets(20));
        overviewPage(overviewLayout);
        overviewTab.setContent(overviewLayout);
        overviewTab.setClosable(false);

        // Register Page
        Tab registerTab = new Tab("Registration");
        VBox registerLayout = new VBox();
        registerLayout.setSpacing(10);
        registerLayout.setPadding(new Insets(20));
        registerPage(registerLayout);
        registerTab.setContent(registerLayout);
        registerTab.setClosable(false);

        // Consultation Page
        Tab consultationTab = new Tab("Consultation");
        VBox consultationLayout = new VBox();
        consultationLayout.setPadding(new Insets(20));
        consultationPage(consultationLayout);
        consultationTab.setContent(consultationLayout);
        consultationTab.setClosable(false);

        // Checkout Page
        Tab checkoutTab = new Tab("Check Out");
        VBox checkoutLayout = new VBox();
        checkoutLayout.setPadding(new Insets(20));
        checkoutLayout.setSpacing(10);
        checkoutPage(checkoutLayout);
        checkoutTab.setContent(checkoutLayout);
        checkoutTab.setClosable(false);

        // Logout Page
        Tab logoutTab = new Tab("Logout");
        logoutTab.setClosable(false);
        logout(logoutTab, mainPageStage);

        // Add the tabs to the tab pane
        tabPane.getTabs().addAll(overviewTab, registerTab, consultationTab, checkoutTab, logoutTab);

        VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.TOP_LEFT);
        mainLayout.getChildren().addAll(systemTitleLabel, tabPane);

        Scene mainScene = new Scene(mainLayout, 1200, 800);
        mainScene.getStylesheets().add(getClass().getResource("style").toExternalForm());
        mainPageStage.setScene(mainScene);
        mainPageStage.setMaximized(true);
        mainPageStage.show();
    }

    static void overviewPage(VBox overviewLayout) {
        // Font
        Font overviewFont_1 = Font.font("Arial", FontWeight.BOLD, 25);
        Font clockFont = Font.font("Arial", FontWeight.BOLD, 40);

        // Label of Overview Page
        Label overviewTitle = new Label("Overview");
        Label timeLabel = new Label();
        Label dateWeek = new Label();

        // Clock function
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    Date currentDate = new Date();

                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    String time = timeFormat.format(currentDate);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String date = dateFormat.format(currentDate);

                    SimpleDateFormat dayFormat = new SimpleDateFormat("E");
                    String dayOfWeek = dayFormat.format(currentDate);

                    Platform.runLater(() -> {
                        timeLabel.setText(time);
                        dateWeek.setText(date + ", " + dayOfWeek);
                    });
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // Create a Rectangle to represent the card
        Rectangle card_1 = new Rectangle(250, 100);
        card_1.setFill(Color.WHITE);
        card_1.setStroke(Color.BLACK);
        card_1.setArcWidth(20);
        card_1.setArcHeight(20);

        Rectangle card_2 = new Rectangle(250, 100);
        card_2.setFill(Color.WHITE);
        card_2.setStroke(Color.BLACK);
        card_2.setArcWidth(20);
        card_2.setArcHeight(20);

        StackPane cardLayout_1 = new StackPane(card_1);
        StackPane cardLayout_2 = new StackPane(card_2);

        // Get total of doctors
        try {
            // Connect to MySQL database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT COUNT(*) AS Total_Doctor FROM staff WHERE EMPLOYEETYPE='Doctor'");
            if (rs.next()) {
                int total = rs.getInt("Total_Doctor");

                // Create an ImageView to display the image
                Image img = new Image("assets/icon/doctor.png");
                ImageView doctorImgView = new ImageView(img);
                doctorImgView.setFitWidth(50);
                doctorImgView.setFitHeight(50);

                Label doctorImg = new Label();
                doctorImg.setGraphic(doctorImgView);
                Label totalLabel = new Label("Total doctor's");
                totalLabel.setFont(Font.font("Arial", 15));
                Label countLabel = new Label("" + total);
                countLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));

                VBox doctorBox = new VBox(5);
                doctorBox.getChildren().addAll(doctorImg, totalLabel, countLabel);
                doctorBox.setPadding(new Insets(0, 20, 0, 20));
                cardLayout_1.getChildren().add(doctorBox);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Opps...Something went wrong");
                alert.showAndWait();
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
        }

        // Get total of nurses
        try {
            // Connect to MySQL database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT COUNT(*) AS Total_Patient FROM patient");
            if (rs.next()) {
                int total = rs.getInt("Total_Patient");

                // Create an ImageView to display the image
                Image img = new Image("assets/icon/patient.png");
                ImageView patientImgView = new ImageView(img);
                patientImgView.setFitWidth(50);
                patientImgView.setFitHeight(50);

                Label patientImg = new Label();
                patientImg.setGraphic(patientImgView);
                Label totalLabel = new Label("Total patient's");
                totalLabel.setFont(Font.font("Arial", 15));
                Label countLabel = new Label("" + total);
                countLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));

                VBox patientBox = new VBox(5);
                patientBox.getChildren().addAll(patientImg, totalLabel, countLabel);
                patientBox.setPadding(new Insets(0, 20, 0, 20));
                cardLayout_2.getChildren().add(patientBox);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Opps...Something went wrong");
                alert.showAndWait();
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
        }

        VBox clockLayout = new VBox(5);
        clockLayout.setPrefSize(250, 150);
        clockLayout.setAlignment(Pos.CENTER);
        clockLayout.getChildren().addAll(timeLabel, dateWeek);

        VBox availabilityShow = new VBox(5);
        availabilityShow.getChildren().addAll(cardLayout_1, cardLayout_2);

        HBox firstRow = new HBox(20);
        firstRow.getChildren().addAll(clockLayout, availabilityShow);

        overviewTitle.setFont(overviewFont_1);
        timeLabel.setFont(clockFont);
        VBox contentLayout = new VBox(10);
        contentLayout.getChildren().addAll(overviewTitle, firstRow);
        overviewLayout.getChildren().add(contentLayout);
    }

    static void registerPage(VBox registerLayout) {
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
        Label dateOfBirthLabel = new Label("Date of Birth (yyyy-mm-dd)");
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
        Label patientIdenficationNumberLabel = new Label("Patient Idenfication Number :");
        patientIdenficationNumberLabel.setFont(registerLabelFont);

        // Textfield
        TextField firstNameField = new TextField();
        firstNameField.setFont(registerLabelFont);
        TextField lastNameField = new TextField();
        lastNameField.setFont(registerLabelFont);
        TextField dateOfBirthField = new TextField();
        dateOfBirthField.setFont(registerLabelFont);
        TextField phoneNumberField = new TextField();
        phoneNumberField.setFont(registerLabelFont);
        TextField personalHealthNumberField = new TextField();
        personalHealthNumberField.setFont(registerLabelFont);
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

        // Button for Registration Page
        Button registerBtn = new Button("Register");
        registerBtn.setPadding(new Insets(10));
        registerBtn.setFont(registerLabelFont);
        HBox btnLayout = new HBox(registerBtn);
        btnLayout.setAlignment(Pos.CENTER_RIGHT);
        btnLayout.setPadding(new Insets(10));

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

            // Insert the data into the database
            try {
                // Please check your port before run it!!!
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
                Statement stmt = con.createStatement();

                String query = "INSERT INTO patient (FIRSTNAME, LASTNAME, DATEOFBIRTH, PHONENUMBER, PHEALTHNUMBER, ADDRESS, POSTALCODE, CITY, COUNTRY, PIDENTIFICATIONNUMBER) VALUES ('"
                        +
                        firstName + "', '" + lastName + "', '" + dateOfBirth + "', '" + phoneNumber + "', '"
                        + personalHealthNumber + "', '"
                        + address + "', '" + postalCode + "', '" + city + "', '" + country + "', '"
                        + patientIdentificationNumber + "')";

                int rowsAffected = stmt.executeUpdate(query);

                if (rowsAffected > 0) {
                    // Show registration success message
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Registration");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Registration successfully!");
                    successAlert.showAndWait();

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
            }
        });

        registerLayout.getChildren().addAll(registrationTitleLabel, spacing, firstNameLabel, firstNameField,
                lastNameLabel,
                lastNameField, phoneNumberLabel, phoneNumberField,
                dateOfBirthLabel, dateOfBirthField, personalHealthNumberLabel, personalHealthNumberField, addressLabel,
                addressField, postalCodeLabel, postalCodeField, cityLabel, cityField, countryLabel, countryField,
                patientIdenficationNumberLabel, patientIdenficationNumberField, btnLayout);
    }

    static void consultationPage(VBox consultationLayout) {
        consultationLayout.getChildren().add(new Label("Consultation Page"));
    }

    static void checkoutPage(VBox checkoutLayout) {
        // Font
        Font checkoutTitleFont = Font.font("Arial", FontWeight.BOLD, 25);
        Font checkouLabelFont = Font.font("Arial", 18);

        // Label and TextField of Checkout Page
        Label checkoutTitleLabel = new Label("Check Out Patient");
        checkoutTitleLabel.setFont(checkoutTitleFont);
        Label patientIDLabel = new Label("Patient ID");
        patientIDLabel.setFont(checkouLabelFont);
        TextField patientIDField = new TextField();
        patientIDField.setFont(checkoutTitleFont);
        CheckBox checkBox = new CheckBox("I had admitted the patient above is ready to check out");
        checkBox.setFont(checkouLabelFont);

        // Checkout Button
        Button checkoutBtn = new Button("Check out");
        checkoutBtn.setOnAction(e -> {
            if (!checkBox.isSelected()) {
                Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                confirmationAlert.setTitle("Confirmation");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Please confirm patient checkout");
                confirmationAlert.showAndWait();
                return;
            }

            String PatientID = patientIDField.getText();

            // Insert the data into the database
            try {
                // Please check your port before run it!!!
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
                Statement stmt = con.createStatement();

                String query = "DELETE FROM patient WHERE PATIENTID = '" + PatientID + "'";

                int rowsAffected = stmt.executeUpdate(query);

                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Check out");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Checkout successfully!");
                    successAlert.showAndWait();
                    // Clear the text fields
                    patientIDField.setText("");
                    checkBox.setSelected(false);
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Information");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Checkout failed! Please confirm the patient ID is existing.");
                    errorAlert.showAndWait();
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
            }
        });

        checkoutLayout.getChildren().addAll(checkoutTitleLabel, patientIDLabel, patientIDField, checkBox, checkoutBtn);
    }

    static void logout(Tab logoutTab, Stage mainPageStage) {
        logoutTab.setOnSelectionChanged(event -> {
            if (logoutTab.isSelected()) {
                // Close the main page stage
                mainPageStage.close();
                // Open the login page
                HMS hms = new HMS();
                Stage loginPageStage = new Stage();
                loginPageStage.setTitle("Log In");
                hms.start(loginPageStage);
            }
        });
    }

    static void getCurrentTime() {
        Date currentDate = new Date();

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
        String time = timeFormat.format(currentDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
        String date = dateFormat.format(currentDate);

        SimpleDateFormat dayFormat = new SimpleDateFormat("E");
        String dayOfWeek = dayFormat.format(currentDate);

        // System.out.println("Time: " + time);
        // System.out.println("Date: " + date);
        // System.out.println("Day: " + dayOfWeek);
    }
}
