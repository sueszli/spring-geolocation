package geolocation.location.model.dto;

import geolocation.location.model.LocationTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

import static geolocation.location.model.entity.Location.MAX_NAME_LENGTH;

@Getter
@Setter
@ToString
public class CreateLocationDto {

    @NotBlank
    @Size(min = 2, max = MAX_NAME_LENGTH)
    private String name;

    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private Double lat;

    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private Double lng;

    @NotNull
    private LocationTypeEnum type;
}