package controller.fxCtrls;

import controller.PopUp;
import controller.Props;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import main.Main;
import model.connection.JDBC;

public class LoginCtrl {

    @FXML private TextField username;
    @FXML private TextField password;

    @FXML
    private void initialize() {
        Main.getWindow().setTitle("Login");
        username.setText(Props.getProp("user"));
    }

    @FXML
    private void openDatabase() throws IOException {
        Main.setConnection(new JDBC(Props.getProp("db-url"), username.getText(), password.getText()));
        if (Main.getConnection().isReady()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/main.fxml"));
            Parent root = loader.load();
            Main.getWindow().setScene(new Scene(root));
            Main.setMainLoader(loader);
            Main.getWindow().show();
        } else {
            PopUp.openError("Cannot login", Main.getConnection().getError(), ButtonType.OK);
        }
    }
}
