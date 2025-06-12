package ap.restaurant.restaurant;

import ap.restaurant.entities.DataBaseManager;
import ap.restaurant.entities.MenuItem;
import ap.restaurant.entities.OrderDetails;
import ap.restaurant.entities.Order;
import ap.restaurant.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class NewOrderPageController implements Initializable {
    private User user;
    private Stage stage;
    private Scene scene;

    Map<MenuItem, Integer> spinnerValues = new HashMap<>();

    @FXML
    private ListView<MenuItem> menu;


    public void initializer(User user) throws SQLException {
        this.user = user;
        List<MenuItem> menuItems = DataBaseManager.readMenuItemList();
        menu.getItems().addAll(menuItems);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menu.setCellFactory(lv -> new ListCell<>() {
            private final HBox content = new HBox();
            private final Label label = new Label();
            private final Spinner<Integer> spinner = new Spinner<>(0, 100, 0);

            {
                content.setSpacing(10);
                content.getChildren().addAll(label, spinner);

                spinner.valueProperty().addListener((obs, oldVal, newVal) -> {
                    if (getItem() != null) {
                        spinnerValues.put(getItem(), newVal);
                    }
                });
            }

            @Override
            protected void updateItem(MenuItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item.toString());
                    spinner.getValueFactory().setValue(spinnerValues.getOrDefault(item, 0));
                    setGraphic(content);
                }
            }
        });
    }

    @FXML
    public void checkout(ActionEvent event) throws Exception {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        int orderId = DataBaseManager.lastId("orders");
        DataBaseManager.placeOrder(orderId);
        int i = 0;
        for (MenuItem item : spinnerValues.keySet()) {
            int quantity = spinnerValues.get(item);
            if(quantity != 0){
                int id = DataBaseManager.lastId("order_details");
                int menuId = item.getId();
                double price = item.getPrice();
                double totalPrice = price * quantity;
                OrderDetails orderDetails = new OrderDetails(id, orderId, menuId, quantity, price, totalPrice);
                orderDetailsList.add(orderDetails);
                DataBaseManager.addNewOrderDetails(orderDetails);
            }
        }

        int orderTotalPrice = 0;
        for (OrderDetails orderDetails : orderDetailsList) {
            orderTotalPrice += orderDetails.getTotalPrice();
        }
        Order order = new Order(orderId, user.getId(), LocalDate.now(), orderTotalPrice);
        order.getOrderDetails().addAll(orderDetailsList);
        user.getOrders().add(order);

        DataBaseManager.addNewOrder(order);

        toCheckoutPage(event, order);
    }

    private void toCheckoutPage(ActionEvent event,Order order) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("OrderViewPage.fxml"));
        Parent root = loader.load();

        OrderViewController controller = loader.getController();
        controller.initializer(order,user);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}