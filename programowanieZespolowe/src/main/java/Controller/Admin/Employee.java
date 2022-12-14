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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The type Employee.
 */
public class Employee implements Initializable {

    @FXML
    private TableView<UsersEntity> employeeTableView;
    @FXML
    private TableColumn<UsersEntity, String> employeeName;
    @FXML
    private TableColumn<UsersEntity, String> employeeSurname;
    @FXML
    private TableColumn<UsersEntity, String> employeeEmail;
    @FXML
    private TableColumn<UsersEntity, Integer> employeeNumber;
    @FXML
    private TableColumn<UsersEntity, String> employeeAddress;
    @FXML
    private TableColumn<UsersEntity, String> employeeLogin;
    @FXML
    private TableColumn<UsersEntity, String> employeeRole;
    @FXML
    private TableColumn<UsersEntity, Integer> employeeSalary;
    @FXML
    private TableColumn employeeEdit;
    @FXML
    private TableColumn employeeDel;

    private ObservableList<UsersEntity> employeeList;
    private Connection connection;
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
        employeeView();
    }

    private void employeeView() {
        try {

            employeeList = FXCollections.observableArrayList();
            PreparedStatement st = dbConnect.getConnection().prepareStatement("SELECT * FROM users WHERE Role = ? AND Archive_status = 0 OR Role = ? AND Archive_status = 0");
            st.setString(1, "Pracownik");
            st.setString(2, "Administrator");
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
                u.setRole(rs.getString("Role"));
                u.setSalary(rs.getString("Salary"));
                employeeList.add(u);
            }

            employeeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            employeeSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
            employeeNumber.setCellValueFactory(new PropertyValueFactory<>("Phone_number"));
            employeeEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
            employeeAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
            employeeLogin.setCellValueFactory(new PropertyValueFactory<>("Login"));
            employeeRole.setCellValueFactory(new PropertyValueFactory<>("Role"));
            employeeSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));

            employeeTableView.setItems(FXCollections.observableArrayList(employeeList));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<UsersEntity, Void>, TableCell<UsersEntity, Void>> cellFactory = new Callback<TableColumn<UsersEntity, Void>, TableCell<UsersEntity, Void>>() {
            @Override
            public TableCell<UsersEntity, Void> call(final TableColumn<UsersEntity, Void> param) {
                final TableCell<UsersEntity, Void> cell = new TableCell<UsersEntity, Void>() {

                    private final Button btn = new Button();

                    {
                        btn.setId("employeeEditButton");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                UsersEntity data = getTableView().getItems().get(getIndex());
                                Stage stage = new Stage();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Admin/editEmployee.fxml"));
                                AnchorPane ap = loader.load();
                                EditEmployee editEmployee = loader.getController();
                                editEmployee.metoda(data.getID_user());

                                time = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        if (EditEmployee.refBool()) {
                                            refresh();
                                            time.stop();
                                            EditEmployee.bool = false;
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

        Callback<TableColumn<UsersEntity, Void>, TableCell<UsersEntity, Void>> cellFactory2 = new Callback<TableColumn<UsersEntity, Void>, TableCell<UsersEntity, Void>>() {
            @Override
            public TableCell<UsersEntity, Void> call(final TableColumn<UsersEntity, Void> param) {
                final TableCell<UsersEntity, Void> cell = new TableCell<UsersEntity, Void>() {

                    private final Button btn = new Button();

                    {
                        btn.setId("employeeDelButton");
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                UsersEntity data = getTableView().getItems().get(getIndex());
                                PreparedStatement st = dbConnect.getConnection().prepareStatement("UPDATE users SET Archive_status = 1, Login = null WHERE ID_user = ?");
                                st.setInt(1, data.getID_user());
                                st.executeUpdate();

                                refresh();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Udane usunięcie pracownika");
                                alert.setHeaderText("Pracownik został usunięty!");
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

        employeeEdit.setCellFactory(cellFactory);
        employeeDel.setCellFactory(cellFactory2);
    }

    private void refresh() {
        employeeList.clear();
        employeeView();
    }

    /**
     * Method for new adding employee to DB.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void addEmployee(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/FXML/Admin/addEmployee.fxml"));

        time = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (AddEmployee.refBool()) {
                    refresh();
                    time.stop();
                    AddEmployee.bool = false;
                }
            }
        }));
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.setTitle("Dodawanie pracownika");
        stage.setResizable(false);
        stage.show();
    }
}
