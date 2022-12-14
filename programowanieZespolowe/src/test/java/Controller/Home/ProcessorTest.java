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

class ProcessorTest {
    private Connection connection;
    DbConnect dbConnect = new DbConnect();

    @BeforeEach
    public void openSession() {
        DbConnect dbConnect = new DbConnect();
        connection = dbConnect.getConnection();
        System.out.println("=============");
    }

    @Test
    void processorTableView() throws SQLException {
        System.out.println("Test sprawdzający wyświetlanie listy procesorów");
        ObservableList<Object> processorList = FXCollections.observableArrayList();
        String query = "SELECT * FROM products WHERE Category='Procesor'";
        ResultSet rs = connection.createStatement().executeQuery(query);
        while (rs.next()) {
            ProductsEntity p = new ProductsEntity();
            processorList.add(p);
        }
        Assertions.assertNotNull(processorList);
    }
}