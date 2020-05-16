package main;

import controller.Props;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.connection.JDBC;
import javafx.scene.image.Image;

public class Main {
    private static FXMLLoader mainLoader = new FXMLLoader();
    private static JDBC connection;
    private static Stage window;

    public static void main(String[] args) {
        boolean propReady = true;
        if (!Props.exists())
            propReady = Props.createProp();
        if (propReady)
            Application.launch(Launcher.class, args);
    }

    public static void setMainLoader(FXMLLoader mainLoader) {
        Main.mainLoader = mainLoader;
    }

    public static FXMLLoader getMainLoader() {
        return mainLoader;
    }

    public static void setConnection(JDBC connection) {
        Main.connection = connection;
    }

    public static JDBC getConnection() {
        return connection;
    }

    public static Stage getWindow() {
        return window;
    }

    public static void setWindow(Stage window) {
        Main.window = window;
    }
}
