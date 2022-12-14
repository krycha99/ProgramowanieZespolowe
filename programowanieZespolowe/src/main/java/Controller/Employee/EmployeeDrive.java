package Controller.Employee;

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
import java.sql.*;
import java.util.ResourceBundle;

/**
 * The type Employee drive.
 */
public class EmployeeDrive implements Initializable {

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
    private TableColumn driveDel;

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
     * Viewing drivers.
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
     * Drivers buttons.
     */
    private void addButtonToTable() {
        Callback<TableColumn<ProductsEntity, Void>, TableCell<ProductsEntity, Void>> cellFactory2 = new Callback<TableColumn<ProductsEntity, Void>, TableCell<ProductsEntity, Void>>() {
            @Override
            public TableCell<ProductsEntity, Void> call(final TableColumn<ProductsEntity, Void> param) {
                final TableCell<ProductsEntity, Void> cell = new TableCell<ProductsEntity, Void>() {

                    private final Button btn = new Button();

                    {
                        btn.setId("processorDelButton");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                ProductsEntity data = getTableView().getItems().get(getIndex());
                                PreparedStatement st = dbConnect.getConnection().prepareStatement("UPDATE products SET Archive_status = 1 WHERE ID_product = ?");
                                st.setInt(1, data.getID_product());
                                st.executeUpdate();

                                refresh();
                            } catch (Exception ex) {

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

        driveDel.setCellFactory(cellFactory2);
    }

    /**
     * Add product.
     *
     * @throws IOException the io exception
     */
    public void addProduct() throws IOException {
        Stage stage2 = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Employee/addProductEmployee.fxml"));
        AnchorPane ap = loader.load();
        AddProductEmployee addProductEmployee = loader.getController();
        addProductEmployee.category("Dysk");
        Scene scene = new Scene(ap);
        stage2.setScene(scene);
        stage2.show();
    }

    /**
     * Refresh.
     */
    public void refresh(){
        driveList.clear();
        driveTableView();
    }
}
