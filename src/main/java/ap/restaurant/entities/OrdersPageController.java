package ap.restaurant.entities;

import ap.restaurant.entities.Order;
import ap.restaurant.entities.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrdersPageController implements Initializable {
    private User user;
    private Order selectedOrder;
    private Stage stage;
    private Scene scene;

    @FXML
    private Label welcomeLabel;

    @FXML
    private ListView<Order> ordersList;



    public void initializer(User user) {
        this.user = user;
        welcomeLabel.setTextFill(Color.GREEN);
        welcomeLabel.setText("Welcome " + user.getUsername());
        if(user.getOrders() != null) {
            ordersList.getItems().addAll(user.getOrders());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ordersList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {

            @Override
            public void changed(ObservableValue<? extends Order> observableValue, Order order, Order t1) {
                selectedOrder = ordersList.getSelectionModel().getSelectedItem();
            }
        });
    }

    @FXML
    public void newOrder(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("NewOrderPage.fxml"));
        Parent root = loader.load();

        NewOrderPageController controller = loader.getController();
        controller.initializer(user);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void viewOrder(ActionEvent event) throws Exception {
        if(selectedOrder != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("OrderViewPage.fxml"));
            Parent root = loader.load();

            OrderViewController controller = loader.getController();
            controller.initializer(selectedOrder, user);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else{
            welcomeLabel.setTextFill(Color.RED);
            welcomeLabel.setText("Please select an order");
        }

    }

}