package hr.from.ivantoplak.recipeapp.repositories;

import hr.from.ivantoplak.recipeapp.domain.Ingredients;
import org.springframework.data.repository.CrudRepository;

public interface IngredientsRepository extends CrudRepository<Ingredients, Long> {
}
