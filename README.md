# 🏥 Pharmacy Management System

A modern desktop application for managing pharmacy operations built with **JavaFX**.

## ✨ Features

- **Login System** with two accounts:
  - `admin` / `admin`
  - `asmaa` / `1234`
- **Dashboard** with clean side menu
- **Add New Medicine** (with validation)
- **View Stock** with search and CRUD operations
- **Services & Reports** including statistics, charts, and alerts
- **SQLite Database** integration (no server needed)
- Beautiful modern UI with emojis and smooth design

## 🛠 Technologies Used

- **Java** + **JavaFX**
- **SQLite** (via JDBC)
- **Scene Builder** (for UI design)
- **Maven** / **Gradle** (depending on your setup)

## 🚀 How to Run the Project

### 1. Prerequisites
- JDK 17 or higher
- JavaFX SDK (version 21 or 23 recommended)
- IntelliJ IDEA  or Eclipse or netbesns

### 2. Run in IntelliJ IDEA

1. Open the project in IntelliJ
2. Go to **Run → Edit Configurations**
3. Add VM Options:
   (Replace `/path/to/javafx-sdk/lib` with your actual JavaFX path)
4. Run the main class: `PhProject.java`

### 3. Login Credentials
- **Admin**: `admin` / `admin`
- **User**: `asmaa` / `1234`

## 📁 Project Structure
phProject/
├── src/
│   └── phproject/
│       ├── PhProject.java          (Main Entry Point)
│       ├── LoginPage.java
│       ├── Dashboard.java
│       ├── AddMedicine.java
│       ├── ViewStock.java
│       ├── Services.java
│       ├── Thanks.java
│       ├── Database.java
│       ├── Medicine.java
│       └── MedicineDAO.java
├── imgs/
│   └── ww.jpg                      (Background image)
└── pharmacy.db                     (SQLite Database)

## 🗄 Database

The project uses **SQLite** (`pharmacy.db`).  
Tables are created automatically on first run.

## 👤 Author

**Asmaa Sami**

## 📄 License

This project is for educational purposes.
