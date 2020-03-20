package net.hoerzintelligence.mygarages.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfigurationBundle {
    private String allGarages;
    private String addGarage;
    private String editGarage;
    private String deleteGarage;
    private String getAllCarsOfGarage;
    private String addCarToGarage;
    private String deleteCarInGarage;
    private String moveCarToOtherGarage;
    private String port;
}
