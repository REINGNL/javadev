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
import javafx.scene.control.CheckBox;
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

public class patientListController {

    public static void patientPage(VBox patientLayout, String employeeType) {
        // Font
        Font patientTitleFont = Font.font("Arial", FontWeight.BOLD, 25);
        Font patientLabelFont = Font.font("Arial", 18);

        // Label for Consulation Page
        Label patientTitle = new Label("Patient List");
        patientTitle.setFont(patientTitleFont);
        Label spacing_1 = new Label();

        // Table
        TableView<ObservableList<String>> table = new TableView<>();
        table.setFixedCellSize(40);
        repopulateTable(table, "", employeeType);

        VBox patientListBox = new VBox(10);
        patientListBox.getChildren().addAll(table);
        patientListBox.setPadding(new Insets(0, 20, 0, 20));

        // Search fields
        TextField searchField = new TextField();
        searchField.setPromptText("Please search with PatientID or Lastname or PHN");
        searchField.setFont(patientLabelFont);
        searchField.setPrefWidth(1450);

        // Search Button
        Button searchBtn = new Button("Search");
        searchBtn.setFont(patientLabelFont);
        searchBtn.setOnAction(event -> repopulateTable(table, searchField.getText(), employeeType));

        // Refersh Button
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setFont(patientLabelFont);
        refreshBtn.setOnAction(event -> {
            repopulateTable(table, "", employeeType);
            searchField.clear();
        });

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(searchField, searchBtn, refreshBtn);
        patientLayout.getChildren().addAll(patientTitle, spacing_1, searchBox, patientListBox);
    }

