package Controller.Admin;

import Config.DbConnect;
import Config.Pojos.OrdersEntity;
import Controller.Invoice.InvoiceCreator;
import Controller.User.OrderConfirm;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * The type Customers order.
 */
public class CustomersOrder implements Initializable {
    @FXML
    private TableView<OrdersEntity> ordersTable;
    @FXML
    private TableColumn<OrdersEntity, Integer> orderID;
    @FXML
    private TableColumn<OrdersEntity, String> customer;
    @FXML
    private TableColumn<OrdersEntity, Date> date;
    @FXML
    private TableColumn<OrdersEntity, String> payment;
    @FXML
    private TableColumn<OrdersEntity, String> statement;
    @FXML
    private TableColumn<OrdersEntity, Void> details;
    @FXML
    private TableColumn<OrdersEntity, Void> details2;

    private ObservableList<OrdersEntity> ordersList;
    /**
     * The Db connect.
     */
    DbConnect dbConnect;
    /**
     * The Time.
     */
    Timeline time;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbConnect = new DbConnect();
        ordersTable();
    }
    /**
     * Method for display orders from DB in a table.
     */
    private void ordersTable() {
        try {
            ordersList = FXCollections.observableArrayList();
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT *, CONCAT(users.Name,' ',users.Surname) as FullName FROM orders " +
                    "INNER JOIN users ON orders.ID_user=users.ID_user " +
                    "INNER JOIN payments ON orders.ID_payment=payments.ID_payment " +
                    "ORDER BY id_order;");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                OrdersEntity o = new OrdersEntity();
                o.setID_order(rs.getInt("ID_order"));
                o.setID_user(rs.getInt("ID_user"));
                o.setFullName(rs.getString("FullName"));
                o.setStatus(rs.getString("Status"));
                o.setDate(rs.getDate("Date"));
                o.setDelivery_address(rs.getString("Delivery_address"));
                o.setID_payment(rs.getInt("ID_payment"));
                o.setPayment(rs.getString("payments.Name"));
                o.setDescription(rs.getString("Description"));
                ordersList.add(o);
            }
            orderID.setCellValueFactory(new PropertyValueFactory<>("ID_order"));
            customer.setCellValueFactory(new PropertyValueFactory<>("FullName"));
            statement.setCellValueFactory(new PropertyValueFactory<>("Status"));
            date.setCellValueFactory(new PropertyValueFactory<>("Date"));
            payment.setCellValueFactory(new PropertyValueFactory<>("Payment"));

            ordersTable.setItems(ordersList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        addButtonToTable();
    }

    /**
     * Method for adding functional buttons to the table.
     */
    private void addButtonToTable() {

        Callback<TableColumn<OrdersEntity, Void>, TableCell<OrdersEntity, Void>> cellFactory = new Callback<TableColumn<OrdersEntity, Void>, TableCell<OrdersEntity, Void>>() {
            @Override
            public TableCell<OrdersEntity, Void> call(final TableColumn<OrdersEntity, Void> param) {
                return new TableCell<OrdersEntity, Void>() {
                    private final Button btn = new Button("Szczegóły");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                time = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        if (CustomersOrder2.refBool()) {
                                            refresh();
                                            time.stop();
                                            CustomersOrder2.bool = false;
                                        }
                                    }
                                }));
                                time.setCycleCount(Timeline.INDEFINITE);
                                time.play();

                                OrdersEntity data = getTableView().getItems().get(getIndex());
                                Stage stage = new Stage();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/orderCustomer2.fxml"));
                                AnchorPane ap = loader.load();
                                CustomersOrder2 customersOrder2 = loader.getController();
                                customersOrder2.metoda(data.getID_order());
                                Scene scene = new Scene(ap);
                                stage.setScene(scene);
                                stage.setTitle("Szczegóły zamówienia klienta");
                                stage.setResizable(false);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
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
        details.setCellFactory(cellFactory);

        Callback<TableColumn<OrdersEntity, Void>, TableCell<OrdersEntity, Void>> cellFactory2 = new Callback<TableColumn<OrdersEntity, Void>, TableCell<OrdersEntity, Void>>() {
            @Override
            public TableCell<OrdersEntity, Void> call(final TableColumn<OrdersEntity, Void> param) {
                return new TableCell<OrdersEntity, Void>() {
                    private final Button btn = new Button("Pobierz Fakture");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                String path = null;
                                final DirectoryChooser directoryChooser = new DirectoryChooser();
                                Stage stage = (Stage) getTableView().getScene().getWindow();
                                File file = directoryChooser.showDialog(stage);

                                if (file != null) {
                                    path = file.getAbsolutePath();
                                    OrdersEntity data = getTableView().getItems().get(getIndex());
                                    InvoiceCreator invoiceCreator = new InvoiceCreator();
                                    invoiceCreator.create(data.getID_order(), path);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
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
        details2.setCellFactory(cellFactory2);
    }

    /**
     * Refresh.
     */
    public void refresh() {
        ordersList.clear();
        ordersTable();
    }
}
