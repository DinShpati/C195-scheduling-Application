package Controller;

/**
 * Customer by type and month, data is taken from the database and displayed in input fields
 *
 * There are two lambda expressions used in this method, one is used for the appointment types and the other is used for the appointment months.
 * You can find the lambdas in the initialize method*/

import Model.Appointment;
import Utility.getAppointments;
import Utility.DBConn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.ResourceBundle;

public class CustomerByTypeAndMonth implements Initializable{
    @FXML
    private Button mainBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private TextField January;
    @FXML
    private TextField February;
    @FXML
    private TextField March;
    @FXML
    private TextField April;
    @FXML
    private TextField May;
    @FXML
    private TextField June;
    @FXML
    private TextField July;
    @FXML
    private TextField August;
    @FXML
    private TextField September;
    @FXML
    private TextField October;
    @FXML
    private TextField December;
    @FXML
    private TextField November;
    @FXML
    private TextField Planning;
    @FXML
    private TextField DeBriefing;
    @FXML
    private TextField phone;
    @FXML
    private TextField online;

    /**
     * Exiting the application
     * @param event button clicked
     * */
    @FXML
    void exit(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        exitBtn.setText(sourceButton.getText());
        DBConn.closeConn();
        System.exit(0);
    }

    /**
     * Going back to the dashboard
     * @param event when the button is clicked
     * */
    @FXML
    void backToMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Dashboard.fxml"));
        loader.load();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Initialize the scene by getting data about the appointment types and months when the scene is loaded.
     * There are two foreach lambda expressions used in this method, one is used for the appointment types and the other is used for the appointment months.
     *
     * @param url screen url
     * @param resourceBundle text localization
     * */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            getAppointments.getAllAppointments();

            /**
             * Using a lambda loop to go though the appointments and setting the type of appointment*/

            /**
             * Each number in this array stands for a type*/
            int types[] = {0, 0, 0, 0};

            /**The foreach lambda loop used to assign the type of the appointment to the relative text field*/
            getAppointments.allAppointments.forEach(appointment -> {
                String aptType = appointment.getType();
                {
                    if (aptType.contains("Planning Session") ) {
                        types[0] = types[0] +1;
                        Planning.setText(String.valueOf(types[0]));

                    } else if (aptType.contains("De-Briefing")) {
                        types[1] = types[1] +1;
                        DeBriefing.setText(String.valueOf(types[1]));
                    }else if (aptType.contains("Phone")) {
                        types[2] = types[2] +1;
                        phone.setText(String.valueOf(types[2]));
                    }else if (aptType.contains("Online")) {
                        types[3] = types[3] +1;
                        online.setText(String.valueOf(types[3]));
                    }
                }
            });

            /**
             * Using a forEach Lambda loop to go though the appointments and setting the month of appointment*/
            /**
             * Each number in this array stands for a month*/
            int[] months = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

            /**The foreach lambda loop used to assign the month of the appointment to the relative text field*/
            getAppointments.allAppointments.forEach(appointment -> {


                Month aptStart = appointment.getStart().getMonth();
                if (aptStart == Month.JANUARY) {
                    months[0] = months[0] + 1;
                    January.setText(String.valueOf(months[0]));

                } else if (aptStart == Month.FEBRUARY) {
                    months[1] = months[1] + 1;
                    February.setText(String.valueOf(months[1]));

                } else if (aptStart == Month.MARCH) {
                    months[2] = months[2] + 1;
                    March.setText(String.valueOf(months[2]));

                } else if (aptStart == Month.APRIL) {
                    months[3] = months[3] + 1;
                    April.setText(String.valueOf(months[3]));
                } else if (aptStart == Month.MAY) {
                    months[4] = months[4] + 1;
                    May.setText(String.valueOf(months[4]));
                } else if (aptStart == Month.JUNE) {
                    months[5] = months[5] + 1;
                    June.setText(String.valueOf(months[5]));
                } else if (aptStart == Month.JULY) {
                    months[6] = months[6] + 1;
                    July.setText(String.valueOf(months[6]));
                } else if (aptStart == Month.AUGUST) {
                    months[7] = months[7] + 1;
                    August.setText(String.valueOf(months[7]));
                } else if (aptStart == Month.SEPTEMBER) {
                    months[8] = months[8] + 1;
                    September.setText(String.valueOf(months[8]));

                } else if (aptStart == Month.OCTOBER) {
                    months[9] = months[9]++;
                    October.setText(String.valueOf(months[9]));

                } else if (aptStart == Month.NOVEMBER) {
                    months[10] = months[10] + 1;
                    November.setText(String.valueOf(months[10]));

                } else if (aptStart == Month.DECEMBER) {
                    months[11] = months[11] + 1;
                    December.setText(String.valueOf(months[11]));

                }
                return;
            });



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
