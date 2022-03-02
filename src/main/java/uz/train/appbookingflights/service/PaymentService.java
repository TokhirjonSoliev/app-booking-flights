package uz.train.appbookingflights.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.train.appbookingflights.exception.ConflictException;
import uz.train.appbookingflights.exception.NotFoundException;
import uz.train.appbookingflights.mapper.PaymentMapper;
import uz.train.appbookingflights.model.PaymentEntity;
import uz.train.appbookingflights.repository.PaymentRepository;
import uz.train.modelappbookingflights.Dto.createDto.PaymentCreateDto;
import uz.train.modelappbookingflights.Dto.responseDto.PaymentResponseDto;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;

    public List<PaymentResponseDto> getPayments() {
        List<PaymentResponseDto> dtoList = new ArrayList<>();
        List<PaymentEntity> paymentRepositoryAll = paymentRepository.findAll();
        for (PaymentEntity paymentEntity : paymentRepositoryAll) {
            dtoList.add(paymentMapper.entityToResponseDto(paymentEntity));
        }
        return dtoList;
    }

    public PaymentResponseDto getPayment(Integer id) {
        PaymentEntity paymentEntity =  paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found",PaymentEntity.class,"id"));
        return paymentMapper.entityToResponseDto(paymentEntity);
    }

    public void addPayment(PaymentCreateDto paymentCreateDto) {
        boolean exists = paymentRepository.existsByName(paymentCreateDto.getName());
        if (exists) {
            throw new ConflictException("This payment already exist",PaymentEntity.class,"name");
        }
        PaymentEntity paymentEntity = paymentMapper.createDtoToEntity(paymentCreateDto);
        paymentRepository.save(paymentEntity);
    }

    public PaymentResponseDto editPayment (Integer id,PaymentCreateDto paymentCreateDto) {
        PaymentEntity paymentEntity =  paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found",PaymentEntity.class,"id"));

        paymentMapper.updateEntity(paymentCreateDto,paymentEntity);
        return paymentMapper.entityToResponseDto(paymentEntity);
    }

    public void deletePayment (Integer id) {
        PaymentEntity paymentEntity =  paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found",PaymentEntity.class,"id"));

        paymentRepository.delete(paymentEntity);
    }

}
