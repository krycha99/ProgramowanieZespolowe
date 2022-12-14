package Controller.Employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * The type Employee settings.
 */
public class EmployeeSettings {
    @FXML
    private Pane SettingPane;

    /**
     * Sets .
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void setting(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/Employee/EmployeeSettingsData.fxml"));
        SettingPane.getChildren().setAll(pane);
    }

    /**
     * Orders.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void orders(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/Employee/EmployeeOrders.fxml"));
        SettingPane.getChildren().setAll(pane);
    }

    /**
     * Invoices.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void invoices(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/Employee/EmployeeInvoicesData.fxml"));
        SettingPane.getChildren().setAll(pane);
    }
}
