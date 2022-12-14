package Controller.User;

import Config.DbConnect;
import Config.Pojos.ProductsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * The type User drive.
 */
public class UserDrive implements Initializable {
    @FXML
    private TableView<ProductsEntity> driveTable;
    @FXML
    private TableColumn<ProductsEntity, String> driveName;
    @FXML
    private TableColumn<ProductsEntity, Integer> driveVolume;
    @FXML
    private TableColumn<ProductsEntity, String> driveInfo;
    @FXML
    private TableColumn<ProductsEntity, Integer> drivePrice;
    @FXML
    private TableColumn<ProductsEntity, Blob> driveImg;
    @FXML
    private TableColumn<ProductsEntity, Void> driveAddCart;

    private ObservableList<ProductsEntity> driveList;
    /**
     * The Db connect.
     */
    DbConnect dbConnect;

    /**
     * The Input stream.
     */
    static InputStream inputStream;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbConnect = new DbConnect();
        driveTableView();
    }
    /**
     * Method to display hard drives from DB in table.
     */
    private void driveTableView() {

        try {
            driveList = FXCollections.observableArrayList();
            String query = "SELECT * FROM products WHERE Category='Dysk' AND Archive_status = 0";
            Connection connection = dbConnect.getConnection();
            ResultSet rs = connection.createStatement().executeQuery(query);
            while (rs.next()) {
                ProductsEntity p = new ProductsEntity();
                p.setID_product(rs.getInt("ID_product"));
                p.setName(rs.getString("Name"));
                p.setPrice(rs.getDouble("Price"));
                p.setQuantity(rs.getInt("Quantity"));
                p.setDescription(rs.getString("Description"));
                p.setCategory(rs.getString("Category"));
                p.setImage(rs.getBlob("Image"));
                driveList.add(p);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        driveName.setCellValueFactory(new PropertyValueFactory<>("name"));
        driveVolume.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        driveInfo.setCellValueFactory(new PropertyValueFactory<>("description"));
        drivePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        driveImg.setCellValueFactory(new PropertyValueFactory<>("image"));

        driveTable.setItems(driveList);

        addButtonToTable();


        driveImg.setCellFactory(e -> {
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(driveImg.getMinWidth());
            imageView.setFitWidth(driveImg.getMinWidth());

            TableCell<ProductsEntity, Blob> driveCell = new TableCell<ProductsEntity, Blob>() {
                public void updateItem(Blob image, boolean empty) {
                    try {
                        if (image != null) {
                            inputStream = image.getBinaryStream();
                            imageView.setImage(new Image(inputStream));
                        }
                    } catch (SQLException throwable) {
                        throwable.printStackTrace();
                    }
                }
            };
            driveCell.setGraphic(imageView);
            return driveCell;
        });
    }
    /**
     * Method for adding functional buttons to the table.
     */
    private void addButtonToTable() {

        Callback<TableColumn<ProductsEntity, Void>, TableCell<ProductsEntity, Void>> cellFactory = new Callback<TableColumn<ProductsEntity, Void>, TableCell<ProductsEntity, Void>>() {
            @Override
            public TableCell<ProductsEntity, Void> call(final TableColumn<ProductsEntity, Void> param) {
                return new TableCell<ProductsEntity, Void>() {

                    private final Button btn = new Button();

                    {
                        btn.setId("addCart");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                ProductsEntity data = getTableView().getItems().get(getIndex());

                                PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM products WHERE ID_product = ?");
                                st.setInt(1, data.getID_product());
                                ResultSet rs = st.executeQuery();
                                int quantity = 0;
                                while (rs.next()) {
                                    quantity = rs.getInt("Quantity");
                                }

                                if (quantity != 0) {

                                    PreparedStatement st1 = dbConnect.getConnection().prepareStatement("SELECT * FROM cart WHERE ID_product = ? AND ID_user = ?");
                                    st1.setInt(1, data.getID_product());
                                    st1.setInt(2, UserMainController.idUser);
                                    ResultSet rs1 = st1.executeQuery();
                                    int size = 0;
                                    while (rs1.next()) {
                                        size++;
                                    }

                                    if (size == 0) {
                                        PreparedStatement st2 = dbConnect.getConnection().prepareStatement("INSERT INTO `cart` (`ID_cart`, `ID_product`, `ID_user`, `Quantity`) " +
                                                "VALUES (NULL, ?, ?, ?);");
                                        st2.setInt(1, data.getID_product());
                                        st2.setInt(2, UserMainController.idUser);
                                        st2.setInt(3, 1);
                                        st2.executeUpdate();

                                        PreparedStatement st3 = dbConnect.getConnection().prepareStatement("UPDATE products SET Quantity = Quantity - 1 WHERE ID_product = ?");
                                        st3.setInt(1, data.getID_product());
                                        st3.executeUpdate();

                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Dodano do koszyka!");
                                        alert.setHeaderText("Przedmiot dodano do koszyka!");
                                        alert.showAndWait();
                                    } else {
                                        PreparedStatement st2 = dbConnect.getConnection().prepareStatement("UPDATE cart SET Quantity = Quantity + 1 WHERE ID_product = ? AND ID_user = ?");
                                        st2.setInt(1, data.getID_product());
                                        st2.setInt(2, UserMainController.idUser);
                                        st2.executeUpdate();

                                        PreparedStatement st3 = dbConnect.getConnection().prepareStatement("UPDATE products SET Quantity = Quantity - 1 WHERE ID_product = ?");
                                        st3.setInt(1, data.getID_product());
                                        st3.executeUpdate();

                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Dodano do koszyka!");
                                        alert.setHeaderText("Przedmiot dodano do koszyka!");
                                        alert.showAndWait();
                                    }

                                    refresh();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error!");
                                    alert.setHeaderText("Nie możesz dodać produktu, brak na stanie!");
                                    alert.showAndWait();
                                }
                            } catch (SQLException throwable) {
                                throwable.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        driveAddCart.setCellFactory(cellFactory);
    }

    private void refresh() {
        driveList.clear();
        driveTableView();
    }
}
