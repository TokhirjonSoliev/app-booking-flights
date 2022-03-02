package uz.train.appbookingflights.conrtoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.train.appbookingflights.service.TrainsService;
import uz.train.modelappbookingflights.Dto.createDto.TrainCreateDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trains")
public class TrainController {
    private final TrainsService trainsService;

    @GetMapping
    public ResponseEntity<?> getTrains() {
        return ResponseEntity.ok(trainsService.getTrains());
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableTrains() {
        return ResponseEntity.ok(trainsService.getAvailableTrains());
    }

    @GetMapping("/{train_id}")
    public ResponseEntity<?> getUser(@PathVariable Integer train_id) {
        return ResponseEntity.ok(trainsService.getTrain(train_id));
    }

    @PostMapping
    public ResponseEntity<?> addTrain(@RequestBody TrainCreateDto trainCreateDto) {
        trainsService.addTrain(trainCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{train_id}")
    public ResponseEntity<?> editTrain(@PathVariable Integer train_id, @RequestBody TrainCreateDto trainCreateDto) {
        return ResponseEntity.ok(trainsService.editTrain(train_id,trainCreateDto));
    }

    @GetMapping("/delete/{train_number}")
    public ResponseEntity<?> deleteTrain(@PathVariable String train_number) {
        return ResponseEntity.ok().body(trainsService.deleteTrain(train_number));
    }

}

