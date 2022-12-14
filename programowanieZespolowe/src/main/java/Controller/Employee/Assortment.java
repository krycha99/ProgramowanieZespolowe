package Controller.Employee;

import Config.DbConnect;
import Controller.Home.HomeMainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Controller.Employee.EmployeeMainController.idUser;

/**
 * Assortment.
 */
public class Assortment implements Initializable {
    @FXML
    private Pane view;

    /**
     * anchorPane.
     */
    public static AnchorPane anchorPane;
    /**
     * Db connect.
     */
    DbConnect dbConnect = new DbConnect();

    @FXML
    private Label userNameText;

    /**
     * Wyloguj
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void logout(ActionEvent actionEvent) throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("/FXML/Home/mainHome.fxml"));
        HomeMainController.anchorPane.getChildren().setAll(ap);
    }

    /**
     * Cofnij
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void back(ActionEvent actionEvent) throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("/FXML/Employee/MainEmployee.fxml"));
        HomeMainController.anchorPane.getChildren().setAll(ap);
    }

    /**
     * Procesory
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void processor(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/Employee/EmployeeProcessor.fxml"));
        view.getChildren().setAll(pane);
    }

    /**
     * Karty graficzne
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void graphicCard(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/Employee/EmployeeGraphics.fxml"));
        view.getChildren().setAll(pane);
    }

    /**
     * Pamięć ram
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void ram(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/Employee/EmployeeRAM.fxml"));
        view.getChildren().setAll(pane);
    }

    /**
     * Dyski
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void drive(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/Employee/EmployeeDrive.fxml"));
        view.getChildren().setAll(pane);
    }

    /**
     * Zasilacze
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void power(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/Employee/EmployeePower.fxml"));
        view.getChildren().setAll(pane);
    }

    /**
     * Inicjalizacja
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String tekst = "Pracownik";
            String imie = null, nazwisko = null;
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM users WHERE ID_user = ?");
            st.setInt(1, idUser);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                imie = rs.getString("Name");
                nazwisko = rs.getString("Surname");
            }
            tekst = imie + " " + nazwisko;
            userNameText.setText(tekst);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
