package ap.restaurant.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private LocalDate createdAt;
    private double totalPrice;
    private final List<OrderDetails> orderDetails = new ArrayList<>();

    public Order(int id, int userId, LocalDate createdAt, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public String toString(){
        return id + "-" + createdAt + "-" + totalPrice;
    }

    public LocalDate getDate() {
        return createdAt;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}