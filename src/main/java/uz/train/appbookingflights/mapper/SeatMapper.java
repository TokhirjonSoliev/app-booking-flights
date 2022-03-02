
package uz.train.appbookingflights.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.train.appbookingflights.model.SeatEntity;
import uz.train.modelappbookingflights.Dto.createDto.SeatCreateDto;
import uz.train.modelappbookingflights.Dto.responseDto.SeatResponseDto;

@Component
public class SeatMapper {
    private final ModelMapper mapper;

    public SeatMapper() {
        this.mapper = new ModelMapper();
    }

    public SeatResponseDto entityToResponseDTO(SeatEntity seat) {
        return mapper.map(seat, SeatResponseDto.class);
    }

    public SeatEntity CreateDtoToEntity(SeatCreateDto seatCreateDto){
        return mapper.map(seatCreateDto, SeatEntity.class);
    }

    public void updateEntity(SeatCreateDto seatCreateDto, SeatEntity seat){
        mapper.map(seatCreateDto, seat);
    }

}
