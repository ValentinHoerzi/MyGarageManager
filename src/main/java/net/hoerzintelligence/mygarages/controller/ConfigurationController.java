package net.hoerzintelligence.mygarages.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import net.hoerzintelligence.mygarages.core.Main;
import net.hoerzintelligence.mygarages.data.ConfigurationBundle;
import net.hoerzintelligence.mygarages.services.Model;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurationController implements Initializable {

    private final Model model = Model.getInstance();
    @FXML
    private TextField txtGetAllGarages;
    @FXML
    private TextField txtAddGarage;
    @FXML
    private TextField txtEditGarage;
    @FXML
    private TextField txtDeleteGarage;
    @FXML
    private TextField txtGetAllCarsOfGarage;
    @FXML
    private TextField txtAddCarToGarage;
    @FXML
    private TextField txtDeleteCarInGarage;
    @FXML
    private TextField txtMoveCarToOtherGarage;
    @FXML
    private TextField txtPort;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void onButtonPerceed(ActionEvent actionEvent) throws Exception {
        String allGaragesText = txtGetAllGarages.getText();
        String addGarageText = txtAddGarage.getText();
        String editGarageText = txtEditGarage.getText();
        String deleteGarageText = txtDeleteGarage.getText();
        String allCarsOfGarageText = txtGetAllCarsOfGarage.getText();
        String addCarToGarageText = txtAddCarToGarage.getText();
        String deleteCarInGarageText = txtDeleteCarInGarage.getText();
        String moveCarToOtherGarageText = txtMoveCarToOtherGarage.getText();
        String port = txtPort.getText();
        ConfigurationBundle bundle = ConfigurationBundle.builder()
                .allGarages(allGaragesText)
                .addGarage(addGarageText)
                .editGarage(editGarageText)
                .deleteGarage(deleteGarageText)
                .getAllCarsOfGarage(allCarsOfGarageText)
                .addCarToGarage(addCarToGarageText)
                .deleteCarInGarage(deleteCarInGarageText)
                .moveCarToOtherGarage(moveCarToOtherGarageText)
                .port(port)
                .build();
        model.setConfigurationBundle(bundle);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/garages.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Main.switchView(scene);
    }

    @FXML
    private void onButtonLoad(ActionEvent actionEvent) {
        throw new NotImplementedException();
    }

    @FXML
    private void onButtonSave(ActionEvent actionEvent) {
        throw new NotImplementedException();
    }
}
