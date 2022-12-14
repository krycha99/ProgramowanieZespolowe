package Controller.User;

import Config.DbConnect;
import Config.PasswordHash;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The type Settings data.
 */
public class SettingsData implements Initializable {
    @FXML
    private TextField SettingsName;
    @FXML
    private TextField SettingsSureName;
    @FXML
    private TextField SettingsAddress;
    @FXML
    private TextField SettingsPhoneNumber;
    @FXML
    private TextField SettingsLogin;
    @FXML
    private PasswordField SettingsPassword;
    @FXML
    private PasswordField SettingsNewPassword;
    @FXML
    private TextField SettingsEmail;

    private Connection connection;
    /**
     * The Db connect.
     */
    DbConnect dbConnect;
    /**
     * The Id.
     */
    int id = UserMainController.idUser;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbConnect = new DbConnect();
        refresh();

    }

    /**
     * Refresh.
     */
    public void refresh() {
        try {
            String query = "SELECT * FROM users WHERE ID_user=" + id;
            connection = dbConnect.getConnection();
            ResultSet rs = connection.createStatement().executeQuery(query);
            while (rs.next()) {
                SettingsName.setText(rs.getString("Name"));
                SettingsSureName.setText(rs.getString("Surname"));
                SettingsAddress.setText(rs.getString("Address"));
                SettingsPhoneNumber.setText(rs.getString("Phone_number"));
                SettingsEmail.setText(rs.getString("Email"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method for editing data.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void SettingsEdit(ActionEvent actionEvent) throws IOException {
        if (!SettingsName.getText().trim().isEmpty() &&
                !SettingsSureName.getText().trim().isEmpty() &&
                !SettingsEmail.getText().trim().isEmpty() &&
                !SettingsPhoneNumber.getText().trim().isEmpty() &&
                !SettingsAddress.getText().trim().isEmpty()
        ) {
            boolean valid = EmailValidator.getInstance().isValid(SettingsEmail.getText());
            if (valid == true) {
                try {
                    PreparedStatement st = dbConnect.getConnection().prepareStatement("UPDATE users SET Name = ?, Surname = ?, Address = ?, Phone_number = ?, Email = ? WHERE ID_user = ?");
                    st.setString(1, SettingsName.getText());
                    st.setString(2, SettingsSureName.getText());
                    st.setString(3, SettingsAddress.getText());
                    st.setString(4, SettingsPhoneNumber.getText());
                    st.setString(5, SettingsEmail.getText());
                    st.setInt(6, id);
                    int rs = st.executeUpdate();

                    if (rs > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Udana zmiana danych!");
                        alert.setHeaderText("Twoje dane zostały zmienione!");
                        alert.showAndWait();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/User/MainUser.fxml"));
                        UserMainController user = loader.getController();
                        user.refresh();
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
     * Password change.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void PasswordChange(ActionEvent actionEvent) throws IOException {
        PasswordHash passwordHash = new PasswordHash();
        String login1 = null, login2, password1 = null, password2, newPassword;

        try {
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM users WHERE ID_user = " + id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                login1 = rs.getString("Login");
                password1 = rs.getString("Password");
            }

            login2 = SettingsLogin.getText();
            password2 = passwordHash.passwordHash2(SettingsPassword.getText());
            newPassword = passwordHash.passwordHash2(SettingsNewPassword.getText());

            if (password1.equals(password2) && login1.equals(login2)) {
                PreparedStatement st2 = dbConnect.getConnection().prepareStatement("UPDATE users SET Password = ? WHERE ID_user = ?");
                st2.setString(1, newPassword);
                st2.setInt(2, id);
                int i = st2.executeUpdate();

                if (i > 0) {
                    SettingsLogin.clear();
                    SettingsPassword.clear();
                    SettingsNewPassword.clear();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Udana zmiana hasła!");
                    alert.setHeaderText("Twoje hasło zostało zmienione!");
                    alert.showAndWait();

                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nie udana zmiana hasła!");
                alert.setHeaderText("Podano nieprawidłowe stare hasło lub login!");
                alert.showAndWait();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
