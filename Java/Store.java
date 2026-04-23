
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Store {
    private List<Product> productsList;
    private Queue<Order> orderQueue;
    private Staff staff;
    private  int storeID;
    private String storeName;
    private Map<DayOfWeek, OpeningHours> openingHoursMap = new HashMap<>();

    public Store(int storeID, String storeName,Staff staff) {
        this.storeID = storeID;
        this.storeName = storeName;
        this.staff = staff;
        this.productsList = new ArrayList<>();
        this.orderQueue = new LinkedList<>();
    }

    public void addProduct(int id, String name, int price) {
        Product p = new Product (id,name,this,price);
        productsList.add(p);
    }

   

    public void setProductStatus(Product product, boolean status) {
       
        if(productsList.contains(product)) {
            product.setAvailable(status);
        }
        else {
            System.out.println("Product นี้ไม่ได้อยู่ในร้านนี้");
        }
    }

    public void addOrder(Order order) {
        orderQueue.add(order);
    }

    public Order peekOrder() {
        return orderQueue.peek();
    }

    public void removeReadyOrder() {
        orderQueue.poll();
    }

    public void setOpeningHours(DayOfWeek day, OpeningHours hours) {
         openingHoursMap.put(day, hours);
    }

    public boolean isOpen() {
    DayOfWeek today = LocalDate.now().getDayOfWeek();

    OpeningHours hours = openingHoursMap.get(today);

    if (hours == null) {
        return false; // ไม่มีข้อมูล = ปิด
    }

    return hours.isOpenNow();
}

   //ตั้งค่าเวลาเปิดวันปกติทีเดียว
    public void setWeekdayHours(OpeningHours hours) {
    for (DayOfWeek day : DayOfWeek.values()) {
        if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
            openingHoursMap.put(day, hours);
             }
        }
    }

    public List<Product> getProductList() { return productsList; }
    public Queue<Order> getOrderQueue() { return orderQueue; }

    
}