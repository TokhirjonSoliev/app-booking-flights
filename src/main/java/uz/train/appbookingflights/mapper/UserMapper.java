
package uz.train.appbookingflights.mapper;

import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uz.train.appbookingflights.model.SeatEntity;
import uz.train.appbookingflights.model.UserEntity;
import uz.train.modelappbookingflights.Dto.SeatDto;
import uz.train.modelappbookingflights.Dto.UserDto;

@Component
public class UserMapper {
    private final ModelMapper mapper;

    public UserMapper() {
        this.mapper = new ModelMapper();
    }

    public UserDto entityToResponseDTO(UserEntity user) {
        return mapper.map(user, UserDto.class);
    }

    public UserEntity CreateDtoToEntity(UserDto userDto){
        return mapper.map(userDto, UserEntity.class);
    }

    public void updateEntity(UserDto userDto,UserEntity userEntity){
        mapper.map(userDto, userEntity);
    }

}
