package phproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginPage {
    
    public void Show(Stage stage){
     //  فوق بعض(الخلفية +login box ) 
        StackPane root = new StackPane();
        
        // 1.Background Image
        BackgroundImage backgroundImage = new BackgroundImage(
           new Image("file:C:/Users/sokas/OneDrive/Desktop/phManage/phProject/imgs/ww.jpg"),           
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true)
        );
        // الصورة خلفية للstackPane
        root.setBackground(new Background(backgroundImage));
    
        // 2.login Container
        VBox LogBox = new VBox(20); // مسافة بين العناصر 20px
        LogBox.setMaxWidth(500);
        LogBox.setMaxHeight(500);
        LogBox.setPadding(new Insets(40));
        LogBox.setAlignment(Pos.CENTER);
        LogBox.setStyle(  //css inline codes
            "-fx-background-color: rgba(255, 255, 255, 0.95);" +
            "-fx-background-radius: 20;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);"
        );      
        //  محتويات الـ Login Box         
        // Logo  
        Label logoLabel = new Label("🏥");
        logoLabel.setFont(Font.font(60));       
        // العنوان
        Label titleLabel = new Label("Pharmacy Management");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.web("#14b8a6"));
        //subtitle
        Label subtitleLabel = new Label("Please login to continue");
        subtitleLabel.setFont(Font.font("System", 18));
        subtitleLabel.setTextFill(Color.web("#64748b"));               
        // Spacer (مسافة فاضية)
        Region spacer1 = new Region();
        spacer1.setMinHeight(10); 
        
        // Login Form Container
        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(30));
        formBox.setMaxWidth(400);
        // Username
        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPrefHeight(40);
        usernameField.setStyle(
            "-fx-background-color: #f1f5f9;" +
            "-fx-border-color: #cbd5e1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 12;" +
            "-fx-font-size: 14;"
        );
        
        // Password
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefHeight(40);
        passwordField.setStyle(
            "-fx-background-color: #f1f5f9;" +
            "-fx-border-color: #cbd5e1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 12;" +
            "-fx-font-size: 14;"
        );
        
        // Error Label
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font("Arial", 12));
        
        // Login Button
        Button loginButton = new Button("🔐 Login");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle(
            "-fx-background-color: linear-gradient(to right, #14b8a6, #10b981);" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 14;" +
            "-fx-background-radius: 10;" +
            "-fx-cursor: hand;"
        );
        // Add elements
        formBox.getChildren().addAll(usernameLabel, usernameField,passwordLabel, passwordField,errorLabel, loginButton);
        LogBox.getChildren().addAll(logoLabel,titleLabel, subtitleLabel, spacer1,formBox);
        root.getChildren().add(LogBox);
        // Login Action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            
            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("⚠ Please fill in all fields!");
            } else if (username.equals("admin") && password.equals("admin")||
                       username.equals("asmaa") && password.equals("1234")) {
                // Successful login - go to Dashboard
                Dashboard dashboard = new Dashboard(stage);
                dashboard.show();
            } else {
                errorLabel.setText("❌ Invalid username or password!");
            }
        });     
        // Scene and Stage
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Pharmacy Management System - Login");
        stage.show();
    }
}


