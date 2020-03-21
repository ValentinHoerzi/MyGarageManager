package net.hoerzintelligence.mygarages.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.hoerzintelligence.mygarages.data.car.CarDto;
import net.hoerzintelligence.mygarages.data.car.CarResource;
import net.hoerzintelligence.mygarages.data.garage.GarageDto;
import net.hoerzintelligence.mygarages.data.garage.GarageResource;
import net.hoerzintelligence.mygarages.services.Model;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GaragesController implements Initializable {
    private final Model model = Model.getInstance();
    ObservableList<GarageResource> garageData = null;
    ObservableList<CarResource> carData = null;
    private GarageResource selectedGarage = null;
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
    @FXML
    private Button buttonAdd;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnCarId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnbrandAndName.setCellValueFactory(new PropertyValueFactory<>("brandAndName"));
        columnPS.setCellValueFactory(new PropertyValueFactory<>("horsePower"));

        init();
    }

    private void init() {
        List<GarageResource> garages = model.getAllGarages();
        garageData = FXCollections.observableArrayList(garages);
        carData = FXCollections.observableArrayList();
        updateTable();
        updateView();
        clearCarTextFields();
        clearGarageTextFields();
    }

    @FXML
    private void onButtonAddGarage(ActionEvent actionEvent) {
        String garageName = txtGarageName.getText();
        String garageAddress = txtGarageAddress.getText();
        GarageDto dto = new GarageDto();
        dto.setName(garageName);
        dto.setAddress(garageAddress);
        model.addGarage(dto);
        garageData.clear();
        garageData.setAll(model.getAllGarages());
        updateTable();
        updateView();
        clearGarageTextFields();
    }

    @FXML
    private void onButtonAddCar(ActionEvent actionEvent) {
        String carName = txtCarName.getText();
        String carBrand = txtCarBrand.getText();
        String carPS = txtCarPS.getText();
        CarDto carDto = new CarDto();
        carDto.setName(carName);
        carDto.setBrand(carBrand);
        carDto.setHorsePower(carPS);
        model.addCarToGarage(selectedGarage.getId(),carDto);

        List<CarResource> allCarsOfGarage = model.getAllCarsOfGarage(selectedGarage.getId());
        carData.clear();
        carData.setAll(allCarsOfGarage);
        updateView();
        updateTable();
        clearCarTextFields();
        clearGarageTextFields();
    }

    @FXML
    private void handleGarageSelection(MouseEvent mouseEvent) {
        GarageResource selectedItem = tableGarages.getSelectionModel().getSelectedItem();
        selectedGarage = selectedItem;

        List<CarResource> allCarsOfGarage = model.getAllCarsOfGarage(selectedGarage.getId());
        carData.clear();
        carData.setAll(allCarsOfGarage);
        updateView();
        updateTable();

        tableGarages.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleCarSelection(MouseEvent mouseEvent) {

    }

    private void updateTable() {
        tableGarages.setItems(garageData);
        tableCars.setItems(carData);
    }

    private void updateView() {
        if (selectedGarage == null) {
            buttonAdd.setDisable(true);
            lblSelectedGarage.setText("No garage selected!");
        } else {
            buttonAdd.setDisable(false);
            lblSelectedGarage.setText(selectedGarage.getName());
        }
    }

    private void clearGarageTextFields() {
        txtGarageAddress.setText("");
        txtGarageName.setText("");
    }

    private void clearCarTextFields() {
        txtCarBrand.setText("");
        txtCarName.setText("");
        txtCarPS.setText("");
    }
}
