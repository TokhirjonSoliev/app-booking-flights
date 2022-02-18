package uz.train.appbookingflights.conrtoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.train.appbookingflights.service.SeatService;
import uz.train.modelappbookingflights.Dto.SeatDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/{wagon_id}/seats")
public class SeatController {
    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<?> getSeats(@PathVariable Integer wagon_id){
        return ResponseEntity.ok(seatService.getSeats(wagon_id));
    }

    @GetMapping("/{seat_id}")
    public ResponseEntity<?> getSeat(@PathVariable Integer wagon_id, @PathVariable Integer seat_id){
        return ResponseEntity.ok(seatService.getSeat(wagon_id, seat_id));
    }

    @PostMapping
    public ResponseEntity<?> addSeat(@PathVariable Integer wagon_id, @RequestBody SeatDto seatDto){
        seatService.addSeat(wagon_id, seatDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{seat_id}")
    public ResponseEntity<?> editSeat(@PathVariable Integer wagon_id, @PathVariable Integer seat_id, @RequestBody SeatDto seatDto){
        return ResponseEntity.ok(seatService.editSeat(wagon_id, seat_id, seatDto));
    }

    @DeleteMapping("/{seat_id}")
    public ResponseEntity<?> deleteSeat(@PathVariable Integer wagon_id, @PathVariable Integer seat_id){
        seatService.deleteSeat(wagon_id, seat_id);
        return ResponseEntity.ok().build();
    }
}
