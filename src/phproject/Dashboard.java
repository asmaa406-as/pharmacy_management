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

public class Dashboard {
    private Stage stage;
    private StackPane contentArea;
    private Button activeButton = null;
    private Button btnHome; // نخزن مرجع للزرار
    
    public Dashboard(Stage stage) {
        this.stage = stage;
    }
    
    public void show() {
        BorderPane root = new BorderPane();
        
        // Top Bar
        VBox topBar = createTopBar();
        root.setTop(topBar);
        
        // Left Side Menu
        VBox sideMenu = createSideMenu();
        root.setLeft(sideMenu);
        
        // Center Content Area with ScrollPane
        contentArea = new StackPane();
        contentArea.setStyle("-fx-background-color: #ecf0f1;");
        showWelcomeScreen();
        // تفعيل زرار Home عند الفتح
        setActiveButton(btnHome);
        // إضافة ScrollPane للمحتوى
        ScrollPane scrollPane = new ScrollPane(contentArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: #ecf0f1; -fx-background: #ecf0f1;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        root.setCenter(scrollPane);
               
        // Scene and Stage
        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
        stage.setTitle("Pharmacy Management System - Dashboard");
        stage.show();
    }
    
    private VBox createTopBar() {
        VBox topBar = new VBox(5);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #2dd4bf;");
        
        Label titleLabel = new Label("🏥 Pharmacy Management System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);
        
        Label welcomeLabel = new Label("Welcome, Admin!");
        welcomeLabel.setFont(Font.font("System", 14));
        welcomeLabel.setTextFill(Color.web("#ecf0f1"));
        
        topBar.getChildren().addAll(titleLabel, welcomeLabel);
        return topBar;
    }
    
    private VBox createSideMenu() {
        VBox sideMenu = new VBox(10);
        sideMenu.setPadding(new Insets(20));
        sideMenu.setStyle("-fx-background-color: #14b8a6;");
        sideMenu.setPrefWidth(220);
        
        Label menuLabel = new Label("MENU");
        menuLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        menuLabel.setTextFill(Color.web("#EDF9FF"));
        VBox.setMargin(menuLabel, new Insets(0, 0, 10, 0));
        
        // Menu Buttons
        btnHome = createMenuButton("🏠 Home"); // نخزنه في المتغير
        Button btnAddMedicine = createMenuButton("💊 Add Medicine");
        Button btnViewStock = createMenuButton("📋 View Stock");
        Button btnServices = createMenuButton("⚙ Services");
        
        // Button Actions
        btnHome.setOnAction(e -> {
            setActiveButton(btnHome);
            showWelcomeScreen();
        });
        
        btnAddMedicine.setOnAction(e -> {
            setActiveButton(btnAddMedicine);
            showAddMedicine();
        });
        
        btnViewStock.setOnAction(e -> {
            setActiveButton(btnViewStock);
            showViewStock();
        });
        
        btnServices.setOnAction(e -> {
            setActiveButton(btnServices);
            showServices();
        });
        
        // Spacer
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        // Logout Button
        Button btnLogout = new Button("🚪 Logout");
        btnLogout.setPrefWidth(180);
        btnLogout.setPrefHeight(45);
        btnLogout.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                          "-fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
        btnLogout.setOnAction(e -> showThankYouPage());
        
        sideMenu.getChildren().addAll(
            menuLabel, btnHome, btnAddMedicine, btnViewStock, 
            btnServices, spacer, btnLogout
        );
        
        return sideMenu;
    }
    
    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(180);
        button.setPrefHeight(45);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; " +
                       "-fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
          
        return button;
    }
    
    private void setActiveButton(Button button) {
        if (activeButton != null) {
            activeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; " +
                                 "-fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
        }
        activeButton = button;
        button.setStyle("-fx-background-color: #2dd4bf; -fx-text-fill: white; " +
                       "-fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
    }
    
    private void showWelcomeScreen() {
        VBox welcomeBox = new VBox(20);
        welcomeBox.setAlignment(Pos.CENTER);
        
        Label welcomeTitle = new Label("Welcome to Pharmacy Management System");
        welcomeTitle.setFont(Font.font("System", FontWeight.BOLD, 28));
        welcomeTitle.setTextFill(Color.web("#2dd4bf"));
        
        Label welcomeSubtitle = new Label("Select an option from the menu to get started");
        welcomeSubtitle.setFont(Font.font("System", 16));
        welcomeSubtitle.setTextFill(Color.web("#7f8c8d"));
        
        welcomeBox.getChildren().addAll(welcomeTitle, welcomeSubtitle);
        
        contentArea.getChildren().clear();
        contentArea.getChildren().add(welcomeBox);
    }
    
    private void showAddMedicine() {
        AddMedicine addMedicinePage = new AddMedicine();
        contentArea.getChildren().clear();
        contentArea.getChildren().add(addMedicinePage.getView());
    }
    
    private void showViewStock() {
        ViewStock viewStockPage = new ViewStock();
        contentArea.getChildren().clear();
        contentArea.getChildren().add(viewStockPage.getView());
    }
    
    private void showServices() {
        Services servicesPage = new Services();
        contentArea.getChildren().clear();
        contentArea.getChildren().add(servicesPage.getView());
    }
    
    private void showThankYouPage() {
        Thanks thankYouPage = new Thanks(stage);
        thankYouPage.show();
    }
}