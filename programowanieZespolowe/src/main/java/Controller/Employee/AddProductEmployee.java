package Controller.Employee;

import Config.DbConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The type Add product employee.
 */
public class AddProductEmployee {
    @FXML
    private TextField addName;
    @FXML
    private TextField addPrice;
    @FXML
    private TextArea addDescription;
    @FXML
    private TextField addQuantity;
    @FXML
    private Button addConfirm;

    /**
     * The Db connect.
     */
    DbConnect dbConnect = new DbConnect();
    private FileInputStream fileInputStream;
    /**
     * The Category.
     */
    String category;

    /**
     * Add image.
     *
     * @param actionEvent the action event
     */
    public void addImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(addConfirm.getScene().getWindow());
        try {
            fileInputStream = new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add confirm.
     *
     * @param actionEvent the action event
     */
    public void addConfirm(ActionEvent actionEvent) {
        try {
            PreparedStatement st = dbConnect.getConnection().prepareStatement("INSERT INTO assortment(ID_product, Name, Category, Description, Price, Quantity, Image) " +
                    "VALUES (null, ?, ?, ?, ?, ?, ?)");
            st.setString(1, addName.getText());
            st.setString(2, category);
            st.setString(3, addDescription.getText());
            st.setDouble(4, Double.parseDouble(addPrice.getText()));
            st.setInt(5, Integer.parseInt(addQuantity.getText()));
            st.setBinaryStream(6, fileInputStream, fileInputStream.available());
            st.executeUpdate();

            Stage stage = (Stage) addConfirm.getScene().getWindow();
            stage.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dodano zamówienie asortymentu!");
            alert.setHeaderText("Twoja prośba o dodanie asortymentu została wysłana do administratorów!");
            alert.showAndWait();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Category.
     *
     * @param category2 the category 2
     */
    public void category(String category2) {
        category = category2;
    }
}
