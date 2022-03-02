package uz.train.appbookingflights.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.train.appbookingflights.model.CityEntity;
import uz.train.appbookingflights.model.UserEntity;
import uz.train.modelappbookingflights.Dto.CityDto;
import uz.train.modelappbookingflights.Dto.UserDto;

@Component
public class CityMapper {
    private final ModelMapper mapper;

    public CityMapper() {
        this.mapper = new ModelMapper();
    }

    public CityDto entityToResponseDTO(CityEntity city) {
        return mapper.map(city, CityDto.class);
    }

    public CityEntity CreateDtoToEntity(CityDto cityDto){
        return mapper.map(cityDto, CityEntity.class);
    }

    public void updateEntity(CityDto cityDto,CityEntity cityEntity){
        mapper.map(cityDto, cityEntity);
    }
}
