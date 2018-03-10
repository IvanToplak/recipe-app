package hr.from.ivantoplak.recipeapp.repositories;

import hr.from.ivantoplak.recipeapp.domain.Recipes;
import org.springframework.data.repository.CrudRepository;

public interface RecipesRepository extends CrudRepository<Recipes, Long> {
}
