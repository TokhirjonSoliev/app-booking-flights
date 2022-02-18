package uz.train.appbookingflights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.train.appbookingflights.model.SeatEntity;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Integer> {
}
