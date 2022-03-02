package uz.train.appbookingflights.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.train.appbookingflights.exception.ConflictException;
import uz.train.appbookingflights.exception.NotFoundException;
import uz.train.appbookingflights.mapper.UserMapper;
import uz.train.appbookingflights.model.UserEntity;
import uz.train.appbookingflights.repository.UserRepository;
import uz.train.modelappbookingflights.Dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        List<UserEntity> users = userRepository.findAll();
        users.forEach(userEntity -> {
            UserDto userDto = userMapper.entityToResponseDTO(userEntity);
            userDtos.add(userDto);
        });
        return userDtos;
    }

    public UserDto getUser(UUID id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found", UserEntity.class, "userId"));

        return userMapper.entityToResponseDTO(userEntity);
    }

    public void addUser(UserDto userDto) {
        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        if (existsByPhoneNumber)
            throw new ConflictException("This phoneNumber already in use", UserEntity.class, "phoneNumber");

        UserEntity userEntity = userMapper.CreateDtoToEntity(userDto);
        userRepository.save(userEntity);
    }

    public UserDto editUser(UUID id, UserDto userDto) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found", UserEntity.class, "userId"));

        userMapper.updateEntity(userDto, userEntity);
        userRepository.save(userEntity);
        return userMapper.entityToResponseDTO(userEntity);
    }

    public void deleteUser(UUID id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found", UserEntity.class, "userId"));

        userRepository.delete(userEntity);
    }
}
