package Utility;
import Model.Appointment;
import Model.User;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This gets the appointments by customer for the appointments per customer report
 * */

public class getAppointmentsByCustomer {
    /**
     * the customers schedule depending on the selection
     * */
    private static Customer newCustomerSchedule;
    
    /**
     * getting the selected customer
     *
     * @param customerSchedule the selected customer for the report*/
    public static void sendCustomerSelection(Customer customerSchedule) {
        //Setting the customer to the local variable
        newCustomerSchedule = customerSchedule;
    }
    /**Appointments list for the selected customer*/
    public static ObservableList<Appointment> customerSchedule = FXCollections.observableArrayList();


    /**
     * Getting all the appointments related to the selected customer
     * @return Observable list of appointments related to the selected customer
     * @throws SQLException if the query isnt successful or if there was a issue it will throw this exception*/
    public static ObservableList<Appointment> getCustomerSchedule() throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        customerSchedule.clear();
        try {
            Connection conn = DBConn.startConn();
            /**Getting all the appointments by the customer id and returning them*/
            ResultSet results = conn.createStatement().executeQuery("SELECT * FROM appointments WHERE Customer_ID=" + newCustomerSchedule.getCustomerID());
            while (results.next()) {
                customerSchedule.add(new Appointment(
                        results.getInt("Appointment_ID"),
                        results.getString("Title"),
                        results.getString("Description"),
                        results.getString("Type"),
                        results.getTimestamp("Start").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getTimestamp("End").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getTimestamp("Create_Date").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getTimestamp("Last_Update").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getString("Last_Updated_By"),
                        results.getInt("Customer_ID")));

            }
            return customerSchedule;
        } catch (SQLException e) {
            java.util.logging.Logger.getLogger(e.toString());
        }
        return null;
    }
}
