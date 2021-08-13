package Model;

/**
 * customer model for customer objects
 * */

import java.time.LocalDateTime;

public class Customer {
    public int customerID;
    public String customerName;
    private String address;
    private String postal;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;


    /**
     * constructor for the customer class the returns the entire customer
     *
     * @param customerID customer ID
     * @param customerName customers name
     * @param address the customer address
     * @param postalCode the customer postal code
     * @param phone the customers phone number
     * @param createDate when the customer was created
     * @param createdBy who created the customer
     * @param lastUpdate the last time the customer was updated
     * @param lastUpdatedBy the last user to update the customer
     * @param divisionID the customers division
     * */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionID) {
        this.customerID=customerID;
        this.customerName=customerName;
        this.address=address;
        this.postal=postalCode;
        this.phone=phone;
        this.createDate=createDate;
        this.createdBy=createdBy;
        this.lastUpdate=lastUpdate;
        this.lastUpdatedBy=lastUpdatedBy;
        this.divisionID=divisionID;
    }

    /**
     *Getter for the customer ID
     * @return customer id
     * */
    public int getCustomerID() {
        return customerID;
    }
    /**
     * Setter for the customer ID
     * @param customerID the customers id
     * */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /**
     * getter for the customer name
     * @return customer name
     * */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * setter for the Customer name
     * @param customerName the customers name
     * */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**
     * Getter for the address
     * @return address
     * */
    public String getAddress() {
        return address;
    }
    /**
     * Setter for the address
     * @param address the address of the customer
     * */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Getter for the postal
     * @return postal
     * */
    public String getPostal() {
        return postal;
    }
    /**
     * Setter for the postal
     * @param postal the postal code of the customer
     * */
    public void setPostal(String postal) {
        this.postal = postal;
    }
    /**
     * getter for the phone
     * @return phone
     * */
    public String getPhone() {
        return phone;
    }
    /**
     * setter for the phone
     * @param phone the phone number of the customer
     * */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * getter for the created date
     * @return created date
     * */
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    /**
     * setter for the create date
     * @param createDate the date that the customer was created
     * */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    /**
     * get the created by user
     * @return created by user
     * */
    public String getCreatedBy() {
        return createdBy;
    }
    /**
     * set the created by user
     * @param createdBy the user that created the customer
     * */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /**
     * getter for the last update
     * @return last update
     * */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    /**
     * setter for the last update
     * @param lastUpdate the last date that the user was updated
     * */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /**
     * getter for the last updated by
     * @return last updated by (user)
     * */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /**
     * setter for the last updated by
     * @param lastUpdatedBy the last updater of the customer
     * */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /**
     * getter for the division id
     * @return division id
     * */
    public int getDivisionID() {
        return divisionID;
    }
    /**
     * setter for the division
     * @param divisionID the division of the customer
     * */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    @Override
    /**Convert hashmap to String for Country objects in combobox (credit to Mr. Kinkead Webinar on Combo Boxes)*/
    public String toString() {

        return ((customerName ));
    }
}
