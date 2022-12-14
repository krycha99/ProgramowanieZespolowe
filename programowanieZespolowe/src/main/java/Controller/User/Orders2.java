package Controller.User;

import Config.DbConnect;
import Config.Pojos.OrdersDetailsEntity;
import Config.Pojos.OrdersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Orders 2.
 */
public class Orders2 {
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
    private Button orderCancel;

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

    /**
     * A method for managing orders from the employee's panel.
     *
     * @param idOrder the id order
     */
    public void metoda(int idOrder) {
        try {
            id = idOrder;
            String status = null;
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM orders_details INNER JOIN orders ON orders_details.ID_order=orders.ID_order INNER JOIN users ON orders.ID_user=users.ID_user WHERE orders_details.ID_order = ? GROUP BY orders_details.ID_order");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                status = rs.getString("Status");
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
                o.setPrice(rs2.getInt("Price"));
                o.setQuantity(rs2.getInt("Quantity"));
                ordersDetailsList.add(o);
            }

            orderDetailProduct.setCellValueFactory(new PropertyValueFactory<>("Product_name"));
            orderDetailPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
            orderDetailQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

            orderDetailTableView.setItems(ordersDetailsList);

            if (status.equals("Anulowane") || status.equals("Zakończone")){
                orderCancel.setVisible(false);
            }

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
