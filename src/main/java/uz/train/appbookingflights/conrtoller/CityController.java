package uz.train.appbookingflights.conrtoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.train.appbookingflights.service.CityService;
import uz.train.modelappbookingflights.Dto.CityDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<?> getCities () {
        return ResponseEntity.ok(cityService.getCities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCityById (@PathVariable Integer id) {
        return ResponseEntity.ok(cityService.getCity(id));
    }

    @PostMapping
    public ResponseEntity<?> addCity (@RequestBody CityDto cityDto) {
        cityService.addCity(cityDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCity(@PathVariable Integer id, @RequestBody CityDto cityDto) {
        return ResponseEntity.ok(cityService.editCity(id,cityDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Integer id) {
        cityService.deleteCity(id);
        return ResponseEntity.ok().build();
    }


}
