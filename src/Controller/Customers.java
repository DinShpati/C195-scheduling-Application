package Controller;
/**
 * Shows all Customers, Allows user to add, edit, and delete customers
 */

import Model.Customer;
import Utility.getCustomer;
import Utility.DBConn;
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
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Customers implements Initializable {

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> customerID;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    private TableColumn<Customer, String> customerAddress;

    @FXML
    private TableColumn<Customer, String> customerPostalCode;

    @FXML
    private TableColumn<Customer, String> customerPhone;

    @FXML
    private TableColumn<Customer, LocalDateTime> customerCreatedDate;

    @FXML
    private TableColumn<Customer, String> customerCreatedBy;

    @FXML
    private TableColumn<Customer, Timestamp> customerLastUpdate;

    @FXML
    private TableColumn<Customer, String> customerLastUpdateBy;

    @FXML
    private TableColumn<Customer, Integer> customerDivisionID;

    @FXML
    private Button exitBtn;

    /**
     * exiting the application
     *
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
     * Add a new customer to the database
     * @see Utility.getCustomer#addCustomer(Integer, String, String, String, String, LocalDateTime, String, LocalDateTime, String, Integer)
     * @param event
     * @throws IOException
     */
    @FXML
    void sceneCusAdd(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/AddCustomer.fxml"));
        loader.load();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Delete a customer
     * @see Utility.getCustomer#deleteCustomer(int)
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void sceneCusDelete(ActionEvent event) throws SQLException, IOException {
        try {
            Customer selectedItem = customerTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setHeaderText("All associated appointments for " + selectedItem.getCustomerName() + " will be deleted");
            alert.setContentText("Are you sure you want to delete the customer?");

            /**
             * confirm that they want to delete the customer
             * */
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                getCustomer.deleteCustomer(selectedItem.getCustomerID());
                System.out.println("Deletion Successful!");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/View/Dashboard.fxml"));
                Parent parent = loader.load();

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();

            }
        } catch (IOException exc) {
            System.out.println((exc));
        }

    }

    /**
     * Sends the user to the edit customer scene if the user selected a customer from the table if not then it displays an error
     * @see getCustomer#editCustomer(Integer, String, String, String, String, Timestamp, String, Timestamp, String, Integer) 
     * @param event
     * @throws IOException
     * @throws NullPointerException
     */
   @FXML
    void sceneCusEdit(ActionEvent event) throws IOException {

       try {

           Customer modifyCustomer = customerTable.getSelectionModel().getSelectedItem();
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/View/EditCustomer.fxml"));
           Parent parent = loader.load();
           Scene modifyCustomerScene = new Scene(parent);
           EditCustomer controller = loader.getController();
           controller.receiveCustomer(modifyCustomer);

           Stage window = (Stage) ((Button) event.getSource()).getScene().getWindow();
           window.setScene(modifyCustomerScene);
           window.setResizable(false);
           window.show();
       }
       catch (Exception exc) {

           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setHeaderText("Select a Customer");
           alert.setHeaderText("Please select a customer");
           alert.setContentText("No customer has been selected!");
           alert.showAndWait();
       }

   }

    /**
     * Going back to the dashboard
     * @param event button clicked
     * */
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
     * Getting the id of customers and auto-incrementing for new custoemrs
     * */
    public void Dashboard() {
        //Auto-Increment new customer ids
        try {
            Statement statement = DBConn.startConn().createStatement();
            statement.executeUpdate("ALTER TABLE customers AUTO_INCREMENT");
        } catch (SQLException ce) {
            Logger.getLogger(ce.toString());
        }
    }

    /**
     * Gets all the data from the Database and puts it in the table
     * @see getCustomer#getCustomers()
     *
     * @param resourceBundle text localisation
     * @param url screen url
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Customer Table
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postal"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerCreatedDate.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        customerCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        customerLastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        customerLastUpdateBy.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        customerDivisionID.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

        try {
            customerTable.setItems(getCustomer.getCustomers());
        }
        catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
