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

    private ObservableList<GarageResource> garageData = null;
    private ObservableList<CarResource> carData = null;

    private GarageResource selectedGarage = null;
    private CarResource selectedCar = null;

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
        garageData = FXCollections.observableArrayList();
        carData = FXCollections.observableArrayList();
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

        new Thread(()->{
            model.addGarage(dto);
            updateView();
        }).start();

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

        new Thread(()->{
            selectedGarage = null;
            model.addCarToGarage(selectedGarage.getId(), carDto);
            updateView();
        }).start();

        clearCarTextFields();
    }

    @FXML
    private void handleGarageSelection(MouseEvent mouseEvent) {
        GarageResource selectedItem = tableGarages.getSelectionModel().getSelectedItem();
        selectedGarage = selectedItem;

        txtGarageName.setText(selectedGarage.getName());
        txtGarageAddress.setText(selectedGarage.getAddress());

        updateView();

        tableGarages.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleCarSelection(MouseEvent mouseEvent) {
        CarResource selectedItem = tableCars.getSelectionModel().getSelectedItem();

    }

    private synchronized void updateView() {
        List<GarageResource> garages = model.getAllGarages();
        garageData.clear();
        garageData.setAll(garages);

        if (selectedGarage != null) {
            buttonAdd.setDisable(false);
            lblSelectedGarage.setText(selectedGarage.getName());

            List<CarResource> allCarsOfGarage = model.getAllCarsOfGarage(selectedGarage.getId());
            carData.clear();
            carData.setAll(allCarsOfGarage);
        }else{
            buttonAdd.setDisable(true);
            lblSelectedGarage.setText("No garage selected!");
        }
        tableGarages.setItems(garageData);
        tableCars.setItems(carData);
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
