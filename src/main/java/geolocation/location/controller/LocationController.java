package geolocation.location.controller;

import geolocation.location.model.dto.LocationSearchDto;
import geolocation.location.model.entity.Location;
import geolocation.location.model.dto.LocationCreateDto;
import geolocation.location.persistence.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class LocationController {

    @Autowired
    LocationRepository locationRepository;

    @PostMapping("/locations")
    public ResponseEntity<Location> createLocation(@RequestBody LocationCreateDto location) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> searchLocations(@RequestBody LocationSearchDto) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
