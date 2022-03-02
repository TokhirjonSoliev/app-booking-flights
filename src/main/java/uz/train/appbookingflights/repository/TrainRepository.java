package uz.train.appbookingflights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.train.appbookingflights.model.TrainEntity;

import java.util.Optional;

public interface TrainRepository extends JpaRepository<TrainEntity, Integer> {

    boolean existsByNameAndTrainNumber(String name, String trainNumber);

    Optional<TrainEntity> findByTrainNumber(String trainNumber);
}
