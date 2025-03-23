package geolocation.model;

import jakarta.annotation.Nullable;
import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@ToString
public class SearchLocationDto {

    @Nullable
    private Point p1;

    @Nullable
    private Point p2;

    @Nullable
    private LocationTypeEnum type;

    @Min(0)
    @Nullable
    private Integer limit;

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Point {

        @DecimalMin(value = "-90")
        @DecimalMax(value = "90")
        @Nullable
        private Double lat;

        @DecimalMin(value = "-180")
        @DecimalMax(value = "180")
        @Nullable
        private Double lng;
    }
}
