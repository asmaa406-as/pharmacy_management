package phproject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * AddMedicine - صفحة إضافة دواء جديد
 * @author Asmaa Sami
 */
public class AddMedicine {
    
    private TextField txtMedicineName;
    private TextField txtBarcode;
    private TextField txtPrice;
    private TextField txtQuantity;
    private DatePicker dateExpiry;
    private TextField txtManufacturer;
    private ComboBox<String> comboType;
    private TextArea txtDescription;
    private Label lblStatus;
    
    public VBox getView() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Title
        Label titleLabel = new Label("💊 Add New Medicine");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 26));
        titleLabel.setTextFill(Color.web("#2c3e50"));
        
        Separator separator = new Separator();
        
        // Form Container
        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(15);
        formGrid.setPadding(new Insets(25));
        formGrid.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        // Medicine Name
        Label lblMedicineName = new Label("Medicine Name:");
        lblMedicineName.setFont(Font.font("System", FontWeight.NORMAL, 14));
        txtMedicineName = new TextField();
        txtMedicineName.setPromptText("Enter medicine name");
        txtMedicineName.setPrefWidth(300);
        txtMedicineName.setPrefHeight(35);
        
        // Barcode
        Label lblBarcode = new Label("Barcode/ID:");
        lblBarcode.setFont(Font.font("System", FontWeight.NORMAL, 14));
        txtBarcode = new TextField();
        txtBarcode.setPromptText("Enter barcode or ID");
        txtBarcode.setPrefWidth(300);
        txtBarcode.setPrefHeight(35);
        
        // Price
        Label lblPrice = new Label("Price (EGP):");
        lblPrice.setFont(Font.font("System", FontWeight.NORMAL, 14));
        txtPrice = new TextField();
        txtPrice.setPromptText("Enter price");
        txtPrice.setPrefWidth(300);
        txtPrice.setPrefHeight(35);
        
        // Quantity
        Label lblQuantity = new Label("Quantity:");
        lblQuantity.setFont(Font.font("System", FontWeight.NORMAL, 14));
        txtQuantity = new TextField();
        txtQuantity.setPromptText("Enter quantity");
        txtQuantity.setPrefWidth(300);
        txtQuantity.setPrefHeight(35);
        
        // Expiry Date
        Label lblExpiry = new Label("Expiry Date:");
        lblExpiry.setFont(Font.font("System", FontWeight.NORMAL, 14));
        dateExpiry = new DatePicker();
        dateExpiry.setPromptText("Select expiry date");
        dateExpiry.setPrefWidth(300);
        
        // Manufacturer
        Label lblManufacturer = new Label("Manufacturer:");
        lblManufacturer.setFont(Font.font("System", FontWeight.NORMAL, 14));
        txtManufacturer = new TextField();
        txtManufacturer.setPromptText("Enter manufacturer name");
        txtManufacturer.setPrefWidth(300);
        txtManufacturer.setPrefHeight(35);
        
        // Type
        Label lblType = new Label("Type:");
        lblType.setFont(Font.font("System", FontWeight.NORMAL, 14));
        comboType = new ComboBox<>();
        comboType.getItems().addAll("Tablet", "Capsule", "Syrup", "Injection", 
                                    "Cream", "Drop", "Inhaler", "Other");
        comboType.setPromptText("Select type");
        comboType.setPrefWidth(300);
        
        // Description
        Label lblDescription = new Label("Description:");
        lblDescription.setFont(Font.font("System", FontWeight.NORMAL, 14));
        txtDescription = new TextArea();
        txtDescription.setPromptText("Enter description (optional)");
        txtDescription.setPrefWidth(300);
        txtDescription.setPrefHeight(80);
        
        // Add to Grid
        formGrid.add(lblMedicineName, 0, 0);
        formGrid.add(txtMedicineName, 1, 0);
        
        formGrid.add(lblBarcode, 0, 1);
        formGrid.add(txtBarcode, 1, 1);
        
        formGrid.add(lblPrice, 0, 2);
        formGrid.add(txtPrice, 1, 2);
        
        formGrid.add(lblQuantity, 0, 3);
        formGrid.add(txtQuantity, 1, 3);
        
        formGrid.add(lblExpiry, 0, 4);
        formGrid.add(dateExpiry, 1, 4);
        
        formGrid.add(lblManufacturer, 0, 5);
        formGrid.add(txtManufacturer, 1, 5);
        
        formGrid.add(lblType, 0, 6);
        formGrid.add(comboType, 1, 6);
        
        formGrid.add(lblDescription, 0, 7);
        formGrid.add(txtDescription, 1, 7);
        
        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button btnSave = new Button("💾 Save Medicine");
        btnSave.setPrefWidth(180);
        btnSave.setPrefHeight(40);
        btnSave.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; " +
                        "-fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;");
        btnSave.setOnAction(e -> handleSave());
        
        Button btnClear = new Button("🔄 Clear Fields");
        btnClear.setPrefWidth(180);
        btnClear.setPrefHeight(40);
        btnClear.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; " +
                         "-fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;");
        btnClear.setOnAction(e -> handleClear());
        
        buttonBox.getChildren().addAll(btnSave, btnClear);
        
        // Status Label
        lblStatus = new Label();
        lblStatus.setFont(Font.font("System", FontWeight.BOLD, 14));
        lblStatus.setAlignment(Pos.CENTER);
        
        root.getChildren().addAll(titleLabel, separator, formGrid, buttonBox, lblStatus);
        
        return root;
    }
    
    private void handleSave() {
        // التحقق من الحقول المطلوبة
        if (txtMedicineName.getText().trim().isEmpty()) {
            showError("⚠ Please enter medicine name!");
            return;
        }
        
        if (txtBarcode.getText().trim().isEmpty()) {
            showError("⚠ Please enter barcode!");
            return;
        }
        
        if (txtPrice.getText().trim().isEmpty()) {
            showError("⚠ Please enter price!");
            return;
        }
        
        if (txtQuantity.getText().trim().isEmpty()) {
            showError("⚠ Please enter quantity!");
            return;
        }
        
        if (dateExpiry.getValue() == null) {
            showError("⚠ Please select expiry date!");
            return;
        }
        
        if (txtManufacturer.getText().trim().isEmpty()) {
            showError("⚠ Please enter manufacturer!");
            return;
        }
        
        if (comboType.getValue() == null) {
            showError("⚠ Please select medicine type!");
            return;
        }
        
        try {
            // التحقق من صحة الأرقام
            double price = Double.parseDouble(txtPrice.getText().trim());
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            
            if (price <= 0) {
                showError("⚠ Price must be greater than zero!");
                return;
            }
            
            if (quantity < 0) {
                showError("⚠ Quantity cannot be negative!");
                return;
            }
            
            // التحقق من الباركود المكرر
            String barcode = txtBarcode.getText().trim();
            if (MedicineDAO.isBarcodeExists(barcode, 0)) {
                showError("⚠ This barcode already exists!");
                return;
            }
            
            // إنشاء كائن الدواء
            String expiryDate = dateExpiry.getValue().toString();
            
            Medicine medicine = new Medicine(
                0,
                txtMedicineName.getText().trim(),
                barcode,
                price,
                quantity,
                expiryDate,
                txtManufacturer.getText().trim(),
                comboType.getValue()
            );
            
            // إضافة الوصف إذا كان موجود
            String description = txtDescription.getText().trim();
            if (!description.isEmpty()) {
                medicine.setDescription(description);
            }
            
            // حفظ في الداتابيز
            boolean success = MedicineDAO.addMedicine(medicine);
            
            if (success) {
                lblStatus.setText("✅ Medicine saved successfully!");
                lblStatus.setTextFill(Color.GREEN);
                handleClear();
            } else {
                showError("❌ Failed to save medicine. Please try again!");
            }
            
        } catch (NumberFormatException e) {
            showError("⚠ Price and Quantity must be valid numbers!");
        } catch (Exception e) {
            showError("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void handleClear() {
        txtMedicineName.clear();
        txtBarcode.clear();
        txtPrice.clear();
        txtQuantity.clear();
        dateExpiry.setValue(null);
        txtManufacturer.clear();
        comboType.setValue(null);
        txtDescription.clear();
        lblStatus.setText("");
    }
    
    private void showError(String message) {
        lblStatus.setText(message);
        lblStatus.setTextFill(Color.RED);
    }
}