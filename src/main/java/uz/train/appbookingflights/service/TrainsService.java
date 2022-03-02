package uz.train.appbookingflights.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.train.appbookingflights.exception.NotFoundException;
import uz.train.appbookingflights.mapper.TrainMapper;
import uz.train.appbookingflights.model.StationEntity;
import uz.train.appbookingflights.model.TrainEntity;
import uz.train.appbookingflights.model.WagonEntity;
import uz.train.appbookingflights.repository.StationRepository;
import uz.train.appbookingflights.repository.TrainRepository;
import uz.train.modelappbookingflights.Dto.createDto.TrainCreateDto;
import uz.train.modelappbookingflights.Dto.responseDto.TrainResponseDto;
import uz.train.modelappbookingflights.reponse.ApiResponse;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainsService {
    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;
    private final TrainMapper trainMapper;
    private final WagonService wagonService;

    public ApiResponse getTrains() {
        List<TrainEntity> trainEntities = trainRepository.findAll();
        List<TrainResponseDto> trainResponseDtos = new ArrayList<>();

        trainEntities.forEach(trainEntity -> {
            List<WagonEntity> wagonEntities = trainEntity.getWagonEntities();


            TrainResponseDto trainResponseDto = trainMapper.entityToResponseDTO(trainEntity);
            trainResponseDto.setFromCity(trainEntity.getFromStation().getCity().getCityName());
            trainResponseDto.setFromStation(trainEntity.getFromStation().getName());
            trainResponseDto.setToCity(trainEntity.getToStation().getCity().getCityName());
            trainResponseDto.setToStation(trainEntity.getToStation().getName());
            List<StationEntity> includeStations = trainEntity.getIncludeStations();
            List<String> stations = new ArrayList<>();
            includeStations.forEach(stationEntity -> {
                stations.add(stationEntity.getName());
            });
            trainResponseDto.setIncludeStations(stations);
            trainResponseDtos.add(trainResponseDto);
        });
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(trainResponseDtos);
        apiResponse.setMessage("trains");
        apiResponse.setStatusCode(200);
        return apiResponse;
    }

    public ApiResponse getAvailableTrains() {
        List<TrainResponseDto> trainResponseDtos = new ArrayList<>();
        List<TrainEntity> trainEntities = trainRepository.findAll();
        trainEntities.forEach(trainEntity -> {
            if (trainEntity.isTrainStatus()) {
                TrainResponseDto trainResponseDto = trainMapper.entityToResponseDTO(trainEntity);
                trainResponseDto.setFromCity(trainEntity.getFromStation().getCity().getCityName());
                trainResponseDto.setFromStation(trainEntity.getFromStation().getName());
                trainResponseDto.setToCity(trainEntity.getToStation().getCity().getCityName());
                trainResponseDto.setToStation(trainEntity.getToStation().getName());
                List<StationEntity> includeStations = trainEntity.getIncludeStations();
                List<String> stations = new ArrayList<>();
                includeStations.forEach(stationEntity -> {
                    stations.add(stationEntity.getName());
                });
                trainResponseDto.setIncludeStations(stations);
                trainResponseDtos.add(trainResponseDto);
            }
        });
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(trainResponseDtos);
        apiResponse.setMessage("trains");
        apiResponse.setStatusCode(200);
        return apiResponse;
    }

    public ApiResponse getTrain(Integer trainId) {
        TrainEntity train = trainRepository.findById(trainId)
                .orElseThrow(() -> new NotFoundException("unable to find train by given trainId", TrainEntity.class, "trainId"));

        TrainResponseDto trainResponseDto = trainMapper.entityToResponseDTO(train);
        trainResponseDto.setFromCity(train.getFromStation().getCity().getCityName());
        trainResponseDto.setFromStation(train.getFromStation().getName());
        trainResponseDto.setToCity(train.getToStation().getCity().getCityName());
        trainResponseDto.setToStation(train.getToStation().getName());
        List<StationEntity> includeStations = train.getIncludeStations();
        List<String> stations = new ArrayList<>();
        includeStations.forEach(stationEntity -> {
            stations.add(stationEntity.getName());
        });
        trainResponseDto.setIncludeStations(stations);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(trainResponseDto);
        apiResponse.setMessage("train");
        apiResponse.setStatusCode(200);
        return apiResponse;
    }

    public void addTrain(TrainCreateDto trainCreateDto) {
        StationEntity fromStation = stationRepository.findById(trainCreateDto.getFromStationId())
                .orElseThrow(() -> new NotFoundException("unable to find stationEntity by given fromStationId", StationEntity.class, "fromStationId"));

        StationEntity toStation = stationRepository.findById(trainCreateDto.getToStationId())
                .orElseThrow(() -> new NotFoundException("unable to find stationEntity by given toStationId", StationEntity.class, "toStationId"));

        boolean exists = trainRepository.existsByNameAndTrainNumber(trainCreateDto.getName(), trainCreateDto.getTrainNumber());
        if (exists)
            throw new NotFoundException("This trainNumber is already in use in the given TrainCreateDto", TrainEntity.class, "name and trainNumber");

        List<StationEntity> stations = new ArrayList<>();
        List<Integer> includeStations = trainCreateDto.getIncludeStations();
        includeStations.forEach(id -> {
            StationEntity station = stationRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("unable to find stationEntity by given stationId", StationEntity.class, "stationId"));
            stations.add(station);
        });
        TrainEntity train = new TrainEntity();
        train.setFromStation(fromStation);
        train.setToStation(toStation);
        train.setName(trainCreateDto.getName());
        train.setTrainNumber(trainCreateDto.getTrainNumber());
        train.setIncludeStations(stations);
        trainRepository.save(train);
    }

    public TrainResponseDto editTrain(Integer trainId, TrainCreateDto trainCreateDto) {
        TrainEntity train = trainRepository.findById(trainId)
                .orElseThrow(() -> new NotFoundException("unable to find train by given trainId", TrainEntity.class, "trainId"));

        StationEntity fromStation = stationRepository.findById(trainCreateDto.getFromStationId())
                .orElseThrow(() -> new NotFoundException("unable to find stationEntity by given fromStationId", StationEntity.class, "fromStationId"));

        StationEntity toStation = stationRepository.findById(trainCreateDto.getToStationId())
                .orElseThrow(() -> new NotFoundException("unable to find stationEntity by given toStationId", StationEntity.class, "toStationId"));

        boolean exists = trainRepository.existsByNameAndTrainNumber(trainCreateDto.getName(), trainCreateDto.getTrainNumber());
        if (exists)
            throw new NotFoundException("This trainNumber is already in use in the given TrainCreateDto", TrainEntity.class, "name and trainNumber");

        List<StationEntity> stations = new ArrayList<>();
        List<Integer> includeStations = trainCreateDto.getIncludeStations();
        includeStations.forEach(id -> {
            StationEntity station = stationRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("unable to find stationEntity by given stationId", StationEntity.class, "stationId"));
            stations.add(station);
        });
        train.setFromStation(fromStation);
        train.setToStation(toStation);
        train.setName(trainCreateDto.getName());
        train.setTrainNumber(trainCreateDto.getTrainNumber());
        train.setIncludeStations(stations);
        trainRepository.save(train);

        TrainResponseDto trainResponseDto = trainMapper.entityToResponseDTO(train);
        trainResponseDto.setFromCity(train.getFromStation().getCity().getCityName());
        trainResponseDto.setFromStation(train.getFromStation().getName());
        trainResponseDto.setToCity(train.getToStation().getCity().getCityName());
        trainResponseDto.setToStation(train.getToStation().getName());
        List<StationEntity> trainIncludeStations = train.getIncludeStations();
        List<String> trainStations = new ArrayList<>();
        trainIncludeStations.forEach(stationEntity -> trainStations.add(stationEntity.getName()));
        trainResponseDto.setIncludeStations(trainStations);
        return trainResponseDto;
    }

    public ApiResponse deleteTrain(String trainNumber) {
//        TrainEntity train = trainRepository.findById(trainId)
//                .orElseThrow(() -> new NotFoundException("unable to find train by given trainId", TrainEntity.class, "trainId"));

        TrainEntity train = trainRepository.findByTrainNumber(trainNumber)
                .orElseThrow(() -> new NotFoundException("unable to find train by given trainNumber", TrainEntity.class, "trainNumber"));

        train.setTrainStatus(false);
        TrainEntity save = trainRepository.save(train);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(200);
        apiResponse.setMessage("successfully deletes");
        apiResponse.setData(trainMapper.entityToResponseDTO(save));
        return apiResponse;
    }

}
