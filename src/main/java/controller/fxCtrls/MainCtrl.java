package controller.fxCtrls;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.ArrayList;

import controller.PopUp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.connection.JDBC;
import model.model.PwdEntity;

public class MainCtrl {

    @FXML private TableView tableView;
    @FXML private TextField filter;
    private JDBC jdbc = Main.getConnection();

    /**
     * Init function, sets up the basics of the main tableView
     * like...  columns, default sizes, row click actions
     */
    @FXML
    private void initialize() {
        Main.getWindow().setTitle("Password Manager");

        // Shown columns
        TableColumn<String, PwdEntity> title = new TableColumn<>("Title");
        TableColumn<String, PwdEntity> username = new TableColumn<>("Username");
        TableColumn<String, PwdEntity> note = new TableColumn<>("Note");

        // Related variable in the bo
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));

        // Default comumn size
        title.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        username.prefWidthProperty().bind(tableView.widthProperty().divide(3));
        note.prefWidthProperty().bind(tableView.widthProperty().divide(4));

        tableView.getColumns().add(title);
        tableView.getColumns().add(username);
        tableView.getColumns().add(note);

        // Click actions on rows
        tableView.setRowFactory(tableView -> {
            TableRow<PwdEntity> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (row.isEmpty()) return;
                if (event.getButton() == MouseButton.SECONDARY) {

                    // Prepare right click menu
                    ContextMenu cm = new ContextMenu();
                    MenuItem mi1 = new MenuItem("Edit Row");
                    MenuItem mi2 = new MenuItem("Copy Password");
                    MenuItem mi3 = new MenuItem("Remove row");

                    cm.getItems().add(mi1);
                    cm.getItems().add(mi2);
                    cm.getItems().add(mi3);

                    // Open edit row modal
                    mi1.setOnAction(e -> openEditRow(row.getItem()));

                    // Copy password to clipboard
                    mi2.setOnAction(e -> {
                        StringSelection stringSelection = new StringSelection(row.getItem().getPassword());
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(stringSelection, null);
                    });

                    // Remove current row from DB
                    mi3.setOnAction(e -> {
                        String body = String.format("This action cannot be undone! \nAre you sure you want to delete row %s", row.getItem().getTitle());
                        ButtonType b = PopUp.openWarning("Remove row", body,ButtonType.YES, ButtonType.NO);

                        if (b == ButtonType.YES) {
                            Main.getConnection().removeRow(row.getItem());
                            updateTable();
                        }
                    });

                    cm.show(Main.getWindow(), event.getScreenX(), event.getScreenY());
                } else if (event.getClickCount() == 2) {
                    // Open edit row modal
                    openEditRow(row.getItem());
                }
            });
            return row ;
        });

        updateTable();
    }

    /**
     * Opens an edit row modal (not fxml related)
     * @param row the row that we wanna edit
     */
    private void openEditRow(PwdEntity row) {
        FXMLLoader loader = openPopUp("/fxml/editRow.fxml", "Edit row");
        if (loader != null) {
            EditRowCtrl editRowCtrl = loader.getController();
            editRowCtrl.init(row);
        }
    }

    /**
     * Opens a modal where we can add new rows (fxml related)
     */
    @FXML
    private void openNewRow() {
        openPopUp("/fxml/newRow.fxml", "Add new password");
    }

    /**
     * Opens a generic modal based on the fxmlPath
     * @param fxmlPath the path of the fxml file (template)
     * @param title the title of modal
     * @return FXMLLoader in case we want to get or pass information through the modal's controller
     */
    private FXMLLoader openPopUp(String fxmlPath, String title) {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(fxmlPath));

        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("Error during opening popUp");
            return null;
        }

        Stage popUp = new Stage();
        popUp.setScene(new Scene(root));
        popUp.setResizable(false);
        popUp.initOwner(Main.getWindow());
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setTitle(title);
        popUp.show();

        return loader;
    }

    /**
     * Updates all rows in the main tableView
     */
    void updateTable() {
        ArrayList<PwdEntity> entities;
        if (filter.getText().length() == 0) {
            entities = jdbc.getAllPwd();
        } else {
            entities = jdbc.getFilteredPwd(filter.getText());
        }
        ObservableList<PwdEntity> data = FXCollections.observableArrayList();
        data.addAll(entities);
        tableView.setItems(data);
    }

    /**
     * Removes all rows from the DB then updates the table
     */
    public void removeAll() {
        String body = "This action cannot be reversed! \nAre you sure you want to delete all passwords?";
        ButtonType t = PopUp.openWarning("Removing all rows", body,ButtonType.YES, ButtonType.NO);

        if (t == ButtonType.YES) {
            Main.getConnection().removeAll();
            updateTable();
        }
    }

    /**
     * Closes the main window/the whole app
     */
    public void closeWindow() {
        Main.getWindow().close();
    }

    @FXML
    private void filter() {
        updateTable();
    }
}
