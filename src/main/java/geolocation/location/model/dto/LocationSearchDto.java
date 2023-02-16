package geolocation.location.model.dto;

import geolocation.location.model.LocationTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class LocationSearchDto {

    @NotNull
    private Point p1;

    @NotNull
    private Point p2;

    private LocationTypeEnum type;

    @Min(0)
    private int limit;

    @Getter
    @Setter
    @ToString
    public class Point {
        @Size(min = -90, max = 90, message = "latitude must be between -90 and 90")
        private double lat;

        @Size(min = -180, max = 180, message = "longitude must be between -180 and 180")
        private double lng;
    }
}
