package net.hoerzintelligence.mygarages.data.car;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CarResource {
    private int id;
    private String brandAndName;
    private String horsePower;
}
