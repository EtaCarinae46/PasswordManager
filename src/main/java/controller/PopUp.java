package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import main.Main;

public class PopUp {

    public static ButtonType openWarning(String title, String body, ButtonType... btns) {
        return open(Alert.AlertType.WARNING, title, body, btns);
    }

    public static ButtonType openError(String title, String body, ButtonType... btns) {
        return open(Alert.AlertType.ERROR, title, body, btns);
    }

    private static ButtonType open(Alert.AlertType type, String title, String body, ButtonType... btns) {
        Alert confirm = new Alert(type, body, btns);

        confirm.setHeaderText(null);
        confirm.setTitle(title);
        confirm.initModality(Modality.APPLICATION_MODAL);
        confirm.initOwner(Main.getWindow());
        confirm.showAndWait();

        return confirm.getResult();
    }
}
