package Controller.Admin;

import Config.DbConnect;
import Config.Pojos.ProductsEntity;
import Config.Pojos.UsersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * The type Assortment.
 */
public class Assortment implements Initializable {

    //drive
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

    //graphic
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

    //power
    @FXML
    private TableView<ProductsEntity> powerTable;
    @FXML
    private TableColumn<ProductsEntity, String> powerName;
    @FXML
    private TableColumn<ProductsEntity, Integer> powerVolume;
    @FXML
    private TableColumn<ProductsEntity, String> powerInfo;
    @FXML
    private TableColumn<ProductsEntity, Integer> powerPrice;
    @FXML
    private TableColumn<ProductsEntity, Blob> powerImg;

    //processor
    @FXML
    private TableView<ProductsEntity> processorTable;
    @FXML
    private TableColumn<ProductsEntity, String> processorName;
    @FXML
    private TableColumn<ProductsEntity, Integer> processorVolume;
    @FXML
    private TableColumn<ProductsEntity, String> processorInfo;
    @FXML
    private TableColumn<ProductsEntity, Integer> processorPrice;
    @FXML
    private TableColumn<ProductsEntity, Blob> processorImg;

    //ram
    @FXML
    private TableView<ProductsEntity> ramTable;
    @FXML
    private TableColumn<ProductsEntity, String> ramName;
    @FXML
    private TableColumn<ProductsEntity, Integer> ramVolume;
    @FXML
    private TableColumn<ProductsEntity, String> ramInfo;
    @FXML
    private TableColumn<ProductsEntity, Integer> ramPrice;
    @FXML
    private TableColumn<ProductsEntity, Blob> ramImg;

    @FXML
    private TableColumn driveEdit;
    @FXML
    private TableColumn driveDel;
    @FXML
    private TableColumn procesorEdit;
    @FXML
    private TableColumn procesorDel;
    @FXML
    private TableColumn ramEdit;
    @FXML
    private TableColumn ramDel;
    @FXML
    private TableColumn powerEdit;
    @FXML
    private TableColumn powerDel;
    @FXML
    private TableColumn graphicEdit;
    @FXML
    private TableColumn graphicDel;


