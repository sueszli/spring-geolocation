package geolocation.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

import static geolocation.model.Location.MAX_NAME_LENGTH;

@Getter
@Setter
@Builder
@ToString
public class CreateLocationDto {

    @NotBlank
    @Size(min = 2, max = MAX_NAME_LENGTH)
    private String name;

    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    private Double lat;

    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    private Double lng;

    @NotNull
    private LocationTypeEnum type;
}