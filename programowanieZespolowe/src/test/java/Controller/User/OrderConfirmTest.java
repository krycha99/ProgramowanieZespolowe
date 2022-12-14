package Controller.User;

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

class OrderConfirmTest {
    private Connection connection;
    DbConnect dbConnect = new DbConnect();

    @BeforeEach
    public void openSession() {
        DbConnect dbConnect = new DbConnect();
        connection = dbConnect.getConnection();
        System.out.println("=============");
    }

    @Test
    void selectPayment() throws SQLException  {
        System.out.println("Test pobierania opcji płatności.");
        ObservableList<Object> List = FXCollections.observableArrayList();
        PaymentsEntity p = new PaymentsEntity();
        PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM payments");
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            List.add(p);
        }
        Assertions.assertEquals(3,List.size());
    }

}