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

class DriveTest {
    private Connection connection;
    DbConnect dbConnect = new DbConnect();

    @BeforeEach
    public void openSession() {
        DbConnect dbConnect = new DbConnect();
        connection = dbConnect.getConnection();
        System.out.println("=============");
    }

    @Test
    void driveTableView() throws SQLException {
        System.out.println("Test sprawdzający wyświetlanie listy dysków");
        ObservableList<Object> driveList = FXCollections.observableArrayList();
        String query = "SELECT * FROM products WHERE Category='Dysk'";
        ResultSet rs = connection.createStatement().executeQuery(query);
        while (rs.next()) {
            ProductsEntity p = new ProductsEntity();
            driveList.add(p);
        }
        Assertions.assertNotNull(driveList);
    }

}