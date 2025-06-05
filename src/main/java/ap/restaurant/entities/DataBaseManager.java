package ap.restaurant.entities;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataBaseManager {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/restaurant_AP";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "D29011385m";
    public static Connection connectToDataBase() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    static void addNewUser(User user) throws SQLException {

        Connection conn = connectToDataBase();
        String sql = "INSERT INTO users VALUES (?,?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,user.getId());
        pstmt.setString(2,user.getUsername());
        pstmt.setString(3,user.getPassword());
        pstmt.setString(4,user.getEmail());
        pstmt.setBytes(5,user.getSalt());
        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    static User findUser(String username) throws SQLException {
        Connection conn = connectToDataBase();
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,username);
        ResultSet rs = pstmt.executeQuery();

        User user = null;
        if(rs.next()) {
            int id = rs.getInt("id");
            String rsUsername = rs.getString("username");
            String rsPassword = rs.getString("password");
            String rsEmail = rs.getString("email");
            byte[] rsSalt = rs.getBytes("salt");
            user = new User(id,rsUsername,rsPassword,rsEmail,rsSalt);
            user.getOrders().addAll(findOrders(user.getId()));
        }

        pstmt.close();
        conn.close();
        return user;
    }

    private static List<Order> findOrders(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        Connection conn = connectToDataBase();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,userId);
        ResultSet rs = pstmt.executeQuery();
        System.out.println(rs);
        while(rs.next()) {
            int id = rs.getInt("id");
            userId = rs.getInt("user_id");
            LocalDate date = rs.getDate("created_at").toLocalDate();
            double totalPrice = rs.getDouble("total_price");
            Order order = new Order(id,userId,date,totalPrice);
            order.getOrderDetails().addAll(orderDetailsFinder(id));
            orders.add(order);
        }


        pstmt.close();
        conn.close();

        return orders;
    }

    //finds all order details related to a single order
    private static Collection<OrderDetails> orderDetailsFinder(int orderId) throws SQLException {
        Collection<OrderDetails> orderDetails = new ArrayList<>();
        Connection conn = connectToDataBase();
        String sql = "SELECT * FROM order_details WHERE order_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,orderId);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            int id = rs.getInt("id");
            orderId = rs.getInt("order_id");
            int menuItemId = rs.getInt("menu_item_id");
            int quantity = rs.getInt("quantity");
            double price = rs.getDouble("price");
            double totalPrice = rs.getDouble("total_price");
            orderDetails.add(new OrderDetails(id,orderId,menuItemId,quantity,price,totalPrice));
        }

        pstmt.close();
        conn.close();
        return orderDetails;
    }

    public static int lastId(String table) throws SQLException {
        Connection conn = connectToDataBase();
        String sql = "SELECT MAX(id) FROM "+table+";";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        int  id = 0;
        try{
            if(rs.next()) {
                id = rs.getInt(1);
            }
        }catch(Exception e) {
            System.out.println("No previous id found");
        }

        pstmt.close();
        conn.close();
        return id+1;
    }

    public static List<MenuItem> readMenuItemList() throws SQLException {
        Connection conn = connectToDataBase();
        String sql = "SELECT * FROM menu_item";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<MenuItem> menuItems = new ArrayList<>();
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            String description = rs.getString("description");
            menuItems.add(new MenuItem(id,name,price,description));
        }
        pstmt.close();
        conn.close();
        return menuItems;

    }

    public static void addNewOrderDetails(OrderDetails orderDetails) throws SQLException {
        Connection conn = connectToDataBase();
        String sql = "INSERT INTO order_details VALUES (?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,orderDetails.getId());
        pstmt.setInt(2,orderDetails.getOrderId());
        pstmt.setInt(3,orderDetails.getMenuItemId());
        pstmt.setInt(4,orderDetails.getQuantity());
        pstmt.setDouble(5,orderDetails.getPrice());
        pstmt.setDouble(6,orderDetails.getTotalPrice());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public static void addNewOrder(Order order) throws SQLException {
        Connection conn = connectToDataBase();
        String sql = "UPDATE orders SET id=? , user_id=? , created_at=?, total_price=? WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,order.getId());
        pstmt.setInt(2,order.getUserId());
        pstmt.setDate(3,java.sql.Date.valueOf(order.getDate()));
        pstmt.setDouble(4,order.getTotalPrice());
        pstmt.setInt(5,order.getId());

        pstmt.executeUpdate();

        pstmt.close();
        conn.close();
    }

    public static void placeOrder(int id) throws SQLException {
        Connection conn = connectToDataBase();
        String sql = "Insert into orders (id) values(?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,id);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }


}