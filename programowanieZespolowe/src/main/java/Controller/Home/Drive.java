package Controller.Home;

import Config.DbConnect;
import Config.Pojos.ProductsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The type Drive.
 */
public class Drive implements Initializable {

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
    private TableColumn driveAddCart;

    private ObservableList<ProductsEntity> driveList;
    private Connection connection;
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
     * Method for display drives from DB in a table.
     */
    private void driveTableView() {

        try {
            driveList = FXCollections.observableArrayList();
            String query = "SELECT * FROM products WHERE Category='Dysk' AND Archive_status = 0";
            connection = dbConnect.getConnection();
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
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
                final TableCell<ProductsEntity, Void> cell = new TableCell<ProductsEntity, Void>() {

                    private final Button btn = new Button();

                    {
                        btn.setId("addCart");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                Stage stage = new Stage();
                                AnchorPane ap = FXMLLoader.load(getClass().getResource("/FXML/Login/login.fxml"));
                                Scene scene = new Scene(ap);
                                stage.setScene(scene);
                                stage.setTitle("Logowanie");
                                stage.show();
                            } catch (Exception ex) {
                                System.out.println(ex);
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

        driveAddCart.setCellFactory(cellFactory);
    }
}