    //Listy
    private ObservableList<ProductsEntity> driveList;
    private ObservableList<ProductsEntity> graphicList;
    private ObservableList<ProductsEntity> powerList;
    private ObservableList<ProductsEntity> processorList;
    private ObservableList<ProductsEntity> ramList;
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
        graphicTableView();
        powerTableView();
        processorTableView();
        ramTableView();
    }
    /**
     * Method to display hard drives from DB in a table.
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


        driveImg.setCellFactory(e -> {
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(driveImg.getMinWidth());
            imageView.setFitWidth(driveImg.getMinWidth());
            driveAddButtonToTable();
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
    private void driveAddButtonToTable() {

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

                                driveRefresh();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Udane usunięcie dysku!");
                                alert.setHeaderText("Dysk został usunięty z strony sklepu!");
                                alert.showAndWait();
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

        driveDel.setCellFactory(cellFactory2);
    }
    /**
     * Method to display graphic cards from DB in a table.
     */
    private void graphicTableView() {

        try {
            graphicList = FXCollections.observableArrayList();
            String query = "SELECT * FROM products WHERE Category='Karta graficzna' AND Archive_status = 0";
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


        graphicImg.setCellFactory(e -> {
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(graphicImg.getMinWidth());
            imageView.setFitWidth(graphicImg.getMinWidth());
            graphicAddButtonToTable();
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
    private void graphicAddButtonToTable() {

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

                                graphicRefresh();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Udane usunięcie Karty graficznej!");
                                alert.setHeaderText("Karta graficzna został usunięty z strony sklepu!");
                                alert.showAndWait();
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

        graphicDel.setCellFactory(cellFactory2);
    }
    /**
     * Method to display powers from DB in a table.
     */
    private void powerTableView() {
        try {
            powerList = FXCollections.observableArrayList();
            String query = "SELECT * FROM products WHERE Category='Zasilacz' AND Archive_status = 0";
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
                powerList.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        powerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        powerVolume.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        powerInfo.setCellValueFactory(new PropertyValueFactory<>("description"));
        powerPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        powerImg.setCellValueFactory(new PropertyValueFactory<>("image"));

        powerTable.setItems(powerList);
        powerAddButtonToTable();

        powerImg.setCellFactory(e -> {
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(powerImg.getMinWidth());
            imageView.setFitWidth(powerImg.getMinWidth());

            TableCell<ProductsEntity, Blob> powerCell = new TableCell<ProductsEntity, Blob>() {
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
            powerCell.setGraphic(imageView);
            return powerCell;
        });
    }
    /**
     * Method for adding functional buttons to the table.
     */
    private void powerAddButtonToTable() {

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

                                powerRefresh();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Udane usunięcie Zasilacza!");
                                alert.setHeaderText("Zasilacz został usunięty z strony sklepu!");
                                alert.showAndWait();
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

        powerDel.setCellFactory(cellFactory2);
    }
    /**
     * Method to display processors cards from DB in a table.
     */
    private void processorTableView() {
        try {
            processorList = FXCollections.observableArrayList();
            String query = "SELECT * FROM products WHERE Category='Procesor' AND Archive_status = 0";
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
                processorList.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        processorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        processorVolume.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        processorInfo.setCellValueFactory(new PropertyValueFactory<>("description"));
        processorPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        processorImg.setCellValueFactory(new PropertyValueFactory<>("image"));

        processorTable.setItems(processorList);
        procesorAddButtonToTable();
        processorImg.setCellFactory(e -> {
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(processorImg.getMinWidth());
            imageView.setFitWidth(processorImg.getMinWidth());

            TableCell<ProductsEntity, Blob> processorCell = new TableCell<ProductsEntity, Blob>() {
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
            processorCell.setGraphic(imageView);
            return processorCell;
        });
    }
    /**
     * Method for adding functional buttons to the table.
     */
    private void procesorAddButtonToTable() {

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

                                processorRefresh();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Udane usunięcie Procesora!");
                                alert.setHeaderText("Procesor został usunięty z strony sklepu!");
                                alert.showAndWait();
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

        procesorDel.setCellFactory(cellFactory2);
    }
    /**
     * Method to display ram from DB in a table.
     */
    private void ramTableView() {
        try {
            ramList = FXCollections.observableArrayList();
            String query = "SELECT * FROM products WHERE Category='Ram' AND Archive_status = 0";
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
                ramList.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ramName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ramVolume.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ramInfo.setCellValueFactory(new PropertyValueFactory<>("description"));
        ramPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ramImg.setCellValueFactory(new PropertyValueFactory<>("image"));
        ramAddButtonToTable();
        ramTable.setItems(ramList);

        ramImg.setCellFactory(e -> {
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(ramImg.getMinWidth());
            imageView.setFitWidth(ramImg.getMinWidth());

            TableCell<ProductsEntity, Blob> ramCell = new TableCell<ProductsEntity, Blob>() {
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
            ramCell.setGraphic(imageView);
            return ramCell;
        });
    }
    /**
     * Method for adding functional buttons to the table.
     */
    private void ramAddButtonToTable() {

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

                                ramRefresh();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Udane usunięcie pamieci RAM!");
                                alert.setHeaderText("RAM został usunięty z strony sklepu!");
                                alert.showAndWait();
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

        ramDel.setCellFactory(cellFactory2);
    }
    /**
     * Method used to refresh data in the specified table.
     */
    private void driveRefresh() {
        driveList.clear();
        driveTableView();
    }
    /**
     * Method used to refresh data in the specified table.
     */
    private void graphicRefresh() {
        graphicList.clear();
        graphicTableView();
    }
    /**
     * Method used to refresh data in the specified table.
     */
    private void powerRefresh() {
        powerList.clear();
        powerTableView();
    }
    /**
     * Method used to refresh data in the specified table.
     */
    private void ramRefresh() {
        ramList.clear();
        ramTableView();
    }
    /**
     * Method used to refresh data in the specified table.
     */
    private void processorRefresh() {
        processorList.clear();
        processorTableView();
    }
}
