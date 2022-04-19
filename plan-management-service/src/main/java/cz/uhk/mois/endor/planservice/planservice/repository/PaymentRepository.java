package cz.uhk.mois.endor.planservice.planservice.repository;

import cz.uhk.mois.endor.planservice.planservice.model.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    List<Payment> findByUserID(Integer userID);
}
