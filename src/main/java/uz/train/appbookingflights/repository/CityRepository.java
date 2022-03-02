package uz.train.appbookingflights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.train.appbookingflights.model.CityEntity;
@Repository
public interface CityRepository extends JpaRepository<CityEntity,Integer> {
    boolean existsByCityNumber(Integer cityNumber);
}
