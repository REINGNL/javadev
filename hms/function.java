// import javafx.animation.Animation;
// import javafx.animation.KeyFrame;
// import javafx.animation.Timeline;
// import javafx.application.Application;
// import javafx.application.Platform;
// import javafx.beans.binding.Bindings;
// import javafx.beans.property.ReadOnlyStringWrapper;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.geometry.Side;
// import javafx.scene.Scene;
// import javafx.scene.control.*;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.*;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Rectangle;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;
// import javafx.stage.Stage;
// import javafx.util.Duration;

// import java.util.ArrayList;
// import java.util.Date;

// import com.mysql.cj.jdbc.result.ResultSetMetaData;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.Statement;
// import java.text.SimpleDateFormat;

//  private static void tabPane_employeeVerify(String EmployeeType, TabPane tabPane, Tab overviewTab, Tab registerTab,
//             Tab consultationTab, Tab patientTab, Tab checkoutTab,
//             Tab logoutTab) {

//         if (EmployeeType.equals("Front-desk")) {
//             consultationTab.setDisable(true);
//             patientTab.setDisable(true);
//             checkoutTab.setDisable(true);
//         } else if (EmployeeType.equals("Nurse")) {
//             registerTab.setDisable(true);
//             checkoutTab.setDisable(true);
//         } else if (EmployeeType.equals("Doctor")) {
//             registerTab.setDisable(true);
//             checkoutTab.setDisable(true);
//         } else if (EmployeeType.equals("Administrator")) {
//             registerTab.setDisable(true);
//             consultationTab.setDisable(true);
//         }

//         tabPane.getTabs().addAll(overviewTab, registerTab, consultationTab, patientTab, checkoutTab, logoutTab);
//         // System.out.println(EmployeeType);
//     }