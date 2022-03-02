
package uz.train.appbookingflights.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.train.appbookingflights.model.StationEntity;
import uz.train.appbookingflights.model.TrainEntity;
import uz.train.modelappbookingflights.Dto.createDto.StationsCreateDto;
import uz.train.modelappbookingflights.Dto.createDto.TrainCreateDto;
import uz.train.modelappbookingflights.Dto.responseDto.StationsResponseDto;
import uz.train.modelappbookingflights.Dto.responseDto.TrainResponseDto;

@Component
public class TrainMapper {
    private final ModelMapper mapper;

    public TrainMapper() {
        this.mapper = new ModelMapper();
    }

    public TrainResponseDto entityToResponseDTO(TrainEntity trainEntity) {
        return mapper.map(trainEntity, TrainResponseDto.class);
    }

    public TrainEntity createDtoToEntity(TrainCreateDto trainCreateDto){
        return mapper.map(trainCreateDto, TrainEntity.class);
    }

    public void updateEntity(TrainCreateDto trainCreateDto, Class<TrainEntity> trainEntityClass){
        mapper.map(trainCreateDto, trainEntityClass);
    }

}
