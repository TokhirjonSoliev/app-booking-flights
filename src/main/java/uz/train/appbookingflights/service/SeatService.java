package uz.train.appbookingflights.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.train.appbookingflights.exception.ConflictException;
import uz.train.appbookingflights.exception.NotFoundException;
import uz.train.appbookingflights.mapper.SeatMapper;
import uz.train.appbookingflights.model.SeatEntity;
import uz.train.appbookingflights.model.TrainEntity;
import uz.train.appbookingflights.model.WagonEntity;
import uz.train.appbookingflights.repository.SeatRepository;
import uz.train.appbookingflights.repository.TrainRepository;
import uz.train.appbookingflights.repository.WagonRepository;
import uz.train.modelappbookingflights.Dto.createDto.SeatCreateDto;
import uz.train.modelappbookingflights.Dto.responseDto.SeatResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final WagonRepository wagonRepository;
    private final TrainRepository trainRepository;
    private final SeatMapper seatMapper;

    public List<SeatResponseDto> getSeats(Integer wagonId) {
        List<SeatResponseDto> seatResponseDtos = new ArrayList<>();
        WagonEntity wagon = wagonRepository.findById(wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon", WagonEntity.class, "wagonId"));

        TrainEntity train = trainRepository.findById(wagon.getTrainEntity().getId())
                .orElseThrow(() -> new NotFoundException("There is no such a train", WagonEntity.class, "trainId"));

        List<SeatEntity> seats = seatRepository.findAllByWagonEntity_WagonNumber(wagon.getWagonNumber());
        seats.forEach(seat -> {
            SeatResponseDto seatResponseDto = seatMapper.entityToResponseDTO(seat);
            seatResponseDto.setWagonNumber(wagon.getWagonNumber());
            seatResponseDto.setTrainName(train.getName());
            seatResponseDto.setTrainNumber(train.getTrainNumber());
            seatResponseDtos.add(seatResponseDto);
        });
        return seatResponseDtos;
    }

    public List<SeatEntity> getAvailableSeats(Integer wagonId){
        List<SeatEntity> seatEntities = new ArrayList<>();
        WagonEntity wagon = wagonRepository.findById(wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon", WagonEntity.class, "wagonId"));

        List<SeatEntity> seats = seatRepository.findAllByWagonEntity_WagonNumber(wagon.getWagonNumber());
        seats.forEach(seat -> {
            if (seat.isStatus()) {
                seatEntities.add(seat);
            }
        });
        return seatEntities;
    }

    public SeatResponseDto getSeat(Integer wagonId, Integer seatId) {
        WagonEntity wagon = wagonRepository.findById(wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon", WagonEntity.class, "wagonId"));

        TrainEntity train = trainRepository.findById(wagon.getTrainEntity().getId())
                .orElseThrow(() -> new NotFoundException("There is no such a train", WagonEntity.class, "trainId"));

        SeatEntity seat = seatRepository.findByIdAndWagonEntity_Id(seatId, wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a seat in given wagon", SeatEntity.class, "seatId"));

        SeatResponseDto seatResponseDto = seatMapper.entityToResponseDTO(seat);
        seatResponseDto.setWagonNumber(wagon.getWagonNumber());
        seatResponseDto.setTrainName(train.getName());
        seatResponseDto.setTrainNumber(train.getTrainNumber());
        return seatResponseDto;
    }

    public void addSeat(Integer wagonId, SeatCreateDto seatCreateDto) {
        WagonEntity wagon = wagonRepository.findById(wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon", WagonEntity.class, "wagonId"));

        boolean existsSeat = seatRepository.existsBySeatNumberAndWagonEntity_Id(seatCreateDto.getSeatNumber(), wagonId);
        if (existsSeat) {
            throw new ConflictException("This seat number is already in use", SeatEntity.class, "seatNumber and wagonNumber");
        }

        if (wagon.getCountOfSeats() >= wagon.getMaxSeats())
            throw new ConflictException("you can not add any seats to this wagon", WagonEntity.class, "maxSeats");

        SeatEntity seatEntity = seatMapper.CreateDtoToEntity(seatCreateDto);
        wagon.setCountOfSeats(wagon.getCountOfSeats()+1);
        seatEntity.setWagonEntity(wagon);
        seatRepository.save(seatEntity);
    }

    public SeatResponseDto editSeat(Integer wagonId, Integer seatId, SeatCreateDto seatCreateDto) {
        WagonEntity wagon = wagonRepository.findById(wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon", WagonEntity.class, "wagonId"));

        SeatEntity seat = seatRepository.findByIdAndWagonEntity_Id(seatId, wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a seat", SeatEntity.class, "seatId"));

        TrainEntity train = trainRepository.findById(wagon.getTrainEntity().getId())
                .orElseThrow(() -> new NotFoundException("There is no such a train", WagonEntity.class, "trainId"));

        seatMapper.updateEntity(seatCreateDto, seat);
        seat.setWagonEntity(wagon);
        seatRepository.save(seat);
        SeatResponseDto seatResponseDto = seatMapper.entityToResponseDTO(seat);
        seatResponseDto.setWagonNumber(wagon.getWagonNumber());
        seatResponseDto.setTrainName(train.getName());
        seatResponseDto.setTrainNumber(train.getTrainNumber());
        return seatResponseDto;
    }

    public void deleteSeat(Integer wagonId, Integer seatId) {
        SeatEntity seat = seatRepository.findByIdAndWagonEntity_Id(seatId, wagonId)
                .orElseThrow(() -> new NotFoundException("There is no such a seat", SeatEntity.class, "seatId"));

        seatRepository.delete(seat);
    }
}
