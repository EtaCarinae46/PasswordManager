package controller.fxCtrls;

import controller.Props;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.connection.JDBC;
import org.apache.commons.lang.StringUtils;

public class RegisterCtrl {
    
    @FXML private TextField username;
    @FXML private TextField password;

    @FXML
    private void initialize() {
        Main.getWindow().setTitle("Register");
    }

    @FXML
    private void createDatabase(ActionEvent event) throws IOException {
        if (StringUtils.isBlank(username.getText()) ||
                StringUtils.isBlank(password.getText()) ||
                password.getText().length() < 4)
            return;
        Props.setProp("user", username.getText());
        Main.setConnection(new JDBC(Props.getProp("db-url"), username.getText(), password.getText()));
        if (Main.getConnection().isReady()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/main.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            Main.setMainLoader(loader);
            stage.show();
        }
    }
}
