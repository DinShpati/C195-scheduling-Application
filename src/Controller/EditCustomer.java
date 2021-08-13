package Controller;

/**
 * This is the controller for the editCustomer view, it allows you to edit customer information*/

import Model.User;
import Utility.DBConn;
import javafx.fxml.Initializable;
import Model.Countries;
import Model.Customer;
import Model.Divisions;
import Utility.getCountries;
import Utility.getCustomer;
import Utility.getDivisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class EditCustomer implements Initializable {

    /**
     * The formatter variable formats the timestamp when the user enters the timestamp to make sure it is properly formatted for the database
     * */
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    /**
     * This is needed to convert the local users time to UTC
     * */
    Long offsetToUTC = Long.valueOf((ZonedDateTime.now().getOffset()).getTotalSeconds());

    private Customer newModifyCustomer;

    @FXML
    private Button exit;

    @FXML
    private Button editCustomer;

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
    private TextField custLastUpdatedBy;

    @FXML
    private TextField custLastUpdate;

    @FXML
    private TextField custCreatedBy;

    @FXML
    private TextField custCreateDate;

    @FXML
    private ComboBox<Countries> custCountry;

    @FXML
    private ComboBox<Divisions> custDivisionID;

    public EditCustomer() throws SQLException {
    }

    /**
     * Exits the app and close the database connection
     *
     * @param event button click
     * */
    @FXML
    void exit(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        exit.setText(sourceButton.getText());
        DBConn.closeConn();
        System.exit(0);
    }

    /**
     * Returns the updated customer to the database
     * @see getCustomer#editCustomer(Integer, String, String, String, String, Timestamp, String, Timestamp, String, Integer)
     * @param event
     * @return
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    boolean editCustomer(ActionEvent event) throws SQLException, IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/Dashboard.fxml"));
            Parent parent = loader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

            return getCustomer.editCustomer(
                    Integer.valueOf(custID.getText()),
                    custName.getText(),
                    custAddress.getText(),
                    custPostalCode.getText(),
                    custPhone.getText(),
                    Timestamp.valueOf(LocalDateTime.parse(custCreateDate.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC))),
                    custCreatedBy.getText(),
                    Timestamp.valueOf(LocalDateTime.parse(custLastUpdate.getText(), formatter).minus(Duration.ofSeconds(offsetToUTC))),
                    custLastUpdatedBy.getText(),
                    Integer.valueOf(String.valueOf(custDivisionID.getSelectionModel().getSelectedItem().getDivisionID())));
        }
        catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing selection");
            alert.setContentText("Please ensure all date and time fields are formatted YYYY-MM-DD HH:MM prior to adding an appointment");
            alert.showAndWait();
            return false;
        }

    }

    /**
     *Gets the selected object and displays the information
     * @see Customers#sceneCusEdit(ActionEvent)
     * @param modifyCustomer this is the Customer object needed to update/modify the selected customer
     */
    @FXML
    public void receiveCustomer(Customer modifyCustomer)
    {

            newModifyCustomer = modifyCustomer;
            custID.setText(String.valueOf(newModifyCustomer.getCustomerID()));
            custName.setText(newModifyCustomer.getCustomerName());
            custAddress.setText(newModifyCustomer.getAddress());
            custPostalCode.setText(newModifyCustomer.getPostal());
            custPhone.setText(String.valueOf(newModifyCustomer.getPhone()));Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            custLastUpdatedBy.setText(String.valueOf(User.getUsername()));
            custLastUpdate.setText(sdf.format(cal.getTime()));

            custCreatedBy.setText(newModifyCustomer.getCreatedBy());
            custCreateDate.setText(newModifyCustomer.getCreateDate().format(formatter));
            int comboBoxPreset = newModifyCustomer.getDivisionID();
            Divisions div = new Divisions(comboBoxPreset);
            try{

                System.out.println(getDivisions.getDivisionByID(comboBoxPreset));
                custDivisionID.setValue(getDivisions.getDivisionByID(comboBoxPreset));

            }catch(SQLException exc){
                exc.printStackTrace();
            }

        /**
         * This sets the comboBox for the division based on the comboBox for the country
         */
            if (div.getDivisionID() <= 54)
            {
                String countryName = "U.S";
                Countries c = new Countries(countryName);
                custCountry.setValue(c);
            }
            else if (div.getDivisionID() > 54 && div.getDivisionID() <= 72)
            {
                String countryName = "Canada";
                Countries c = new Countries(countryName);
                custCountry.setValue(c);
            }
            else if (div.getDivisionID() > 72)
            {
                String countryName = "UK";
                Countries c = new Countries(countryName);
                custCountry.setValue(c);
            }

            try {
                custCountry.setItems(getCountries.getAllCountries());

            } catch (SQLException exc) {
                exc.printStackTrace();
            }

            try {
                custDivisionID.setItems(getDivisions.getAllDivisions());

                if (custCountry.getSelectionModel().getSelectedItem().getCountry().equals("U.S")) {
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

            } catch (SQLException exc) {
                exc.printStackTrace();
            }
    }

    /**
     * This sets the division id of the division combobox after selecting the customers country box
     *
     * @param event comboBox mouse select
     * */
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
     * This exits to the customer view
     * @param event button click
     * */
    @FXML
    void exitEditCustomer(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/Customer.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
