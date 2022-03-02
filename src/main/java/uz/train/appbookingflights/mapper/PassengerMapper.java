package uz.train.appbookingflights.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.train.appbookingflights.model.PassengerEntity;
import uz.train.modelappbookingflights.Dto.createDto.PassengerCreateDto;
import uz.train.modelappbookingflights.Dto.responseDto.PassengerResponseDto;

@Component
public class PassengerMapper {

    private final ModelMapper mapper;

    public PassengerMapper() {
        this.mapper = new ModelMapper();
    }
    public PassengerResponseDto entityToResponseDTO(PassengerEntity passengerEntity) {
        return mapper.map(passengerEntity, PassengerResponseDto.class);
    }

    public PassengerEntity CreateDtoToEntity(PassengerCreateDto passengerCreateDto){
        return mapper.map(passengerCreateDto, PassengerEntity.class);
    }

    public void updateEntity(PassengerCreateDto passengerCreateDto, PassengerEntity passengerEntity){
        mapper.map(passengerCreateDto, passengerEntity);
    }

}
