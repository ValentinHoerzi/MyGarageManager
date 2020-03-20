package net.hoerzintelligence.mygarages.services;

import net.hoerzintelligence.mygarages.data.ConfigurationBundle;
import net.hoerzintelligence.mygarages.data.car.CarDto;
import net.hoerzintelligence.mygarages.data.car.CarResource;
import net.hoerzintelligence.mygarages.data.garage.GarageDto;
import net.hoerzintelligence.mygarages.data.garage.GarageResource;
import org.springframework.data.rest.webmvc.BaseUri;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RestDataService {
    private static final RestTemplate template = new RestTemplate();
    private static String BASE_URL = "";
    private static ConfigurationBundle bundle;

    public void setConfigurationBundle(ConfigurationBundle bundle) {
        this.bundle = bundle;
        this.BASE_URL = String.format("http://localhost:%s/", bundle.getPort());
    }

    List<GarageResource> getAllGarages() {
        List<GarageResource> result = new ArrayList<>();
        GarageResource[] garages = template.getForObject(BASE_URL + bundle.getAllGarages(), GarageResource[].class);
        for (GarageResource g : garages) {
            result.add(g);
        }
        return result;
    }

    GarageResource addGarage(GarageDto garageDto) {
        return template.postForObject(BASE_URL + bundle.getAddGarage(), garageDto, GarageResource.class);
    }

    GarageResource editGarage(Integer id, GarageDto garageDto) {
        Map<String, String> vars = new HashMap<>();
        vars.put("garageId", String.valueOf(id));
        template.put(BASE_URL + bundle.getEditGarage(), garageDto, vars);

        List<GarageResource> allGarages = getAllGarages();
        for (GarageResource r : allGarages) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    String deleteGarage(Integer id) {
        Map<String, String> vars = new HashMap<>();
        vars.put("garageId", String.valueOf(id));
        template.delete(BASE_URL + bundle.getDeleteGarage(), vars);
        return "Nice!!";
    }

    List<CarResource> getAllCarsOfGarage(Integer id) {
        List<CarResource> result = new ArrayList<>();
        Map<String, String> vars = new HashMap<>();
        vars.put("garageId", String.valueOf(id));
        CarResource[] cars = template.getForObject(BASE_URL + bundle.getGetAllCarsOfGarage(), CarResource[].class, vars);
        for (CarResource c : cars) {
            result.add(c);
        }
        return result;
    }

    GarageResource addCarToGarage(Integer id, CarDto carDto){
        Map<String, String> vars = new HashMap<>();
        vars.put("garageId", String.valueOf(id));
        GarageResource garageResource = template.postForObject(BASE_URL + bundle.getAddCarToGarage(), carDto, GarageResource.class, vars);
        return garageResource;
    }

    String deleteCarInGarage(Integer garageId,Integer carId){
        Map<String, String> vars = new HashMap<>();
        vars.put("garageId", String.valueOf(garageId));
        vars.put("carId", String.valueOf(carId));
        template.delete(BASE_URL+bundle.getDeleteCarInGarage(),vars);
        return "Nice!!";
    }

    GarageResource moveCarToOtherGarage(Integer garageIdOld, Integer carId, Integer garageIdNew){
        Map<String, String> vars = new HashMap<>();
        vars.put("garageIdOld", String.valueOf(garageIdOld));
        vars.put("carId", String.valueOf(carId));
        vars.put("garageIdNew",String.valueOf(garageIdNew));
        GarageResource result = template.getForObject(BASE_URL + bundle.getMoveCarToOtherGarage(), GarageResource.class, vars);
        return result;
    }
}