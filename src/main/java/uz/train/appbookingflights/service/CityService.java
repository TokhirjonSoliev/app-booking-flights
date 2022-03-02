package uz.train.appbookingflights.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.train.appbookingflights.exception.ConflictException;
import uz.train.appbookingflights.exception.NotFoundException;
import uz.train.appbookingflights.mapper.CityMapper;
import uz.train.appbookingflights.model.CityEntity;
import uz.train.appbookingflights.repository.CityRepository;
import uz.train.modelappbookingflights.Dto.CityDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public List<CityDto> getCities () {
        List<CityDto> cityDtos = new ArrayList<>();
        List<CityEntity> cityEntities = cityRepository.findAll();
        for (CityEntity city: cityEntities) {
            cityDtos.add(cityMapper.entityToResponseDTO(city));
        }
        return cityDtos;
    }

    public CityDto getCity (Integer id) {
        CityEntity cityEntity =  cityRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("City not found",CityEntity.class,"id"));

        return cityMapper.entityToResponseDTO(cityEntity);
    }

    public void addCity(CityDto cityDto) {
        boolean existsByCityNumber = cityRepository.existsByCityNumber(cityDto.getCityNumber());
        if (existsByCityNumber) {
            throw new ConflictException("This CityNumber is already exist",CityEntity.class,"cityNumber");
        }

        CityEntity cityEntity = cityMapper.CreateDtoToEntity(cityDto);
        CityEntity saveCityEntity = cityRepository.save(cityEntity);
    }

    public CityDto editCity (Integer id, CityDto cityDto) {
        CityEntity cityEntity =  cityRepository.findById(id)
                .orElseThrow(() -> new ConflictException("City not found",CityEntity.class,"id"));
        cityMapper.updateEntity(cityDto,cityEntity);
        cityRepository.save(cityEntity);

        return cityMapper.entityToResponseDTO(cityEntity);
    }

    public void deleteCity (Integer id) {
        CityEntity cityEntity = cityRepository.findById(id)
                .orElseThrow(() -> new ConflictException("city not found", CityEntity.class,"id"));

        cityRepository.delete(cityEntity);
    }

}
