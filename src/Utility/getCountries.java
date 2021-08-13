package Utility;

import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.logging.Logger;

/**
 * gets the countries from the database
 * */

public class getCountries {

    /**
     * A list of all the countries
     * */
    public static ObservableList<Countries> allCountries = FXCollections.observableArrayList();

    /**
     * get all the countries
     * @return returns a list of all the countries in the database
     * @throws SQLException throws a sql exception if something goes wrong
     */
    public static ObservableList<Countries> getAllCountries() throws SQLException {
        allCountries.clear();
        try {
            Connection conn = DBConn.startConn();
            ResultSet results = conn.createStatement().executeQuery("SELECT * FROM countries");
            while (results.next()) {
                allCountries.add(new Countries(
                        results.getInt("Country_ID"),
                        results.getString("Country"),
                        results.getTimestamp("Create_Date").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getString("Created_By"),
                        results.getTimestamp("Last_Update").toInstant().atOffset(ZoneOffset.from(ZonedDateTime.now())).toLocalDateTime(),
                        results.getString("Last_Updated_By")));
            }
            return allCountries;
        } catch (SQLException exc) {
            Logger.getLogger(exc.toString());
        }
        return null;
    }
}
