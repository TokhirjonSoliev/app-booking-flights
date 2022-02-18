package uz.train.appbookingflights.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.train.appbookingflights.exception.ConflictException;
import uz.train.appbookingflights.exception.NotFoundException;
import uz.train.appbookingflights.mapper.SeatMapper;
import uz.train.appbookingflights.model.SeatEntity;
import uz.train.appbookingflights.model.WagonEntity;
import uz.train.appbookingflights.repository.SeatRepository;
import uz.train.appbookingflights.repository.WagonRepository;
import uz.train.modelappbookingflights.Dto.SeatDto;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final WagonRepository wagonRepository;
    private final SeatMapper seatMapper;

    public List<SeatDto> getSeats(Integer wagonId){
        List<SeatDto> seatDtos = new ArrayList<>();
        WagonEntity wagon = wagonRepository.findById(wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon", WagonEntity.class, "wagonId"));

        List<SeatEntity> seats = seatRepository.findAllByWagonEntity_WagonNumber(wagon.getWagonNumber());
        seats.forEach(seat -> {
            SeatDto seatDto = seatMapper.entityToResponseDTO(seat);
            seatDtos.add(seatDto);
        });
        return seatDtos;
    }

    public SeatDto getSeat(Integer wagonId, Integer seatId){
        SeatEntity seat = seatRepository.findByIdAndWagonEntity_Id(seatId, wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a seat in given wagon", SeatEntity.class, "seatId"));

        return seatMapper.entityToResponseDTO(seat);
    }

    public void addSeat(Integer wagonId, SeatDto seatDto){
        WagonEntity wagon = wagonRepository.findById(wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon", WagonEntity.class, "wagonId"));

        boolean existsSeat = seatRepository.existsBySeatNumberAndWagonEntity_Id(seatDto.getSeatNumber(), wagonId);
        if (existsSeat){
            throw new ConflictException("This seat number is already in use", SeatEntity.class, "seatNumber and wagonNumber");
        }

        SeatEntity seatEntity = seatMapper.CreateDtoToEntity(seatDto);
        seatEntity.setWagonEntity(wagon);
        seatRepository.save(seatEntity);
    }

    public SeatDto editSeat(Integer wagonId, Integer seatId, SeatDto seatDto){
        WagonEntity wagon = wagonRepository.findById(wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon", WagonEntity.class, "wagonId"));

        SeatEntity seat = seatRepository.findByIdAndWagonEntity_Id(seatId, wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a seat", SeatEntity.class, "seatId"));

        seatMapper.updateEntity(seatDto, seat);
        seat.setWagonEntity(wagon);
        seatRepository.save(seat);
        return seatMapper.entityToResponseDTO(seat);
    }

    public void deleteSeat(Integer wagonId, Integer seatId){
        SeatEntity seat = seatRepository.findByIdAndWagonEntity_Id(seatId, wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a seat", SeatEntity.class, "seatId"));

        seatRepository.delete(seat);
    }
}
