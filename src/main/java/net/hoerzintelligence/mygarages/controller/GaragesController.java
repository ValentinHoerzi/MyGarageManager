package net.hoerzintelligence.mygarages.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import net.hoerzintelligence.mygarages.data.car.CarDto;
import net.hoerzintelligence.mygarages.data.car.CarResource;
import net.hoerzintelligence.mygarages.data.garage.GarageDto;
import net.hoerzintelligence.mygarages.data.garage.GarageResource;
import net.hoerzintelligence.mygarages.services.Model;
import org.apache.catalina.filters.RemoteIpFilter;

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
    private Label lblPerformingOperation;
    @FXML
    private TableView<GarageResource> tblGarages;
    @FXML
    private TableColumn<GarageResource, Integer> columnId;
    @FXML
    private TableColumn<GarageResource, String> columnName;
    @FXML
    private TableColumn<GarageResource, String> columnAddress;
    @FXML
    private TableView<CarResource> tblCars;
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
    private TextField garageIdToMove;
    @FXML
    private Label lblSelectedGarage;
    @FXML
    private Button btnAddCar;
    @FXML
    private Button btnEditGarage;
    @FXML
    private Button btnDeleteGarage;
    @FXML
    private Button btnDeleteCar;
    @FXML
    private Button btnMoveCar;

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

        updateView(true);

        clearCarTextFields();
        clearGarageTextFields();
        lblPerformingOperation.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @FXML
    private void onbtnAddGarage() {
        String garageName = txtGarageName.getText();
        String garageAddress = txtGarageAddress.getText();

        GarageDto dto = new GarageDto();
        dto.setName(garageName);
        dto.setAddress(garageAddress);

        selectedGarage = null;

        new Thread(() -> {
            Platform.runLater(() -> performOperation(true));

            GarageResource garageResource = model.addGarage(dto);
            Platform.runLater(() -> garageData.add(garageResource));

            Platform.runLater(() -> performOperation(false));
        }).start();

        updateView(false);

        clearGarageTextFields();
    }

    @FXML
    private void onbtnAddCar() {
        String carName = txtCarName.getText();
        String carBrand = txtCarBrand.getText();
        String carPS = txtCarPS.getText();

        CarDto carDto = new CarDto();
        carDto.setName(carName);
        carDto.setBrand(carBrand);
        carDto.setHorsePower(carPS);

        selectedCar = null;
        new Thread(() -> {
            Platform.runLater(() -> performOperation(true));

            GarageResource garageResource = model.addCarToGarage(selectedGarage.getId(), carDto);
            selectedGarage.setCars(garageResource.getCars());
            Platform.runLater(() -> carData.setAll(selectedGarage.getCars()));

            Platform.runLater(() -> performOperation(false));
        }).start();

        updateView(false);

        clearCarTextFields();
    }

    @FXML
    private void onbtnEditGarage() {
        String garageName = txtGarageName.getText();
        String garageAddress = txtGarageAddress.getText();

        GarageDto dto = new GarageDto();
        dto.setName(garageName);
        dto.setAddress(garageAddress);

        int id = selectedGarage.getId();
        new Thread(() -> {
            Platform.runLater(() -> performOperation(true));

            model.editGarage(id, dto);

            Platform.runLater(() -> performOperation(false));

            selectedGarage = null;

            Platform.runLater(() -> updateView(true));
        }).start();

        clearGarageTextFields();
    }

    @FXML
    private void onbtnDeleteGarage() {
        int id = selectedGarage.getId();
        new Thread(() -> {
            Platform.runLater(() -> performOperation(true));

            model.deleteGarage(id);

            Platform.runLater(() -> performOperation(false));

            selectedGarage = null;

            Platform.runLater(() -> updateView(true));
        }).start();

        clearGarageTextFields();
    }

    @FXML
    private void onbtnDeleteCar() {
        int carId = selectedCar.getId();
        int garageId = selectedGarage.getId();

        new Thread(() -> {
            Platform.runLater(() -> performOperation(true));

            model.deleteCarInGarage(garageId,carId);

            for(CarResource c : carData){
                if(c.getId() == carId){
                    carData.remove(c);
                    break;
                }
            }

            Platform.runLater(() -> performOperation(false));

            selectedCar = null;
        }).start();
    }

    @FXML
    private void onbtnMoveCar() {

    }

    @FXML
    private void handleGarageSelection() {
        GarageResource selectedItem = tblGarages.getSelectionModel().getSelectedItem();
        if (selectedItem.equals(selectedGarage)) return;
        selectedGarage = selectedItem;
        selectedCar = null;

        txtGarageName.setText(selectedGarage.getName());
        txtGarageAddress.setText(selectedGarage.getAddress());

        updateView(false);

        tblGarages.getSelectionModel().clearSelection();

        refreshCarsAsync();
    }

    @FXML
    private void handleCarSelection() {
        CarResource selectedItem = tblCars.getSelectionModel().getSelectedItem();
        if (selectedItem.equals(selectedCar)) return;
        selectedCar = selectedItem;

        String[] vars = selectedCar.getBrandAndName().split(" ");
        txtCarName.setText(vars[1]);
        txtCarBrand.setText(vars[0]);
        txtCarPS.setText(selectedCar.getHorsePower());

        tblCars.getSelectionModel().clearSelection();

        updateView(false);
    }

    private void refreshCarsAsync() {
        new Thread(() -> {
            Platform.runLater(() -> performOperation(true));

            List<CarResource> allCarsOfGarage = model.getAllCarsOfGarage(selectedGarage.getId());
            Platform.runLater(() -> carData.setAll(allCarsOfGarage));

            Platform.runLater(() -> performOperation(false));
        }).start();
    }

    private void updateView(boolean refreshGarages) {
        if (refreshGarages) {
            List<GarageResource> garages = model.getAllGarages();
            garageData.setAll(garages);
        }

        checkGarageSelection();
        checkCarSelection();

        tblGarages.setItems(garageData);
        tblCars.setItems(carData);
    }

    private void checkGarageSelection() {
        if (selectedGarage != null) {
            btnAddCar.setDisable(false);
            btnDeleteGarage.setDisable(false);
            btnEditGarage.setDisable(false);

            lblSelectedGarage.setText(selectedGarage.getName());
        } else {
            btnDeleteGarage.setDisable(true);
            btnEditGarage.setDisable(true);
            btnAddCar.setDisable(true);

            lblSelectedGarage.setText("No garage selected!");

            selectedCar = null;
            carData.clear();
        }
    }

    private void checkCarSelection() {
        if (selectedCar != null) {
            btnDeleteCar.setDisable(false);
            btnMoveCar.setDisable(false);
        } else {
            btnDeleteCar.setDisable(true);
            btnMoveCar.setDisable(true);
            clearCarTextFields();
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

    private void performOperation(boolean performing) {
        if (performing) {
            lblPerformingOperation.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            lblPerformingOperation.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        lblPerformingOperation.setText(performing ? "Currently performing operation" : "Currently not performing operation");
    }
}
