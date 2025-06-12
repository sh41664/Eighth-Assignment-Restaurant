package ap.restaurant.restaurant;

import ap.restaurant.entities.Order;
import ap.restaurant.entities.OrderDetails;
import ap.restaurant.entities.OrdersPageController;
import ap.restaurant.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;


public class OrderViewController {
    private Stage stage;

    private User user;

    @FXML
    private Label priceTag;

    @FXML
    private ListView<OrderDetails> orderDetails;

    public void initializer(Order order, User user) {
        this.user = user;
        priceTag.setText(order.getTotalPrice() + " $");
        orderDetails.getItems().addAll(order.getOrderDetails());
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("OrdersPage.fxml"));
        Parent root = loader.load();

        OrdersPageController controller = loader.getController();
        controller.initializer(user);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


}