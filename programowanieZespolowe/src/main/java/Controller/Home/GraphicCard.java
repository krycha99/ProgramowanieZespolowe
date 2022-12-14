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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The type Graphic card.
 */
public class GraphicCard implements Initializable {

    @FXML
    private TableView<ProductsEntity> graphicTable;
    @FXML
    private TableColumn<ProductsEntity, String> graphicName;
    @FXML
    private TableColumn<ProductsEntity, Integer> graphicVolume;
    @FXML
    private TableColumn<ProductsEntity, String> graphicInfo;
    @FXML
    private TableColumn<ProductsEntity, String> graphicPrice;
    @FXML
    private TableColumn<ProductsEntity, Blob> graphicImg;
    @FXML
    private TableColumn<ProductsEntity, Void> graphicAddCart;

    private ObservableList<ProductsEntity> graphicList;
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
        graphicTableView();
    }
    /**
     * Method for display graphic cards from DB in a table.
     */
    private void graphicTableView() {

        try {
            graphicList = FXCollections.observableArrayList();
            String query = "SELECT * FROM products WHERE Category='Karta graficzna' AND Archive_status = 0";
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
                graphicList.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        graphicName.setCellValueFactory(new PropertyValueFactory<>("name"));
        graphicVolume.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        graphicInfo.setCellValueFactory(new PropertyValueFactory<>("description"));
        graphicPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        graphicImg.setCellValueFactory(new PropertyValueFactory<>("image"));

        graphicTable.setItems(graphicList);

        addButtonToTable();

        graphicImg.setCellFactory(e -> {
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(graphicImg.getMinWidth());
            imageView.setFitWidth(graphicImg.getMinWidth());

            TableCell<ProductsEntity, Blob> graphicCell = new TableCell<ProductsEntity, Blob>() {
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

        Callback<TableColumn<ProductsEntity, Void>, TableCell<ProductsEntity, Void>> cellFactory = new Callback<TableColumn<ProductsEntity, Void>, TableCell<ProductsEntity, Void>>() {
            @Override
            public TableCell<ProductsEntity, Void> call(final TableColumn<ProductsEntity, Void> param) {
                return new TableCell<ProductsEntity, Void>() {

                    private final Button btn = new Button();

                    {
                        btn.setId("addCart");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                Stage stage = new Stage();
                                AnchorPane ap = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Login/login.fxml")));
                                Scene scene = new Scene(ap);
                                stage.setScene(scene);
                                stage.setTitle("Logowanie");
                                stage.show();
                            } catch (IOException throwable) {
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

        graphicAddCart.setCellFactory(cellFactory);
    }
}
