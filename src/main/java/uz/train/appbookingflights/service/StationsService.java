package uz.train.appbookingflights.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.train.appbookingflights.exception.NotFoundException;
import uz.train.appbookingflights.mapper.StationMapper;
import uz.train.appbookingflights.model.CityEntity;
import uz.train.appbookingflights.model.SeatEntity;
import uz.train.appbookingflights.model.StationEntity;
import uz.train.appbookingflights.repository.CityRepository;
import uz.train.appbookingflights.repository.StationRepository;
import uz.train.modelappbookingflights.Dto.createDto.StationsCreateDto;
import uz.train.modelappbookingflights.Dto.responseDto.StationsResponseDto;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StationsService {
    private final StationRepository stationRepository;
    private final CityRepository cityRepository;
    private final StationMapper stationMapper;

    public List<StationsResponseDto> getStations(Integer cityId){
        CityEntity city = cityRepository.findById(cityId)
                .orElseThrow(() -> new NotFoundException("unable to find city by Id", CityEntity.class, "cityId"));

        List<StationsResponseDto> stationsResponseDtos = new ArrayList<>();

        List<StationEntity> cities = stationRepository.findAllByCity_Id(cityId);
        cities.forEach(stationEntity -> {
            stationEntity.setCity(city);
            StationsResponseDto stationsResponseDto = stationMapper.entityToResponseDTO(stationEntity);
            stationsResponseDtos.add(stationsResponseDto);
        });
        return stationsResponseDtos;
    }

    public StationsResponseDto getStation(Integer cityId, Integer stationId){
        CityEntity city = cityRepository.findById(cityId)
                .orElseThrow(() -> new NotFoundException("unable to find city by given cityId", CityEntity.class, "cityId"));

        StationEntity station = stationRepository.findByIdAndCity_Id(stationId, cityId)
                .orElseThrow(() -> new NotFoundException("unable to find station in the given city", StationEntity.class, "id and cityId"));

        station.setCity(city);
        return stationMapper.entityToResponseDTO(station);
    }

    public void addStation(Integer cityId, StationsCreateDto stationsCreateDto){
        CityEntity city = cityRepository.findById(cityId)
                .orElseThrow(() -> new NotFoundException("unable to find city by given cityId", CityEntity.class, "cityId"));

        boolean exists = stationRepository.existsByNameAndCity_Id(stationsCreateDto.getName(), cityId);
        if (exists)
                throw  new NotFoundException("This station is already in use in the given city", StationEntity.class, "id and cityId");

        StationEntity station = stationMapper.createDtoToEntity(stationsCreateDto);
        station.setCity(city);
        stationRepository.save(station);
    }

    public StationsResponseDto editStation(Integer cityId, Integer stationId, StationsCreateDto stationsCreateDto){
        CityEntity city = cityRepository.findById(cityId)
                .orElseThrow(() -> new NotFoundException("unable to find city by given cityId", CityEntity.class, "cityId"));

        StationEntity station = stationRepository.findByIdAndCity_Id(stationId, cityId)
                .orElseThrow(() -> new NotFoundException("unable to find station in the given city", StationEntity.class, "id and cityId"));

        stationMapper.updateEntity(stationsCreateDto, StationEntity.class);
        station.setCity(city);
        stationRepository.save(station);
        return stationMapper.entityToResponseDTO(station);
    }

    public void deleteStation(Integer cityId, Integer stationId){
        CityEntity city = cityRepository.findById(cityId)
                .orElseThrow(() -> new NotFoundException("unable to find city by given cityId", CityEntity.class, "cityId"));

        StationEntity station = stationRepository.findByIdAndCity_Id(stationId, cityId)
                .orElseThrow(() -> new NotFoundException("unable to find station in the given city", StationEntity.class, "id and cityId"));

        stationRepository.delete(station);
    }

}
