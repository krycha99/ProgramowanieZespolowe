package Controller.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * The type Settings.
 */
public class Settings {

    @FXML
    private Pane SettingPane;

    /**
     * Sets .
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void setting(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/Admin/settingsData.fxml"));
        SettingPane.getChildren().setAll(pane);
    }

    /**
     * Report.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void report(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/Admin/report.fxml"));
        SettingPane.getChildren().setAll(pane);
    }
}
