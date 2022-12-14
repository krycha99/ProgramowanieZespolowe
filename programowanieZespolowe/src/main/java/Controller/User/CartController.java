package Controller.User;

import Config.DbConnect;
import Config.Pojos.CartEntity;
import Controller.Home.HomeMainController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The type Cart controller.
 */
public class CartController implements Initializable {
    @FXML
    private Label userNameText;
    @FXML
    private TableView<CartEntity> cartProductTable;
    @FXML
    private TableColumn<CartEntity, Blob> cartProductImg;
    @FXML
    private TableColumn<CartEntity, String> cartProductName;
    @FXML
    private TableColumn<CartEntity, Integer> cartProductVolume;
    @FXML
    private TableColumn<CartEntity, Double> cartProductPrice;
    @FXML
    private TableColumn<CartEntity, String> cartProductInfo;
    @FXML
    private Label cartSuma;
    @FXML
    private TableColumn<CartEntity, Void> cartDelProduct;

    /**
     * The Cart product list.
     */
    public static ObservableList<CartEntity> cartProductList;
    /**
     * The Db connect.
     */
    DbConnect dbConnect = new DbConnect();
    /**
     * The Time.
     */
    Timeline time;

    /**
     * The Input stream.
     */
    static InputStream inputStream;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cartProduct();
        label();
    }

    /**
     * The method used to display the products in the shopping cart.
     */
    private void cartProduct() {
        try {
            double cena = 0;
            cartProductList = FXCollections.observableArrayList();
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM cart INNER JOIN products ON cart.ID_product=products.ID_product WHERE ID_user=?");
            st.setInt(1, UserMainController.idUser);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                CartEntity c = new CartEntity();
                c.setID_cart(rs.getInt("ID_cart"));
                c.setID_product(rs.getInt("ID_product"));
                c.setProduct_image(rs.getBlob("Image"));
                c.setProduct_name(rs.getString("Name"));
                c.setQuantity(rs.getInt("Quantity"));
                c.setProduct_price(rs.getDouble("Price"));
                c.setProduct_description(rs.getString("Description"));
                cena += rs.getDouble("Price") * rs.getInt("Quantity");
                cartProductList.add(c);
            }
            cartProductImg.setCellValueFactory(new PropertyValueFactory<>("Product_image"));
            cartProductName.setCellValueFactory(new PropertyValueFactory<>("Product_name"));
            cartProductVolume.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
            cartProductPrice.setCellValueFactory(new PropertyValueFactory<>("Product_price"));
            cartProductInfo.setCellValueFactory(new PropertyValueFactory<>("Product_description"));
            cartSuma.setText("Kwota łącznie: " + cena + " zł");

            cartProductTable.setItems(cartProductList);


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        addButtonToTable();

        cartProductImg.setCellFactory(e -> {
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(cartProductImg.getMinWidth());
            imageView.setFitWidth(cartProductImg.getMinWidth());

            TableCell<CartEntity, Blob> graphicCell = new TableCell<CartEntity, Blob>() {
                public void updateItem(Blob image, boolean empty) {
                    try {
                        if (image != null) {
                            inputStream = image.getBinaryStream();
                            imageView.setImage(new Image(inputStream));
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            };
            graphicCell.setGraphic(imageView);
            return graphicCell;
        });
    }
    /**
     * Method for adding functional buttons to the table.
     */
    private void addButtonToTable() {

        Callback<TableColumn<CartEntity, Void>, TableCell<CartEntity, Void>> cellFactory = new Callback<TableColumn<CartEntity, Void>, TableCell<CartEntity, Void>>() {
            @Override
            public TableCell<CartEntity, Void> call(final TableColumn<CartEntity, Void> param) {
                final TableCell<CartEntity, Void> cell = new TableCell<CartEntity, Void>() {

                    private final Button btn = new Button();

                    {
                        btn.setId("delCart");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                CartEntity data = getTableView().getItems().get(getIndex());

                                PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM cart WHERE ID_product = ? AND ID_user = ?");
                                st.setInt(1, data.getID_product());
                                st.setInt(2, UserMainController.idUser);
                                ResultSet rs = st.executeQuery();
                                int quantity = 0;
                                while (rs.next()) {
                                    quantity = rs.getInt("Quantity");
                                }

                                if (quantity > 1) {
                                    PreparedStatement st2 = dbConnect.getConnection().prepareStatement("UPDATE cart SET Quantity = Quantity - 1 WHERE ID_product = ? AND ID_user = ?");
                                    st2.setInt(1, data.getID_product());
                                    st2.setInt(2, UserMainController.idUser);
                                    st2.executeUpdate();

                                    PreparedStatement st3 = dbConnect.getConnection().prepareStatement("UPDATE products SET Quantity = Quantity + 1 WHERE ID_product = ?");
                                    st3.setInt(1, data.getID_product());
                                    st3.executeUpdate();
                                } else {
                                    PreparedStatement st2 = dbConnect.getConnection().prepareStatement("DELETE FROM cart WHERE ID_cart = ?");
                                    st2.setInt(1, data.getID_cart());
                                    st2.executeUpdate();
                                    PreparedStatement st3 = dbConnect.getConnection().prepareStatement("UPDATE products SET Quantity = Quantity + 1 WHERE ID_product = ?");
                                    st3.setInt(1, data.getID_product());
                                    st3.executeUpdate();
                                }

                                refresh();
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

                return cell;

            }
        };

        cartDelProduct.setCellFactory(cellFactory);
    }

    /**
     * Refresh.
     */
    public void refresh() {
        cartProductList.clear();
        cartProduct();
    }

    /**
     * Logout.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void logout(ActionEvent actionEvent) throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("/FXML/Home/mainHome.fxml"));
        HomeMainController.anchorPane.getChildren().setAll(ap);
    }

    /**
     * Back.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void back(ActionEvent actionEvent) throws IOException {
        AnchorPane ap = FXMLLoader.load(getClass().getResource("/FXML/User/MainUser.fxml"));
        HomeMainController.anchorPane.getChildren().setAll(ap);
    }

    /**
     * Cart accept.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void cartAccept(ActionEvent actionEvent) throws IOException {
        if (cartProductList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Twój koszyk jest pusty");
            alert.showAndWait();
        } else {
            time = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (OrderConfirm.refBool()) {
                        refresh();
                        time.stop();
                        OrderConfirm.bool = false;
                    }
                }
            }));
            time.setCycleCount(Timeline.INDEFINITE);
            time.play();

            Stage stage = new Stage();
            AnchorPane ap = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/User/OrderConfirm.fxml")));
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Potwierdzenie zamówienia");
            stage.setResizable(false);
            stage.show();
        }
    }

    private void label() {
        userNameText.setText(String.valueOf(UserMainController.userNameText2.getText()));
    }
}
