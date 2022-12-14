package Controller.Employee;

import Config.DbConnect;
import Config.Pojos.PaymentsEntity;
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

class EmployeeOrders2Test {
    private Connection connection;
    DbConnect dbConnect = new DbConnect();

    @BeforeEach
    public void openSession() {
        DbConnect dbConnect = new DbConnect();
        connection = dbConnect.getConnection();
        System.out.println("=============");
    }

    @Test
    void orders() throws SQLException {
        System.out.println("Test pobierania szczegółów zamówień.");
        int id = 1 ;
        ObservableList<Object> List = FXCollections.observableArrayList();
        PaymentsEntity p = new PaymentsEntity();
        PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM orders_details INNER JOIN orders ON orders_details.ID_order=orders.ID_order INNER JOIN users ON orders.ID_user=users.ID_user WHERE orders_details.ID_order = ? GROUP BY orders_details.ID_order");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            List.add(p);
        }
        Assertions.assertNotNull(List);
    }
}