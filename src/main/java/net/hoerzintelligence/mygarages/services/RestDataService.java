package net.hoerzintelligence.mygarages.services;

import net.hoerzintelligence.mygarages.data.ConfigurationBundle;
import net.hoerzintelligence.mygarages.data.garage.GarageDto;
import net.hoerzintelligence.mygarages.data.garage.GarageResource;
import org.springframework.web.client.RestTemplate;

class RestDataService {
    private final RestTemplate template = new RestTemplate();
    private ConfigurationBundle bundle;

    GarageResource addGarage(GarageDto garageDto){

        GarageResource garageResource = template.postForObject("http://localhost:9001/garages", garageDto, GarageResource.class);
        return garageResource;
    }


    public void setConfigurationBundle(ConfigurationBundle bundle) {
        this.bundle = bundle;
    }
}
