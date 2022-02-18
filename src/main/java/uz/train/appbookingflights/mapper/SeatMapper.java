
package uz.train.appbookingflights.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.train.appbookingflights.model.SeatEntity;
import uz.train.modelappbookingflights.Dto.SeatDto;

@Component
public class SeatMapper {
    private final ModelMapper mapper;

    public SeatMapper() {
        this.mapper = new ModelMapper();
    }

    public SeatDto entityToResponseDTO(SeatEntity seat) {
        return mapper.map(seat, SeatDto.class);
    }

    public SeatEntity CreateDtoToEntity(SeatDto seatDto){
        return mapper.map(seatDto, SeatEntity.class);
    }

    public void updateEntity(SeatDto seatDto, SeatEntity seat){
        mapper.map(seatDto, seat);
    }

}
