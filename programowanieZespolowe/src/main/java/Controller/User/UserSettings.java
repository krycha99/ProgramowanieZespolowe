package Controller.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * The type User settings.
 */
public class UserSettings {
    @FXML
    private Pane SettingPane;

    /**
     * Sets .
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void setting(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/User/SettingsData.fxml"));
        SettingPane.getChildren().setAll(pane);
    }

    /**
     * Orders.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void orders(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/User/Orders.fxml"));
        SettingPane.getChildren().setAll(pane);
    }

    /**
     * Invoices.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void invoices(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/User/InvoicesData.fxml"));
        SettingPane.getChildren().setAll(pane);
    }


}
