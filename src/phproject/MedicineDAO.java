package phproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAO {
    
    // إضافة دواء جديد
    public static boolean addMedicine(Medicine medicine) {
        String sql = """
            INSERT INTO medicines (name, barcode, price, quantity, expiry_date, manufacturer, type, description)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, medicine.getName());
            pstmt.setString(2, medicine.getBarcode());
            pstmt.setDouble(3, medicine.getPrice());
            pstmt.setInt(4, medicine.getQuantity());
            pstmt.setString(5, medicine.getExpiryDate());
            pstmt.setString(6, medicine.getManufacturer());
            pstmt.setString(7, medicine.getType());
            pstmt.setString(8, medicine.getDescription());
            
            int rowsAffected = pstmt.executeUpdate(); // ضيف الدوا الجديد في قاعدة البيانات
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // الحصول على جميع الأدوية
    public static List<Medicine> getAllMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        String sql = "SELECT * FROM medicines ORDER BY id DESC";
        
        try (Statement stmt = Database.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Medicine medicine = new Medicine(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("barcode"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("expiry_date"),
                    rs.getString("manufacturer"),
                    rs.getString("type")
                );
                medicine.setDescription(rs.getString("description"));
                medicines.add(medicine);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }    
        return medicines;
    }
    
    // البحث عن دواء بالاسم أو الباركود
    public static List<Medicine> searchMedicine(String keyword) {
        List<Medicine> medicines = new ArrayList<>();
        String sql = "SELECT * FROM medicines WHERE name LIKE ? OR barcode LIKE ? ORDER BY name";
        
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Medicine medicine = new Medicine(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("barcode"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("expiry_date"),
                    rs.getString("manufacturer"),
                    rs.getString("type")
                );
                medicine.setDescription(rs.getString("description"));
                medicines.add(medicine);
            }  
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return medicines;
    }
    
    // تحديث بيانات دواء
    public static boolean updateMedicine(Medicine medicine) {
        String sql = """
            UPDATE medicines 
            SET name = ?, barcode = ?, price = ?, quantity = ?, 
                expiry_date = ?, manufacturer = ?, type = ?, description = ?
            WHERE id = ?
        """;
        
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, medicine.getName());
            pstmt.setString(2, medicine.getBarcode());
            pstmt.setDouble(3, medicine.getPrice());
            pstmt.setInt(4, medicine.getQuantity());
            pstmt.setString(5, medicine.getExpiryDate());
            pstmt.setString(6, medicine.getManufacturer());
            pstmt.setString(7, medicine.getType());
            pstmt.setString(8, medicine.getDescription());
            pstmt.setInt(9, medicine.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // حذف دواء
    public static boolean deleteMedicine(int id) {
        String sql = "DELETE FROM medicines WHERE id = ?";
        
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // الحصول على دواء بالـ ID
    public static Medicine getMedicineById(int id) {
        String sql = "SELECT * FROM medicines WHERE id = ?";
        
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Medicine medicine = new Medicine(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("barcode"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("expiry_date"),
                    rs.getString("manufacturer"),
                    rs.getString("type")
                );
                medicine.setDescription(rs.getString("description"));
                return medicine;
            } 
        } catch (SQLException e) {
            System.out.println("❌ Error : " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // التحقق من وجود باركود مكرر
    public static boolean isBarcodeExists(String barcode, int excludeId) {
        String sql = "SELECT COUNT(*) FROM medicines WHERE barcode = ? AND id != ?";
        
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, barcode);
            pstmt.setInt(2, excludeId);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return false;
    }
    
    // الحصول على عدد الأدوية
    public static int getMedicineCount() {
        String sql = "SELECT COUNT(*) FROM medicines";
        
        try (Statement stmt = Database.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error : " + e.getMessage());
        }
        
        return 0;
    }
    
    // الحصول على الأدوية منتهية الصلاحية أو قريبة من الانتهاء
    public static List<Medicine> getExpiringMedicines(int daysThreshold) {
        List<Medicine> medicines = new ArrayList<>();
        String sql = """
            SELECT * FROM medicines 
            WHERE date(expiry_date) <= date('now', '+' || ? || ' days')
            ORDER BY expiry_date
        """;
        
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, daysThreshold);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Medicine medicine = new Medicine(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("barcode"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("expiry_date"),
                    rs.getString("manufacturer"),
                    rs.getString("type")
                );
                medicine.setDescription(rs.getString("description"));
                medicines.add(medicine);
            }   
        } catch (SQLException e) {
            System.out.println("❌ Error : " + e.getMessage());
        }
        return medicines;
    }
    
    // الحصول على الأدوية قليلة الكمية
    public static List<Medicine> getLowStockMedicines(int threshold) {
        List<Medicine> medicines = new ArrayList<>();
        String sql = "SELECT * FROM medicines WHERE quantity <= ? ORDER BY quantity";
        
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, threshold);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Medicine medicine = new Medicine(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("barcode"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("expiry_date"),
                    rs.getString("manufacturer"),
                    rs.getString("type")
                );
                medicine.setDescription(rs.getString("description"));
                medicines.add(medicine);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        
        return medicines;
    }
}