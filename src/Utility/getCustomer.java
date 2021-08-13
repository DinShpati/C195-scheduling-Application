package Utility;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * Getting and setting all the data from and to the database related to customers
 * */

public class getCustomer {

    /**
     * A list of Customers
     * */
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();

    /**
     *  gets the customers from the database
     *
     * @throws SQLException throws a sql exception if there was a error related to query or the database
     * @return observable list
     * */
    public static ObservableList<Customer> getCustomers() throws SQLException{
        customers.clear();

        try{

            Connection con = DBConn.startConn();
            ResultSet results = con.createStatement().executeQuery("SELECT * FROM customers");

            while(results.next()){
                customers.add(new Customer(
                        results.getInt("Customer_ID"),
                        results.getString("Customer_Name"),
                        results.getString("Address"),
                        results.getString("Postal_Code"),
                        results.getString("Phone"),
                        results.getTimestamp("Create_Date").toLocalDateTime(),
                        results.getString("Created_By"),
                        results.getTimestamp("Last_Update").toLocalDateTime(),
                        results.getString("Last_Updated_By"),
                        results.getInt("Division_ID")));
            }
            return customers;

        }catch(SQLException exc){
            Logger.getLogger(exc.toString());
        }
        return null;
    }

    /**
     * Deleting customers
     * @param id This ID is needed to delete the customer
     * @return returns true or false
     */
    public static boolean deleteCustomer(int id) {

        try {
            Statement statement = DBConn.startConn().createStatement();
            String query = "DELETE FROM appointments WHERE Customer_ID=" + id;
            statement.executeUpdate(query);
            String queryOne = "DELETE FROM customers WHERE Customer_ID=" + id;
            statement.executeUpdate(queryOne);
            System.out.println("Customer deleted");

        } catch(SQLException exc) {
            System.out.println("SQLException: " + exc.getMessage());
        }
        return false;
    }

    /**
     * Updating Customers based on selection in the customer dashboard
     * @param customerID the customers id
     * @param customerName the name of the edited customer
     * @param customerAddress the address of the edited customer
     * @param customerPostal the postal code of the edited customer
     * @param customerPhone the phone number of the edited customer
     * @param createDate the date the customer was created
     * @param createdBy the user that created the customer
     * @param divisionID the division of the location of the customer
     * @param lastUpdate the last time the customer was updated
     * @param lastUpdatedBy the last user to update the customer
     *
     * @return retruns either true or false depending on the success of the execution
     * @throws SQLException Throws a exception if the database isnt updated
     * */

    public static boolean editCustomer(Integer customerID, String customerName, String customerAddress, String customerPostal, String customerPhone, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, Integer divisionID) throws SQLException {

        try {
            Statement statement = DBConn.startConn().createStatement();
            statement.executeUpdate("UPDATE customers SET  Customer_Name='"+customerName+"', Address='"+customerAddress+"', Postal_Code='"+customerPostal+"', Phone='"+customerPhone+"',Create_Date='"+createDate+"', Created_By='"+createdBy+"', Last_Updated_By='"+lastUpdatedBy+"', Last_Updated_By='"+lastUpdatedBy+"', Division_ID= "+divisionID+" WHERE Customer_ID ="+customerID);

        } catch (SQLException exc) {
            System.out.println("SQLException: " + exc.getMessage());
        }

        return false;
    }

    /**
     * Adding new customers to the database
     * @param customerID the customers id
     * @param customerName the name of the edited customer
     * @param customerAddress the address of the edited customer
     *@param customerPostal the postal code of the edited customer
     *@param customerPhone the phone number of the edited customer
     *@param createDate the date the customer was created
     *@param createdBy the user that created the customer
     *@param divisionID the division of the location of the customer
     *@param lastUpdate the last time the customer was updated
     *@param lastUpdatedBy the last user to update the customer
     *
     *@return retruns either true or false depending on the success of the execution
     * */
    public static boolean addCustomer(Integer customerID, String customerName, String customerAddress, String customerPostal, String customerPhone, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, Integer divisionID) {
        try {
            Statement statement = DBConn.startConn().createStatement();
            statement.executeUpdate("INSERT INTO customers SET Customer_ID='"+customerID+"', Customer_Name='"+customerName+"', Address='"+customerAddress+"', Postal_Code='"+customerPostal+"', Phone='"+customerPhone+"',Create_Date='"+createDate+"', Created_By='"+createdBy+"',Last_Update='"+lastUpdate+"', Last_Updated_By='"+lastUpdatedBy+"', Division_ID= "+divisionID);
        }
        catch (SQLException exc) {
            System.out.println("SQLException: " + exc.getMessage());
        }
        return false;
    }


}
