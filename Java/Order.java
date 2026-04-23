
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderID;
    private Customer customer;
    private Store store;
    private  List<OrderItem> orderItems;  
    private OrderStatus status;

    public enum OrderStatus {
        Received,
        Cooking,
        Ready
    }

    public Order(int orderID, Customer customer,Store store){
        this.orderID = orderID;
        this.customer = customer;
        this.store = store;
        this.orderItems = new ArrayList<>();
        this.status = OrderStatus.Received;
    }

     public void addItem(Product product, int quantity) {
        for (OrderItem item : orderItems) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(quantity);
                return;
            }
        }

        orderItems.add(new OrderItem(product, quantity));
    }

     public void removeItem(Product product) {
        orderItems.removeIf(item -> item.getProduct().equals(product));
    }

    public boolean addOrderItem(OrderItem orderItem) {
        // ตรวจสอบว่าสินค้าอยู่ใน store เดียวกัน
        if (orderItem.getProduct().getStore().equals(this.store)) {
            orderItems.add(orderItem);
            return true;
        } else {
            System.out.println("Product ไม่ได้อยู่ในร้านนี้");
            return false;
        }
    }

    public double calculateTotalPrice() {
        double total = 0;

        for (OrderItem item : orderItems) {
            total += item.getTotalPrice();
        }

        return total;
    }

    public void updateStatus(OrderStatus newStatus) {

         //คุมไม่ให้อัพเดทสถานะย้อนกลับ
         if (this.status == OrderStatus.Received && newStatus == OrderStatus.Cooking) {
            this.status = newStatus;
        } 
        else if (this.status == OrderStatus.Cooking && newStatus == OrderStatus.Ready) {
            this.status = newStatus;
        } 
        else {
            System.out.println("เปลี่ยนสถานะไม่ถูกต้อง");
        }

    }

    public int getOrderID() { return orderID; }
    public Customer getCustomer() { return  customer;}
    public Store getStore() { return  store; }
    public List<OrderItem> getOrderItemList() { return  orderItems; }
    public OrderStatus getOrderStatus() { return  status;}
    
}
