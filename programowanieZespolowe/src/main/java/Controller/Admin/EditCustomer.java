package Controller.Admin;

import Config.DbConnect;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Edit customer.
 */
public class EditCustomer {

    @FXML
    private TextField editCustomerName;
    @FXML
    private TextField editCustomerSurname;
    @FXML
    private TextField editCustomerLogin;
    @FXML
    private TextField editCustomerEmail;
    @FXML
    private TextField editCustomerNumber;
    @FXML
    private Button editCustomerButton;

    /**
     * The constant bool.
     */
    public static boolean bool;
    /**
     * The Db connect.
     */
    DbConnect dbConnect = new DbConnect();
    /**
     * The Id.
     */
    int id;

    /**
     * Method for editing customers.
     */
    public void editCustomer() {
        if (!editCustomerName.getText().trim().isEmpty() &&
                !editCustomerSurname.getText().trim().isEmpty() &&
                !editCustomerLogin.getText().trim().isEmpty() &&
                !editCustomerEmail.getText().trim().isEmpty() &&
                !editCustomerNumber.getText().trim().isEmpty()) {
            boolean valid = EmailValidator.getInstance().isValid(editCustomerEmail.getText());
            if (valid == true) {
                try {
                    PreparedStatement st = dbConnect.getConnection().prepareStatement("UPDATE users SET Name = ?, Surname = ?, Login = ?, Email = ?, Phone_number = ? WHERE ID_user = ?");
                    st.setString(1, editCustomerName.getText());
                    st.setString(2, editCustomerSurname.getText());
                    st.setString(3, editCustomerLogin.getText());
                    st.setString(4, editCustomerEmail.getText());
                    st.setInt(5, Integer.parseInt(editCustomerNumber.getText()));
                    st.setInt(6, id);
                    int rs = st.executeUpdate();

                    if (rs > 0) {
                        Stage stage = (Stage) editCustomerButton.getScene().getWindow();
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
                editCustomerName.setText(rs.getString("Name"));
                editCustomerSurname.setText(rs.getString("Surname"));
                editCustomerLogin.setText(rs.getString("Login"));
                editCustomerEmail.setText(rs.getString("Email"));
                editCustomerNumber.setText(String.valueOf(rs.getInt("Phone_number")));
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
