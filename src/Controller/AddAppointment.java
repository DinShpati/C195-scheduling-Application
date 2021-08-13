package Controller;

/**
 * This is the controller for the add appointments view
 * @author Din Spataj
 * */

import Model.Appointment;
import Model.Contact;
import Model.User;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Logger;

import static java.lang.Integer.valueOf;

public class AddAppointment implements Initializable {

    @FXML
    private TextField apntID;

    @FXML
    private TextField apntTitle;

    @FXML
    private TextField apntDescription;

    @FXML
    private TextField apntLocation;

    @FXML
    private TextField apntType;

    @FXML
    private TextField apntCreatedBy;

    @FXML
    private TextField apntLastUpdatedBy;

    @FXML
    private TextField apntStart;

    @FXML
    private TextField apntEnd;

    @FXML
    private TextField apntCreatedDate;

    @FXML
    private TextField apntLastUpdated;

    @FXML
    private TextField customerId;

    @FXML
    private TextField userId;

    @FXML
    private TextField contactId;

    @FXML
    private ComboBox<Contact> contactName;

    @FXML
    private Button addAppointment;

    @FXML
    private Button exitBtn;

    /**
     * these two variable are used when converting the users local time to UTC and making sure they are formatted properly*/
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Long offsetToUTC = Long.valueOf((ZonedDateTime.now().getOffset()).getTotalSeconds());


    /**
     * Exits the add appointment view and brings the user back to the appointments
     * @param event Action event
     * @throws IOException If there is a error
     * */
    @FXML
    void exitAppointment(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/Appointment.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
    /**
     * Exits the application
     * */
    @FXML
    void exit(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        exitBtn.setText(sourceButton.getText());
        DBConn.closeConn();
        System.exit(0);
    }

    /**
     * Used to set the ID of the Contact ID field based on what name the user chose in the combobox
     * @param event
     * @throws IOException
     */
    @FXML
    private void SetContactName (ActionEvent event) throws IOException {
        if (contactName.getSelectionModel().isEmpty()) {
            return;
        }
        else {
            Contact c = contactName.getSelectionModel().getSelectedItem();
            contactId.setText(String.valueOf(c.getContactID()));
        }
    }

    /**
     * The list of contacts for the dropdown
     * */
    ObservableList<Contact> contactList = FXCollections.observableArrayList();

    /**
     * All the dates entered are converted to EST.
     * @param event
     * @see getAppointments#addAppointment(Integer, String, String, String, String, LocalDateTime, LocalDateTime, LocalDateTime, String, LocalDateTime, String, Integer, Integer, Integer)
     * @return
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    boolean addAppointment(ActionEvent event) throws IOException, SQLException {
        try {
            //get the users TimeZone and convert to EST

            TimeZone est = TimeZone.getTimeZone("America/New_York");
            Long offsetToEST = Long.valueOf(est.getOffset(new Date().getTime()) /1000 /60);
            Integer appointmentID = valueOf(apntID.getText());
            String title = apntTitle.getText();
            String description = apntDescription.getText();
            String location = apntLocation.getText();
            String type = apntType.getText();
            LocalDateTime start = LocalDateTime.parse(apntStart.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
            LocalDateTime end = LocalDateTime.parse(apntEnd.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
            LocalDateTime createDate = LocalDateTime.parse(apntCreatedDate.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
            String createdBy = apntCreatedBy.getText();
            LocalDateTime lastUpdate = LocalDateTime.parse(apntLastUpdated.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
            String lastUpdatedBy = apntLastUpdatedBy.getText();
            Integer customerID = valueOf(customerId.getText());
            Integer userID = valueOf(userId.getText());
            Integer contactID = valueOf(contactId.getText());
            /**
             *Get the time, convert it to EST, and compare it to the business hours
             */
            LocalDateTime startTime = LocalDateTime.parse(apntStart.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));

            startTime = startTime.plus(Duration.ofMinutes(offsetToEST));
            /**
             *Get the users time zone and set it to utc and set the end time to EST
             */
            LocalDateTime endTime = LocalDateTime.parse(apntEnd.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
            endTime = endTime.plus(Duration.ofMinutes(offsetToEST));

            /**
             * Compare the start and end time to business hours and check if the time is overlapping other appointments
             */
            LocalTime businessHoursStart = LocalTime.of(8, 00);
            LocalTime businessHoursEnd = LocalTime.of(22, 00);

            LocalDateTime startDateTime = LocalDateTime.parse(apntStart.getText(), formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(apntEnd.getText(), formatter);

            /**
             * Using a loop to check for overlapping appointment times
             */

            for (Appointment appointment : getAppointments.allAppointments) {
                if((startDateTime.isEqual(appointment.getStart()) || startDateTime.isAfter(appointment.getStart()) && startDateTime.isBefore(appointment.getEnd()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("There already is an appointment during that time, please change the start/end time.");
                    alert.showAndWait();
                    return false;
                }
            }

            /**
             * Checking if the time selected is between business hours...
             */

            if (startTime.toLocalTime().isBefore(businessHoursStart) || endTime.toLocalTime().isAfter(businessHoursEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Time!");
                System.out.println(endTime.toLocalTime() + " " + startTime.toLocalTime());
                alert.setContentText("Please enter a time after opening hour of 08:00 EST and before closing hours of 10:00 EST");
                alert.showAndWait();

            } else if (!title.equals("") && !type.equals("") && !description.equals("") && !location.equals("")) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/View/Dashboard.fxml"));
                Parent parent = loader.load();

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();

                return getAppointments.addAppointment(appointmentID, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);}

        }
        catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please ensure all date and time fields are formatted YYYY-MM-DD HH:MM");
            alert.showAndWait();
        }
        return false;
    }

    /**
     * Adding contacts to the combobox
     * @throws SQLException Adds appointments to the list
     */
    public AddAppointment() throws SQLException {

        try {
            Connection conn = DBConn.startConn();
            ResultSet results = conn.createStatement().executeQuery("SELECT * FROM contacts");
            while (results.next()) {
                /**
                 * Create a contact object and add it to the contact list
                 */
                contactList.add(new Contact(results.getInt("Contact_ID"),results.getString("Contact_Name"),results.getString("Email")));
            }
        } catch (SQLException ce) {
            Logger.getLogger(ce.toString());
        }
    }


    /**
     * Adds all the information needed as soon as the page opens
     * @param url screen url
     * @param resourceBundle resource bundle localization
     * */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Auto populate the created by, last updated by, created date, and the last updated date
         */
        apntCreatedBy.setText(String.valueOf(User.getUsername()));
        apntLastUpdatedBy.setText(String.valueOf(User.getUsername()));
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        apntCreatedDate.setText(sdf.format(cal.getTime()));
        apntLastUpdated.setText(sdf.format(cal.getTime()));

        try {
            /**
             * Connecting to the DB
             */
            Connection conn = DBConn.startConn();
            /**
             * Select the the maximum appointment id and incrementing it by one to set the id for the new appointment
             */
            ResultSet results = conn.createStatement().executeQuery("SELECT MAX(Appointment_ID) AS highestID FROM appointments");
            while (results.next()) {
                int tempID = results.getInt("highestID");
                apntID.setText(String.valueOf(tempID + 1));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        contactName.setItems(contactList);
    }
}