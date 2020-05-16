package controller.fxCtrls;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Main;
import model.model.PwdEntity;

public class EditRowCtrl {

    @FXML private TextField title;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField note;
    @FXML private JFXButton togglePasswd;

    private String tmpPass = "";
    private PwdEntity editedPwd;

    void init(PwdEntity item) {
        editedPwd = item;
        title.setText(item.getTitle());
        username.setText(item.getUsername());
        password.setText(item.getPassword());
        note.setText(item.getNote());
        togglePasswd.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            tmpPass = password.getText();
            password.clear();
            password.setPromptText(tmpPass);
        });
        togglePasswd.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            password.setPromptText("");
            password.setText(tmpPass);
        });
    }

    public void editRow(ActionEvent event) {

        if (!title.getText().isBlank()) editedPwd.setTitle(title.getText());
        if (!username.getText().isBlank()) editedPwd.setUsername(username.getText());
        if (!password.getText().isBlank()) editedPwd.setPassword(password.getText());
        if (!note.getText().isBlank()) editedPwd.setNote(note.getText());

        Main.getConnection().updateRow(editedPwd);
        MainCtrl mainCtrl = Main.getMainLoader().getController();
        mainCtrl.updateTable();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
