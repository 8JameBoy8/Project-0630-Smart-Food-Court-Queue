
public class Customer extends User {
    private Reservation currentReservation;
    private Order currentOrder;

    public Customer(int userID, String userName, String email, String password) {
        super(userID, userName, email, password); 
    }
    
    public Reservation reserveTable(Table table, int durationMinutes, int reservationId) {

        // เช็คว่ามีการจองอยู่แล้วไหม
        if (currentReservation != null && currentReservation.isActive()) {
          System.out.println("คุณมีการจองอยู่แล้ว");
            return null;
    }

         if (!table.isAvailable()) {
            System.out.println("โต๊ะไม่ว่าง");
            return null;
        }

        currentReservation = new Reservation(
            reservationId,
            this,
            table,
            durationMinutes
        );

    return currentReservation;
}

    public void cancelReservation() {
    if (currentReservation != null) {
        currentReservation.cancel();
        currentReservation = null;
    }
}

    public void startOrder(int id,Store store) {
        if (currentOrder != null) {
           System.out.println("คุณมีออเดอร์อยู่แล้ว");
           return;
         }

         currentOrder = new Order(id, this, store);
    }

    public void addItemToOrder(Product product, int quantity) {
    if (currentOrder == null) {
        System.out.println("ยังไม่ได้เริ่มสั่งอาหาร");
        return;
    }

    currentOrder.addItem(product, quantity);
}

    public void placeOrder() {
         if (currentOrder == null) {
              System.out.println("ไม่มีออเดอร์");
              return;
            }

        currentOrder.getStore().addOrder(currentOrder);

        currentOrder = null; // เคลียร์หลังส่ง
}

    public Reservation getReservations() {
        return currentReservation;
    }

}