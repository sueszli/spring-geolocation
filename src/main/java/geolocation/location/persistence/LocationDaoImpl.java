package geolocation.location.persistence;

import geolocation.location.model.dto.LocationSearchDto;
import geolocation.location.model.entity.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LocationDaoImpl implements LocationDao {

    private final LocationJpaRepository locationJpaRepository;

    @Override
    public List<Location> searchLocations(LocationSearchDto query) {
        return null;
    }
}
