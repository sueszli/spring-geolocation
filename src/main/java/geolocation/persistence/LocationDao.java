package geolocation.persistence;

import geolocation.model.Location;
import geolocation.model.SearchLocationDto;

import java.util.List;

public interface LocationDao {
    List<Location> searchLocations(SearchLocationDto query);
}
