package cz.uhk.mois.endor.planservice.planservice.repository;

import cz.uhk.mois.endor.planservice.planservice.model.Project;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface SubProjectRepository<T, ID> extends BaseRepository<T, ID> {
    List<T> findByProject(Project project);
}
