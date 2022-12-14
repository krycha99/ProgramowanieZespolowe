package Controller.Home;

import Config.DbConnect;
import Config.Pojos.ProductsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RamTest {
    private Connection connection;
    DbConnect dbConnect = new DbConnect();

    @BeforeEach
    public void openSession() {
        DbConnect dbConnect = new DbConnect();
        connection = dbConnect.getConnection();
        System.out.println("=============");
    }

    @Test
    void ramTableView() throws SQLException {
        System.out.println("Test sprawdzający wyświetlanie listy ramów");
        ObservableList<Object> ramList = FXCollections.observableArrayList();
        String query = "SELECT * FROM products WHERE Category='Ram'";
        ResultSet rs = connection.createStatement().executeQuery(query);
        while (rs.next()) {
            ProductsEntity p = new ProductsEntity();
            ramList.add(p);
        }
        Assertions.assertNotNull(ramList);
    }
}