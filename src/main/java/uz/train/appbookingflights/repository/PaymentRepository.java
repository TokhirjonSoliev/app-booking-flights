package uz.train.appbookingflights.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.train.appbookingflights.model.PaymentEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity,Integer> {
    boolean existsByName(String name);
}
