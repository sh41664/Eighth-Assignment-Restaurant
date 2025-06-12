package ap.restaurant.entities;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private int id;
    private String name;
    private double price;
    private String description;

    public MenuItem(int id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String toString(){
        return name + " - " + price + "$ : \"" + description +"\"";
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }
}