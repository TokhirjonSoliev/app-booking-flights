package uz.train.appbookingflights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.train.appbookingflights.model.CityEntity;
import uz.train.appbookingflights.model.StationEntity;


public interface CityRepository extends JpaRepository<CityEntity, Integer> {
}
