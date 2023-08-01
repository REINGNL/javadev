package hms.controller;

import hms.HMS;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

public class logoutController {

    public static void logout(Tab logoutTab, Stage mainPageStage) {
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
}
