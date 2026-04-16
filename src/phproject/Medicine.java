package phproject;

public class Medicine {
    private int id;
    private String name;
    private String barcode;
    private double price;
    private int quantity;
    private String expiryDate;
    private String manufacturer;
    private String type;
    private String description;
    
    // Constructor الكامل
    public Medicine(int id, String name, String barcode, double price, 
                   int quantity, String expiryDate, String manufacturer, String type) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.manufacturer = manufacturer;
        this.type = type;
        this.description = ""; // قيمة افتراضية
    }
    
    // Constructor بدون ID (للإضافة الجديدة)
    public Medicine(String name, String barcode, double price, 
                   int quantity, String expiryDate, String manufacturer, String type) {
        this(0, name, barcode, price, quantity, expiryDate, manufacturer, type);
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getBarcode() {
        return barcode;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public String getExpiryDate() {
        return expiryDate;
    }
    
    public String getManufacturer() {
        return manufacturer;
    }
    
    public String getType() {
        return type;
    }
    
    public String getDescription() {
        return description;
    }
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
  }
