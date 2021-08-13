package Controller;
/**
 * The Main Login screen
 * This will pull the attempts and log them into the logger
 * The username is taken and user to auto fill the created by in the appointments fields
 */

import Model.Appointment;
import Utility.DBConn;
import Utility.getAppointments;
import Utility.getUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;


public class Login implements Initializable{
    private Alert alert;
    private String loginError;
    private String userNPasswordValidator;
    private String errorHeader;

    @FXML
    private Button exitButton;

    @FXML
    private Label yourLocation;

    @FXML
    private Button loginButton;

    @FXML
    private Label detectedLoc;

    @FXML
    private Label CompanyName;

    @FXML
    private Label UserIDText;

    @FXML
    private Label PasswordText;

    @FXML
    private TextField userIDField;

    @FXML
    private PasswordField passwordField;

    /**
     * The validates the user login by getting the credentials and checking for them in the database
     * It also checks if the user has any appointments in the next 15 min, if the user logins in successfully
     *
     * @param event button click
     * */
    @FXML
    void login(ActionEvent event) throws IOException, SQLException {
        //Get the users credentials
        String username = userIDField.getText();
        String password = passwordField.getText();

        //verify the users credentials
        boolean verifiedUser = getUser.login(username, password);
        if (verifiedUser) {
            boolean isFound = true;

            getAppointments.getAllAppointments();
            //labmda loop that loops through the appointments and looks for appointments within 15 min
            for (Appointment appointment : getAppointments.allAppointments) {
                LocalDateTime within15Minutes = LocalDateTime.now();
                isFound = true;
                //If there is a appointment for today, get the local time, convert it and check its within 15 min of the next appointment
                if (within15Minutes.isAfter(appointment.getStart().minusMinutes(15)) && within15Minutes.isBefore(appointment.getStart())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("UPCOMING APPOINTMENT");
                    alert.setContentText("Appointment: " + appointment.getAppointmentID() + " starts at " + appointment.getStart());
                    alert.showAndWait();
                    isFound = true;
                    break;
                }
                else {
                    isFound = false;
                }
            }
            if (isFound != true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No upcoming appointments!");
                alert.setContentText("You have no upcoming appointments in the next 15 minutes");
                alert.showAndWait();
            }

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
        else if (!verifiedUser || userIDField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(loginError);
            alert.setHeaderText(errorHeader);
            alert.setContentText(userNPasswordValidator);
            alert.showAndWait();
        }

    }

    /**
     * This exits the app and closes the database connection
     * @param event button click
     * */
    @FXML
    void exit(ActionEvent event) throws IOException {
        Button sourceButton = (Button) event.getSource();
        exitButton.setText(sourceButton.getText());
        DBConn.closeConn();
        System.exit(0);
    }

    /**
     * This initializes the scene by getting the resource bundle and checking the language of the user
     * It looks for EN (English) or FR (French)
     *
     * @param url screen url
     * @param rb text localization and language detection
     * */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        detectedLoc.setText(Locale.getDefault().getLanguage());
        try {
            rb = ResourceBundle.getBundle("Resources/Global", Locale.getDefault());

            if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
                CompanyName.setText(rb.getString("CompanyName"));
                UserIDText.setText(rb.getString("UserIDText"));
                PasswordText.setText(rb.getString("PasswordText"));
                loginButton.setText(rb.getString("Login"));
                exitButton.setText(rb.getString("Exit"));
                yourLocation.setText(rb.getString("yourLocation"));
                loginError = rb.getString("loginError");
                userNPasswordValidator = rb.getString("userNPasswordValidator");
                errorHeader = rb.getString(errorHeader);

            }
        } catch (Exception e) {
        }
    }
}
