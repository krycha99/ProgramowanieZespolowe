package Controller.Employee;

import Config.DbConnect;
import Config.Pojos.ProductsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeProcessorTest {
    private Connection connection;
    DbConnect dbConnect = new DbConnect();

    @BeforeEach
    public void openSession() {
        DbConnect dbConnect = new DbConnect();
        connection = dbConnect.getConnection();
        System.out.println("==========");
    }
    @Test
    void readProcessorTest() throws SQLException {
        System.out.println("Test pobierania listy procesorów.");
        ProductsEntity p = new ProductsEntity();
        ObservableList<Object> processorList = FXCollections.observableArrayList();
        String query = "SELECT * FROM products WHERE Category='Procesor'";
        ResultSet rs = connection.createStatement().executeQuery(query);
        while (rs.next()) {
            processorList.add(p);
        }
        Assertions.assertNotNull(processorList);
    }

    @Test
    void readProductByID() throws SQLException{
        System.out.println("Test pobierania danych przedmiotu po pobraniu poprawnego ID.");
        int data = 5;
        ObservableList<Object> processorList = FXCollections.observableArrayList();
        ProductsEntity p = new ProductsEntity();
        PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM products WHERE ID_product = ?");
        st.setInt(1, data);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            processorList.add(p);
        }
        System.out.println(processorList);
        Assertions.assertEquals(1,processorList.size());
    }

    @Test
    void readProductByWrongID() throws SQLException{
        System.out.println("Test pobierania danych przedmiotu po pobraniu niepoprawnego ID.");
        int data = 3434;
        ObservableList<Object> processorList = FXCollections.observableArrayList();
        ProductsEntity p = new ProductsEntity();
        PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM products WHERE ID_product = ?");
        st.setInt(1, data);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            processorList.add(p);
        }
        System.out.println(processorList);
        Assertions.assertEquals(1,processorList.size());
    }
}