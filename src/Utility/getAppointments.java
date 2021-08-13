package Utility;

import Controller.AddAppointment;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * getting and setting all the data from and to the database related to appointments
 */

public class getAppointments {
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * Create a list of all the appointments in the database
     * @return returns all the appointments
     * @throws SQLException if it was unable to execute the query
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        allAppointments.clear();
        try {
            Connection conn = DBConn.startConn();
            ResultSet results = conn.createStatement().executeQuery("SELECT * FROM appointments");
            while (results.next()) {
                allAppointments.add(new Appointment(
                        results.getInt("Appointment_ID"),
                        results.getString("Title"),
                        results.getString("Description"),
                        results.getString("Location"),
                        results.getString("Type"),
                        results.getTimestamp("Start").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getTimestamp("End").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getTimestamp("Create_Date").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getString("Created_By"),
                        results.getTimestamp("Last_Update").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getString("Last_Updated_By"),
                        results.getInt("Customer_ID"),
                        results.getInt("User_ID"),
                        results.getInt("Contact_ID")));
            }
            for (Appointment appointment : getAppointments.allAppointments) {
                System.out.println(appointment.getStart());
            }
            return allAppointments;
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return null;
    }

    /**
     * @see Controller.EditAppointment#editAppointment(Appointment)
     * @param appointmentID is auto generated
     * @param title the title of the edited appointment
     * @param description the description of the edited appointment
     * @param location the location of the edited appointment
     * @param type the type appointment of the edited appointment
     * @param start the start date of the edited appointment
     * @param end the end date of the edited appointment
     * @param createDate the created Date of the edited appointment
     * @param createdBy the user that created the edited appointment
     * @param lastUpdate the last time the appointment was edited
     * @param lastUpdatedBy the last user to update
     * @param customerID the customer id of the edited appointment
     * @param userID the user id of the edited appointment
     * @param contactID the contact id of the edited appointment
     * @return returns either true or false depending on the success of the query
     * @throws SQLException throws a exception if the query was unable to execute successfully
     */
    public static boolean editAppointment(Integer appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, Integer customerID, Integer userID, Integer contactID) throws SQLException {
        try {
            Statement statement = DBConn.startConn().createStatement();
            statement.executeUpdate("UPDATE appointments SET Title='"+title+"',Description='"+description+"', Location='"+location+"', Type='"+type+"', Start='"+start+"', End='"+end+"', Create_Date='"+createDate+"',  Created_By='"+createdBy+"', Last_Update='"+lastUpdate+"', Last_Updated_By='"+lastUpdatedBy+"', Customer_ID='"+customerID+"', User_ID='"+userID+"', Contact_ID="+contactID+" WHERE Appointment_ID ="+appointmentID);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * Connect to the DB and add a new appointment
     * @param appointmentID the appointment id of the new appointment
     * @param title the title of the new appointment
     * @param description the description of the new appointment
     * @param location the location of the new appointment
     * @param type the type of the new appointment
     * @param start the start date of the new appointment
     * @param end the end date of the new appointment
     * @param createDate the created date of the new appointment
     * @param createdBy the user that created the new appointment
     * @param lastUpdate the last time it was updated
     * @param lastUpdatedBy the last person to update
     * @param customerID the customer id of the new appointment
     * @param userID the user id of the new appointment
     * @param contactID the contact id of the new appointment
     * @return returns either true or false depending on the success of the query
     */
    public static boolean addAppointment(Integer appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, Integer customerID, Integer userID, Integer contactID) {
    /**
     * @see AddAppointment#OnActionAddAppointment(ActionEvent) ()
     */
        try {
            Statement statement = DBConn.startConn().createStatement();
            statement.executeUpdate("INSERT INTO appointments SET Appointment_ID='"+appointmentID+"',Title='"+title+"',Description='"+description+"', Location='"+location+"', Type='"+type+"', Start='"+start+"', End='"+end+"', Create_Date='"+createDate+"',  Created_By='"+createdBy+"', Last_Update='"+lastUpdate+"', Last_Updated_By='"+lastUpdatedBy+"', Customer_ID='"+customerID+"', User_ID='"+userID+"', Contact_ID="+contactID);
        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }


    /**
     * Delete from DB

     * @param id the id of the appointment that needs to be deleted
     * @return returns true or false depending on the success of the query
     */
    public static boolean deleteAppointment(int id) {
        /**
         * @see Controller.AppointmentMain#sceneDeleteAppointment(ActionEvent)
         */
        try {
            Statement statement = DBConn.startConn().createStatement();
            String deleteQuery = "DELETE FROM appointments WHERE Appointment_ID=" + id;
            statement.executeUpdate(deleteQuery);
            System.out.println(deleteQuery);

        } catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

}
