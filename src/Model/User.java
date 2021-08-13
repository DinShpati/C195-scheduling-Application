package Model;

import Utility.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;

/**
 * User class is used for log in purposes and log activity
 */
public class User {
    Integer userID;
    static String username;
    String password;
    Date createDate;
    String createdBy;
    Timestamp lastUpdate;
    String lastUpdatedBy;

    public User() {

    }

    /**
     *getter for the user id
     * @return user id
     * */
    public Integer getUserID() {
        return userID;
    }
    /**
     * setter for the user id
     * @param userID the id of the user
     * */
    public void setUserID(Integer userID) {
        this.userID = userID;
    }
    /**
     * getter for the username
     * @return username
     * */
    public static String getUsername() {
        return username;
    }
    /**
     * setter for the username
     * @param userName the users username
     * */
    public void setUserName(String userName) {
        this.username = userName;
    }
    /**
     * getter for the password
     * @return password
     * */
    public String getPassword() {
        return password;
    }
    /**
     * setter for the password
     * @param password the the users password
     * */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * getter for the create date
     * @return create date
     * */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * Setter for the create date
     * @param createDate the date the user was created
     * */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     * getter for the created by (user)
     * @return created by (user)
     * */
    public String getCreatedBy() {
        return createdBy;
    }
    /**
     * setter for the created by
     * @param createdBy the user that created the user
     * */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /**
     * getter for the last update
     * @return last update
     * */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
    /**
     * setter for the last update
     * @param lastUpdate the last date the the user was updated
     * */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /**
     * getter for the last update by
     * @return last updated by (user)
     * */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /**
     *setter for the last update by
     * @param lastUpdatedBy the last user to update the user
     * */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * The default constructor
     *
     * @param userID user id
     * @param username users username
     * @param password users password
     * @param createDate the date that the user was created
     * @param createdBy the user that created the user
     * @param lastUpdate the last time the user was updated
     * @param lastUpdatedBy the last user to update the user
     * */
    public User(Integer userID, String username, String password, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This constructor returns the user id and the username
     *
     * @param user_id users id
     * @param user_name users username
     * */
    public User(int user_id, String user_name) {
        this.userID = user_id;
        this.username = user_name;
    }
    //contacts
    ObservableList<Contact> contList = FXCollections.observableArrayList();
    public void UserMain() {
        try {
            Connection conn = DBConn.startConn();
            ResultSet rc = conn.createStatement().executeQuery("select * FROM contacts");
            while (rc.next())
                contList.add(new Contact(rc.getInt("Contact_ID"), rc.getString("Contact_Name"), rc.getString("Email")));
        }
        catch (
                SQLException e) {
            Logger.getLogger(e.toString());
        }

    }

    @Override
    /**Convert hashmap to String for logs*/
    public String toString() {

        return  " userId=" + userID +
                ", username='" + username + '\'';
    }


}