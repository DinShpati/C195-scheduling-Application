package Controller;

/**
 * A contact schedule report controller, gets the data and displays it in a table view
 */

import Model.Appointment;
import Model.Contact;
import Utility.DBConn;
import Utility.getReportsData;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ContactSchedules implements Initializable {


    @FXML
    private Button mainBtn;
    @FXML
    private ComboBox customer;
    @FXML
    private Button exitBtn;
    @FXML
    private TableView AppointmentsTable;
    @FXML
    private TableColumn ID;
    @FXML
    private TableColumn title;
    @FXML
    private TableColumn desc;
    @FXML
    private TableColumn type;
    @FXML
    private TableColumn start;
    @FXML
    private TableColumn end;
    @FXML
    private TableColumn customerID;

    /**
     * This brings the user back to the dashboard
     * @param event Button clicked
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
     * This is exits the app and closes the databse
     * @param event Button clicked
     * */
    @FXML
    void exit(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        exitBtn.setText(sourceButton.getText());
        DBConn.closeConn();
        System.exit(0);
    }

    /**
     * Sending the selection to the database and populating the table
     * @param event comboBox*/
    @FXML
    void contactSchedule(ActionEvent event) throws IOException {
        try {
            /*Selecting the contact and sending it to the database*/
            Contact contactSchedule = (Contact) customer.getSelectionModel().getSelectedItem();
            getReportsData.sendContactSelection(contactSchedule);
            //Displaying the data in the table view
            AppointmentsTable.setItems(getReportsData.getContactSchedule());
            ID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            desc.setCellValueFactory(new PropertyValueFactory<>("description"));
            start.setCellValueFactory(new PropertyValueFactory<>("start"));
            end.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * The list of contacts used in the combobox
     * */
    ObservableList<Contact> contactList = FXCollections.observableArrayList();

    /**
     * Adds the contacts to the list which are then added to the combobox
     * @param resourceBundle resource bundle
     * @param url screen url
     * */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection conn = DBConn.startConn();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM contacts");
            while (rs.next()) {
                //populating the combobox with contact information
                contactList.add(new Contact(rs.getInt("Contact_ID"), rs.getString("Contact_Name"), rs.getString("Email")));
            }
            customer.setItems(contactList);

        }
        catch (SQLException ce) {
            Logger.getLogger(ce.toString());
        }
    }
}