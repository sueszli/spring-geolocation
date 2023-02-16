package geolocation.controller;

import geolocation.model.LocationMapper;
import geolocation.model.CreateLocationDto;
import geolocation.model.SearchLocationDto;
import geolocation.model.Location;
import geolocation.persistence.LocationDao;
import geolocation.persistence.LocationJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class LocationController {

    LocationMapper locationMapper;
    LocationJpaRepository locationJpaRepository;
    LocationDao locationDao;

    @PostMapping("/locations")
    public ResponseEntity<Location> createLocation(@RequestBody CreateLocationDto dto) {
        log.info("Creating location: {}", dto);
        var entity = locationMapper.dtoToEntity(dto);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(locationJpaRepository.save(entity));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> searchLocations(@RequestBody SearchLocationDto query) {
        log.info("Searching locations: {}", query);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(locationDao.searchLocations(query));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
