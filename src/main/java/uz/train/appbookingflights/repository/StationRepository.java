package uz.train.appbookingflights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.train.appbookingflights.model.StationEntity;

import java.util.List;
import java.util.Optional;


public interface StationRepository extends JpaRepository<StationEntity, Integer> {

    List<StationEntity> findAllByCity_Id(Integer city_id);

    Optional<StationEntity> findByIdAndCity_Id(Integer id, Integer city_id);

    boolean existsByNameAndCity_Id(String name, Integer city_id);
}
