package Controller.Admin;

import Config.DbConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Edit employee.
 */
public class EditEmployee {

    @FXML
    private TextField editEmployeeName;
    @FXML
    private TextField editEmployeeSurname;
    @FXML
    private TextField editEmployeeLogin;
    @FXML
    private TextField editEmployeeEmail;
    @FXML
    private TextField editEmployeeNumber;
    @FXML
    private TextField editEmployeeSalary;
    @FXML
    private Button editEmployeeButton;

    /**
     * The constant bool.
     */
    public static boolean bool;
    private Connection connection;
    /**
     * The Db connect.
     */
    DbConnect dbConnect = new DbConnect();
    /**
     * The Id.
     */
    int id;

    /**
     * Method for editing employees data.
     *
     * @param actionEvent the action event
     */
    public void editEmployee(ActionEvent actionEvent) {
        if (!editEmployeeName.getText().trim().isEmpty() &&
                !editEmployeeSurname.getText().trim().isEmpty() &&
                !editEmployeeLogin.getText().trim().isEmpty() &&
                !editEmployeeEmail.getText().trim().isEmpty() &&
                !editEmployeeNumber.getText().trim().isEmpty() &&
                !editEmployeeSalary.getText().trim().isEmpty()) {
            boolean valid = EmailValidator.getInstance().isValid(editEmployeeEmail.getText());
            if (valid == true) {
                try {
                    PreparedStatement st = dbConnect.getConnection().prepareStatement("UPDATE users SET Name = ?, Surname = ?, Login = ?, Email = ?, Phone_number = ?, Salary = ? WHERE ID_user = ?");
                    st.setString(1, editEmployeeName.getText());
                    st.setString(2, editEmployeeSurname.getText());
                    st.setString(3, editEmployeeLogin.getText());
                    st.setString(4, editEmployeeEmail.getText());
                    st.setInt(5, Integer.parseInt(editEmployeeNumber.getText()));
                    st.setInt(6, Integer.parseInt(editEmployeeSalary.getText()));
                    st.setInt(7, id);
                    int rs = st.executeUpdate();

                    if (rs > 0) {
                        Stage stage = (Stage) editEmployeeButton.getScene().getWindow();
                        stage.close();

                        bool = true;

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Udana zmiana danych!");
                        alert.setHeaderText("Twoje dane zostały zmienione!");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Nie udana zmiana danych!");
                        alert.setHeaderText("Coś poszło nie tak, spróbuj ponownie!");
                        alert.showAndWait();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd danych");
                alert.setHeaderText("Podano błędny email!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd!");
            alert.setHeaderText("Pola nie mogą być puste!");
            alert.showAndWait();
        }
    }

    /**
     * Method used to pass data to the next window.
     *
     * @param id_user the id user
     */
    public void metoda(int id_user) {
        try {
            id = id_user;
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM users WHERE ID_user = ?");
            st.setInt(1, id_user);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                editEmployeeName.setText(rs.getString("Name"));
                editEmployeeSurname.setText(rs.getString("Surname"));
                editEmployeeLogin.setText(rs.getString("Login"));
                editEmployeeEmail.setText(rs.getString("Email"));
                editEmployeeNumber.setText(String.valueOf(rs.getInt("Phone_number")));
                editEmployeeSalary.setText(String.valueOf(rs.getInt("Salary")));
            }
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
