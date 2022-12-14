package Controller.Employee;

import Config.DbConnect;
import Config.Pojos.OrdersDetailsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The type Employee customers orders 2.
 */
public class EmployeeCustomersOrders2 implements Initializable {

    @FXML
    private TextField orderDetailIdCustomer;
    @FXML
    private TextField orderDetailName;
    @FXML
    private TextField orderDetailSurname;
    @FXML
    private TextField orderDetailMail;
    @FXML
    private TextField orderDetailAdress;
    @FXML
    private TextField orderDetailDetail;
    @FXML
    private TextField orderDetailIdOrder;

    @FXML
    private TableView<OrdersDetailsEntity> orderDetailTableView;
    @FXML
    private TableColumn<OrdersDetailsEntity, String> orderDetailProduct;
    @FXML
    private TableColumn<OrdersDetailsEntity, String> orderDetailPrice;
    @FXML
    private TableColumn<OrdersDetailsEntity, Integer> orderDetailQuantity;

    @FXML
    private Button orderConfirm;
    @FXML
    private Button orderCancel;

    @FXML
    private ComboBox<String> orderDetailStatus;

    /**
     * The constant bool.
     */
    public static boolean bool;
    /**
     * The Db connect.
     */
    DbConnect dbConnect = new DbConnect();
    /**
     * The Id.
     */
    int id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderDetailStatus.getItems().add("W trakcie realizacji");
        orderDetailStatus.getItems().add("Gotowe do wysyłki");
        orderDetailStatus.getItems().add("Zakończone");
    }

    /**
     * Method of orders details.
     *
     * @param idOrder the id order
     */
    public void metoda(int idOrder) {
        try {
            id = idOrder;
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM orders_details INNER JOIN orders ON orders_details.ID_order=orders.ID_order INNER JOIN users ON orders.ID_user=users.ID_user WHERE orders_details.ID_order = ? GROUP BY orders_details.ID_order");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                orderDetailIdCustomer.setText(rs.getString("ID_user"));
                orderDetailName.setText(rs.getString("Name"));
                orderDetailSurname.setText(rs.getString("Surname"));
                orderDetailMail.setText(rs.getString("Email"));
                orderDetailAdress.setText(rs.getString("Delivery_address"));
                orderDetailDetail.setText(rs.getString("Description"));
                orderDetailIdOrder.setText(rs.getString("ID_order"));
            }

            ObservableList<OrdersDetailsEntity> ordersDetailsList = FXCollections.observableArrayList();
            PreparedStatement st2 = dbConnect.getConnection().prepareStatement("SELECT * FROM orders_details INNER JOIN products ON orders_details.ID_product=products.ID_product WHERE ID_order=?");
            st2.setInt(1, id);
            ResultSet rs2 = st2.executeQuery();
            while (rs2.next()) {
                OrdersDetailsEntity o = new OrdersDetailsEntity();
                o.setProduct_name(rs2.getString("Name"));
                o.setPrice(rs2.getDouble("Price"));
                o.setQuantity(rs2.getInt("Quantity"));
                ordersDetailsList.add(o);
            }

            orderDetailProduct.setCellValueFactory(new PropertyValueFactory<>("Product_name"));
            orderDetailPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
            orderDetailQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

            orderDetailTableView.setItems(ordersDetailsList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Order confirm.
     *
     * @param actionEvent the action event
     */
    public void orderConfirm(ActionEvent actionEvent) {
        try {
            String tak = orderDetailStatus.getSelectionModel().getSelectedItem();
            PreparedStatement st = dbConnect.getConnection().prepareStatement("UPDATE orders SET Status = ? WHERE ID_order = ?");
            st.setString(1, tak);
            st.setInt(2, id);
            st.executeUpdate();
            bool = true;

            Stage stage = (Stage) orderConfirm.getScene().getWindow();
            stage.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Order cancel.
     *
     * @param actionEvent the action event
     */
    public void orderCancel(ActionEvent actionEvent) {
        try {
            PreparedStatement st = dbConnect.getConnection().prepareStatement("UPDATE orders SET Status = ? WHERE ID_order = ?");
            st.setString(1, "Anulowane");
            st.setInt(2, id);
            st.executeUpdate();
            bool = true;

            Stage stage = (Stage) orderCancel.getScene().getWindow();
            stage.close();
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
