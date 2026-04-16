/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package phproject;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 *
 * @author Asmaa Sami
 */
public class PhProject extends Application {
    
    @Override
    public void init() {
     Database.getConnection();
}
    @Override
    public void start(Stage primaryStage) {
       
        // Show Login Page first
        LoginPage loginPage = new LoginPage();
        loginPage.Show(primaryStage);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}