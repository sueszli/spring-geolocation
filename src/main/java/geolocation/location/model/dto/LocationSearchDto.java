package geolocation.location.model.dto;

import geolocation.location.model.LocationTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class LocationSearchDto {

    @Min(-90)
    @Max(90)
    private double latP1;

    @Min(-180)
    @Max(180)
    private double lngP1;

    @Min(-90)
    @Max(90)
    private double latP2;

    @Min(-180)
    @Max(180)
    private double lngP2;

    @NotNull
    private LocationTypeEnum type;

    @Max(0)
    private double limit;
}
