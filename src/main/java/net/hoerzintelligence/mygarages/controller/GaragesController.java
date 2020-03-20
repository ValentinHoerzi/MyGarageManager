package net.hoerzintelligence.mygarages.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.hoerzintelligence.mygarages.data.car.CarResource;
import net.hoerzintelligence.mygarages.data.garage.GarageResource;
import net.hoerzintelligence.mygarages.services.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class GaragesController implements Initializable {
    private final Model model = Model.getInstance();
    private GarageResource selectedGarage = null;
    @FXML
    private TableView<GarageResource> tableGarages;
    ObservableList<GarageResource> data = null;
    @FXML
    private TableColumn<GarageResource,Integer> columnId;
    @FXML
    private TableColumn<GarageResource,String> columnName;
    @FXML
    private TableColumn<GarageResource,String > columnAddress;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        data = FXCollections.observableArrayList(model.getAllGarages());
        updateTable();
    }


    @FXML
    private void handleGarageSelection(MouseEvent mouseEvent) {

    }

    private void updateTable(){
        tableGarages.setItems(data);
    }
}
