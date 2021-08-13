package Controller;

/**
 * Appointments per customer report, you select the customer and the appointments for that customer will show up in a tableview**/

import Model.Appointment;
import Model.Customer;
import Model.User;
import Utility.DBConn;
import Utility.getAppointmentsByCustomer;
import Utility.getCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AppointmentsPerCustomer implements Initializable {

    @FXML
    private ComboBox user;
    @FXML
    private Button mainBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private TableView apntTable;
    @FXML
    private TableColumn apntAppointmentID;
    @FXML
    private TableColumn apntTitle;
    @FXML
    private TableColumn apntDescription;
    @FXML
    private TableColumn apntType;
    @FXML
    private TableColumn apntStart;
    @FXML
    private TableColumn apntEnd;
    @FXML
    private TableColumn apntCustomerID;

    /**
     * This brings the user back to the dashboard
     * @param event when the button is clicked
     * */
    @FXML
    void backToMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Dashboard.fxml"));
        loader.load();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This exits the application and closes the database
     * @param event When the button is clicked
     * */
    @FXML
    void exit(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        exitBtn.setText(sourceButton.getText());
        DBConn.closeConn();
        System.exit(0);
    }

    /**
     * this method gets the selected customer and displays the related appointments
     * @param event When the combobox is selected
     * @throws IOException if anything goes wrong we get an error
     * */
    @FXML
    void displayAppointmentsByCustomer(ActionEvent event) throws IOException {

        try {
            /**selecting the customer*/
            Customer customerSchedule = (Customer) user.getSelectionModel().getSelectedItem();
            getAppointmentsByCustomer.sendCustomerSelection(customerSchedule);
            /**Getting the appointments related to the selected customer and displaying to the user*/
            apntTable.setItems(getAppointmentsByCustomer.getCustomerSchedule());
            apntAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            apntTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            apntType.setCellValueFactory(new PropertyValueFactory<>("type"));
            apntDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            apntStart.setCellValueFactory(new PropertyValueFactory<>("start"));
            apntEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            apntCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will set the combobox items from the database as soon as the scene loads
     * @param url screen url
     * @param resourceBundle text localization
     * */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            /**
             *Sets the user comboBox items to the customers taken from the database
             * */
            user.setItems(getCustomer.getCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
