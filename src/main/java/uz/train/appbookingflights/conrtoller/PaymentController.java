package uz.train.appbookingflights.conrtoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.train.appbookingflights.model.PaymentEntity;
import uz.train.appbookingflights.service.PassengerService;
import uz.train.appbookingflights.service.PaymentService;
import uz.train.modelappbookingflights.Dto.createDto.PaymentCreateDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<?> getPayments () {
        return ResponseEntity.ok(paymentService.getPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPayment (@PathVariable Integer id) {
        return ResponseEntity.ok(paymentService.getPayment(id));
    }

    @PostMapping
    public ResponseEntity<?> addPayment (@RequestBody PaymentCreateDto paymentCreateDto) {
        paymentService.addPayment(paymentCreateDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editPayment (@PathVariable Integer id, @RequestBody PaymentCreateDto paymentCreateDto) {
        return ResponseEntity.ok(paymentService.editPayment(id,paymentCreateDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePayment (@PathVariable Integer id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok().build();
    }
    
}
