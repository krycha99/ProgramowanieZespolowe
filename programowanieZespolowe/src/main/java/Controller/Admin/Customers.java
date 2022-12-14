package Controller.Admin;

import Config.DbConnect;
import Config.Pojos.UsersEntity;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The type Customers.
 */
public class Customers implements Initializable {

    @FXML
    private TableView<UsersEntity> customerTableView;
    @FXML
    private TableColumn<UsersEntity, String> customerName;
    @FXML
    private TableColumn<UsersEntity, String> customerSurname;
    @FXML
    private TableColumn<UsersEntity, Integer> customerNumber;
    @FXML
    private TableColumn<UsersEntity, String> customerEmail;
    @FXML
    private TableColumn<UsersEntity, String> customerAddress;
    @FXML
    private TableColumn<UsersEntity, String> customerLogin;
    @FXML
    private TableColumn<UsersEntity, Void> customerEdit;
    @FXML
    private TableColumn<UsersEntity, Void> customerDel;

    /**
     * The Customer list.
     */
    ObservableList<UsersEntity> customerList;
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
        customerView();
    }
    /**
     * Method for display customers from DB in a table.
     */
    private void customerView() {

        try {
            customerList = FXCollections.observableArrayList();
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM users WHERE Role = ? AND Archive_status = 0");
            st.setString(1, "Klient");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                UsersEntity u = new UsersEntity();
                u.setID_user(rs.getInt("ID_user"));
                u.setName(rs.getString("Name"));
                u.setSurname(rs.getString("Surname"));
                u.setPhone_number(rs.getInt("Phone_Number"));
                u.setEmail(rs.getString("Email"));
                u.setAddress(rs.getString("Address"));
                u.setLogin(rs.getString("Login"));
                u.setPassword(rs.getString("Password"));
                customerList.add(u);
            }
            customerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            customerSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
            customerNumber.setCellValueFactory(new PropertyValueFactory<>("Phone_number"));
            customerEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
            customerAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
            customerLogin.setCellValueFactory(new PropertyValueFactory<>("Login"));

            customerTableView.setItems(customerList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        addButtonToTable();
    }
    /**
     * Method for adding functional buttons to the table.
     */
    private void addButtonToTable() {
        Callback<TableColumn<UsersEntity, Void>, TableCell<UsersEntity, Void>> cellFactory = new Callback<TableColumn<UsersEntity, Void>, TableCell<UsersEntity, Void>>() {
            @Override
            public TableCell<UsersEntity, Void> call(final TableColumn<UsersEntity, Void> param) {
                return new TableCell<UsersEntity, Void>() {

                    private final Button btn = new Button();

                    {
                        btn.setId("customerEditButton");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                UsersEntity data = getTableView().getItems().get(getIndex());
                                Stage stage = new Stage();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/editCustomer.fxml"));
                                AnchorPane ap = loader.load();
                                EditCustomer editCustomer = loader.getController();
                                editCustomer.metoda(data.getID_user());

                                time = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        if (EditCustomer.refBool()) {
                                            refresh();
                                            time.stop();
                                            EditCustomer.bool = false;
                                        }
                                    }
                                }));
                                time.setCycleCount(Timeline.INDEFINITE);
                                time.play();

                                Scene scene = new Scene(ap);
                                stage.setScene(scene);
                                stage.setResizable(false);
                                stage.show();
                            } catch (IOException exception) {
                                exception.printStackTrace();
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

        Callback<TableColumn<UsersEntity, Void>, TableCell<UsersEntity, Void>> cellFactory2 = new Callback<TableColumn<UsersEntity, Void>, TableCell<UsersEntity, Void>>() {
            @Override
            public TableCell<UsersEntity, Void> call(final TableColumn<UsersEntity, Void> param) {
                return new TableCell<UsersEntity, Void>() {

                    private final Button btn = new Button();

                    {
                        btn.setId("customerDelButton");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                UsersEntity data = getTableView().getItems().get(getIndex());
                                PreparedStatement st = dbConnect.getConnection().prepareStatement("UPDATE users SET Archive_status = 1, Login = null WHERE ID_user = ?");
                                st.setInt(1, data.getID_user());
                                st.executeUpdate();

                                refresh();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Udane usunięcie klienta");
                                alert.setHeaderText("Klient został usunięty!");
                                alert.showAndWait();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
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

        customerEdit.setCellFactory(cellFactory);
        customerDel.setCellFactory(cellFactory2);
    }

    private void refresh() {
        customerList.clear();
        customerView();
    }

    /**
     * Add customer.
     *
     * @throws IOException the io exception
     */
    public void addCustomer() throws IOException {
        Stage stage = new Stage();
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Admin/addCustomer.fxml")));

        time = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (AddCustomer.refBool()) {
                    refresh();
                    time.stop();
                    AddCustomer.bool = false;
                }
            }
        }));
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.setTitle("Dodawanie klienta");
        stage.setResizable(false);
        stage.show();
    }
}