package Controller;

/**
 * This is the controller for the edit appointment view. It allows the user to edit the appointment*/

import Model.Appointment;
import Model.Contact;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Logger;

public class EditAppointment implements Initializable {

    /**
     *  The new modifed appointment
     * */
    private Appointment newModifyAppointment;
    /**
     * The time and date formatter for the timestamp fields
     * */
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    /**
     * This variable is used when converting local time to UTC
     * */
    Long offsetToUTC = Long.valueOf((ZonedDateTime.now().getOffset()).getTotalSeconds());

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
    private TextField apntCustomerID;

    @FXML
    private TextField apntUserID;

    @FXML
    private TextField apntContactID;

    @FXML
    private Button editAppointment;

    @FXML
    private Button exitBtn;

    @FXML
    private TextField apntStart;

    @FXML
    private TextField apntEnd;

    @FXML
    private TextField apntCreatedDate;

    @FXML
    private TextField apntLastUpdated;

    @FXML
    private ComboBox<Contact> apntContactName;

    /**
     * This is the comboBox contact list used when scheduling appointments
     * */
    ObservableList<Contact> contactList = FXCollections.observableArrayList();

    /**
     *This method selects all the contacts from the database and adds them the contactList variable so it can then be added to the comboBox
     * @throws SQLException throws a sql exception
     * */
    public EditAppointment() throws SQLException {
        try {
            Connection conn = DBConn.startConn();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM contacts");
            while (rs.next()) {
                /**Populating the comboBox with contact information*/
                contactList.add(new Contact(rs.getInt("Contact_ID"),rs.getString("Contact_Name"),rs.getString("Email")));

            }
        } catch (SQLException ce) {
            Logger.getLogger(ce.toString());
        }
    }


    /**
     * Setting the values for the edited appointment
     * @see Appointments#sceneApntEdit(ActionEvent)
     * Sets the fields based on selection from Appointment Main Table View
     * @param apnt the appointment object that is used to update the appointment
     */
    @FXML
    public void editAppointment(Appointment apnt)
    {
        newModifyAppointment = apnt;
        apntID.setText(String.valueOf(newModifyAppointment.getAppointmentID()));
        apntTitle.setText(newModifyAppointment.getTitle());
        apntDescription.setText(newModifyAppointment.getDescription());
        apntLocation.setText(newModifyAppointment.getLocation());
        apntType.setText(newModifyAppointment.getType());
        apntStart.setText(newModifyAppointment.getStart().format(formatter));
        apntEnd.setText(newModifyAppointment.getEnd().format(formatter));
        apntLastUpdatedBy.setText(newModifyAppointment.getLastUpdatedBy());
        apntLastUpdated.setText(newModifyAppointment.getLastUpdate().format(formatter));
        apntCreatedBy.setText(newModifyAppointment.getCreatedBy());
        apntCreatedDate.setText(newModifyAppointment.getCreateDate().format(formatter));
        apntCustomerID.setText(String.valueOf(newModifyAppointment.getCustomerID()));
        apntUserID.setText(String.valueOf(newModifyAppointment.getUserID()));
        apntContactID.setText(String.valueOf(newModifyAppointment.getContactID()));
        int comboBoxPreset = newModifyAppointment.getContactID();
        Contact c = new Contact(comboBoxPreset);
        apntContactName.setValue(c);
    }

