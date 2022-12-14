package Controller.Admin;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * The type Admin main controller.
 */
public class AdminMainController implements Initializable {
    @FXML
    private Pane adminView;
    @FXML
    private Label userNameText;

    /**
     * The Db connect.
     */
    static DbConnect dbConnect = new DbConnect();

    /**
     * The constant adminView2.
     */
    public static Pane adminView2;
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
     * The Imie.
     */
    static String imie = null, /**
     * The Nazwisko.
     */
    nazwisko = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameText2 = userNameText;
        userNameText2.setText(tekst);

        adminView2 = adminView;
    }

    /**
     * Logout.
     *
     * @throws IOException the io exception
     */
    public void logout() throws IOException {
        AnchorPane ap = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Home/mainHome.fxml")));
        HomeMainController.anchorPane.getChildren().setAll(ap);
    }

    /**
     * Assortment.
     *
     * @throws IOException the io exception
     */
    public void assortment() throws IOException {
        AnchorPane ap = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Admin/assortment.fxml")));
        adminView.getChildren().setAll(ap);
    }

    /**
     * Customer.
     *
     * @throws IOException the io exception
     */
    public void customer() throws IOException {
        AnchorPane ap = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Admin/customers.fxml")));
        adminView.getChildren().setAll(ap);
    }

    /**
     * Orders customer.
     *
     * @throws IOException the io exception
     */
    public void ordersCustomer() throws IOException {
        AnchorPane ap = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Admin/orderCustomer.fxml")));
        adminView.getChildren().setAll(ap);
    }

    /**
     * Employee.
     *
     * @throws IOException the io exception
     */
    public void employee() throws IOException {
        AnchorPane ap = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Admin/employee.fxml")));
        adminView.getChildren().setAll(ap);
    }

    /**
     * Order employee.
     *
     * @throws IOException the io exception
     */
    public void orderEmployee() throws IOException {
        AnchorPane ap = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Admin/orderEmployee.fxml")));
        adminView.getChildren().setAll(ap);
    }

    /**
     * Admin label.
     *
     * @param idUser the id user
     * @throws SQLException the sql exception
     */
    public void adminLabel(int idUser) throws SQLException {
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
    public void setings(ActionEvent actionEvent) throws IOException {
        AnchorPane ap = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Admin/setings.fxml")));
        adminView.getChildren().setAll(ap);
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
