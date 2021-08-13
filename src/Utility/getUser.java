package Utility;

/**
 * There is another lambda expression in here used to to log in the log file. */

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * getting and setting the data for the user in the database
 * */

public class getUser {
    /**
     * the user model
     * */
    private static User currentUser;

    /**
     * The getter for the user which returns the user
     * @return the current user
     * */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * A list of users from the database
     * */
    public static ObservableList<User> users = FXCollections.observableArrayList();

    /**
     * This validates the user login information given
     * @param password the users password
     * @param username the users username
     * @return true or false based on the validation of the credentials entered
     * */
    public static Boolean login(String username, String password) {
        //Verifying username and password from text fields
        //logging username, timestamps, dates and success/failure
        try {
            String query = "SELECT * FROM users WHERE User_Name='" + username + "'AND Password='" + password + "'";
            //Connecting to DB and executing query
            ResultSet results = DBQuery.statement.executeQuery(query);

            //Creating User object and using setters for object
            if (results.next()) {
                currentUser = new User();
                currentUser.setUserName(results.getString("User_Name"));
                currentUser.setPassword(results.getString("Password"));
                User myUser = new User(results.getInt("User_ID"), results.getString("User_Name"));
                users.add(myUser);

                createLoginFile(users, true);
                return true;
            } else{
                createLoginFile(users, false);
                return false;
            }

        }
        catch (SQLException | IOException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    /**
     * This logs login information onto a txt file
     * There is also use of a lambda expression in this method, its being used to write failed and successful
     * logins of the user into the log.txt file
     *
     * @param users list of users
     * @param success the success of the login
     * @throws IOException in case of an error
     * */
    public static void createLoginFile(ObservableList<User> users, Boolean success) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("logs.txt", true))) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            if(success){
                writer.write("Date and Time User " + "successfully " + "Logged in " + timeStamp);
            }else{
                writer.write("Date and Time User " + "failed" + "Log in " + timeStamp);
            }

            /**
             * Lambda foreach internal loop*/
            users.forEach(i -> {
                try {
                    writer.write(i.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
