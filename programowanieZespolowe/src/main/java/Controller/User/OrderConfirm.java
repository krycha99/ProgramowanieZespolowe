package Controller.User;

import Config.DbConnect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The type Order confirm.
 */
public class OrderConfirm implements Initializable {

    /**
     * The constant bool.
     */
    public static boolean bool;
    @FXML
    private TextField orderAddress;
    @FXML
    private ComboBox<String> orderPayment;
    @FXML
    private TextField orderDescription;
    @FXML
    private Button orderConfirm;

    private final ArrayList<Integer> paymentList = new ArrayList<Integer>();
    /**
     * The Db connect.
     */
    DbConnect dbConnect = new DbConnect();
    /**
     * The Id.
     */
    int id = UserMainController.idUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectPayment();
    }

    /**
     * Method for choosing the payment method in the checkout process.
     */
    private void selectPayment() {
        try {
            paymentList.clear();
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM payments");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                orderPayment.getItems().add(rs.getString("Name"));
                paymentList.add(rs.getInt("ID_payment"));
            }

            PreparedStatement st2 = dbConnect.getConnection().prepareStatement("SELECT * FROM users WHERE ID_user = ?");
            st2.setInt(1, id);
            ResultSet rs2 = st2.executeQuery();
            while (rs2.next()){
                orderAddress.setText(rs2.getString("Address"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Order confirm.
     */
    public void orderConfirm() {
        try {
            int idPayment = paymentList.get(orderPayment.getSelectionModel().getSelectedIndex());
            LocalDate date = LocalDate.now();
            PreparedStatement st = dbConnect.getConnection().prepareStatement("INSERT INTO orders(id_order, id_user, status, date, delivery_address, id_payment, description) " +
                    "VALUES (null,?,'Złożone',?,?,?,?)");
            st.setInt(1, id);
            st.setDate(2, Date.valueOf(date));
            st.setString(3, orderAddress.getText());
            st.setInt(4, idPayment);
            st.setString(5, orderDescription.getText());
            st.executeUpdate();

            int idOrder = 0;
            PreparedStatement st2 = dbConnect.getConnection().prepareStatement("SELECT * FROM orders WHERE ID_user = ? ORDER BY ID_order DESC LIMIT 1");
            st2.setInt(1, UserMainController.idUser);
            ResultSet rs = st2.executeQuery();
            while (rs.next()) {
                idOrder = rs.getInt("ID_order");
            }

            PreparedStatement st3 = dbConnect.getConnection().prepareStatement("SELECT * FROM cart INNER JOIN products ON cart.ID_product=products.ID_product WHERE ID_user = ?");
            st3.setInt(1, UserMainController.idUser);
            ResultSet rs2 = st3.executeQuery();
            while (rs2.next()) {
                PreparedStatement st4 = dbConnect.getConnection().prepareStatement("INSERT INTO orders_details(ID_order_details, id_order, id_product, price, quantity) " +
                        "values (null,?,?,?,?)");
                st4.setInt(1, idOrder);
                st4.setInt(2, rs2.getInt("ID_product"));
                st4.setDouble(3, rs2.getDouble("Price"));
                st4.setInt(4, rs2.getInt("cart.Quantity"));
                st4.executeUpdate();
            }

            PreparedStatement st5 = dbConnect.getConnection().prepareStatement("DELETE FROM cart WHERE ID_user = ?");
            st5.setInt(1, UserMainController.idUser);
            st5.executeUpdate();

            bool = true;

            Stage stage = (Stage) orderConfirm.getScene().getWindow();
            stage.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Udało się");
            alert.setHeaderText("Twoje zamówienie zostało przekazane do realizacji");
            alert.showAndWait();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Ref bool boolean.
     *
     * @return the boolean
     */
    public static boolean refBool() {
        return bool;
    }
}