    protected static void repopulateTable(TableView<ObservableList<String>> table, String searchInfo,
            String employeeType) {
        if (employeeType.equals("Administrator") || employeeType.equals("Developer")) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
                Statement stmt = con.createStatement();

                String query = "SELECT p.PATIENTID, p.FIRSTNAME, p.LASTNAME, p.DATEOFBIRTH, p.PHONENUMBER, p.PIDENTIFICATIONNUMBER, p.PHEALTHNUMBER, p.ADDRESS, p.POSTALCODE, p.CITY, p.COUNTRY, p.isHospitalized, b.WardType, b.BEDID, b.assigned_Doctor, b.assigned_Nurse FROM patient AS p LEFT JOIN bed AS b ON p.PHEALTHNUMBER = b.PHEALTHNUMBER";

                if (!searchInfo.isEmpty()) {
                    query += " WHERE p.LASTNAME = '" + searchInfo + "' OR p.PHEALTHNUMBER = '" + searchInfo
                            + "' OR p.PATIENTID = '" + searchInfo + "'";
                }

                query += " ORDER BY p.PATIENTID ASC";

                ResultSet rs = stmt.executeQuery(query);

                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                table.getColumns().clear();
                for (int i = 1; i <= columnCount; i++) {
                    final int columnIndex = i;
                    String columnName = getColumnName(metaData.getColumnName(i));
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

            TableColumn<ObservableList<String>, Void> checkoutColumn = new TableColumn<>("Checkout");
            checkoutColumn.setCellFactory(tc -> {
                Button logoutButton = new Button("checkout");
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
                    String firstName = rowData.get(1);
                    String lastName = rowData.get(2);
                    String PIdentificationNumber = rowData.get(5);
                    String PHealthNumber = rowData.get(6);
                    showCheckoutDialog(firstName, lastName, PIdentificationNumber, PHealthNumber, table, employeeType);
                });

                return cell;
            });
            table.getColumns().add(checkoutColumn);
        } else {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
                Statement stmt = con.createStatement();

                String query = "SELECT p.PATIENTID, p.FIRSTNAME, p.LASTNAME, p.DATEOFBIRTH, p.PHONENUMBER, p.PIDENTIFICATIONNUMBER, p.PHEALTHNUMBER, p.ADDRESS, p.POSTALCODE, p.CITY, p.COUNTRY, p.isHospitalized, b.WardType, b.BEDID, b.assigned_Doctor, b.assigned_Nurse FROM patient AS p LEFT JOIN bed AS b ON p.PHEALTHNUMBER = b.PHEALTHNUMBER";

                if (!searchInfo.isEmpty()) {
                    query += " WHERE p.LASTNAME = '" + searchInfo + "' OR p.PHEALTHNUMBER = '" + searchInfo
                            + "' OR p.PATIENTID = '" + searchInfo + "'";
                }

                query += " ORDER BY p.PATIENTID ASC";

                ResultSet rs = stmt.executeQuery(query);

                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                table.getColumns().clear();
                for (int i = 1; i <= columnCount; i++) {
                    final int columnIndex = i;
                    String columnName = getColumnName(metaData.getColumnName(i));
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
        }
    }

    protected static String getColumnName(String columnName) {
        switch (columnName) {
            case "PATIENTID":
                return "Patient ID";
            case "FIRSTNAME":
                return "First Name";
            case "LASTNAME":
                return "Last Name";
            case "DATEOFBIRTH":
                return "Date of Birth";
            case "PHONENUMBER":
                return "Phone Number";
            case "PIDENTIFICATIONNUMBER":
                return "P.IC";
            case "PHEALTHNUMBER":
                return "P.HN";
            case "ADDRESS":
                return "Address";
            case "POSTALCODE":
                return "Postal Code";
            case "CITY":
                return "City";
            case "COUNTRY":
                return "Country";
            case "isHospitalized":
                return "Hospitalized";
            case "WardType":
                return "Ward Type";
            case "BEDID":
                return "Bed ID";
            case "assigned_Doctor":
                return "Doctor In Charge";
            case "assigned_Nurse":
                return "Nurse In Charge";
            default:
                return columnName;
        }
    }

    protected static void showCheckoutDialog(String firstName, String lastName, String PIdentificationNumber,
            String PHealthNumber, TableView<ObservableList<String>> table, String employeeType) {
        Font font = Font.font("Arial", 18);

        VBox vbox = new VBox(10);
        Label patientName = new Label("Patient Name: " + firstName + " " + lastName);
        patientName.setFont(font);
        Label patientIdentificationNum = new Label("P.IC: " + PIdentificationNumber);
        patientIdentificationNum.setFont(font);
        Label patientHealthnNum = new Label("P.HN: " + PHealthNumber);
        patientHealthnNum.setFont(font);
        Label spacing = new Label();
        CheckBox checkBox = new CheckBox("I had admitted the patient above is ready to check out");
        vbox.getChildren().addAll(patientName, patientIdentificationNum, patientHealthnNum, spacing, checkBox);
        vbox.setAlignment(Pos.CENTER_LEFT);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Checkout Confirmation");
        alert.setHeaderText("Do you want to continue?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().setContent(vbox);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                if (checkBox.isSelected()) {
                    try {
                        // Please check your port before run it!!!
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "");
                        Statement stmt = con.createStatement();

                        String deletePatientQuery = "DELETE FROM patient WHERE PHEALTHNUMBER = '" + PHealthNumber
                                + "'";
                        String deleteBedQuery = "UPDATE bed AS b " +
                                "INNER JOIN ward AS w " +
                                "SET b.PHEALTHNUMBER = NULL, b.Description = NULL, b.assigned_Doctor = NULL, b.assigned_Nurse = NULL, w.occupied_bed = w.occupied_bed - 1 "
                                +
                                "WHERE b.PHEALTHNUMBER = '" + PHealthNumber + "' AND w.WARDNAME = b.WardType";

                        int rowsAffectedPatient = stmt.executeUpdate(deletePatientQuery);
                        int rowsAffectedBed = stmt.executeUpdate(deleteBedQuery);

                        if (rowsAffectedPatient > 0 || rowsAffectedBed > 0) {
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Check out");
                            successAlert.setHeaderText(null);
                            successAlert.setContentText("Checkout successfully!");
                            successAlert.showAndWait();
                            repopulateTable(table, "", employeeType);
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
                } else {
                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setTitle("Information");
                    infoAlert.setHeaderText(null);
                    infoAlert.setContentText("Please confirm that the patient is ready to check out.");
                    infoAlert.showAndWait();
                    showCheckoutDialog(firstName, lastName, PIdentificationNumber, PHealthNumber, table, employeeType);
                }
            }
        });
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Checkout Dialog");
        dialogStage.setScene(new Scene((Parent) alert.getDialogPane().getContent()));
        dialogStage.show();
    }
}
