package Utility;

import Model.Divisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.logging.Logger;

/**
 * getting the divisions from the database related to the divisions
 * */

public class getDivisions {

    /**
     *  A list of all the divisions
     * */
    public static ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();

    /**
     *  Starts the connection to the database
     * */
    public static Connection conn = DBConn.startConn();

    /**
     * Gets all the divisions
     *
     * @return Observable list of all divisions
     * @throws SQLException throws a exception if there was a error related to the query
     * */
    public static ObservableList<Divisions> getAllDivisions() throws SQLException {
        allDivisions.clear();
        try {

            ResultSet results = conn.createStatement().executeQuery("SELECT * FROM first_level_divisions");
            while (results.next()) {
                allDivisions.add(new Divisions(
                        results.getInt("Division_ID"),
                        results.getString("Division"),
                        results.getTimestamp("Create_Date").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getString("Created_By"),
                        results.getTimestamp("Last_Update").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getString("Last_Updated_By"),
                        results.getInt("Country_ID")));
            }
            return allDivisions;
        } catch (SQLException e) {
            Logger.getLogger(e.toString());
        }
        return null;
    }

    /**
     * This is a list of US divisions
     * */
    public static ObservableList<Divisions> usFilteredDivisions = FXCollections.observableArrayList();

    /**
     *  getting the US divisions
     * @throws SQLException throws a exception if there is an error in the query
     *
     * @return ObservaleList of US filtered divisions
     * */
    public static ObservableList<Divisions> getusFilteredDivisions() throws SQLException {
        usFilteredDivisions.clear();
        try {

            ResultSet results = conn.createStatement().executeQuery("SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 1");
            while (results.next()) {
                usFilteredDivisions.add(new Divisions(
                        results.getInt("Division_ID"),
                        results.getString("Division"),
                        results.getTimestamp("Create_Date").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getString("Created_By"),
                        results.getTimestamp("Last_Update").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getString("Last_Updated_By"),
                        results.getInt("Country_ID")));
            }
            return usFilteredDivisions;
        } catch (SQLException e) {
            Logger.getLogger(e.toString());
        }
        return null;
    }

    /**
     * This is a list of UK divisions
     * */
    public static ObservableList<Divisions> ukFilteredDivisions = FXCollections.observableArrayList();

    /**
     * getting the UK divisions
     * @return Observable List of UK filtered Divisions
     * @throws SQLException throws a exception if there is an error in the query
     * */
    public static ObservableList<Divisions> getukFilteredDivisions() throws SQLException {
        ukFilteredDivisions.clear();
        try {

            ResultSet results = conn.createStatement().executeQuery("SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 2");
            while (results.next()) {
                ukFilteredDivisions.add(new Divisions(
                        results.getInt("Division_ID"),
                        results.getString("Division"),
                        results.getTimestamp("Create_Date").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getString("Created_By"),
                        results.getTimestamp("Last_Update").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getString("Last_Updated_By"),
                        results.getInt("Country_ID")));
            }
            return ukFilteredDivisions;
        } catch (SQLException e) {
            Logger.getLogger(e.toString());
        }
        return null;
    }

    /**
     * This is a list of CA divisions
     * */
    public static ObservableList<Divisions> caFilteredDivisions = FXCollections.observableArrayList();

    /**
     * getting the CA divisions
     * @return Observable List of CA filtered Divisions
     * @throws SQLException throws a exception if there is an error in the query
     * */
    public static ObservableList<Divisions> getcanadaFilteredDivisions() throws SQLException {
        caFilteredDivisions.clear();
        try {

            ResultSet rb = conn.createStatement().executeQuery("SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 3");
            while (rb.next()) {
                caFilteredDivisions.add(new Divisions(
                        rb.getInt("Division_ID"),
                        rb.getString("Division"),
                        rb.getTimestamp("Create_Date").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        rb.getString("Created_By"),
                        rb.getTimestamp("Last_Update").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        rb.getString("Last_Updated_By"),
                        rb.getInt("Country_ID")));
            }
            return caFilteredDivisions;
        } catch (SQLException e) {
            Logger.getLogger(e.toString());
        }
        return null;
    }

    /**
     * the selected division
     * */
    public static Divisions divison;

    /**
     * Gets a division based on a id selected
     *
     * @param id the division id
     * @throws SQLException throws a exception if there is an error in the query
     * @return the Division based on the ID provided
     * */
    public static Divisions getDivisionByID(int id) throws SQLException{
        try{

            ResultSet results = conn.createStatement().executeQuery("SELECT * FROM first_level_divisions WHERE Division_ID = " + id);

            while (results.next()) {
                divison = (new Divisions(
                        results.getInt("Division_ID"),
                        results.getString("Division"),
                        results.getTimestamp("Create_Date").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getString("Created_By"),
                        results.getTimestamp("Last_Update").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getString("Last_Updated_By"),
                        results.getInt("Country_ID")));
            }

            return divison;

        }catch(SQLException exc){
            Logger.getLogger(exc.toString());
        }
        return null;
    }

}
