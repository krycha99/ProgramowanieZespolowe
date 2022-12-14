package Controller.Admin;

import Config.DbConnect;
import Config.Pojos.AssortmentEntity;
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

import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * The type Employee order.
 */
public class EmployeeOrder implements Initializable {
    @FXML
    private TableView<AssortmentEntity> assortmentTable;
    @FXML
    private TableColumn<AssortmentEntity, Blob> assortmentImg;
    @FXML
    private TableColumn<AssortmentEntity, String> assortmentName;
    @FXML
    private TableColumn<AssortmentEntity, Integer> assortmentQuantity;
    @FXML
    private TableColumn<AssortmentEntity, String> assortmentCategory;
    @FXML
    private TableColumn<AssortmentEntity, String> assortmentDescription;
    @FXML
    private TableColumn<AssortmentEntity, Double> assortmentPrice;
    @FXML
    private TableColumn assorntmentAccept;
    @FXML
    private TableColumn assorntmentCancel;

    private ObservableList<AssortmentEntity> assortmentList;
    private Connection connection;
    /**
     * The Db connect.
     */
    DbConnect dbConnect;
    /**
     * The Input stream.
     */
    static InputStream inputStream;
    /**
     * The Time.
     */
    Timeline time;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbConnect = new DbConnect();
        assortmentTable();
    }
    /**
     * Method for display assortment from DB in a table.
     */
    private void assortmentTable() {
        try {
            assortmentList = FXCollections.observableArrayList();
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM assortment");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                AssortmentEntity a = new AssortmentEntity();
                a.setID_product(rs.getInt("ID_product"));
                a.setName(rs.getString("Name"));
                a.setQuantity(rs.getInt("Quantity"));
                a.setCategory(rs.getString("Category"));
                a.setDescription(rs.getString("Description"));
                a.setPrice(rs.getDouble("Price"));
                a.setImage(rs.getBlob("Image"));
                assortmentList.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assortmentImg.setCellValueFactory(new PropertyValueFactory<>("Image"));
        assortmentName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        assortmentQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        assortmentCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        assortmentDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        assortmentPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        assortmentTable.setItems(assortmentList);

        addButtonToTable();


        assortmentImg.setCellFactory(e -> {
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(assortmentImg.getMinWidth());
            imageView.setFitWidth(assortmentImg.getMinWidth());

            TableCell<AssortmentEntity, Blob> driveCell = new TableCell<AssortmentEntity, Blob>() {
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
        Callback<TableColumn<AssortmentEntity, Void>, TableCell<AssortmentEntity, Void>> cellFactory = new Callback<TableColumn<AssortmentEntity, Void>, TableCell<AssortmentEntity, Void>>() {
            @Override
            public TableCell<AssortmentEntity, Void> call(final TableColumn<AssortmentEntity, Void> param) {
                final TableCell<AssortmentEntity, Void> cell = new TableCell<AssortmentEntity, Void>() {

                    private final Button btn = new Button();

                    {


                        btn.setId("assortmentButtonAccept");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                AssortmentEntity data = getTableView().getItems().get(getIndex());
                                Stage stage = new Stage();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/editProductEmployee.fxml"));
                                AnchorPane ap = loader.load();
                                EditProductEmployee editProductEmployee = loader.getController();
                                editProductEmployee.metoda(data.getID_product());

                                time = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        if (EditProductEmployee.refBool()) {
                                            refresh();
                                            time.stop();
                                            EditProductEmployee.bool = false;
                                        }
                                    }
                                }));
                                time.setCycleCount(Timeline.INDEFINITE);
                                time.play();


                                Scene scene = new Scene(ap);
                                stage.setScene(scene);
                                stage.setResizable(false);
                                stage.show();

                            } catch (Exception ex) {
                                ex.printStackTrace();
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
        Callback<TableColumn<AssortmentEntity, Void>, TableCell<AssortmentEntity, Void>> cellFactory2 = new Callback<TableColumn<AssortmentEntity, Void>, TableCell<AssortmentEntity, Void>>() {
            @Override
            public TableCell<AssortmentEntity, Void> call(final TableColumn<AssortmentEntity, Void> param) {
                final TableCell<AssortmentEntity, Void> cell = new TableCell<AssortmentEntity, Void>() {

                    private final Button btn = new Button();

                    {
                        btn.setId("assortmentButtonCancel");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                AssortmentEntity data = assortmentTable.getItems().get(getIndex());
                                PreparedStatement st = dbConnect.getConnection().prepareStatement("DELETE FROM assortment WHERE ID_product = ?");
                                st.setInt(1, data.getID_product());
                                st.executeUpdate();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Produkt odrzucony");
                                alert.setHeaderText("Odrzuciłeś produkt");
                                alert.showAndWait();

                                refresh();
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

        assorntmentAccept.setCellFactory(cellFactory);
        assorntmentCancel.setCellFactory(cellFactory2);
    }

    /**
     * Refresh.
     */
    public void refresh() {
        assortmentList.clear();
        assortmentTable();
    }

}
