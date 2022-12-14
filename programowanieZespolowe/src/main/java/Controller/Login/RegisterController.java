package Controller.Login;

import Config.DbConnect;
import Config.PasswordHash;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The type Register controller.
 */
public class RegisterController {

    @FXML
    private TextField registerName;
    @FXML
    private TextField registerSurname;
    @FXML
    private TextField registerLogin;
    @FXML
    private PasswordField registerPassword;
    @FXML
    private TextField registerEmail;
    @FXML
    private TextField registerNumber;
    @FXML
    private TextField registerAddress;
    @FXML
    private Button registerButton;

    /**
     * The Db connect.
     */
    DbConnect dbConnect;

    /**
     * The method to handle user registration.
     */
    public void register() {
        dbConnect = new DbConnect();
        try {
            if (!registerName.getText().trim().isEmpty() &&
                    !registerSurname.getText().trim().isEmpty() &&
                    !registerLogin.getText().trim().isEmpty() &&
                    !registerPassword.getText().trim().isEmpty() &&
                    !registerEmail.getText().trim().isEmpty() &&
                    !registerNumber.getText().trim().isEmpty() &&
                    !registerAddress.getText().trim().isEmpty()
            ) {

                boolean valid = EmailValidator.getInstance().isValid(registerEmail.getText());
                if (valid == true) {
                    PasswordHash passwordHash = new PasswordHash();

                    String name = registerName.getText();
                    String surname = registerSurname.getText();
                    String login = registerLogin.getText();
                    String password = registerPassword.getText();
                    password = passwordHash.passwordHash2(password);
                    String number = registerNumber.getText();
                    String email = registerEmail.getText();
                    String address = registerAddress.getText();

                    String query = "INSERT INTO `users` (`ID_user`, `Login`, `Password`, `Name`, `Surname`, `Email`, `Phone_number`, `Address`, `Salary`, `Role`, `Archive_status`) " +
                            "VALUES (NULL, '" + login + "', '" + password + "', '" + name + "', '" + surname + "', '" + email + "', '" + number + "', '" + address + "', NULL, 'Klient', 0);";
                    Connection connection = dbConnect.getConnection();
                    connection.createStatement().execute(query);

                    Stage stage = (Stage) registerButton.getScene().getWindow();
                    stage.close();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Udana rejestracja");
                    alert.setHeaderText("Udało się!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd danych");
                    alert.setHeaderText("Podano błędny email!");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd danych");
                alert.setHeaderText("Pola nie mogą być puste!");
                alert.showAndWait();
            }
        } catch (SQLException throwables) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Taki użytkownik już istnieje, zmień login!");
            alert.showAndWait();
        }
    }
}
