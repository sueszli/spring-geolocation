package geolocation.controller;

import geolocation.model.CreateLocationDto;
import geolocation.model.Location;
import geolocation.model.LocationMapper;
import geolocation.model.SearchLocationDto;
import geolocation.persistence.LocationDao;
import geolocation.persistence.LocationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * In practice, you would have a dedicated ExceptionHandler class or at least use "try-catch" blocks to hide
 * system details from the client and wrap them in a more generic error message like "HttpStatus.INTERNAL_SERVER_ERROR".
 */

@Slf4j
@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocationController {

    private final LocationMapper locationMapper;
    private final LocationJpaRepository locationJpaRepository;
    private final LocationDao locationDao;

    @PostMapping("/locations")
    public ResponseEntity<Location> createLocation(@RequestBody CreateLocationDto dto) {
        log.info("Creating location: {}", dto);
        var entity = locationMapper.dtoToEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(locationJpaRepository.save(entity));
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> searchLocations(@RequestBody(required = false) SearchLocationDto query) {
        log.info("Searching locations: {}", query);
        return ResponseEntity.status(HttpStatus.OK).body(locationDao.searchLocations(query));
    }
}
