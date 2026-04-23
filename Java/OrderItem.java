
public class OrderItem {
    private Product product;
    private int quantity;
    private int totalPrice; 

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;
    }

    public void addQuantity() {
        quantity += 1;
        updateTotalPrice();
    }

    public void reduceQuantity() {
        if ((quantity-1) > 0) {
            quantity -= 1;
            updateTotalPrice();
        }
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
            updateTotalPrice();
        }
    }

    private void updateTotalPrice() {
        this.totalPrice = product.getPrice() * quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() {return  quantity; }
    public int getTotalPrice() { return  totalPrice; }

}