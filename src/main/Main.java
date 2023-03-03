package main;

/**
 * @author Lynn Weidman
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/** This is the first page that loads when the application is run.
 * javadoc files are located in C:\Users\Owner\Downloads\javaDocC482
 * */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


    /**
     * Tests adding parts.
     */
    public static void addTestPartsData() {
    }
    /**
     * Tests adding products.
     */
    public static void addTestProductsData() {

    }


    /**
     * Calls the test methods, loads the data to the Main Screen, and launches the application.
     * @param args-
     */
    public static void main(String[] args) {

       addTestPartsData();
       addTestProductsData();



            launch(args);
    }

}
