// คลาสแม่ (Superclass) สำหรับสินค้าในร้าน
public  class Product {
    protected  int productID;
    protected  String productName;
    protected   Store store;
    protected  int price;
    protected   boolean isAvailable=true;

    public Product(int id, String name,Store store, int price) {
        this.productID = id;
        this.productName = name;
        this.store = store;
        this.price = price;
    }

    public void setAvailable(boolean status) {
        this.isAvailable = status;
    }

    public int getProductID() {return  productID; }
    public String getName() { return productName; }
    public int getPrice() { return price; }
    public Store getStore() {return  store; }
    public boolean isAvailable() {return  isAvailable; }
    
    
}
