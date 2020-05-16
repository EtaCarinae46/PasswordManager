package main;

import controller.Props;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

public class Launcher extends Application {
    private FXMLLoader mainLoader = Main.getMainLoader();

    public void start(Stage stage) throws Exception {
        Main.setWindow(stage);
        Stage window = Main.getWindow();
        String user = Props.getProp("user");
        if (StringUtils.isBlank(user)) {
            mainLoader.setLocation(Main.class.getResource("/fxml/register.fxml"));
        } else {
            mainLoader.setLocation(Main.class.getResource("/fxml/login.fxml"));
        }
        Parent root = mainLoader.load();
        Scene scene = new Scene(root);
        window.getIcons().add(new Image("/imgs/favicon.png"));
        window.setResizable(false);
        window.setScene(scene);
        window.show();
    }
}
