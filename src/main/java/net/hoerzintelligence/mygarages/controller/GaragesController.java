package net.hoerzintelligence.mygarages.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.hoerzintelligence.mygarages.data.car.CarResource;
import net.hoerzintelligence.mygarages.data.garage.GarageResource;
import net.hoerzintelligence.mygarages.services.Model;

import java.net.PortUnreachableException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GaragesController implements Initializable {
    private final Model model = Model.getInstance();
    private GarageResource selectedGarage = null;

    ObservableList<GarageResource> garageData = null;
    ObservableList<CarResource> carData = null;

    @FXML
    private TableView<GarageResource> tableGarages;
    @FXML
    private TableColumn<GarageResource, Integer> columnId;
    @FXML
    private TableColumn<GarageResource, String> columnName;
    @FXML
    private TableColumn<GarageResource, String> columnAddress;
    @FXML
    private TableView<CarResource> tableCars;
    @FXML
    private TableColumn<CarResource, Integer> columnCarId;
    @FXML
    private TableColumn<CarResource, String> columnbrandAndName;
    @FXML
    private TableColumn<CarResource, String> columnPS;
    @FXML
    private TextField txtGarageAddress;
    @FXML
    private TextField txtGarageName;
    @FXML
    private TextField txtCarPS;
    @FXML
    private TextField txtCarName;
    @FXML
    private TextField txtCarBrand;
    @FXML
    private Label lblSelectedGarage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnCarId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnbrandAndName.setCellValueFactory(new PropertyValueFactory<>("brandAndName"));
        columnPS.setCellValueFactory(new PropertyValueFactory<>("horsePower"));

        initData();
    }

    private void initData() {
        List<GarageResource> garages = model.getAllGarages();
        garageData = FXCollections.observableArrayList(garages);
        carData = FXCollections.observableArrayList();
        updateTable();
    }


    @FXML
    private void handleGarageSelection(MouseEvent mouseEvent) {

    }

    private void updateTable() {
        tableGarages.setItems(garageData);
        tableCars.setItems(carData);
    }

    @FXML
    private void onButtonAddGarage(ActionEvent actionEvent) {
    }

    @FXML
    private void onButtonAddCar(ActionEvent actionEvent) {
    }

    private void clearGarageTextFields(){
        txtGarageAddress.setText("");
        txtGarageName.setText("");
    }
    private void clearCarTextFields(){
        txtCarBrand.setText("");
        txtCarName.setText("");
        txtCarPS.setText("");
    }
}
