package cz.uhk.mois.endor.planservice.planservice.repository;

import cz.uhk.mois.endor.planservice.planservice.model.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
}
