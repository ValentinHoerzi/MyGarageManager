package net.hoerzintelligence.mygarages.services;

import net.hoerzintelligence.mygarages.data.ConfigurationBundle;
import net.hoerzintelligence.mygarages.data.car.CarDto;
import net.hoerzintelligence.mygarages.data.car.CarResource;
import net.hoerzintelligence.mygarages.data.garage.GarageDto;
import net.hoerzintelligence.mygarages.data.garage.GarageResource;

import java.util.List;

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

    public List<GarageResource> getAllGarages() {
        return service.getAllGarages();
    }

    public GarageResource addGarage(GarageDto garageDto){
        return service.addGarage(garageDto);
    }

    public  GarageResource editGarage(Integer id, GarageDto garageDto){
        return service.editGarage(id,garageDto);
    }

    public String deleteGarage(Integer id){
        return service.deleteGarage(id);
    }

    public List<CarResource> getAllCarsOfGarage(Integer id){
        return service.getAllCarsOfGarage(id);
    }

    public GarageResource addCarToGarage(Integer id, CarDto carDto){
        return service.addCarToGarage(id,carDto);
    }

    public String deleteCarInGarage(Integer garageId, Integer carId) {
        return service.deleteCarInGarage(garageId,carId);
    }

    GarageResource moveCarToOtherGarage(Integer garageIdOld, Integer carId, Integer garageIdNew){
        return service.moveCarToOtherGarage(garageIdOld,carId,garageIdNew);
    }
}
