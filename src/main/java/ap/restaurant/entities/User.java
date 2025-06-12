package ap.restaurant.entities;

import java.util.ArrayList;
import java.util.List;

public class User{
    private int id;
    private String username;
    private String password;
    private String email;
    private byte[] salt;
    private List<Order> orders = new ArrayList<Order>();
    public User(int id, String username, String password, String email, byte[] salt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.salt = salt;
    }
    public int getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public List<Order> getOrders(){
        return orders;
    }
    public byte[] getSalt(){
        return salt;
    }
}