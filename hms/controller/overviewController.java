package hms.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class overviewController {

    public static void overviewPage(VBox overviewLayout) {
        // Font
        Font overviewFont_1 = Font.font("Arial", FontWeight.BOLD, 25);
        Font clockFont = Font.font("Arial", FontWeight.BOLD, 40);

        // Label of Overview Page
        Label overviewTitle = new Label("Overview");
        Label timeLabel = new Label();
        Label dateWeek = new Label();

        // Create a Rectangle to represent the card
        Rectangle card_1 = new Rectangle(410, 100);
        card_1.setFill(Color.WHITE);
        card_1.setStroke(Color.BLACK);
        card_1.setArcWidth(20);
        card_1.setArcHeight(20);

        Rectangle card_2 = new Rectangle(200, 80);
        card_2.setFill(Color.WHITE);
        card_2.setStroke(Color.BLACK);
        card_2.setArcWidth(20);
        card_2.setArcHeight(20);

        Rectangle card_3 = new Rectangle(200, 80);
        card_3.setFill(Color.WHITE);
        card_3.setStroke(Color.BLACK);
        card_3.setArcWidth(20);
        card_3.setArcHeight(20);

        Rectangle card_4 = new Rectangle(200, 80);
        card_4.setFill(Color.WHITE);
        card_4.setStroke(Color.BLACK);
        card_4.setArcWidth(20);
        card_4.setArcHeight(20);

        Rectangle card_5 = new Rectangle(200, 80);
        card_5.setFill(Color.WHITE);
        card_5.setStroke(Color.BLACK);
        card_5.setArcWidth(20);
        card_5.setArcHeight(20);

        Rectangle card_6 = new Rectangle(300, 280);
        card_6.setFill(Color.WHITE);
        card_6.setStroke(Color.BLACK);
        card_6.setArcWidth(20);
        card_6.setArcHeight(20);

        StackPane cardLayout_1 = new StackPane(card_1);
        StackPane cardLayout_2 = new StackPane(card_2);
        StackPane cardLayout_3 = new StackPane(card_3);
        StackPane cardLayout_4 = new StackPane(card_4);
        StackPane cardLayout_5 = new StackPane(card_5);
        StackPane cardLayout_6 = new StackPane(card_6);

        // Get total patient
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
                patientImgView.setFitWidth(80);
                patientImgView.setFitHeight(80);

                Label patientImg = new Label();
                patientImg.setGraphic(patientImgView);
                Label totalLabel = new Label("Total patient's");
                totalLabel.setFont(Font.font("Arial", 20));
                Label countLabel = new Label("" + total);
                countLabel.setFont(Font.font("Arial", FontWeight.BOLD, 17));

                HBox patientBox = new HBox(5);
                HBox.setMargin(patientImg, new Insets(0, 30, 0, 0));
                VBox patientContentBox = new VBox(5);
                patientContentBox.getChildren().addAll(totalLabel, countLabel);
                patientBox.getChildren().addAll(patientImg, patientContentBox);
                patientBox.setAlignment(Pos.CENTER);
                patientContentBox.setAlignment(Pos.CENTER_LEFT);
                cardLayout_1.getChildren().add(patientBox);
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
            utilities.writeErrorLogs(a);
        }

        // Get total bed from Maternity Ward
        try {
            // Connect to MySQL database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT total_bed, occupied_bed FROM ward WHERE WARDNAME='Maternity'");
            if (rs.next()) {

                Label wardName = new Label("Maternity Ward");
                wardName.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                Label totalBedLabel = new Label("Total Bed");
                totalBedLabel.setFont(Font.font("Arial", 13));
                Label bedAvailabilityLabel = new Label(
                        rs.getString("occupied_bed") + " / " + rs.getString("total_bed"));
                bedAvailabilityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));

                VBox wardBox = new VBox(5);
                wardBox.getChildren().addAll(wardName, totalBedLabel, bedAvailabilityLabel);
                wardBox.setPadding(new Insets(0, 20, 0, 20));
                wardBox.setAlignment(Pos.CENTER_LEFT);
                cardLayout_2.getChildren().add(wardBox);
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
            utilities.writeErrorLogs(a);
        }

        // Get total bed from Surgical Ward
        try {
            // Connect to MySQL database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT total_bed, occupied_bed FROM ward WHERE WARDNAME='Surgical'");
            if (rs.next()) {
                Label wardName = new Label("Surgical Ward");
                wardName.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                Label totalBedLabel = new Label("Total Bed");
                totalBedLabel.setFont(Font.font("Arial", 13));
                Label bedAvailabilityLabel = new Label(
                        rs.getString("occupied_bed") + " / " + rs.getString("total_bed"));
                bedAvailabilityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));

                VBox wardBox = new VBox(5);
                wardBox.getChildren().addAll(wardName, totalBedLabel, bedAvailabilityLabel);
                wardBox.setPadding(new Insets(0, 20, 0, 20));
                wardBox.setAlignment(Pos.CENTER_LEFT);
                cardLayout_3.getChildren().add(wardBox);
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
            utilities.writeErrorLogs(a);
        }

        // Get total bed from Cancer Ward
        try {
            // Connect to MySQL database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT total_bed, occupied_bed FROM ward WHERE WARDNAME='Cancer'");
            if (rs.next()) {
                Label wardName = new Label("Cancer Ward");
                wardName.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                Label totalBedLabel = new Label("Total Bed");
                totalBedLabel.setFont(Font.font("Arial", 13));
                Label bedAvailabilityLabel = new Label(
                        rs.getString("occupied_bed") + " / " + rs.getString("total_bed"));
                bedAvailabilityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));

                VBox wardBox = new VBox(5);
                wardBox.getChildren().addAll(wardName, totalBedLabel, bedAvailabilityLabel);
                wardBox.setPadding(new Insets(0, 20, 0, 20));
                wardBox.setAlignment(Pos.CENTER_LEFT);
                cardLayout_4.getChildren().add(wardBox);
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
            utilities.writeErrorLogs(a);
        }

        // Get total bed from Cardiac Ward
        try {
            // Connect to MySQL database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT total_bed, occupied_bed FROM ward WHERE WARDNAME='Cardiac'");
            if (rs.next()) {
                Label wardName = new Label("Cardiac Ward");
                wardName.setFont(Font.font("Arial", FontWeight.BOLD, 15));
                Label totalBedLabel = new Label("Total Bed");
                totalBedLabel.setFont(Font.font("Arial", 13));
                Label bedAvailabilityLabel = new Label(
                        rs.getString("occupied_bed") + " / " + rs.getString("total_bed"));
                bedAvailabilityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));

                VBox wardBox = new VBox(5);
                wardBox.getChildren().addAll(wardName, totalBedLabel, bedAvailabilityLabel);
                wardBox.setPadding(new Insets(0, 20, 0, 20));
                wardBox.setAlignment(Pos.CENTER_LEFT);
                cardLayout_5.getChildren().add(wardBox);
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
            utilities.writeErrorLogs(a);
        }

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
                doctorImgView.setFitWidth(80);
                doctorImgView.setFitHeight(80);

                Label doctorImg = new Label();
                doctorImg.setGraphic(doctorImgView);
                Label totalLabel = new Label("Total doctor's");
                totalLabel.setFont(Font.font("Arial", 20));
                Label countLabel = new Label("" + total);
                countLabel.setFont(Font.font("Arial", FontWeight.BOLD, 17));

                VBox doctorBox = new VBox(5);
                VBox.setMargin(totalLabel, new Insets(30, 0, 0, 0));
                doctorBox.getChildren().addAll(doctorImg, totalLabel, countLabel);
                doctorBox.setPadding(new Insets(0, 20, 0, 20));
                doctorBox.setAlignment(Pos.CENTER);
                cardLayout_6.getChildren().add(doctorBox);
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
            utilities.writeErrorLogs(a);
        }

        getCurrentTime(timeLabel, dateWeek);
        VBox clockLayout = new VBox(5);
        clockLayout.setPrefSize(250, 150);
        clockLayout.setAlignment(Pos.CENTER);
        clockLayout.getChildren().addAll(timeLabel, dateWeek);

        VBox availabilityShow = new VBox(10);
        VBox wardStatus = new VBox(10);
        HBox bedAvailability_1 = new HBox(10);
        HBox bedAvailability_2 = new HBox(10);
        bedAvailability_1.getChildren().addAll(cardLayout_2, cardLayout_3);
        bedAvailability_2.getChildren().addAll(cardLayout_4, cardLayout_5);
        wardStatus.getChildren().addAll(bedAvailability_1, bedAvailability_2);
        availabilityShow.getChildren().addAll(cardLayout_1, wardStatus);

        HBox firstRow = new HBox(20);
        firstRow.getChildren().addAll(clockLayout, availabilityShow, cardLayout_6);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        firstRow.getChildren().addAll(spacer);

        HBox.setHgrow(clockLayout, Priority.ALWAYS);
        HBox.setHgrow(cardLayout_6, Priority.ALWAYS);

        HBox.setMargin(availabilityShow, new Insets(0, 10, 0, 10));
        firstRow.setAlignment(Pos.CENTER);

        overviewTitle.setFont(overviewFont_1);
        timeLabel.setFont(clockFont);
        VBox contentLayout = new VBox(10);
        contentLayout.getChildren().addAll(overviewTitle, firstRow);
        overviewLayout.getChildren().add(contentLayout);
    }

    protected static void getCurrentTime(Label timeLabel, Label dateWeek) {
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
    }
}
