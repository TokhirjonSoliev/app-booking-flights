package uz.train.appbookingflights.conrtoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.train.appbookingflights.service.StationsService;
import uz.train.modelappbookingflights.Dto.createDto.StationsCreateDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stations")
public class StationController {
    private final StationsService stationsService;

    @GetMapping("/{city_id}")
    public ResponseEntity<?> getUsers(@PathVariable Integer city_id) {
        return ResponseEntity.ok(stationsService.getStations(city_id));
    }

    @GetMapping("/{city_id}/{station_id}")
    public ResponseEntity<?> getUser(@PathVariable Integer city_id, @PathVariable Integer station_id) {
        return ResponseEntity.ok(stationsService.getStation(city_id, station_id));
    }

    @PostMapping("/{city_id}")
    public ResponseEntity<?> addUser(@PathVariable Integer city_id, @RequestBody StationsCreateDto stationsCreateDto) {
        stationsService.addStation(city_id, stationsCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{city_id}/{station_id}")
    public ResponseEntity<?> editUser (@PathVariable Integer city_id, @PathVariable Integer station_id, @RequestBody StationsCreateDto stationsCreateDto) {
        return ResponseEntity.ok(stationsService.editStation(city_id, station_id,stationsCreateDto));
    }

    @DeleteMapping("/{city_id}/{station_id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer city_id, @PathVariable Integer station_id) {
        stationsService.deleteStation(city_id, station_id);
        return ResponseEntity.ok().build();
    }

}

