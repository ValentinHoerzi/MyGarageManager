package net.hoerzintelligence.mygarages.data.garage;

import lombok.Data;
import lombok.ToString;
import net.hoerzintelligence.mygarages.data.car.CarResource;

import java.util.List;

@Data
@ToString
public class GarageResource {
    private int id;
    private String name;
    private String address;
    private List<CarResource> cars;
}
