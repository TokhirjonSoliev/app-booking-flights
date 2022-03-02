package uz.train.appbookingflights.conrtoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.train.appbookingflights.service.SeatService;
import uz.train.modelappbookingflights.Dto.createDto.SeatCreateDto;

@RestController
@RequestMapping("/wagons/{wagon_id}/seats")
public class SeatController {
    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public ResponseEntity<?> getSeats(@PathVariable Integer wagon_id){
        return ResponseEntity.ok(seatService.getSeats(wagon_id));
    }

    @GetMapping("/{seat_id}")
    public ResponseEntity<?> getSeat(@PathVariable Integer wagon_id, @PathVariable Integer seat_id){
        return ResponseEntity.ok(seatService.getSeat(wagon_id, seat_id));
    }

    @PostMapping
    public ResponseEntity<?> addSeat(@PathVariable Integer wagon_id, @RequestBody SeatCreateDto seatCreateDto){
        seatService.addSeat(wagon_id, seatCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{seat_id}")
    public ResponseEntity<?> editSeat(@PathVariable Integer wagon_id, @PathVariable Integer seat_id, @RequestBody SeatCreateDto seatCreateDto){
        return ResponseEntity.ok(seatService.editSeat(wagon_id, seat_id, seatCreateDto));
    }

    @DeleteMapping("/{seat_id}")
    public ResponseEntity<?> deleteSeat(
            @PathVariable Integer wagon_id,
            @PathVariable Integer seat_id){
        seatService.deleteSeat(wagon_id, seat_id);
        return ResponseEntity.ok().build();
    }
}
