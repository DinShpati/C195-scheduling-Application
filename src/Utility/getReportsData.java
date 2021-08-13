package Utility;
import Model.Appointment;
import Model.Contact;
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
 * Getting data need for the reports
 * */

public class getReportsData {
    /**
     * the contacts schedule depending on the contact selection
     * */
    private static Contact newContactSchedule;

    /**
     * Get the selected contact
     * @param contactSchedule the selected contact
     * */
    public static void sendContactSelection(Contact contactSchedule) {
        /**set the contact to a local variable*/
        newContactSchedule = contactSchedule;
    }
    /**Appointments list for the selected contact*/
    public static ObservableList<Appointment> contactSchedule = FXCollections.observableArrayList();

    /**
     * Get the data based on the selected contact amd add it to the list
     * @return returns a list of appointments related to the selected contact
     * @throws SQLException if something goes wrong*/
    public static ObservableList<Appointment> getContactSchedule() throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        contactSchedule.clear();
        try {
            Connection conn = DBConn.startConn();
            /**Getting the data by contact id and storing it in the observable list*/
            ResultSet results = conn.createStatement().executeQuery("SELECT * FROM appointments WHERE Contact_ID=" + newContactSchedule.getContactID());
            while (results.next()) {
                contactSchedule.add(new Appointment(
                        results.getInt("Appointment_ID"),
                        results.getString("Title"),
                        results.getString("Description"),
                        results.getString("Type"),
                        results.getTimestamp("Start").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getTimestamp("End").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getInt("Customer_ID")));

            }
            return contactSchedule;
        } catch (SQLException e) {
            java.util.logging.Logger.getLogger(e.toString());
        }
        return null;
    }
}
