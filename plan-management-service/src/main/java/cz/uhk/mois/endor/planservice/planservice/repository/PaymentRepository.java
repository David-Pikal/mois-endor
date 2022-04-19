package cz.uhk.mois.endor.planservice.planservice.repository;

import cz.uhk.mois.endor.planservice.planservice.model.Payment;
import cz.uhk.mois.endor.planservice.planservice.model.Project;

import java.util.List;

public interface PaymentRepository extends BaseRepository<Payment, Integer> {
    List<Payment> findByProject(Project userID);
}
