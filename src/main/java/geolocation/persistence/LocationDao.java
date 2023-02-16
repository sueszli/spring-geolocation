package geolocation.persistence;

import geolocation.model.SearchLocationDto;
import geolocation.model.Location;

import java.util.List;

public interface LocationDao {
    List<Location> searchLocations(SearchLocationDto query);
}
