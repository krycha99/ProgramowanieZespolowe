package Controller.User;

import Config.DbConnect;
import Config.Pojos.UsersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SettingsDataTest {
    private Connection connection;
    DbConnect dbConnect = new DbConnect();

    @BeforeEach
    public void openSession() {
        DbConnect dbConnect = new DbConnect();
        connection = dbConnect.getConnection();
        System.out.println("=============");
    }

    @Test
    void refresh() throws SQLException {
        System.out.println("Test metody odświeżającej wyświetlanie danych klienta.");
        ObservableList<Object> List = FXCollections.observableArrayList();
        UsersEntity p = new UsersEntity();
        int id = 1;
        String query = "SELECT * FROM users WHERE ID_user="+ id;
        connection = dbConnect.getConnection();
        ResultSet rs = connection.createStatement().executeQuery(query);
        while (rs.next()) {
            List.add(p);
        }
        Assertions.assertNotNull(List);
    }
}