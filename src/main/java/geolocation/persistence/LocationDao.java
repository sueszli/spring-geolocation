package geolocation.persistence;

import geolocation.model.dto.SearchLocationDto;
import geolocation.model.entity.Location;

import java.util.List;

public interface LocationDao {
    List<Location> searchLocations(SearchLocationDto query);
}
