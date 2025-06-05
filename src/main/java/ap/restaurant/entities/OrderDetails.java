package ap.restaurant.entities;

public class OrderDetails {
    private int id;
    private int orderId;
    private int menuItemId;
    private int quantity;
    private double price;
    private double totalPrice;

    public OrderDetails(int id, int orderId, int menuItemId, int quantity, double price, double totalPrice) {
        this.id = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }
    public int getOrderId() {
        return orderId;
    }
    public int getMenuItemId() {
        return menuItemId;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    public String toString() {
        return menuItemId + "\t" + quantity + "\t" + price + "\t" + totalPrice;
    }
}