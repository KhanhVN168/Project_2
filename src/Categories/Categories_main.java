/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Categories;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author icom
 */
public class Categories_main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
    primaryStage.setTitle("Home");
        Navigator.getInstance().setStage(primaryStage);
        Navigator.getInstance().goToIndex();
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
