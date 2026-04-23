public class Staff extends User {
     private Store store;

    public Staff(int userID, String userName, String email, String password, Store store) {
        super(userID, userName, email, password);
        this.store = store;
    }

    public void startCooking() {
        Order order = store.peekOrder();

        if (order != null) {
            order.updateStatus(Order.OrderStatus.Cooking);
        }
    }

    public void finishOrder() {
        Order order = store.peekOrder();

        if (order != null) {
            order.updateStatus(Order.OrderStatus.Ready);
        }
    }

    public void callNextOrder() {
        store.removeReadyOrder();
    }

    public Store getStore() {return store;}
}