package ap.restaurant.restaurant;


import ap.restaurant.entities.OrdersPageController;
import ap.restaurant.entities.User;
import ap.restaurant.entities.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPageController {
    Stage stage;
    Scene scene;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label message;

    @FXML
    public void logIn(ActionEvent event) throws Exception {
        try {
            String username = this.username.getText();
            String password = this.password.getText();
            Object result = UserManager.logIn(username, password);
            if (result instanceof User loggedInUser) {
                OrdersPage(event, loggedInUser);
            } else if (result instanceof String warningMessage) {
                message.setText(warningMessage);
            }
        }catch(Exception e) {
            message.setText(e.getMessage());
        }
    }

    @FXML
    public void toSignUpPage(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SignUpPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void OrdersPage(ActionEvent event, User user) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("OrdersPage.fxml"));
        Parent root = loader.load();

        OrdersPageController controller = loader.getController();
        controller.initializer(user);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}