package phproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Thanks {
    
    private Stage stage;
    
    public Thanks(Stage stage) {
        this.stage = stage;
    }
    
    public void show() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2dd4bf, #34495e);");
        
        VBox contentBox = new VBox(30);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(50));
        
        // Logo/Icon
        Label iconLabel = new Label("🏥");
        iconLabel.setStyle("-fx-font-size: 80px;");
        
        // Thank You Message
        Label thankYouLabel = new Label("Thank You!");
        thankYouLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        thankYouLabel.setTextFill(Color.WHITE);
        
        Label subtitleLabel = new Label("for using Pharmacy Management System");
        subtitleLabel.setFont(Font.font("Arial", 22));
        subtitleLabel.setTextFill(Color.web("#ecf0f1"));
        
        // Divider
        Separator separator = new Separator();
        separator.setPrefWidth(300);
        separator.setStyle("-fx-background-color: white; -fx-opacity: 0.3;");
        
        // Additional Messages
        VBox messageBox = new VBox(10);
        messageBox.setAlignment(Pos.CENTER);
        
        Label message1 = new Label("We hope our system made your work easier!");
        message1.setFont(Font.font("Arial", 16));
        message1.setTextFill(Color.web("#bdc3c7"));
        
        Label message2 = new Label("Have a great day!");
        message2.setFont(Font.font("Arial", 16));
        message2.setTextFill(Color.web("#bdc3c7"));
        
        messageBox.getChildren().addAll(message1, message2);
        
        // Buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button btnBackToLogin = new Button("🔙 Back to Login");
        btnBackToLogin.setPrefWidth(180);
        btnBackToLogin.setPrefHeight(45);
        btnBackToLogin.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                               "-fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand; " +
                               "-fx-background-radius: 25;");
        btnBackToLogin.setOnAction(e -> handleBackToLogin());
        
        Button btnExit = new Button("🚪 Exit Application");
        btnExit.setPrefWidth(180);
        btnExit.setPrefHeight(45);
        btnExit.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                        "-fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand; " +
                        "-fx-background-radius: 25;");
        btnExit.setOnAction(e -> handleExit());
        
        buttonBox.getChildren().addAll(btnBackToLogin, btnExit);
        
        // Footer
        Label footerLabel = new Label("© 2024 Pharmacy Management System - All Rights Reserved");
        footerLabel.setFont(Font.font("System", 12));
        footerLabel.setTextFill(Color.web("#95a5a6"));
        
        // Add all elements
        contentBox.getChildren().addAll(iconLabel, thankYouLabel, subtitleLabel, separator, messageBox, buttonBox, footerLabel);       
        root.getChildren().add(contentBox);
        
        // Scene and Stage
        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Thank You!");
        stage.show();
    }
    
    private void handleBackToLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.Show(stage);
    }
    
    private void handleExit() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Exit Application");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to exit the application?");
        
        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            stage.close();
            System.exit(0);
        }
    }
}

