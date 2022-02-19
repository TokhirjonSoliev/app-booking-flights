package uz.train.appbookingflights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.train.appbookingflights.model.WagonEntity;

import java.util.List;
import java.util.Optional;

public interface WagonRepository extends JpaRepository<WagonEntity, Integer> {

    List<WagonEntity> findAllByTrainEntity_TrainNumber(String trainEntity_trainNumber);

    Optional<WagonEntity> findByIdAndTrainEntity_Id(Integer id, Integer trainEntity_id);

    boolean existsByWagonNumberAndTrainEntity_Id(Integer wagonNumber, Integer trainEntity_id);
}
