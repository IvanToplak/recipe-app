package hr.from.ivantoplak.recipeapp.repositories;

import hr.from.ivantoplak.recipeapp.domain.UnitsOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitsOfMeasureRepository extends CrudRepository<UnitsOfMeasure, Long> {

    Optional<UnitsOfMeasure> findByDescription(String description);
}
