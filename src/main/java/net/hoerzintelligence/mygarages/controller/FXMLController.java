package net.hoerzintelligence.mygarages.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import net.hoerzintelligence.mygarages.data.garage.GarageDto;
import net.hoerzintelligence.mygarages.data.garage.GarageResource;
import net.hoerzintelligence.mygarages.services.Model;

public class FXMLController implements Initializable {

    private Model model = Model.getInstance();

    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        GarageResource garageResource = model.addGarage(new GarageDto());
        System.out.println(garageResource);
        label.setText(garageResource.toString());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
