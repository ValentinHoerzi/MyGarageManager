package net.hoerzintelligence.mygarages.services;

import net.hoerzintelligence.mygarages.data.ConfigurationBundle;
import net.hoerzintelligence.mygarages.data.garage.GarageDto;
import net.hoerzintelligence.mygarages.data.garage.GarageResource;

public class Model {
    private RestDataService service = new RestDataService();
    private static final Model instance = new Model();
    private Model(){}
    public static Model getInstance(){
        return instance;
    }

    public void setConfigurationBundle(ConfigurationBundle bundle){
        service.setConfigurationBundle(bundle);
    }

    public GarageResource addGarage(GarageDto garageDto){
        GarageDto temp = new GarageDto();
        temp.setAddress("Hydenstraße 14");
        temp.setName("Vali's Garage");
        return service.addGarage(temp);
    }
}
