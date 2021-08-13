package Controller;

/**Add customer controller adds a customer to the database*/

import Model.Countries;
import Model.Divisions;
import Model.User;
import Utility.getCountries;
import Utility.getCustomer;
import Utility.DBConn;
import Utility.getDivisions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable{

    /**
     * format the date and time
     */
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Long offsetToUTC = Long.valueOf((ZonedDateTime.now().getOffset()).getTotalSeconds());

    @FXML
    private Button exitBtn;

    @FXML
    private TextField custID;

    @FXML
    private TextField custName;

    @FXML
    private TextField custAddress;

    @FXML
    private TextField custPostalCode;

    @FXML
    private TextField custPhone;

    @FXML
    private TextField custCreatedBy;

    @FXML
    private TextField custCreatedDate;

    @FXML
    private TextField custLastUpdate;

    @FXML
    private TextField custLastUpdateBy;


    @FXML
    private ComboBox<Divisions> custDivisionID;

    @FXML
    private ComboBox<Countries> custCountry;

    public AddCustomer() throws SQLException {
    }

    /**
     * Exits the user from the application
     * and closes the databse
     *
     * @param event When the buttton is clicked
     *
     * */

    @FXML
    void exit(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        exitBtn.setText(sourceButton.getText());
        DBConn.closeConn();
        System.exit(0);
    }

    /**
     * Gets the fields and add them to the database
     * @see getCustomer#addCustomer(Integer, String, String, String, String, LocalDateTime, String, LocalDateTime, String, Integer)
     * @param event
     * @return
     * @throws IOException
     */
    @FXML
    boolean addCustomer(ActionEvent event) throws IOException {
        try {
            Integer customerID = Integer.valueOf(custID.getText());
            String custname = custName.getText();
            String custaddress = custAddress.getText();
            String custpostal = custPostalCode.getText();
            String custphone = custPhone.getText();
            LocalDateTime createDate = LocalDateTime.parse(custCreatedDate.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
            String createdBy = custCreatedBy.getText();
            LocalDateTime lastUpdate = LocalDateTime.parse(custLastUpdate.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC));
            String lastUpdatedBy = custLastUpdateBy.getText();
            int divisionID = Integer.valueOf(String.valueOf(custDivisionID.getSelectionModel().getSelectedItem().getDivisionID()));

            /**
             * Makes sure that all fields are populated and send a error if a field has not been populated
             */
            if (!custname.equals("") && !custaddress.equals("") && !custpostal.equals("") && !custphone.equals("")) {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/View/Dashboard.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();

                return getCustomer.addCustomer(customerID, custname, custaddress, custpostal, custphone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing value");
                alert.setContentText("Please enter a value for customer name, address, postal code, and phone number for customer");
                alert.showAndWait();
            }
            return false;
            /**
             * Checks the formatting in the date time and sends a error if there isn't proper formatting
             */
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing value");
            alert.setContentText("Make sure that all date and time fields are formatted YYYY-MM-DD HH:MM before to adding an appointment");
            alert.showAndWait();
            return true;
        }
    }

    /**
     * Exiting the current scene to go back to the customer scene
     * @param event when the button is clicked
     *
     * */

    @FXML
    void exitAddCustomer(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/Customer.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /**
     * Division ID ComboBox
     */

    ObservableList<Divisions> fldList = getDivisions.getAllDivisions();


    /**
     * Each country has its own list of divisions
     * @see getDivisions#usFilteredDivisions
     * @see getDivisions#ukFilteredDivisions
     * @see getDivisions#caFilteredDivisions
     * @param event on selection of one of the countries the filtered lists will be set in the division combo box
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void SetDivisionID(MouseEvent event) throws IOException, SQLException {

        if (custCountry.getSelectionModel().isEmpty()) {
            System.out.println(custCountry.getSelectionModel().toString());
            return;
        }

        //US Filter
        else if (custCountry.getSelectionModel().getSelectedItem().getCountry().equals("U.S")) {
            try {
                custDivisionID.setItems(getDivisions.getusFilteredDivisions());
            }
            catch (SQLException exc) {
                exc.printStackTrace();
            }
        }

        //Canada Filter
        else if (custCountry.getSelectionModel().getSelectedItem().getCountry().equals("Canada")) {
            try {
                custDivisionID.setItems(getDivisions.getcanadaFilteredDivisions());

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
        //UK Filter
        else if (custCountry.getSelectionModel().getSelectedItem().getCountry().equals("UK")) {
            try {
                custDivisionID.setItems(getDivisions.getukFilteredDivisions());

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }

    }

    /**
     * Setting the values for the combo boxes, the customer ID, the created by, the last edited by, and setting the current time and date
     * for both of the time and date fields so the user wont have to bother with typing it in.
     * @param url url needed to initialize
     * @param resourceBundle the resource bundle needed to initialize
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            custCountry.setItems(getCountries.getAllCountries());

        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        try {
            custDivisionID.setItems(getDivisions.getAllDivisions());

        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        /**
         * Auto populate the created by and last updated by with the last user login information
         */

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        custCreatedBy.setText(String.valueOf(User.getUsername()));
        custCreatedDate.setText(sdf.format(cal.getTime()));
        custLastUpdateBy.setText(String.valueOf(User.getUsername()));
        custLastUpdate.setText(sdf.format(cal.getTime()));
        try {
            // Connection to the database
            Connection conn = DBConn.startConn();
            //Select the last row from Customer ID so we can add one later on
            ResultSet results = conn.createStatement().executeQuery("SELECT Customer_ID FROM customers ORDER BY Customer_ID DESC LIMIT 1 ");
            // SQL query

            /**
             * @param custID is automatically generated
             */
            while (results.next()) {

                int tempID = results.getInt("Customer_ID");
                //Set the customer ID to the previous id plus 1
                custID.setText(String.valueOf(tempID + 1));

            }


        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

}
