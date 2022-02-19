
package uz.train.appbookingflights.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.train.appbookingflights.model.WagonEntity;
import uz.train.modelappbookingflights.Dto.WagonDto;

@Component
public class WagonMapper {
    private final ModelMapper mapper;

    public WagonMapper() {
        this.mapper = new ModelMapper();
    }

    public WagonDto entityToResponseDTO(WagonEntity wagon) {
        return mapper.map(wagon, WagonDto.class);
    }

    public WagonEntity CreateDtoToEntity(WagonDto wagonDto){
        return mapper.map(wagonDto, WagonEntity.class);
    }

    public void updateEntity(WagonDto wagonDto, WagonEntity wagonEntity){
        mapper.map(wagonDto, wagonEntity);
    }

}
