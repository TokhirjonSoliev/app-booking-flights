package uz.train.appbookingflights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.train.appbookingflights.model.WagonEntity;

public interface WagonRepository extends JpaRepository<WagonEntity, Integer> {
}
