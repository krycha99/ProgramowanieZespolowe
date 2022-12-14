package Controller.Admin;

import Config.DbConnect;
import Config.PasswordHash;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

/**
 *  Add Employee class.
 */
public class AddEmployee implements Initializable {

    @FXML
    private TextField addEmployeeName;
    @FXML
    private TextField addEmployeeSurname;
    @FXML
    private TextField addEmployeeLogin;
    @FXML
    private PasswordField addEmployeePassword;
    @FXML
    private TextField addEmployeeEmail;
    @FXML
    private TextField addEmployeeNumber;
    @FXML
    private TextField addEmployeeSalary;
    @FXML
    private ComboBox<String> addEmployeePosition;
    @FXML
    private TextField addEmployeeAddress;
    @FXML
    private Button addEmployeeButton;

    /**
     * The constant bool.
     */
    public static boolean bool;
    /**
     * The Db connect.
     */
    DbConnect dbConnect = new DbConnect();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addEmployeePosition.getItems().add("Administrator");
        addEmployeePosition.getItems().add("Pracownik");
    }

    /**
     * Method for adding a new employee to the database.
     */
    public void addEmployee() {
        try {
            if (!addEmployeeName.getText().trim().isEmpty() &&
                    !addEmployeeSurname.getText().trim().isEmpty() &&
                    !addEmployeeLogin.getText().trim().isEmpty() &&
                    !addEmployeePassword.getText().trim().isEmpty() &&
                    !addEmployeeEmail.getText().trim().isEmpty() &&
                    !addEmployeeNumber.getText().trim().isEmpty() &&
                    !addEmployeeSalary.getText().trim().isEmpty() &&
                    !addEmployeeAddress.getText().trim().isEmpty() &&
                    !addEmployeePosition.getSelectionModel().isEmpty()
            ) {
                boolean valid = EmailValidator.getInstance().isValid(addEmployeeEmail.getText());
                if (valid == true) {
                    PasswordHash passwordHash = new PasswordHash();

                    PreparedStatement st = dbConnect.getConnection().prepareStatement("INSERT INTO `users` (`ID_user`, `Login`, `Password`, `Name`, `Surname`, `Email`, `Phone_number`, `Address`, `Salary`, `Role`, `Archive_status`) " +
                            "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0);");
                    st.setString(1, addEmployeeLogin.getText());
                    st.setString(2, passwordHash.passwordHash2(addEmployeePassword.getText()));
                    st.setString(3, addEmployeeName.getText());
                    st.setString(4, addEmployeeSurname.getText());
                    st.setString(5, addEmployeeEmail.getText());
                    st.setInt(6, Integer.parseInt(addEmployeeNumber.getText()));
                    st.setString(7, addEmployeeAddress.getText());
                    st.setDouble(8, Double.parseDouble(addEmployeeSalary.getText()));
                    st.setString(9, addEmployeePosition.getSelectionModel().getSelectedItem());

                    st.executeUpdate();
                    bool = true;

                    Stage stage = (Stage) addEmployeeButton.getScene().getWindow();
                    stage.close();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Udane dodanie pracownika!");
                    alert.setHeaderText("Udało się dodać pracownika!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd danych");
                    alert.setHeaderText("Podano błędny email!");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd danych!");
                alert.setHeaderText("Pola nie mogą być puste");
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
