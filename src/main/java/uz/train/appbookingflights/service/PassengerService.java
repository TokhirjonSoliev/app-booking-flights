package uz.train.appbookingflights.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.train.appbookingflights.exception.ConflictException;
import uz.train.appbookingflights.exception.NotFoundException;
import uz.train.appbookingflights.mapper.PassengerMapper;
import uz.train.appbookingflights.model.PassengerEntity;
import uz.train.appbookingflights.model.UserEntity;
import uz.train.appbookingflights.repository.PassengerRepository;
import uz.train.appbookingflights.repository.UserRepository;
import uz.train.modelappbookingflights.Dto.createDto.PassengerCreateDto;
import uz.train.modelappbookingflights.Dto.responseDto.PassengerResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final UserRepository userRepository;


    public List<PassengerResponseDto> getPassengers (UUID userId) {
        List<PassengerResponseDto> passengerDtoList = new ArrayList<>();
        UserEntity userEntity =  userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found",UserEntity.class,"userId"));

        List<PassengerEntity> passengerEntityList = passengerRepository.findAllByUserEntity_Id(userEntity.getId());
        passengerEntityList.forEach(passengerEntity -> {
            PassengerResponseDto passengerResponseDto = passengerMapper.entityToResponseDTO(passengerEntity);
            passengerDtoList.add(passengerResponseDto);
        });
        return passengerDtoList;
    }

    public PassengerResponseDto getPassenger(UUID userId,UUID passengerId) {
        PassengerEntity passengerEntity = passengerRepository.findByIdAndUserEntity_Id(passengerId,userId)
                .orElseThrow(() -> new NotFoundException("Passenger not found",PassengerEntity.class,"passengerId"));

        return passengerMapper.entityToResponseDTO(passengerEntity);
    }

    public void addPassenger(UUID userId, PassengerCreateDto passengerCreateDto) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found", UserEntity.class, "userId"));

        boolean existsByPassportAndUserEntity_id = passengerRepository.existsByPassportAndUserEntity_Id(passengerCreateDto.getPassport(), userEntity.getId());
        if (existsByPassportAndUserEntity_id) {
            throw new ConflictException("This passenger is already use", PassengerEntity.class, "passport and userId");
        }

        PassengerEntity passengerEntity = passengerMapper.CreateDtoToEntity(passengerCreateDto);
        passengerEntity.setUserEntity(userEntity);
        passengerRepository.save(passengerEntity);
    }

    public PassengerResponseDto editPassenger (UUID userId,UUID passengerId, PassengerCreateDto passengerCreateDto) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found",UserEntity.class,"userId"));

        PassengerEntity passengerEntity = passengerRepository.findByIdAndUserEntity_Id(passengerId,userEntity.getId())
                .orElseThrow(() -> new NotFoundException("Passenger not found",PassengerEntity.class,"passengerId"));

        passengerMapper.updateEntity(passengerCreateDto,passengerEntity);
        passengerEntity.setUserEntity(userEntity);
        passengerRepository.save(passengerEntity);
        return passengerMapper.entityToResponseDTO(passengerEntity);
    }

    public void deletePassenger (UUID userId, UUID passengerId) {
        PassengerEntity passengerEntity = passengerRepository.findByIdAndUserEntity_Id(passengerId,userId)
                .orElseThrow(() -> new NotFoundException("Passenger not found",PassengerEntity.class,"userId and passengerId"));

        passengerRepository.delete(passengerEntity);
    }



}
