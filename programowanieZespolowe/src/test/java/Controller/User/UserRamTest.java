package Controller.User;

import Config.DbConnect;
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

class UserRamTest {
    private Connection connection;
    DbConnect dbConnect = new DbConnect();

    @BeforeEach
    public void openSession() {
        DbConnect dbConnect = new DbConnect();
        connection = dbConnect.getConnection();
        System.out.println("=============");
    }

    @Test
    void readRamTest() throws SQLException{
        System.out.println("Test pobierania listy ramów.");
        ProductsEntity p = new ProductsEntity();
        ObservableList<Object> ramList = FXCollections.observableArrayList();
        String query = "SELECT * FROM products WHERE Category='Ram'";
        ResultSet rs = connection.createStatement().executeQuery(query);
        while (rs.next()) {
            ramList.add(p);
        }
        Assertions.assertNotNull(ramList);
    }

    @Test
    void readProductByID() throws SQLException{
        System.out.println("Test pobierania danych przedmiotu po pobraniu poprawnego ID.");
        int data = 13;
        ObservableList<Object> List = FXCollections.observableArrayList();
        ProductsEntity p = new ProductsEntity();
        PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM products WHERE ID_product = ?");
        st.setInt(1, data);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            List.add(p);
        }
        System.out.println(List);
        Assertions.assertEquals(1,List.size());
    }

    @Test
    void readProductByWrongID() throws SQLException{
        System.out.println("Test pobierania danych przedmiotu po pobraniu niepoprawnego ID.");
        int data = -1001;
        ObservableList<Object> List = FXCollections.observableArrayList();
        ProductsEntity p = new ProductsEntity();
        PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM products WHERE ID_product = ?");
        st.setInt(1, data);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            List.add(p);
        }
        System.out.println(List);
        Assertions.assertEquals(1,List.size());
    }

}