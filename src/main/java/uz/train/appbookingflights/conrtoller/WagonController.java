package uz.train.appbookingflights.conrtoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.train.appbookingflights.service.WagonService;
import uz.train.modelappbookingflights.Dto.WagonDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trains/{train_id}/wagons")
public class WagonController {
    private final WagonService wagonService;

    @GetMapping
    public ResponseEntity<?> getWagons(@PathVariable Integer train_id){
        return ResponseEntity.ok(wagonService.getWagons(train_id));
    }

    @GetMapping("/{wagon_id}")
    public ResponseEntity<?> getWagon(@PathVariable Integer train_id, @PathVariable Integer wagon_id){
        return ResponseEntity.ok(wagonService.getWagon(train_id, wagon_id));
    }

    @PostMapping
    public ResponseEntity<?> addWagon(@PathVariable Integer train_id, @RequestBody WagonDto wagonDto){
        wagonService.addWagon(train_id, wagonDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{wagon_id}")
    public ResponseEntity<?> editWagon(@PathVariable Integer train_id, @PathVariable Integer wagon_id, @RequestBody WagonDto wagonDto){
        return ResponseEntity.ok(wagonService.editWagon(train_id, wagon_id, wagonDto));
    }

    @DeleteMapping("/{wagon_id}")
    public ResponseEntity<?> deleteWagon(@PathVariable Integer train_id, @PathVariable Integer wagon_id){
        wagonService.deleteWagon(train_id, wagon_id);
        return ResponseEntity.ok().build();
    }
}
