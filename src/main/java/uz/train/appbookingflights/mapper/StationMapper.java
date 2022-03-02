
package uz.train.appbookingflights.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.train.appbookingflights.model.StationEntity;
import uz.train.modelappbookingflights.Dto.createDto.StationsCreateDto;
import uz.train.modelappbookingflights.Dto.responseDto.StationsResponseDto;

@Component
public class StationMapper {
    private final ModelMapper mapper;

    public StationMapper() {
        this.mapper = new ModelMapper();
    }

    public StationsResponseDto entityToResponseDTO(StationEntity stationEntity) {
        return mapper.map(stationEntity, StationsResponseDto.class);
    }

    public StationEntity createDtoToEntity(StationsCreateDto stationsCreateDto){
        return mapper.map(stationsCreateDto, StationEntity.class);
    }

    public void updateEntity(StationsCreateDto stationsCreateDto, Class<StationEntity> station){
        mapper.map(stationsCreateDto, station);
    }

}
