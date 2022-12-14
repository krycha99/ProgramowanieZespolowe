package Controller.Admin;

import Config.DbConnect;
import Config.PasswordHash;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Add customer class.
 */
public class AddCustomer {
    @FXML
    private TextField addCustomerName;
    @FXML
    private TextField addCustomerSurname;
    @FXML
    private TextField addCustomerLogin;
    @FXML
    private PasswordField addCustomerPassword;
    @FXML
    private TextField addCustomerEmail;
    @FXML
    private TextField addCustomerNumber;
    @FXML
    private Button addCustomerButton;

    /**
     * The constant bool.
     */
    public static boolean bool;
    /**
     * The Db connect.
     */
    DbConnect dbConnect = new DbConnect();


    /**
     * Method for adding a new client to the database.
     */
    public void addCustomer() {
        try {
            if (!addCustomerName.getText().trim().isEmpty() &&
                    !addCustomerSurname.getText().trim().isEmpty() &&
                    !addCustomerLogin.getText().trim().isEmpty() &&
                    !addCustomerPassword.getText().trim().isEmpty() &&
                    !addCustomerEmail.getText().trim().isEmpty() &&
                    !addCustomerNumber.getText().trim().isEmpty()
            ) {
                boolean valid = EmailValidator.getInstance().isValid(addCustomerEmail.getText());
                if (valid == true) {
                    PasswordHash passwordHash = new PasswordHash();

                    PreparedStatement st = dbConnect.getConnection().prepareStatement("INSERT INTO `users` (`ID_user`, `Login`, `Password`, `Name`, `Surname`, `Email`, `Phone_number`, `Address`, `Salary`, `Role`, `Archive_status`) " +
                            "VALUES (NULL, ?, ?, ?, ?, ?, ?, NULL, NULL, 'Klient', 0);");
                    st.setString(1, addCustomerLogin.getText());
                    st.setString(2, passwordHash.passwordHash2(addCustomerPassword.getText()));
                    st.setString(3, addCustomerName.getText());
                    st.setString(4, addCustomerSurname.getText());
                    st.setString(5, addCustomerEmail.getText());
                    st.setInt(6, Integer.parseInt(addCustomerNumber.getText()));

                    st.executeUpdate();
                    bool = true;

                    Stage stage = (Stage) addCustomerButton.getScene().getWindow();
                    stage.close();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Udane dodanie klienta");
                    alert.setHeaderText("Udało się dodać klienta!");
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
        } catch (SQLIntegrityConstraintViolationException throwables) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd danych");
            alert.setHeaderText("Użytkownik o takim loginie już istnieje!");
            alert.showAndWait();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Ref bool boolean.
     *
     * @return the boolean
     */
    public static boolean refBool() {
        return bool;
    }
}
