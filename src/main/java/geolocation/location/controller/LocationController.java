package geolocation.location.controller;

import geolocation.location.model.dto.LocationCreateDto;
import geolocation.location.model.dto.LocationSearchDto;
import geolocation.location.model.entity.Location;
import geolocation.location.persistence.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class LocationController {

    @Autowired
    LocationRepository locationRepository;

    @PostMapping("/locations")
    public ResponseEntity<Location> createLocation(@RequestBody LocationCreateDto dto) {
        try {
            log.info("Creating location with dto: {}", dto);
            Location entity = new Location();
            entity.setName(dto.getName());
            entity.setLat(dto.getLat());
            entity.setLng(dto.getLng());
            entity.setType(dto.getType());
            log.info("Created location with entity: {}", entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(locationRepository.save(entity));
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> searchLocations(@RequestBody LocationSearchDto query) {
        log.info("Searching locations with query: {}", query);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
