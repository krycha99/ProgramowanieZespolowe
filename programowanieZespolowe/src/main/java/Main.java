import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The type Main.
 */
public class Main extends Application {

    /**
     *
     * @param args
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        try {
            String mysqlUrl = "jdbc:mysql://localhost/shop";
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            DriverManager.getConnection(mysqlUrl, "root", "");
        } catch (SQLException sqlException) {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String mysql = "jdbc:mysql://localhost/";
            Connection con = DriverManager.getConnection(mysql, "root", "");
            ScriptRunner sr = new ScriptRunner(con);
            Reader reader = new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream("Sql/shop.sql"), StandardCharsets.UTF_8);
            sr.runScript(reader);
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/FXML/main.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        Scene scene = new Scene(anchorPane);
        primaryStage.getIcons().add(new Image("/img/icon/logo.png"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sklep");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
