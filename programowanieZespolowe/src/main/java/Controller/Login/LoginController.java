package Controller.Login;

import Config.DbConnect;
import Config.PasswordHash;
import Controller.Admin.AdminMainController;
import Controller.Employee.EmployeeMainController;
import Controller.Home.HomeMainController;
import Controller.User.UserMainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * The type Login controller.
 */
public class LoginController {

    @FXML
    private TextField loginFXML;
    @FXML
    private PasswordField passwordFXML;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    /**
     * The Id user.
     */
    public int idUser;

    /**
     * The Db connect.
     */
    DbConnect dbConnect;

    /**
     * The method to handle the user's login.
     *
     * @throws IOException the io exception
     */
    public void login() throws IOException {
        dbConnect = new DbConnect();
        try {
            PasswordHash passwordHash = new PasswordHash();

            String login = loginFXML.getText();
            String password = passwordFXML.getText();
            String role = null;
            password = passwordHash.passwordHash2(password);
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM users WHERE Login='" + login + "' AND Password='" + password + "'");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                role = rs.getString("Role");
                idUser = rs.getInt("ID_user");
            }

            if (role == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd danych");
                alert.setHeaderText("Login lub hasło jest nieprawidłowe");
                alert.showAndWait();
            } else if (role.equals("Klient")) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/User/MainUser.fxml"));
                AnchorPane ap = loader.load();

                UserMainController userMainController = loader.getController();
                userMainController.customerLabel(idUser);

                HomeMainController.anchorPane.getChildren().setAll(ap);

            } else if (role.equals("Pracownik")) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Employee/MainEmployee.fxml"));
                AnchorPane ap = loader.load();

                EmployeeMainController employeeMainController = loader.getController();
                employeeMainController.employeeLabel(idUser);

                HomeMainController.anchorPane.getChildren().setAll(ap);

            } else if (role.equals("Administrator")) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/mainAdmin.fxml"));
                AnchorPane ap = loader.load();

                AdminMainController adminMainController = loader.getController();
                adminMainController.adminLabel(idUser);

                HomeMainController.anchorPane.getChildren().setAll(ap);
            }
        } catch (SQLException sqlException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Login lub hasło jest nieprawidłowe");
            alert.showAndWait();
        }
    }

    /**
     * Register.
     *
     * @throws IOException the io exception
     */
    public void register() throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();

        Stage stage2 = new Stage();
        AnchorPane ap = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Login/register.fxml")));
        Scene scene = new Scene(ap);
        stage2.setScene(scene);
        stage2.setTitle("Rejestracja");
        stage2.show();
    }
}
