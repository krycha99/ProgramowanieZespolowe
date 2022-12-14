package Controller.User;

import Config.DbConnect;
import Config.Pojos.CartEntity;
import Config.Pojos.ProductsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CartControllerTest {
    private Connection connection;
    DbConnect dbConnect = new DbConnect();

    @BeforeEach
    public void openSession() {
        DbConnect dbConnect = new DbConnect();
        connection = dbConnect.getConnection();
        System.out.println("=============");
    }

    @Test
    void cartProduct() throws SQLException {
        System.out.println("Test pobierania danych koszyka.");
        ObservableList<Object> cartProductList = FXCollections.observableArrayList();
        PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM cart INNER JOIN products ON cart.ID_product=products.ID_product WHERE ID_user=?");
        st.setInt(1, UserMainController.idUser);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            CartEntity c = new CartEntity();
            cartProductList.add(c);
        }
        Assertions.assertNotNull(cartProductList);
    }

    @Test
    void readProductFromCart() throws SQLException {
        System.out.println("Test pobierania danych z koszyka po pobraniu poprawnego ID użytkownika i produktu.");
        int idUser = 4;
        int idProduct = 6;
        ObservableList<Object> List = FXCollections.observableArrayList();
        ProductsEntity p = new ProductsEntity();
        PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM cart WHERE ID_product = ? AND ID_user = ?");
        st.setInt(1, idProduct);
        st.setInt(2, idUser);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            List.add(p);
        }
        System.out.println(List);
        Assertions.assertEquals(1,List.size());
    }

    @Test
    void readProductFromCartByWrongID() throws SQLException {
        System.out.println("Test pobierania danych z koszyka po pobraniu niepoprawnego ID użytkownika i produktu.");
        int idUser = -10;
        int idProduct = -10;
        ObservableList<Object> List = FXCollections.observableArrayList();
        ProductsEntity p = new ProductsEntity();
        PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM cart WHERE ID_product = ? AND ID_user = ?");
        st.setInt(1, idProduct);
        st.setInt(2, idUser);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            List.add(p);
        }
        System.out.println(List);
        Assertions.assertEquals(1,List.size());
    }
}