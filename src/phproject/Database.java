package phproject;

import java.sql.*;

public class Database {
    private static Connection connection = null;
    
    // الاتصال بالداتابيز
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:sqlite:pharmacy.db");
                createTables(); // إنشاء الجداول عند الاتصال
            }
        } catch (SQLException e) {
            System.out.println( e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
    
    // إنشاء جدول الأدوية
    private static void createTables() {
        String createMedicinesTable = """
            CREATE TABLE IF NOT EXISTS medicines (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                barcode TEXT UNIQUE NOT NULL,
                price REAL NOT NULL,
                quantity INTEGER NOT NULL,
                expiry_date TEXT NOT NULL,
                manufacturer TEXT NOT NULL,
                type TEXT NOT NULL,
                description TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createMedicinesTable);
        } catch (SQLException e) {
            System.out.println( e.getMessage());
            e.printStackTrace();
        }
    }
    
    // إغلاق الاتصال
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }
}