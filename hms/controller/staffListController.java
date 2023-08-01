package hms.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class staffListController {

    public static void staffTab(VBox staffLayout, String employeeType) {

        // Font
        Font staffTitleFont = Font.font("Arial", FontWeight.BOLD, 25);
        Font staffLabelFont = Font.font("Arial", 18);

        // Label for Consulation Page
        Label patientTitle = new Label("Staff List");
        patientTitle.setFont(staffTitleFont);
        Label spacing_1 = new Label();

        // Table
        TableView<ObservableList<String>> table = new TableView<>();
        table.setFixedCellSize(40);
        repopulateStaffTable(table, "", employeeType);

        VBox patientListBox = new VBox(10);
        patientListBox.getChildren().addAll(table);
        patientListBox.setPadding(new Insets(0, 20, 0, 20));

        // Search fields
        TextField searchField = new TextField();
        searchField.setPromptText("Please search with Staff ID or Lastname");
        searchField.setFont(staffLabelFont);
        searchField.setPrefWidth(1450);

        // Search Button
        Button searchBtn = new Button("Search");
        searchBtn.setFont(staffLabelFont);
        searchBtn.setOnAction(event -> repopulateStaffTable(table, searchField.getText(), employeeType));

        // Refersh Button
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setFont(staffLabelFont);
        refreshBtn.setOnAction(event -> {
            repopulateStaffTable(table, "", employeeType);
            searchField.clear();
        });

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(searchField, searchBtn, refreshBtn);
        staffLayout.getChildren().addAll(patientTitle, spacing_1, searchBox, patientListBox);
    }

    protected static void repopulateStaffTable(TableView<ObservableList<String>> table, String searchInfo,
            String employeeType) {
        if (employeeType.equals("Administrator") || employeeType.equals("Developer")) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
                Statement stmt = con.createStatement();

                String query = "SELECT STAFFID, FIRSTNAME, LASTNAME, PHONENUMBER, ADDRESS, POSTALCODE, CITY, COUNTRY, EMPLOYEETYPE FROM staff";

                if (!searchInfo.isEmpty()) {
                    query += " WHERE LASTNAME = '" + searchInfo + "' OR STAFFID = '" + searchInfo + "'";
                }

                query += " ORDER BY STAFFID ASC";

                ResultSet rs = stmt.executeQuery(query);

                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                table.getColumns().clear();
                for (int i = 1; i <= columnCount; i++) {
                    final int columnIndex = i;
                    String columnName = (metaData.getColumnName(i));
                    TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
                    column.setCellValueFactory(
                            cellData -> new ReadOnlyStringWrapper(cellData.getValue().get(columnIndex - 1)));

                    // Center-align the cell content
                    column.setCellFactory(tc -> {
                        TableCell<ObservableList<String>, String> cell = new TableCell<ObservableList<String>, String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(empty ? null : item);
                                setAlignment(Pos.CENTER);
                            }
                        };
                        return cell;
                    });
                    column.setSortable(false);
                    table.getColumns().add(column);
                }

                ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(rs.getString(i));
                    }
                    data.add(row);
                }

                table.setItems(data);
                table.prefHeightProperty()
                        .bind(table.fixedCellSizeProperty().multiply(Bindings.size(table.getItems()).add(1.0)));

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

            TableColumn<ObservableList<String>, Void> logoutColumn = new TableColumn<>("Edit");
            logoutColumn.setCellFactory(tc -> {
                Button logoutButton = new Button("Edit");
                TableCell<ObservableList<String>, Void> cell = new TableCell<ObservableList<String>, Void>() {
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(logoutButton);
                        }
                    }
                };
                logoutButton.setOnAction(e -> {
                    ObservableList<String> rowData = (ObservableList<String>) cell.getTableRow().getItem();
                    String staffID = rowData.get(0);
                    String firstName = rowData.get(1);
                    String lastName = rowData.get(2);
                    String phoneNumber = rowData.get(3);
                    String address = rowData.get(4);
                    String postalCode = rowData.get(5);
                    String city = rowData.get(6);
                    String country = rowData.get(7);
                    String employee = rowData.get(8);
                    showEditDialog(staffID, firstName, lastName, phoneNumber, address, postalCode, city, country,
                            employee,
                            table, employeeType);
                });

                return cell;
            });
            table.getColumns().add(logoutColumn);
        }
    }

    protected static void showEditDialog(String staffID, String firstName, String lastName, String phoneNumber,
            String address,
            String postalCode, String city, String country, String employee,
            TableView<ObservableList<String>> table, String employeeType) {

        Font font = Font.font("Arial", 18);

        VBox vbox = new VBox(10);
        Label firstNameLabel = new Label("First Name");
        firstNameLabel.setFont(font);
        TextField staffFirstName = new TextField();
        staffFirstName.setText(firstName);
        staffFirstName.setFont(font);
        Label lastNameLabel = new Label("Last Name");
        lastNameLabel.setFont(font);
        TextField staffLastName = new TextField();
        staffLastName.setText(lastName);
        staffLastName.setFont(font);
        Label phoenNumberLabel = new Label("Phone Number");
        phoenNumberLabel.setFont(font);
        TextField staffPhoneNumber = new TextField();
        staffPhoneNumber.setText(phoneNumber);
        staffPhoneNumber.setFont(font);
        Label addressLabel = new Label("Address");
        addressLabel.setFont(font);
        TextField staffAddress = new TextField();
        staffAddress.setText(address);
        staffAddress.setFont(font);
        Label postalCodeLabel = new Label("Postal Code");
        postalCodeLabel.setFont(font);
        TextField staffPostalCode = new TextField();
        staffPostalCode.setText(postalCode);
        staffPostalCode.setFont(font);
        Label cityLabel = new Label("City");
        cityLabel.setFont(font);
        TextField staffCity = new TextField();
        staffCity.setText(city);
        staffCity.setFont(font);
        Label countryLabel = new Label("Country");
        countryLabel.setFont(font);
        TextField staffCountry = new TextField();
        staffCountry.setText(country);
        staffCountry.setFont(font);
        Label staffTypeLabel = new Label("Employee Type");
        staffTypeLabel.setFont(font);
        TextField staffType = new TextField();
        staffType.setText(employee);
        staffType.setFont(font);

        Label spacing = new Label();
        vbox.getChildren().addAll(firstNameLabel, staffFirstName, lastNameLabel, staffLastName, phoenNumberLabel,
                staffPhoneNumber, addressLabel, staffAddress,
                postalCodeLabel, staffPostalCode, cityLabel, staffCity, countryLabel, staffCountry, staffTypeLabel,
                staffType, spacing);
        vbox.setAlignment(Pos.CENTER_LEFT);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Edit Staff Information");
        alert.setHeaderText("Do you want to continue?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().setContent(vbox);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                try {
                    // Please check your port before running it!!!
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
                    Statement stmt = con.createStatement();
                    String updateStaffQuery = "UPDATE staff SET FIRSTNAME='" + staffFirstName.getText() + "', " +
                            "LASTNAME='" + staffLastName.getText() + "', PHONENUMBER='" + staffPhoneNumber.getText() +
                            "', ADDRESS='" + staffAddress.getText() + "', POSTALCODE='" + staffPostalCode.getText() +
                            "', CITY='" + staffCity.getText() + "', COUNTRY='" + staffCountry.getText() +
                            "', EMPLOYEETYPE='" + staffType.getText() + "' WHERE FIRSTNAME='" + firstName + "' AND " +
                            "LASTNAME='" + lastName + "' AND PHONENUMBER='" + phoneNumber + "'";

                    int rowsAffected = stmt.executeUpdate(updateStaffQuery);

                    if (rowsAffected > 0) {
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Edit Staff Information");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Staff information updated successfully!");
                        successAlert.showAndWait();
                        repopulateStaffTable(table, "", employeeType);
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Failed to update staff information!");
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
                    utilities.writeErrorLogs(ex);
                }
            }
        });
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Staff Dialog");
        dialogStage.setScene(new Scene((Parent) alert.getDialogPane().getContent()));
        dialogStage.show();
    }
}
