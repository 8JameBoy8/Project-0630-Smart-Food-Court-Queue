// คลาสแม่ (Superclass) สำหรับสินค้าในร้าน
public abstract class MenuItem {
    private String id;
    private String name;
    private double price;

    public MenuItem(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    
    // Method ที่ให้คลาสลูกไปเขียนการทำงานต่อ
    public abstract void displayItemDetails(); 
}

// คลาสลูก (Subclass) สำหรับอาหารจานหลัก
public class MainCourse extends MenuItem {
    private boolean isSpicy;

    public MainCourse(String id, String name, double price, boolean isSpicy) {
        super(id, name, price); // เรียกใช้ Constructor ของคลาสแม่
        this.isSpicy = isSpicy;
    }

    @Override
    public void displayItemDetails() {
        System.out.println("อาหาร: " + getName() + " | ราคา: " + getPrice() + " บาท" + (isSpicy ? " (เผ็ด)" : ""));
    }
}

// คลาสลูก (Subclass) สำหรับท็อปปิ้ง (เช่น ไข่ดาว, ไข่เจียว ตามใน Figma)
public class Topping extends MenuItem {
    public Topping(String id, String name, double price) {
        super(id, name, price);
    }

    @Override
    public void displayItemDetails() {
        System.out.println("เพิ่มท็อปปิ้ง: " + getName() + " | +" + getPrice() + " บาท");
    }
}