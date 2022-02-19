package uz.train.appbookingflights.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.train.appbookingflights.exception.ConflictException;
import uz.train.appbookingflights.exception.NotFoundException;
import uz.train.appbookingflights.mapper.WagonMapper;
import uz.train.appbookingflights.model.TrainEntity;
import uz.train.appbookingflights.model.WagonEntity;
import uz.train.appbookingflights.repository.TrainRepository;
import uz.train.appbookingflights.repository.WagonRepository;
import uz.train.modelappbookingflights.Dto.WagonDto;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WagonService {
    private final WagonRepository wagonRepository;
    private final TrainRepository trainRepository;
    private final WagonMapper wagonMapper;

    public List<WagonDto> getWagons(Integer trainId) {
        List<WagonDto> wagonDtos = new ArrayList<>();
        TrainEntity train = trainRepository.findById(trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a train", TrainEntity.class, "trainId"));

        List<WagonEntity> wagons = wagonRepository.findAllByTrainEntity_TrainNumber(train.getTrainNumber());
        wagons.forEach(wagon -> {
            WagonDto wagonDto = wagonMapper.entityToResponseDTO(wagon);
            wagonDtos.add(wagonDto);
        });
        return wagonDtos;
    }

    public WagonDto getWagon(Integer trainId, Integer wagonId) {
        WagonEntity wagon = wagonRepository.findByIdAndTrainEntity_Id(wagonId, trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon in given train", WagonEntity.class, "wagonId and trainId"));

        return wagonMapper.entityToResponseDTO(wagon);
    }

    public void addWagon(Integer trainId, WagonDto wagonDto) {
        TrainEntity train = trainRepository.findById(trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a train", TrainEntity.class, "trainId"));

        boolean existsWagon = wagonRepository.existsByWagonNumberAndTrainEntity_Id(wagonDto.getWagonNumber(), trainId);
        if (existsWagon) {
            throw new ConflictException("This wagon number is already in use", WagonEntity.class, "wagonNumber and trainId");
        }

        WagonEntity wagon = wagonMapper.CreateDtoToEntity(wagonDto);
        wagon.setTrainEntity(train);
        wagonRepository.save(wagon);
    }

    public WagonDto editWagon(Integer trainId, Integer wagonId, WagonDto wagonDto) {
        TrainEntity train = trainRepository.findById(trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a train", TrainEntity.class, "trainId"));

        WagonEntity wagon = wagonRepository.findByIdAndTrainEntity_Id(wagonId, trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon", WagonEntity.class, "wagonId and trainId"));

        wagonMapper.updateEntity(wagonDto, wagon);
        wagon.setTrainEntity(train);
        wagonRepository.save(wagon);
        return wagonMapper.entityToResponseDTO(wagon);
    }

    public void deleteWagon(Integer trainId, Integer wagonId) {
        WagonEntity wagon = wagonRepository.findByIdAndTrainEntity_Id(wagonId, trainId)
                .orElseThrow(() -> new NotFoundException("There is no such a wagon", WagonEntity.class, "wagonId and trainId"));

        wagonRepository.delete(wagon);
    }
}
