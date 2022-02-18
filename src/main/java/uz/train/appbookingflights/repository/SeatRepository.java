package uz.train.appbookingflights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.train.appbookingflights.model.SeatEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Integer> {

    boolean existsBySeatNumberAndWagonEntity_Id(Integer seatNumber, Integer wagonEntity_id);

    Optional<SeatEntity> findBySeatNumberAndWagonEntity_WagonNumber(Integer seatNumber, Integer wagonEntity_wagonNumber);

    Optional<SeatEntity> findByIdAndWagonEntity_Id(Integer id, Integer wagonEntity_id);

    List<SeatEntity> findAllByWagonEntity_WagonNumber(Integer wagonEntity_wagonNumber);
}
