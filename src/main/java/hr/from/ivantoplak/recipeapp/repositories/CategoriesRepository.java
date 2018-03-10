package hr.from.ivantoplak.recipeapp.repositories;

import hr.from.ivantoplak.recipeapp.domain.Categories;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoriesRepository extends CrudRepository<Categories, Long> {

    Optional<Categories> findByDescription(String description);
}
