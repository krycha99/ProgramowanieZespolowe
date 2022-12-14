package Controller.Employee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

/**
 * The type Employee shop.
 */
public class EmployeeShop {
    @FXML
    private Pane view;

    /**
     * Processor.
     *
     * @throws IOException the io exception
     */
    public void processor() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Employee/EmployeeShopProcessor.fxml")));
        view.getChildren().setAll(pane);
    }

    /**
     * Graphic card.
     *
     * @throws IOException the io exception
     */
    public void graphicCard() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Employee/EmployeeShopGraphicCard.fxml")));
        view.getChildren().setAll(pane);
    }

    /**
     * Ram.
     *
     * @throws IOException the io exception
     */
    public void ram() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Employee/EmployeeShopRam.fxml")));
        view.getChildren().setAll(pane);
    }

    /**
     * Drive.
     *
     * @throws IOException the io exception
     */
    public void drive() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Employee/EmployeeShopDrive.fxml")));
        view.getChildren().setAll(pane);
    }

    /**
     * Power.
     *
     * @throws IOException the io exception
     */
    public void power() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Employee/EmployeeShopPower.fxml")));
        view.getChildren().setAll(pane);
    }

    /**
     * Test.
     *
     * @throws IOException the io exception
     */
    public void test() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Home/proponowane.fxml")));
        view.getChildren().setAll(pane);
    }
}
