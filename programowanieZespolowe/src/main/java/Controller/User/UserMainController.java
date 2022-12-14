package Controller.User;

import Config.DbConnect;
import Controller.Home.HomeMainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
 * The type User main controller.
 */
public class UserMainController implements Initializable {

    @FXML
    private Button CartButton;
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
     * The Imie.
     */
    static String imie = null, /**
     * The Nazwisko.
     */
    nazwisko = null;


    /**
     * Cart.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void cart(ActionEvent actionEvent) throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("/FXML/User/Cart.fxml"));
        HomeMainController.anchorPane.getChildren().setAll(ap);
    }

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
     * Processor.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void processor(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/User/UserProcessor.fxml"));
        userView.getChildren().setAll(pane);
    }

    /**
     * Graphic card.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void graphicCard(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/User/UserGraphicCard.fxml"));
        userView.getChildren().setAll(pane);
    }

    /**
     * Ram.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void ram(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/User/UserRam.fxml"));
        userView.getChildren().setAll(pane);
    }

    /**
     * Drive.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void drive(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/User/UserDrive.fxml"));
        userView.getChildren().setAll(pane);
    }

    /**
     * Power.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void power(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/User/UserPower.fxml"));
        userView.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameText2 = userNameText;
        userNameText2.setText(tekst);

        userView2 = userView;
    }

    /**
     * Sets .
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void seting(ActionEvent actionEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/FXML/User/UserSettings.fxml"));
        userView.getChildren().setAll(pane);
    }

    /**
     * Customer label.
     *
     * @param idUser the id user
     * @throws SQLException the sql exception
     */
    public void customerLabel(int idUser) throws SQLException {
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
