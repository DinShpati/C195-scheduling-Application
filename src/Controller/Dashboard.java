package Controller;

/**
 * The dashboard allows access to all the other links such as reports, customers, and appointments
 */

import Utility.DBConn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard {

    @FXML
    private Button appointmentReport;

    @FXML
    private Button contactScheduleReport;

    @FXML
    private Button removedCustomerReport;

    @FXML
    private Button custMain;

    @FXML
    private Button aptMain;

    @FXML
    private Button exitBtn;

    @FXML
    private Button logoutBtn;

    /**
     * Exiting the application
     * @param event
     */
    @FXML
    void exit(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        exitBtn.setText(sourceButton.getText());
        DBConn.closeConn();
        System.exit(0);
    }

    /**
     * Access to the appointments
     * @param event
     * @throws IOException
     */
    @FXML
    void sceneApptMain(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/Appointment.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
    /**
     * Access to the customers
     * @param event
     * @throws IOException
     */
    @FXML
    void sceneCustomerMain(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/Customer.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /**
     * Appointments by type & month
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void sceneAppointmentReport(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/CustomerByTypeAndMonth.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /**
     * Access to the Schedule per Contact report
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void sceneContactScheduleReport(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/ContactSchedules.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /**
     * Access to the appointments per customer report
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void sceneCustomerByAppointments(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/AppointmentsPerCustomer.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /**
     *Access to the login screen
     *
     * @param event
     * @throws IOException
     * */
    @FXML
    void sceneLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
