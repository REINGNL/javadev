/*
Group Assignment
--------------------------------------------------------------------
GROUP MEMBER: Ng Yan Lam, Leong Yung Thai, Desmond Cheng Wen Xuan
Title: Hospital Management System
*/

package hms;

import hms.controller.consultationController;
import hms.controller.loginController;
import hms.controller.logoutController;
import hms.controller.overviewController;
import hms.controller.patientListController;
import hms.controller.registrationController;
import hms.controller.staffListController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
        loginBtn.setOnAction(e -> loginController.login(staffIDField, passwordField, loginPage));
    }

    // Main Page
    public void mainPage(String staffID, String EmployeeType) {
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

        // Overview Tab Icon
        Image overviewIcon = new Image("assets/icon/overview-logo.png");
        ImageView overviewIconView = new ImageView(overviewIcon);
        overviewIconView.setFitWidth(30);
        overviewIconView.setFitHeight(30);
        overviewIconView.getStyleClass().add("TabIcon");
        overviewTab.setGraphic(overviewIconView);

        VBox overviewLayout = new VBox();
        overviewLayout.setSpacing(10);
        overviewLayout.setPadding(new Insets(20));
        overviewController.overviewPage(overviewLayout);
        overviewTab.setContent(overviewLayout);
        overviewTab.setClosable(false);

        // Click tab to reload the page
        overviewTab.setOnSelectionChanged(event -> {
            if (overviewTab.isSelected()) {
                overviewLayout.getChildren().clear();
                overviewController.overviewPage(overviewLayout);
            }
        });

        // Register Page
        Tab registerTab = new Tab("Registration");

        // Register Tab Icon
        Image registerIcon = new Image("assets/icon/register-logo.png");
        ImageView registerIconView = new ImageView(registerIcon);
        registerIconView.setFitWidth(30);
        registerIconView.setFitHeight(30);
        registerIconView.getStyleClass().add("TabIcon");
        registerTab.setGraphic(registerIconView);

        VBox registerLayout = new VBox(10);
        registerLayout.setPadding(new Insets(20));
        registrationController.registerPage(registerLayout);
        registerTab.setContent(registerLayout);
        registerTab.setClosable(false);

        // Consultation Page
        Tab consultationTab = new Tab("Consultation");

        // Consultation Tab Icon
        Image consultationIcon = new Image("assets/icon/consultation-logo.png");
        ImageView consultationIconView = new ImageView(consultationIcon);
        consultationIconView.setFitWidth(30);
        consultationIconView.setFitHeight(30);
        consultationIconView.getStyleClass().add("TabIcon");
        consultationTab.setGraphic(consultationIconView);

        VBox consultationLayout = new VBox(10);
        consultationLayout.setPadding(new Insets(20));
        consultationController.consultationPage(consultationLayout);
        consultationTab.setContent(consultationLayout);
        consultationTab.setClosable(false);

        // Patient Page
        Tab patientTab = new Tab("Patient List");

        // Patient Tab Icon
        Image patientIcon = new Image("assets/icon/patient-logo.png");
        ImageView patientIconnView = new ImageView(patientIcon);
        patientIconnView.setFitWidth(30);
        patientIconnView.setFitHeight(30);
        patientIconnView.getStyleClass().add("TabIcon");
        patientTab.setGraphic(patientIconnView);

        VBox patientLayout = new VBox();
        patientLayout.setPadding(new Insets(20));
        patientLayout.setSpacing(10);
        patientListController.patientPage(patientLayout, EmployeeType);
        patientTab.setContent(patientLayout);
        patientTab.setClosable(false);

        patientTab.setOnSelectionChanged(event -> {
            if (patientTab.isSelected()) {
                patientLayout.getChildren().clear();
                patientListController.patientPage(patientLayout, EmployeeType);
            }
        });

        // Staff Page
        Tab staffTab = new Tab("Staff List");

        // Staff Tab Icon
        Image staffIcon = new Image("assets/icon/checkout-logo.png");
        ImageView staffIconView = new ImageView(staffIcon);
        staffIconView.setFitWidth(30);
        staffIconView.setFitHeight(30);
        staffIconView.getStyleClass().add("TabIcon");
        staffTab.setGraphic(staffIconView);

        VBox staffLayout = new VBox();
        staffLayout.setPadding(new Insets(20));
        staffLayout.setSpacing(10);
        staffListController.staffTab(staffLayout, EmployeeType);
        staffTab.setContent(staffLayout);
        staffTab.setClosable(false);

        // Logout Page
        Tab logoutTab = new Tab("Logout");

        // Logout Tab Icon
        Image logoutIcon = new Image("assets/icon/logout-logo.png");
        ImageView logoutIconView = new ImageView(logoutIcon);
        logoutIconView.setFitWidth(30);
        logoutIconView.setFitHeight(30);
        logoutIconView.getStyleClass().add("TabIcon");
        logoutTab.setGraphic(logoutIconView);

        logoutTab.setClosable(false);
        logoutController.logout(logoutTab, mainPageStage);

        // Add the tabs to the tab pane
        loginController.tabPane_employeeVerify(EmployeeType, tabPane, overviewTab, registerTab, consultationTab,
                patientTab,
                staffTab,
                logoutTab);

        VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.TOP_LEFT);
        mainLayout.getChildren().addAll(systemTitleLabel, tabPane);

        Scene mainScene = new Scene(mainLayout, 1200, 800);
        mainScene.getStylesheets().add(getClass().getResource("style").toExternalForm());
        mainPageStage.setScene(mainScene);
        mainPageStage.setMaximized(true);
        // mainPageStage.setResizable(false);
        mainPageStage.show();
    }
}