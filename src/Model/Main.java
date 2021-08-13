package Model;

/**This is the login screen*/

import Utility.DBConn;
import Utility.DBQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.util.Locale;


public class Main extends Application {

    /**
     * @param primaryStage This is the first scene showed to the user, the login scene
     * @throws IOException This is the exception thrown if the application was unable to open the scene
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Locale.setDefault(new Locale("fr"));
        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        primaryStage.setTitle("Global Consulting Organization Scheduler");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * the main method used to execute the application
     * @param args args
     * @throws SQLException throws exception if the DB connection goes wrong
     * */
    public static void main(String[] args) throws SQLException {
        //Set up DB connection
        Connection conn = DBConn.startConn();
        DBQuery.setStatementobj(conn);
        Statement statement = DBQuery.getStatementobj();
        ResultSet rs = statement.getResultSet();

        launch(args);

        DBConn.closeConn();

    }

}
