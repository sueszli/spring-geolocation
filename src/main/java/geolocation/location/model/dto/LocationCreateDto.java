package geolocation.location.model.dto;

import geolocation.location.model.LocationTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class LocationCreateDto {

    @NotNull
    @NotBlank
    private String name;

    @Min(-90)
    @Max(90)
    private double lat;

    @Min(-180)
    @Max(180)
    private double lng;

    @NotNull
    private LocationTypeEnum type;
}