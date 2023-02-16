package geolocation.location.persistence;

import geolocation.location.model.dto.LocationSearchDto;
import geolocation.location.model.entity.Location;

import java.util.List;

public interface LocationDao {
    List<Location> searchLocations(LocationSearchDto query);
}
