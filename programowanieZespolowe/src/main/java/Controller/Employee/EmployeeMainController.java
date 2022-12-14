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


/**
 * The type Employee main controller.
 */
public class EmployeeMainController implements Initializable {

    @FXML
    private Pane userView;
    @FXML
    private Label userNameText;

    /**
     * The Db connect.
     */
    static DbConnect dbConnect = new DbConnect();

    /**
     * The constant userView2.
     */
    public static Pane userView2;
    /**
     * The constant userNameText2.
     */
    public static Label userNameText2;
    /**
     * The constant idUser.
     */
    public static int idUser;
    private static String tekst;
    /**
     * The Name.
     */
    static String imie = null, /**
     * The Surname.
     */
    nazwisko = null;

    /**
     * Logout.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void logout(ActionEvent actionEvent) throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("/FXML/Home/mainHome.fxml"));
        HomeMainController.anchorPane.getChildren().setAll(ap);
    }


    /**
     * Assortment.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void assortment(ActionEvent actionEvent) throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("/FXML/Employee/assortment.fxml"));
        HomeMainController.anchorPane.getChildren().setAll(ap);
    }

    /**
     * Orders.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void orders(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/Employee/EmployeeCustomersOrders.fxml"));
        userView.getChildren().setAll(pane);
    }

    /**
     * Shop.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void shop(ActionEvent actionEvent) throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("/FXML/Employee/EmployeeShop.fxml"));
        userView.getChildren().setAll(ap);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameText2 = userNameText;
        userNameText2.setText(tekst);

        userView2 = userView;
    }

    /**
     * Employee label.
     *
     * @param idUser the id user
     * @throws SQLException the sql exception
     */
    public void employeeLabel(int idUser) throws SQLException {
        this.idUser = idUser;

        PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM users WHERE ID_user = ?");
        st.setInt(1, idUser);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            imie = rs.getString("Name");
            nazwisko = rs.getString("Surname");
        }
        tekst = imie + " " + nazwisko;
        userNameText2.setText(tekst);
    }

    /**
     * Sets .
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void seting(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/Employee/EmployeeSettings.fxml"));
        userView.getChildren().setAll(pane);
    }

    /**
     * Cart.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void cart(ActionEvent actionEvent) throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("/FXML/Employee/Cart.fxml"));
        HomeMainController.anchorPane.getChildren().setAll(ap);
    }

    /**
     * Refresh.
     *
     * @throws SQLException the sql exception
     */
    public static void refresh() throws SQLException {
        PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM users WHERE ID_user = ?");
        st.setInt(1, idUser);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            imie = rs.getString("Name");
            nazwisko = rs.getString("Surname");
        }
        tekst = imie + " " + nazwisko;
        userNameText2.setText(tekst);
    }
}
