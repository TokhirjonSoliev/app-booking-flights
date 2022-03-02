
package uz.train.appbookingflights.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.train.appbookingflights.model.WagonEntity;
import uz.train.modelappbookingflights.Dto.createDto.WagonCreateDto;
import uz.train.modelappbookingflights.Dto.responseDto.WagonResponseDto;

@Component
public class WagonMapper {
    private final ModelMapper mapper;

    public WagonMapper() {
        this.mapper = new ModelMapper();
    }

    public WagonResponseDto entityToResponseDTO(WagonEntity wagon) {
        return mapper.map(wagon, WagonResponseDto.class);
    }

    public WagonEntity createDtoToEntity(WagonCreateDto wagonCreateDto){
        return mapper.map(wagonCreateDto, WagonEntity.class);
    }

    public void updateEntity(WagonCreateDto wagonCreateDto, WagonEntity wagonEntity){
        mapper.map(wagonCreateDto, wagonEntity);
    }

}
