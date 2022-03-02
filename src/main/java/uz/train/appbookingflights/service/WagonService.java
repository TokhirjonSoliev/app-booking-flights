package uz.train.appbookingflights.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.train.appbookingflights.exception.ConflictException;
import uz.train.appbookingflights.exception.NotFoundException;
import uz.train.appbookingflights.mapper.WagonMapper;
import uz.train.appbookingflights.model.SeatEntity;
import uz.train.appbookingflights.model.TrainEntity;
import uz.train.appbookingflights.model.WagonEntity;
import uz.train.appbookingflights.repository.TrainRepository;
import uz.train.appbookingflights.repository.WagonRepository;
import uz.train.modelappbookingflights.Dto.createDto.WagonCreateDto;
import uz.train.modelappbookingflights.Dto.responseDto.WagonResponseDto;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WagonService {
    private final WagonRepository wagonRepository;
    private final TrainRepository trainRepository;
    private final WagonMapper wagonMapper;
    private final SeatService seatService;

    public List<WagonResponseDto> getWagons(Integer trainId) {
        List<WagonResponseDto> wagonResponseDtos = new ArrayList<>();
        TrainEntity train = trainRepository.findById(trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a train", TrainEntity.class, "trainId"));

        List<WagonEntity> wagons = wagonRepository.findAllByTrainEntity_TrainNumber(train.getTrainNumber());
        wagons.forEach(wagon -> {
            WagonResponseDto wagonResponseDto = wagonMapper.entityToResponseDTO(wagon);
            wagonResponseDto.setTrainNumber(train.getTrainNumber());
            wagonResponseDto.setTrainName(train.getName());
            wagonResponseDtos.add(wagonResponseDto);
        });
        return wagonResponseDtos;
    }

    public List<WagonEntity> getAvailableWagons(Integer trainId) {
        List<WagonEntity> wagonEntities = new ArrayList<>();
        TrainEntity train = trainRepository.findById(trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a train", TrainEntity.class, "trainId"));

        List<WagonEntity> wagons = wagonRepository.findAllByTrainEntity_TrainNumber(train.getTrainNumber());
        wagons.forEach(wagon -> {
            List<SeatEntity> availableSeats = seatService.getAvailableSeats(wagon.getId());
            if (availableSeats.size() > 0) {
                wagon.setSeatEntities(availableSeats);
                wagonEntities.add(wagon);
            }
        });
        return wagonEntities;
    }

    public WagonResponseDto getWagon(Integer trainId, Integer wagonId) {
        TrainEntity train = trainRepository.findById(trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a train", TrainEntity.class, "trainId"));

        WagonEntity wagon = wagonRepository.findByIdAndTrainEntity_Id(wagonId, trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon in given train", WagonEntity.class, "wagonId and trainId"));

        WagonResponseDto wagonResponseDto = wagonMapper.entityToResponseDTO(wagon);
        wagonResponseDto.setTrainNumber(train.getTrainNumber());
        wagonResponseDto.setTrainName(train.getName());
        return wagonResponseDto;
    }

    public void addWagon(Integer trainId, WagonCreateDto wagonCreateDto) {
        TrainEntity train = trainRepository.findById(trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a train", TrainEntity.class, "trainId"));

        boolean existsWagon = wagonRepository.existsByWagonNumberAndTrainEntity_Id(wagonCreateDto.getWagonNumber(), trainId);
        if (existsWagon) {
            throw new ConflictException("This wagon number is already in use", WagonEntity.class, "wagonNumber and trainId");
        }

        if (train.getCountOfWagons() >= train.getMaxWagons())
            throw new ConflictException("you can not add any wagons to this train", TrainEntity.class, "maxWagons");

        WagonEntity wagon = wagonMapper.createDtoToEntity(wagonCreateDto);
        train.setCountOfWagons(train.getCountOfWagons() + 1);
        wagon.setTrainEntity(train);
        wagonRepository.save(wagon);
    }

    public WagonResponseDto editWagon(Integer trainId, Integer wagonId, WagonCreateDto wagonCreateDto) {
        TrainEntity train = trainRepository.findById(trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a train", TrainEntity.class, "trainId"));

        WagonEntity wagon = wagonRepository.findByIdAndTrainEntity_Id(wagonId, trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon", WagonEntity.class, "wagonId and trainId"));

        wagonMapper.updateEntity(wagonCreateDto, wagon);
        wagon.setTrainEntity(train);
        wagonRepository.save(wagon);
        WagonResponseDto wagonResponseDto = wagonMapper.entityToResponseDTO(wagon);
        wagonResponseDto.setTrainNumber(train.getTrainNumber());
        wagonResponseDto.setTrainName(train.getName());
        return wagonResponseDto;
    }

    public void deleteWagon(Integer trainId, Integer wagonId) {
        WagonEntity wagon = wagonRepository.findByIdAndTrainEntity_Id(wagonId, trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon", WagonEntity.class, "wagonId and trainId"));

        wagonRepository.delete(wagon);
    }
}
