package uz.train.appbookingflights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.train.appbookingflights.model.PassengerEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, UUID> {
    Optional<PassengerEntity> findByIdAndUserEntity_Id (UUID id,UUID userEntity_id);
    List<PassengerEntity> findAllByUserEntity_Id(UUID userEntity_id);
    boolean existsByPassportAndUserEntity_Id(String passport,UUID userEntity_id);
}