    /**
     * This is used to exit the current scene and go back to the Appointment view
     * @param event button click
     * */
    @FXML
    void exitToApnt(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/Appointment.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /**
     * This is used to exit the application and close the database connection
     * @param event button click
     * */
    @FXML
    void exit(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        exitBtn.setText(sourceButton.getText());
        DBConn.closeConn();
        System.exit(0);
    }

    /**
     * Sets the values in the comboBox for the divisions
     * @param event comboBox
     */
    @FXML
    void SetDivisionID(ActionEvent event) {
        /**
         * Check if empty and return if it is, otherwise continue
         * */
        if (apntContactName.getSelectionModel().isEmpty()) {
            return;
        }
        else {
            Contact c = apntContactName.getSelectionModel().getSelectedItem();
            apntContactID.setText(String.valueOf(c.getContactID()));
        }
    }

    /**
     * @see getAppointments#editAppointment(Integer, String, String, String, String, LocalDateTime, LocalDateTime, LocalDateTime, String, LocalDateTime, String, Integer, Integer, Integer)
     * Sends the edited appointment to the DB
     * @param event
     * @return
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    boolean editApnt(ActionEvent event) throws SQLException, IOException{
        TimeZone est = TimeZone.getTimeZone("America/New_York");
        Long offsetToEST = Long.valueOf(est.getOffset(new Date().getTime()) /1000 /60);
        LocalDateTime startTime = LocalDateTime.parse(apntStart.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
        /**
         * Setting the time to EST
         * Getting the user local time and converting to UTC
         * Comparing the time to the business hours
         * Check if the new appointments time interferes with other appointments
         */
        startTime = startTime.plus(Duration.ofMinutes(offsetToEST));
        LocalDateTime endTime = LocalDateTime.parse(apntEnd.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
        endTime = endTime.plus(Duration.ofMinutes(offsetToEST));
        LocalTime businessHoursStart = LocalTime.of(8, 00);
        LocalTime businessHoursEnd = LocalTime.of(22, 00);
        LocalDateTime startDateTime = LocalDateTime.parse(apntStart.getText(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(apntEnd.getText(), formatter);
        try {
            /**
             * Checks all fields to make sure they have been entered
             */
            if (apntTitle.getText().isEmpty() || apntDescription.getText().isEmpty() || apntLocation.getText().isEmpty() || apntType.getText().isEmpty()
                    || apntStart.getText().isEmpty() || apntEnd.getText().isEmpty() || apntCreatedDate.getText().isEmpty() || apntCreatedBy.getText().isEmpty()
                    || apntLastUpdated.getText().isEmpty() || apntLastUpdatedBy.getText().isEmpty() || apntCustomerID.getText().isEmpty()
                    || apntUserID.getText().isEmpty() || apntContactID.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing Entries");
                alert.setContentText("Please ensure all fields are entered");
                alert.showAndWait();
            }


            /**
             * Check for overlapping appointments
             */

            for (Appointment appointment : getAppointments.allAppointments) {
                if((startDateTime.isEqual(appointment.getStart()) || startDateTime.isAfter(appointment.getStart()) && startDateTime.isBefore(appointment.getEnd()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("CONFLICT");
                    alert.setContentText("Please enter a time for the start and end time of the appointment that is not already taken");
                    alert.showAndWait();
                    return false;
                }
            }

            /**
             * Check if appointment is in between business hours
             */

            if (startTime.toLocalTime().isBefore(businessHoursStart) || endTime.toLocalTime().isAfter(businessHoursEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("TOO EARLY!");
                alert.setContentText("Please enter a time after business opening hour of 0800 EST and before business closing hours of 1000 EST");
                alert.showAndWait();

            }

            /**
             * @see AppointmentDB#editAppointment(Integer, String, String, String, String, LocalDateTime, LocalDateTime, LocalDateTime, String, LocalDateTime, String, Integer, Integer, Integer)
             *
             */
            else if (!apntTitle.equals("") && !apntType.equals("") && !apntDescription.equals("") && !apntLocation.equals("")){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/View/Dashboard.fxml"));
                Parent parent = loader.load();

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();

                return getAppointments.editAppointment(Integer.valueOf(
                        apntID.getText()),
                        apntTitle.getText(),
                        apntDescription.getText(),
                        apntLocation.getText(),
                        apntType.getText(),
                        LocalDateTime.parse(apntStart.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC)),
                        LocalDateTime.parse(apntEnd.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC)),
                        LocalDateTime.parse(apntCreatedDate.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC)),
                        apntCreatedBy.getText(),
                        LocalDateTime.parse(apntLastUpdated.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC)),
                        apntLastUpdatedBy.getText(),
                        Integer.valueOf(apntCustomerID.getText()),
                        Integer.valueOf(apntUserID.getText()),
                        Integer.valueOf(apntContactID.getText()));
            }
        }
        /**
         * @exception DateTimeParseException e Alert error if not data and time are entered correctly
         */
        catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing selection");
            alert.setContentText("Please ensure all date and time fields are formatted YYYY-MM-DD HH:MM prior to adding an appointment");
            alert.showAndWait();
            return false;
        }
        return false;
    }

    /**
     * Add all the contacts from the contactList to the comboBox
     *
     * @param url screen url
     * @param resourceBundle localization
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apntContactName.setItems(contactList);

    }
}
