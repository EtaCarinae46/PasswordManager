package controller.fxCtrls;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import controller.PopUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.model.PwdEntity;

public class NewRowCtrl {

    @FXML private TextField title;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField note;
    @FXML private JFXCheckBox upperCase;
    @FXML private JFXCheckBox lowerCase;
    @FXML private JFXCheckBox digits;
    @FXML private JFXCheckBox special;
    @FXML private JFXSlider length;
    @FXML private JFXTextField preview;

    @FXML
    private void buttonAction(ActionEvent event) {
        String t = title.getText();
        String u = username.getText();
        String p = password.getText();
        String n = note.getText();

        // Validate
        if (t.length() == 0 || p.length() == 0) {
            PopUp.openError("Cannot add row","The title & password needs to be filled in", ButtonType.OK);
            return;
        }

        // Update row
        PwdEntity entity = new PwdEntity(Main.getConnection().getNextId(), t, u, p, n);
        Main.getConnection().addPwd(entity);

        // Update table
        MainCtrl mainCtrl = Main.getMainLoader().getController();
        mainCtrl.updateTable();

        // Close modal
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void generate() {
        char[] upper = upperCase.isSelected() ? PasswordGenerator.getUpperCase() : new char[0];
        char[] lower = lowerCase.isSelected() ? PasswordGenerator.getLowerCase() : new char[0];
        char[] digit = digits.isSelected() ? PasswordGenerator.getDigits() : new char[0];
        char[] spec = special.isSelected() ? PasswordGenerator.getSpecial() : new char[0];
        char[] charSet = PasswordGenerator.concat(upper, lower, digit, spec);
        
        PasswordGenerator pg = new PasswordGenerator((int)length.getValue(), charSet);
        String pwd = pg.generate();
        preview.setText(pwd);
        password.setText(pwd);
    }
}
