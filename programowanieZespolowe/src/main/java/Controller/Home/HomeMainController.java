package Controller.Home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The type Home main controller.
 */
public class HomeMainController implements Initializable {


    @FXML
    private Pane view;

    /**
     * The Main view.
     */
    @FXML
    public AnchorPane mainView;

    /**
     * The constant anchorPane.
     */
    public static AnchorPane anchorPane;

    /**
     * Login.
     *
     * @throws IOException the io exception
     */
    public void login() throws IOException {
        Stage stage = new Stage();
        AnchorPane ap = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Login/login.fxml")));
        Scene scene = new Scene(ap);
        stage.setScene(scene);
        stage.setTitle("Logowanie");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Register.
     *
     * @throws IOException the io exception
     */
    public void register() throws IOException {
        Stage stage = new Stage();
        AnchorPane ap = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Login/register.fxml")));
        Scene scene = new Scene(ap);
        stage.setScene(scene);
        stage.setTitle("Rejestracja");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Processor.
     *
     * @throws IOException the io exception
     */
    public void processor() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Home/processor.fxml")));
        view.getChildren().setAll(pane);
    }

    /**
     * Graphic card.
     *
     * @throws IOException the io exception
     */
    public void graphicCard() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Home/graphicCard.fxml")));
        view.getChildren().setAll(pane);
    }

    /**
     * Ram.
     *
     * @throws IOException the io exception
     */
    public void ram() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Home/ram.fxml")));
        view.getChildren().setAll(pane);
    }

    /**
     * Drive.
     *
     * @throws IOException the io exception
     */
    public void drive() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Home/drive.fxml")));
        view.getChildren().setAll(pane);
    }

    /**
     * Power.
     *
     * @throws IOException the io exception
     */
    public void power() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Home/power.fxml")));
        view.getChildren().setAll(pane);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (anchorPane == null) {
            anchorPane = mainView;
        }

    }
}
