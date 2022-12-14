package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Main controller.
 */
public class MainController implements Initializable {

    @FXML
    private Pane mainView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            AnchorPane ap = FXMLLoader.load(getClass().getResource("/FXML/Home/mainHome.fxml"));
            mainView.getChildren().setAll(ap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}