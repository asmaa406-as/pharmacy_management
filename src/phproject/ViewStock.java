package phproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.List;

public class ViewStock {
    
    private TableView<Medicine> tableStock;
    private TextField txtSearch;
    private Label lblInfo;
    private ObservableList<Medicine> medicineList;
    
    public VBox getView() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Title
        Label titleLabel = new Label("📋 View Medicine Stock");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 26));
        titleLabel.setTextFill(Color.web("#2dd4bf"));
        
        Separator separator = new Separator();
        
        // Search and Action Bar
        HBox actionBar = new HBox(15);
        actionBar.setAlignment(Pos.CENTER_LEFT);
        actionBar.setPadding(new Insets(10));
        
        txtSearch = new TextField();
        txtSearch.setPromptText("🔍 Search by medicine name or barcode...");
        txtSearch.setPrefWidth(350);
        txtSearch.setPrefHeight(35);
        txtSearch.setOnKeyReleased(e -> handleSearch());
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button btnRefresh = new Button("🔄 Refresh");
        btnRefresh.setPrefHeight(35);
        btnRefresh.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                           "-fx-font-size: 14px; -fx-cursor: hand;");
        btnRefresh.setOnAction(e -> handleRefresh());
        
        Button btnPrint = new Button("🖨️ Print/Export");
        btnPrint.setPrefHeight(35);
        btnPrint.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; " +
                         "-fx-font-size: 14px; -fx-cursor: hand;");
        btnPrint.setOnAction(e -> handlePrint());
        
        actionBar.getChildren().addAll(txtSearch, spacer, btnRefresh, btnPrint);
        
        // Table View
        tableStock = new TableView<>();
        tableStock.setStyle("-fx-background-color: white;");
        VBox.setVgrow(tableStock, Priority.ALWAYS);
        
        // Table Columns
        TableColumn<Medicine, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(60);
        
        TableColumn<Medicine, String> colName = new TableColumn<>("Medicine Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setPrefWidth(180);
        
        TableColumn<Medicine, String> colBarcode = new TableColumn<>("Barcode");
        colBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        colBarcode.setPrefWidth(120);
        
        TableColumn<Medicine, Double> colPrice = new TableColumn<>("Price (EGP)");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPrice.setPrefWidth(100);
        
        TableColumn<Medicine, Integer> colQuantity = new TableColumn<>("Quantity");
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQuantity.setPrefWidth(90);
        
        TableColumn<Medicine, String> colExpiry = new TableColumn<>("Expiry Date");
        colExpiry.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        colExpiry.setPrefWidth(120);
        
        TableColumn<Medicine, String> colManufacturer = new TableColumn<>("Manufacturer");
        colManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        colManufacturer.setPrefWidth(150);
        
        TableColumn<Medicine, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colType.setPrefWidth(100);
        
        tableStock.getColumns().addAll(colId, colName, colBarcode, colPrice, 
                                       colQuantity, colExpiry, colManufacturer, colType);
        
        // Action Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button btnEdit = new Button("✏️ Edit Selected");
        btnEdit.setPrefWidth(150);
        btnEdit.setPrefHeight(40);
        btnEdit.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; " +
                        "-fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
        btnEdit.setOnAction(e -> handleEdit());
        
        Button btnDelete = new Button("🗑️ Delete Selected");
        btnDelete.setPrefWidth(150);
        btnDelete.setPrefHeight(40);
        btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                          "-fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
        btnDelete.setOnAction(e -> handleDelete());
        
        buttonBox.getChildren().addAll(btnEdit, btnDelete);
        
        // Info Label
        lblInfo = new Label("Loading...");
        lblInfo.setFont(Font.font("System", 13));
        lblInfo.setTextFill(Color.web("#7f8c8d"));
        
        // تحميل البيانات من الداتابيز بعد إنشاء كل العناصر
        loadMedicinesFromDatabase();
        
        root.getChildren().addAll(titleLabel, separator, actionBar, tableStock, buttonBox, lblInfo);
        
        return root;
    }
    
    // تحميل الأدوية من الداتابيز
    private void loadMedicinesFromDatabase() {
        try {
            List<Medicine> medicines = MedicineDAO.getAllMedicines();
            medicineList = FXCollections.observableArrayList(medicines);
            tableStock.setItems(medicineList);
            updateInfoLabel();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", 
                     "Failed to load medicines from database!");
            e.printStackTrace();
            medicineList = FXCollections.observableArrayList();
            tableStock.setItems(medicineList);
        }
    }
    
    // البحث في الأدوية
    private void handleSearch() {
        String searchText = txtSearch.getText().trim();
        
        if (searchText.isEmpty()) {
            // إذا كان البحث فارغ، عرض كل الأدوية
            loadMedicinesFromDatabase();
        } else {
           
            try {
                List<Medicine> results = MedicineDAO.searchMedicine(searchText);
                medicineList = FXCollections.observableArrayList(results);
                tableStock.setItems(medicineList);
                updateInfoLabel();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Search Error", 
                         "Failed to search medicines!");
                e.printStackTrace();
            }
        }
    }
    
    // تحديث البيانات
    private void handleRefresh() {
        loadMedicinesFromDatabase();
        txtSearch.clear();
        showAlert(Alert.AlertType.INFORMATION, "Refreshed", 
                 "Medicine list has been refreshed successfully!");
    }
    
    // طباعة/تصدير البيانات
    private void handlePrint() {
        if (medicineList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Data", 
                     "No medicines to print!");
            return;
        }
        
        // عرض ملخص البيانات
        StringBuilder summary = new StringBuilder();
        summary.append("Total Medicines: ").append(medicineList.size()).append("\n\n");
        
        for (Medicine med : medicineList) {
            summary.append("• ").append(med.getName())
                   .append(" - ").append(med.getQuantity()).append(" units")
                   .append(" - EGP ").append(med.getPrice())
                   .append("\n");
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Medicine Stock Summary");
        alert.setHeaderText("Current Stock Summary");
        alert.setContentText(summary.toString());
        alert.showAndWait();
    }
    
    // تعديل دواء
    private void handleEdit() {
        Medicine selected = tableStock.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", 
                     "Please select a medicine to edit!");
            return;
        }
        
        // إنشاء نافذة التعديل
        Dialog<Medicine> dialog = new Dialog<>();
        dialog.setTitle("Edit Medicine");
        dialog.setHeaderText("Edit: " + selected.getName());
        
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        
        // إنشاء الحقول
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField txtName = new TextField(selected.getName());
        TextField txtBarcode = new TextField(selected.getBarcode());
        TextField txtPrice = new TextField(String.valueOf(selected.getPrice()));
        TextField txtQuantity = new TextField(String.valueOf(selected.getQuantity()));
        TextField txtManufacturer = new TextField(selected.getManufacturer());
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(txtName, 1, 0);
        grid.add(new Label("Barcode:"), 0, 1);
        grid.add(txtBarcode, 1, 1);
        grid.add(new Label("Price:"), 0, 2);
        grid.add(txtPrice, 1, 2);
        grid.add(new Label("Quantity:"), 0, 3);
        grid.add(txtQuantity, 1, 3);
        grid.add(new Label("Manufacturer:"), 0, 4);
        grid.add(txtManufacturer, 1, 4);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    selected.setName(txtName.getText());
                    selected.setBarcode(txtBarcode.getText());
                    selected.setPrice(Double.parseDouble(txtPrice.getText()));
                    selected.setQuantity(Integer.parseInt(txtQuantity.getText()));
                    selected.setManufacturer(txtManufacturer.getText());
                    return selected;
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Input", 
                             "Price and Quantity must be valid numbers!");
                    return null;
                }
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(medicine -> {
            boolean success = MedicineDAO.updateMedicine(medicine);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", 
                         "Medicine updated successfully!");
                handleRefresh();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", 
                         "Failed to update medicine!");
            }
        });
    }
    
    // حذف دواء
    private void handleDelete() {
        Medicine selected = tableStock.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", 
                     "Please select a medicine to delete!");
            return;
        }
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete: " + selected.getName() + "?");
        
        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            boolean success = MedicineDAO.deleteMedicine(selected.getId());
            
            if (success) {
                medicineList.remove(selected);
                updateInfoLabel();
                showAlert(Alert.AlertType.INFORMATION, "Deleted", 
                         "Medicine deleted successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", 
                         "Failed to delete medicine!");
            }
        }
    }
    
    // تحديث عداد الأدوية
    private void updateInfoLabel() {
        lblInfo.setText("Total Items: " + medicineList.size());
    }
    
    // عرض رسالة
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}