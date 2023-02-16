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

    class Point {
        @Min(-90)
        @Max(90)
        private double lat;

        @Min(-180)
        @Max(180)
        private double lng;
    }

    @NotNull
    private Point p1;

    @NotNull
    private Point p2;

    @NotNull
    private LocationTypeEnum type;

    @Max(0)
    private double limit;
}
