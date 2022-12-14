package Controller.Admin;

import Config.DbConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Edit product employee.
 */
public class EditProductEmployee {
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
     * The Category.
     */
    String Category;
    /**
     * The Image.
     */
    Blob image;

    /**
     * Method for editing products.
     *
     * @param actionEvent the action event
     */
    public void edycja(ActionEvent actionEvent) {
        if (!addName.getText().trim().isEmpty() &&
                !addPrice.getText().trim().isEmpty() &&
                !addDescription.getText().trim().isEmpty() &&
                !addQuantity.getText().trim().isEmpty()) {
            try {

                int i = 0;
                PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM products WHERE Name = ?");
                st.setString(1, addName.getText());
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    i++;
                }
                if (i == 0) {
                    PreparedStatement st2 = dbConnect.getConnection().prepareStatement("INSERT INTO products(ID_product, Name, Price, Quantity, Description, Category, Image, Archive_status) " +
                            "VALUES (null, ?, ?, ?, ?, ?, ?, 0)");
                    st2.setString(1, addName.getText());
                    st2.setDouble(2, Double.parseDouble(addPrice.getText()));
                    st2.setInt(3, Integer.parseInt(addQuantity.getText()));
                    st2.setString(4, addDescription.getText());
                    st2.setString(5, Category);
                    st2.setBlob(6, image);
                    st2.executeUpdate();
                    PreparedStatement st3 = dbConnect.getConnection().prepareStatement("DELETE FROM assortment WHERE ID_product = ?");
                    st3.setInt(1, id);
                    st3.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Dodano nowy przedmiot");
                    alert.setHeaderText("Nowy przedmiot został dodany do asortymentu");
                    alert.showAndWait();
                    bool = true;
                } else {
                    PreparedStatement st2 = dbConnect.getConnection().prepareStatement("UPDATE products SET Quantity = Quantity + ? WHERE Name = ?");
                    st2.setInt(1, Integer.parseInt(addQuantity.getText()));
                    st2.setString(2, addName.getText());
                    st2.executeUpdate();
                    PreparedStatement st3 = dbConnect.getConnection().prepareStatement("DELETE FROM assortment WHERE ID_product = ?");
                    st3.setInt(1, id);
                    st3.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Dodano ilość do asortymentu");
                    alert.setHeaderText("Dodano ilość do asortymentu");
                    alert.showAndWait();
                    bool = true;
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * Metoda.
     *
     * @param ID_product the id product
     */
    public void metoda(int ID_product) {
        try {
            id = ID_product;
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM assortment WHERE ID_product = ?");
            st.setInt(1, ID_product);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                addName.setText(rs.getString("Name"));
                addPrice.setText(rs.getString("Price"));
                addDescription.setText(rs.getString("Description"));
                addQuantity.setText(rs.getString("Quantity"));

                Category = rs.getString("Category");
                image = rs.getBlob("Image");

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
