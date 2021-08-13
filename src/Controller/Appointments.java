package Controller;

/**
 * Main Appointment class where you can add, edit, and delete Appointments
 */

import Model.Appointment;
import Utility.getAppointments;
import Utility.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static Utility.getAppointments.deleteAppointment;

public class Appointments implements Initializable{

    /**
     * @param allApnts selects all appointments
     */
    @FXML
    private RadioButton allApnts;

    @FXML
    private ToggleGroup apntTableToggle;
    /**
     * @param weekly selects weekly appointments
     */
    @FXML
    private RadioButton weekly;

    /**
     * @param monthly selects monthly appointments
     */
    @FXML
    private RadioButton monthly;


    @FXML
    private TableView<Appointment> apntTable;

    @FXML
    private TableColumn<Appointment, Integer> apntAppointmentID;

    @FXML
    private TableColumn<Appointment, String> apntTitle;

    @FXML
    private TableColumn<Appointment, String> apntDescription;

    @FXML
    private TableColumn<Appointment, String> apntLocation;

    @FXML
    private TableColumn<Appointment, String> apntType;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apntStart;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apntEnd;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apntCreatedDate;

    @FXML
    private TableColumn<Appointment, String> apntCreatedBy;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apntLastUpdate;

    @FXML
    private TableColumn<Appointment, String> apntLastUpdateBy;

    @FXML
    private TableColumn<Appointment, Integer> apntCustomerID;

    @FXML
    private TableColumn<Appointment, Integer> apntUserID;

    @FXML
    private TableColumn<Appointment, Integer> apntContactID;

    @FXML
    private Button addAppointment;

    @FXML
    private Button editAppointment;

    @FXML
    private Button deleteAppointment;

    @FXML
    private Button menuButton;

    @FXML
    private Button exitButton;

    /**
     * @param appointmentList this is a list of all the appointments
     * @param weeklyAppointmentList this is a list of all the appointments this week
     * @param monthlyAppointmentList this is a list of all the appointments this month
     * */
    ObservableList<Appointment> appointmentList = getAppointments.getAllAppointments();
    ObservableList<Appointment> weeklyAppointmentList = FXCollections.observableArrayList();
    ObservableList<Appointment> monthlyAppointmentList = FXCollections.observableArrayList();

    /**
     * This handles the exception for the lists above
     * @throws SQLException when the query isnt returned
     * */
    public Appointments() throws SQLException {
    }

    /**
     * @param event
     * @throws SQLException
     */
    @FXML
    void allAppointments(ActionEvent event) throws SQLException {

        try {
            apntTable.setItems(getAppointments.getAllAppointments());

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Filter for the weekly appointment radio button
     *
     * @param event
     */

    @FXML
    void weeklyAppointments(ActionEvent event) throws SQLException {
        /**Getting todays date and the date of one week from now to calculate weekly appointments*/
        LocalDate today = LocalDate.from(ZonedDateTime.now());
        LocalDate oneWeekFromNow = LocalDate.from(ZonedDateTime.now()).plusWeeks(1);


        if ((this.apntTableToggle.getSelectedToggle().equals(this.weekly))) {
            /**Checking if the appointments date is today, after today, or before one week from now*/
            Predicate<Appointment> weeklyView = appointment -> (appointment.getStart().toLocalDate().equals(today))
                    || appointment.getStart().toLocalDate().isAfter(today)
                    && appointment.getStart().toLocalDate().isBefore((oneWeekFromNow));

            var weeklyApnts = appointmentList.stream().filter(weeklyView).collect(Collectors.toList());
            /**Displaying the weekly appointments*/
            apntTable.setItems(weeklyAppointmentList = FXCollections.observableList(weeklyApnts));
        }
    }

    /**
     * Filters for the monthly appointment radio button
     *
     * @param event
     */
    @FXML
    void monthlyAppointments(ActionEvent event) {

        /**Getting todays date and the date of one month from now to calculate monthly appointments*/
        LocalDate today = LocalDate.from(ZonedDateTime.now());
        LocalDate oneMonthFromNow = LocalDate.from(ZonedDateTime.now()).plusMonths(1);

        /**Checking if the appointments date is today, after today, or before one month from now*/
        if ((this.apntTableToggle.getSelectedToggle().equals(this.monthly))) {
            Predicate<Appointment> monthlyView = appointment -> (appointment.getStart().toLocalDate().equals(today))
                    || appointment.getStart().toLocalDate().isAfter((today))
                    && appointment.getStart().toLocalDate().isBefore((oneMonthFromNow));

            var monthApnts = appointmentList.stream().filter(monthlyView).collect(Collectors.toList());
            /** Displaying the monthly appointments*/
            apntTable.setItems(monthlyAppointmentList = FXCollections.observableList(monthApnts));
        }
    }

    /**Exiting the program*/
    @FXML
    void exit(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        exitButton.setText(sourceButton.getText());
        DBConn.closeConn();
        System.exit(0);
    }

    /**
     * Add a new appointment to the database
     * @see getAppointments#addAppointment(Integer, String, String, String, String, LocalDateTime, LocalDateTime, LocalDateTime, String, LocalDateTime, String, Integer, Integer, Integer)
     */

    @FXML
    void sceneApntAdd(ActionEvent event) throws IOException {

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/AddAppointment.fxml"));
            loader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }
    /**
     * Delete a appointment to the database
     */
    @FXML
    void sceneApntDelete(ActionEvent event) {
        try {
            /**
             * @see AppointmentDB#deleteAppointment(int)
             */
            Appointment selectedItem = apntTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setHeaderText("Delete appointment type: " + selectedItem.getType() + " ID Number: " + selectedItem.getAppointmentID() + " ?");
            alert.setContentText("Are you sure you want to delete the appointment?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                deleteAppointment(selectedItem.getAppointmentID());
                System.out.println("Appointment: " + selectedItem.getAppointmentID() + " Successful!");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/View/Appointment.fxml"));
                Parent parent = loader.load();

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();

            }
        } catch (IOException e) {
            String s = "a customer";
        }
    }

    /**
     * Edit a appointment to the database
     * @param event Button clicked
     */
    @FXML
    void sceneApntEdit(ActionEvent event) throws IOException {
        try {
            /**
             * @see AppointmentDB#editAppointment(Integer, String, String, String, String, LocalDateTime, LocalDateTime, LocalDateTime, String, LocalDateTime, String, Integer, Integer, Integer)
             */
            Appointment modifyAppointment = apntTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/EditAppointment.fxml"));
            Parent parent = loader.load();
            Scene modifyCustomerScene = new Scene(parent);
            /**Getting the selected appointment and sending it to the edit scene*/
            EditAppointment controller = loader.getController();
            controller.editAppointment(modifyAppointment);

            Stage window = (Stage) ((Button) event.getSource()).getScene().getWindow();
            window.setScene(modifyCustomerScene);
            window.setResizable(false);
            window.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing selection");
            alert.setContentText("Please select an appointment.");
            alert.showAndWait();
        }
    }

    /**
     * Going back to the dashboard
     * @param event Button clicked
     */
    @FXML
    void sceneLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Dashboard.fxml"));
        loader.load();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This initializes the scene by adding all the appointments as soon as the scene loads
     * @param resourceBundle text localization
     * @param url screen url
     * */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Appointments Table
        apntAppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apntTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apntLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        apntDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        apntType.setCellValueFactory(new PropertyValueFactory<>("type"));
        apntStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        apntEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        apntCreatedDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        apntCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        apntLastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        apntLastUpdateBy.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        apntCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apntUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        apntContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        try {
            apntTable.setItems(getAppointments.getAllAppointments());

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
