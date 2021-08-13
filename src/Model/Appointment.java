package Model;
/**
 * Appointment model for Appointment objects
 */

import java.time.LocalDateTime;
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Constructor for the appointments view
     *
     * @param appointmentID the Appointment ID
     * @param title The appointment title
     * @param description the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param start the appointment start time
     * @param end the appointment end time
     * @param createDate the appointments created date
     * @param createdBy the user that created the appointment
     * @param lastUpdate the appointments last update date
     * @param lastUpdatedBy the user that last updated appointment
     * @param contactID The contact ID in the application
     * @param customerID the customer ID of the application
     * @param userID the user ID of the application
     * */

    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) {
        this.appointmentID=appointmentID;
        this.title=title;
        this.description=description;
        this.location=location;
        this.type=type;
        this.start=start;
        this.end=end;
        this.createDate=createDate;
        this.createdBy=createdBy;
        this.lastUpdate=lastUpdate;
        this.lastUpdatedBy=lastUpdatedBy;
        this.customerID=customerID;
        this.userID=userID;
        this.contactID=contactID;
    }

    /**Constructor For weekly and monthly Appointment Schedules
     *
     * @param appointmentID This is the appointment ID of the weekly and monthly appointment
     * @param title this is the title of the weekly and monthly appointment
     * @param location this is the location of the weekly and monthly appointment
     * @param type this is the type of the weekly and monthly appointment
     * @param description this is the description of the weekly and monthly appointment
     * @param contactID this is the contact id of the weekly and monthly appointment
     * @param customerID this is the customers id of the weekly and monthly appointment
     * @param start this is the start date and time of the weekly and monthly appointment
     * @param end this is the end date and time for the weekly and monthly appointment
     *
     * */
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int contactID) {
        this.appointmentID=appointmentID;
        this.title=title;
        this.description=description;
        this.location=location;
        this.type=type;
        this.start=start;
        this.end=end;
        this.customerID=customerID;
        this.contactID=contactID;
    }

    /**
     * Constructor for Contact schedule and Customer Schedule Report
     * @param appointmentID this appointments id need for the contact and customer schedule report
     * @param title this is the title of the appointment for the for the contact and customer schedule report
     * @param description this is the description for the contact and customer schedule report
     * @param type this is the type of appointment for the contact and customer schedule report
     * @param start this is the start date and time for the contact and customer schedule report
     * @param end this is the end date and time for the contact and customer schedule report
     * @param customerID this is the customer id for the contact and customer schedule report
     */
    public Appointment(int appointmentID, String title, String description, String type, LocalDateTime start, LocalDateTime end, int customerID) {
        this.appointmentID=appointmentID;
        this.title=title;
        this.description=description;
        this.type=type;
        this.start=start;
        this.end=end;
        this.customerID=customerID;

    }
    /**Constructor for appointments created per user report
     * @param appointment_id This is the appointment for the created per user report
     * @param title this is the title of the appointment for the created per user report
     * @param description this is the description of the appointment for the created per user report
     * @param create_date this is the date that the appointment was created
     * @param start this is the start date of the appointment
     * @param end this is the end date of the appointment
     * @param customer_id this is the customer id for the appointment
     * @param last_update this is the date of the last time the appointment was updated
     * @param last_updated_by this the last user to update the appointment
     * @param type this is the type of appointment */
    public Appointment(int appointment_id, String title, String description, String type, LocalDateTime start, LocalDateTime end, LocalDateTime create_date, LocalDateTime last_update, String last_updated_by, int customer_id) {
        this.appointmentID=appointment_id;
        this.title=title;
        this.description=description;
        this.type=type;
        this.start=start;
        this.end=end;
        this.customerID=customer_id;
        this.createDate=create_date;
        this.lastUpdate=last_update;
        this.lastUpdatedBy=last_updated_by;
    }

    /**
     * Get the appointment id
     * @return appointment id
     * */
    public int getAppointmentID() {
        return appointmentID;
    }
    /**
     *Set the appointment id
     * @param appointmentID the appointments id
     * */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }
    /**
     * Get the appointment title
     * @return title
     * */
    public String getTitle() {
        return title;
    }
    /**
     * set the appointment title
     * @param title the appointments title
     * */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     *get the appointment description
     * @return description
     * */
    public String getDescription() {
        return description;
    }
    /**
     *set the appointment description
     * @param description the appointments description
     * */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * get the appointment location
     * @return location
     * */
    public String getLocation() {
        return location;
    }
    /**
     * set the appointment location
     * @param location the appointments location
     * */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * get the appointment type
     * @return type
     * */
    public String getType() {
        return type;
    }
    /**
     *set the appointment type
     * @param type the appointments type
     * */
    public void setType(String type) {
        this.type = type;
    }
    /**
     *get the appointment start date
     * @return start date
     * */
    public LocalDateTime getStart() {
        return start;
    }
    /**
     * set the appointment start date
     * @param start the appointments start date
     * */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    /**
     * get the appointment end date
     * @return end date
     * */
    public LocalDateTime getEnd() {
        return end;
    }
    /**
     * set the appointment end date
     * @param end the appointments end date
     * */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    /**
     * get the appointment created date
     * @return created date
     * */
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    /**
     * set the appointment created date
     * @param createDate the appointments create date
     * */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    /**
     * get the created by
     * @return created by (user)
     * */
    public String getCreatedBy() {
        return createdBy;
    }
    /**
     * set the created by
     * @param createdBy the user that created the appointment
     * */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /**
     * get the last update date
     * @return last update date
     * */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    /**
     * set the last update date
     * @param lastUpdate the last time the appointment was updated
     * */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /**
     * get the last update by user
     * @return update by (user)
     * */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /**
     *set the last update by user
     * @param lastUpdatedBy the last user to update the appointment
     * */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /**
     * get the customer id
     * @return customer id
     * */
    public int getCustomerID() {
        return customerID;
    }
    /**
     * set the customer id
     * @param customerID the customer id in the appointment
     * */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /**
     * get the user id
     * @return user id
     * */
    public int getUserID() {
        return userID;
    }
    /**
     * set the user id
     * @param userID the user id in the appointment
     * */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /**
     *get the contact id
     * @return contact id
     * */
    public int getContactID() {
        return contactID;
    }
    /**
     * set the contact id
     * @param contactID the contact id in the appointment
     * */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
