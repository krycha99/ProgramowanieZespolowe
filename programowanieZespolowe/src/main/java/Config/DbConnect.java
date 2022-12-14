package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Db connect.
 */
public class DbConnect {
    /**
     * The Connection.
     */
    protected Connection connection;

    /**
     * Gets connection.
     *
     * @return connection zwraca połącznie
     */
    public Connection getConnection() {
        final String connectionString = "jdbc:mysql://localhost:3306/shop";
        try {
            connection = DriverManager.getConnection(connectionString, "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

}