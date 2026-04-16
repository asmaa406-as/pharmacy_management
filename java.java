package phproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Services - صفحة الخدمات والتقارير
 * @author Asmaa Sami
 */
public class Services {
    
    private Label lblTotalMedicines;
    private Label lblLowStock;
    private Label lblExpired;
    private Label lblTotalValue;
    private ListView<String> listAlerts;
    
    public VBox getView() {
        VBox root = new VBox(25);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Title
        Label titleLabel = new Label("⚙️ Services & Reports");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 26));
        titleLabel.setTextFill(Color.web("#2dd4bf"));
        
        Separator separator = new Separator();
        
        // Statistics Section
        VBox statsBox = new VBox(15);
        statsBox.setPadding(new Insets(20));
        statsBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        Label statsTitle = new Label("📊 Statistics");
        statsTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        statsTitle.setTextFill(Color.web("#2dd4bf"));
        
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(30);
        statsGrid.setVgap(15);
        
        // Total Medicines Card
        VBox totalCard = createStatCard("Total Medicines", "0", "#3498db");
        lblTotalMedicines = (Label) totalCard.getChildren().get(1);
        
        // Low Stock Card
        VBox lowStockCard = createStatCard("Low Stock Items", "0", "#e74c3c");
        lblLowStock = (Label) lowStockCard.getChildren().get(1);
        
        // Expired Card
        VBox expiredCard = createStatCard("Expired/Expiring", "0", "#f39c12");
        lblExpired = (Label) expiredCard.getChildren().get(1);
        
        // Total Value Card
        VBox valueCard = createStatCard("Total Stock Value", "0 EGP", "#27ae60");
        lblTotalValue = (Label) valueCard.getChildren().get(1);
        
        statsGrid.add(totalCard, 0, 0);
        statsGrid.add(lowStockCard, 1, 0);
        statsGrid.add(expiredCard, 2, 0);
        statsGrid.add(valueCard, 3, 0);
        
        statsBox.getChildren().addAll(statsTitle, statsGrid);
        
        // Alerts Section
        VBox alertsBox = new VBox(15);
        alertsBox.setPadding(new Insets(20));
        alertsBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        Label alertsTitle = new Label("🔔 Alerts & Notifications");
        alertsTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        alertsTitle.setTextFill(Color.web("#2dd4bf"));
        
        listAlerts = new ListView<>();
        listAlerts.setPrefHeight(250);
        listAlerts.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 1;");
        
        Button btnRefreshAlerts = new Button("🔄 Refresh Alerts");
        btnRefreshAlerts.setPrefHeight(35);
        btnRefreshAlerts.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                                 "-fx-font-size: 14px; -fx-cursor: hand;");
        btnRefreshAlerts.setOnAction(e -> loadAlerts());
        
        alertsBox.getChildren().addAll(alertsTitle, listAlerts, btnRefreshAlerts);
        
        // Reports Section
        VBox reportsBox = new VBox(15);
        reportsBox.setPadding(new Insets(20));
        reportsBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        Label reportsTitle = new Label("📄 Reports");
        reportsTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        reportsTitle.setTextFill(Color.web("#2dd4bf"));
        
        HBox reportButtons = new HBox(15);
        
        Button btnDailyReport = new Button("📊 Generate Daily Report");
        btnDailyReport.setPrefHeight(40);
        btnDailyReport.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; " +
                               "-fx-font-size: 14px; -fx-cursor: hand; -fx-padding: 10 20;");
        btnDailyReport.setOnAction(e -> handleDailyReport());
        
        Button btnMonthlyReport = new Button("📈 Generate Monthly Report");
        btnMonthlyReport.setPrefHeight(40);
        btnMonthlyReport.setStyle("-fx-background-color: #16a085; -fx-text-fill: white; " +
                                 "-fx-font-size: 14px; -fx-cursor: hand; -fx-padding: 10 20;");
        btnMonthlyReport.setOnAction(e -> handleMonthlyReport());
        
        Button btnLowStockReport = new Button("📉 Low Stock Report");
        btnLowStockReport.setPrefHeight(40);
        btnLowStockReport.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                              "-fx-font-size: 14px; -fx-cursor: hand; -fx-padding: 10 20;");
        btnLowStockReport.setOnAction(e -> handleLowStockReport());
        
        reportButtons.getChildren().addAll(btnDailyReport, btnMonthlyReport, btnLowStockReport);
        
        reportsBox.getChildren().addAll(reportsTitle, reportButtons);
        
        // Add all sections
        root.getChildren().addAll(titleLabel, separator, statsBox, alertsBox, reportsBox);
        
        // Load initial statistics and alerts
        loadStatistics();
        loadAlerts();
        
        return root;
    }
    
    private VBox createStatCard(String title, String value, String color) {
        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setPrefWidth(200);
        card.setPrefHeight(100);
        card.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 8;");
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", 14));
        titleLabel.setTextFill(Color.WHITE);
        
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 32));
        valueLabel.setTextFill(Color.WHITE);
        
        card.getChildren().addAll(titleLabel, valueLabel);
        
        return card;
    }
    
    // تحميل الإحصائيات من الداتابيز
    private void loadStatistics() {
        try {
            List<Medicine> allMedicines = MedicineDAO.getAllMedicines();
            
            // إجمالي الأدوية
            int totalMedicines = allMedicines.size();
            lblTotalMedicines.setText(String.valueOf(totalMedicines));
            
            // حساب الأدوية قليلة المخزون (أقل من 20 وحدة)
            int lowStockCount = 0;
            for (Medicine med : allMedicines) {
                if (med.getQuantity() < 20) {
                    lowStockCount++;
                }
            }
            lblLowStock.setText(String.valueOf(lowStockCount));
            
            // حساب الأدوية منتهية الصلاحية أو قريبة من الانتهاء (خلال 60 يوم)
            int expiredCount = 0;
            LocalDate today = LocalDate.now();
            LocalDate threshold = today.plusDays(60);
            
            for (Medicine med : allMedicines) {
                try {
                    LocalDate expiryDate = LocalDate.parse(med.getExpiryDate());
                    if (expiryDate.isBefore(threshold)) {
                        expiredCount++;
                    }
                } catch (Exception e) {
                    // ignore invalid dates
                }
            }
            lblExpired.setText(String.valueOf(expiredCount));
            
            // حساب القيمة الإجمالية للمخزون
            double totalValue = 0;
            for (Medicine med : allMedicines) {
                totalValue += (med.getPrice() * med.getQuantity());
            }
            lblTotalValue.setText(String.format("%.2f EGP", totalValue));
            
        } catch (Exception e) {
            System.out.println("❌ خطأ في تحميل الإحصائيات: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // تحميل التنبيهات
    private void loadAlerts() {
        ObservableList<String> alerts = FXCollections.observableArrayList();
        
        try {
            List<Medicine> allMedicines = MedicineDAO.getAllMedicines();
            LocalDate today = LocalDate.now();
            
            int alertCount = 0;
            
            for (Medicine med : allMedicines) {
                // تنبيه للأدوية قليلة المخزون
                if (med.getQuantity() < 10) {
                    alerts.add("🔴 URGENT: " + med.getName() + " - Critical low stock (Only " + 
                              med.getQuantity() + " units left) - Order immediately!");
                    alertCount++;
                } else if (med.getQuantity() < 20) {
                    alerts.add("⚠️ WARNING: " + med.getName() + " - Low stock (Only " + 
                              med.getQuantity() + " units left) - Consider reordering");
                    alertCount++;
                }
                
                // تنبيه للأدوية منتهية الصلاحية أو قريبة من الانتهاء
                try {
                    LocalDate expiryDate = LocalDate.parse(med.getExpiryDate());
                    long daysUntilExpiry = ChronoUnit.DAYS.between(today, expiryDate);
                    
                    if (daysUntilExpiry < 0) {
                        alerts.add("❌ EXPIRED: " + med.getName() + " - Expired " + 
                                  Math.abs(daysUntilExpiry) + " days ago - Remove from stock!");
                        alertCount++;
                    } else if (daysUntilExpiry <= 30) {
                        alerts.add("⏰ URGENT: " + med.getName() + " - Expiring in " + 
                                  daysUntilExpiry + " days - Clear stock soon!");
                        alertCount++;
                    } else if (daysUntilExpiry <= 60) {
                        alerts.add("📅 NOTICE: " + med.getName() + " - Expiring in " + 
                                  daysUntilExpiry + " days - Monitor closely");
                        alertCount++;
                    }
                    
                    // تنبيه للطلب المسبق (قبل انتهاء الصلاحية بـ 90 يوم)
                    if (daysUntilExpiry <= 90 && daysUntilExpiry > 60 && med.getQuantity() < 30) {
                        alerts.add("📦 REORDER: " + med.getName() + " - Expiring in " + 
                                  daysUntilExpiry + " days with low stock (" + med.getQuantity() + 
                                  " units) - Plan reorder now!");
                        alertCount++;
                    }
                    
                } catch (Exception e) {
                    // ignore invalid dates
                }
                
                // حد أقصى 20 تنبيه لتجنب التحميل الزائد
                if (alertCount <= 20) break;
            }
            
            if (alerts.isEmpty()) {
                alerts.add("✅ All good! No alerts at this time.");
            }
            
            listAlerts.setItems(alerts);
            
        } catch (Exception e) {
            alerts.add("❌ Error loading alerts: " + e.getMessage());
            listAlerts.setItems(alerts);
            e.printStackTrace();
        }
    }
    
    // تقرير يومي
    private void handleDailyReport() {
        try {
            List<Medicine> allMedicines = MedicineDAO.getAllMedicines();
            LocalDate today = LocalDate.now();
            
            StringBuilder report = new StringBuilder();
            report.append("📊 DAILY REPORT - ").append(today).append("\n");
            report.append("═══════════════════════════════════\n\n");
            
            report.append("📦 Total Medicines: ").append(allMedicines.size()).append("\n");
            
            // حساب الكميات
            int totalQuantity = 0;
            double totalValue = 0;
            for (Medicine med : allMedicines) {
                totalQuantity += med.getQuantity();
                totalValue += (med.getPrice() * med.getQuantity());
            }
            
            report.append("📊 Total Units: ").append(totalQuantity).append("\n");
            report.append("💰 Total Value: ").append(String.format("%.2f EGP", totalValue)).append("\n\n");
            
            report.append("⚠️ ALERTS:\n");
            int lowStock = 0;
            int expiringSoon = 0;
            
            for (Medicine med : allMedicines) {
                if (med.getQuantity() < 20) lowStock++;
                
                try {
                    LocalDate expiryDate = LocalDate.parse(med.getExpiryDate());
                    long daysUntilExpiry = ChronoUnit.DAYS.between(today, expiryDate);
                    if (daysUntilExpiry <= 60) expiringSoon++;
                } catch (Exception e) {}
            }
            
            report.append("  • Low Stock Items: ").append(lowStock).append("\n");
            report.append("  • Expiring Soon: ").append(expiringSoon).append("\n");
            
            showReport("Daily Report", report.toString());
            
        } catch (Exception e) {
            showError("Failed to generate daily report: " + e.getMessage());
        }
    }
    
    // تقرير شهري
    private void handleMonthlyReport() {
        try {
            List<Medicine> allMedicines = MedicineDAO.getAllMedicines();
            
            StringBuilder report = new StringBuilder();
            report.append("📈 MONTHLY REPORT\n");
            report.append("═══════════════════════════════════\n\n");
            
            report.append("📦 Total Medicines in Stock: ").append(allMedicines.size()).append("\n\n");
            
            // تصنيف حسب النوع
            report.append("📋 BY TYPE:\n");
            int tablets = 0, capsules = 0, syrups = 0, injections = 0, others = 0;
            
            for (Medicine med : allMedicines) {
                switch (med.getType().toLowerCase()) {
                    case "tablet": tablets++; break;
                    case "capsule": capsules++; break;
                    case "syrup": syrups++; break;
                    case "injection": injections++; break;
                    default: others++;
                }
            }
            
            report.append("  • Tablets: ").append(tablets).append("\n");
            report.append("  • Capsules: ").append(capsules).append("\n");
            report.append("  • Syrups: ").append(syrups).append("\n");
            report.append("  • Injections: ").append(injections).append("\n");
            report.append("  • Others: ").append(others).append("\n\n");
            
            // أعلى 5 أدوية قيمة
            report.append("💎 TOP 5 BY VALUE:\n");
            allMedicines.sort((a, b) -> Double.compare(
                b.getPrice() * b.getQuantity(), 
                a.getPrice() * a.getQuantity()
            ));
            
            for (int i = 0; i < Math.min(5, allMedicines.size()); i++) {
                Medicine med = allMedicines.get(i);
                double value = med.getPrice() * med.getQuantity();
                report.append("  ").append(i + 1).append(". ")
                      .append(med.getName())
                      .append(" - ").append(String.format("%.2f EGP", value))
                      .append("\n");
            }
            
            showReport("Monthly Report", report.toString());
            
        } catch (Exception e) {
            showError("Failed to generate monthly report: " + e.getMessage());
        }
    }
    
    // تقرير المخزون المنخفض
    private void handleLowStockReport() {
        try {
            List<Medicine> allMedicines = MedicineDAO.getAllMedicines();
            
            StringBuilder report = new StringBuilder();
            report.append("📉 LOW STOCK REPORT\n");
            report.append("═══════════════════════════════════\n\n");
            
            boolean foundLowStock = false;
            
            for (Medicine med : allMedicines) {
                if (med.getQuantity() < 20) {
                    foundLowStock = true;
                    String urgency = med.getQuantity() < 10 ? "🔴 CRITICAL" : "⚠️ LOW";
                    report.append(urgency).append(" - ")
                          .append(med.getName())
                          .append(" (").append(med.getQuantity()).append(" units)\n")
                          .append("  Barcode: ").append(med.getBarcode())
                          .append(" | Price: ").append(med.getPrice()).append(" EGP\n\n");
                }
            }
            
            if (!foundLowStock) {
                report.append("✅ All medicines are sufficiently stocked!");
            }
            
            showReport("Low Stock Report", report.toString());
            
        } catch (Exception e) {
            showError("Failed to generate low stock report: " + e.getMessage());
        }
    }
    
    // عرض التقرير
    private void showReport(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        
        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(20);
        textArea.setPrefColumnCount(50);
        
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }
    
    // عرض خطأ
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}