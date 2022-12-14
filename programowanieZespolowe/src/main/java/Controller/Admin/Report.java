package Controller.Admin;

import Controller.Report.ReportCreator;
import com.google.protobuf.Descriptors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * The type Report.
 */
public class Report implements Initializable {

    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private AnchorPane reportAnchorPane;

    /**
     * A method for generating reports from a time period selected by the user.
     *
     * @param actionEvent the action event
     */
    public void report(ActionEvent actionEvent) {
        String path = null;
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) reportAnchorPane.getScene().getWindow();

        if (dateFrom.getValue() == null || dateTo.getValue() == null) {
            try {
                File file = directoryChooser.showDialog(stage);
                if (file != null) {
                    path = file.getAbsolutePath();
                    ReportCreator reportCreator = new ReportCreator();
                    reportCreator.create(path);
                    dateFrom.setValue(null);
                    dateTo.setValue(null);
                    dateTo.setDisable(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                File file = directoryChooser.showDialog(stage);
                if (file != null) {
                    path = file.getAbsolutePath();
                    ReportCreator reportCreator = new ReportCreator();
                    reportCreator.create(dateFrom.getValue(), dateTo.getValue(), path);
                    dateFrom.setValue(null);
                    dateTo.setValue(null);
                    dateTo.setDisable(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dateTo.setDisable(true);
        LocalDate localDate = LocalDate.now();
        dateFrom.setDayCellFactory(e -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(localDate) > 0);
            }
        });

        dateFrom.valueProperty().addListener((observable, oldValue, newValue) -> {
            dateTo.setDisable(false);
            dateTo.setDayCellFactory(e -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || date.compareTo(dateFrom.getValue()) < 0 || date.compareTo(localDate) > 0);
                }
            });
        });

    }
}
