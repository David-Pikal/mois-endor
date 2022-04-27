package cz.uhk.mois.endor.planservice.planservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends CrudRepository<T, ID> {
    List<T> findByUserID(String id);
}
