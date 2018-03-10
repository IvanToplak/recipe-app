package hr.from.ivantoplak.recipeapp.services;

import hr.from.ivantoplak.recipeapp.commands.RecipesCommand;
import hr.from.ivantoplak.recipeapp.domain.Recipes;

import java.util.Set;

public interface RecipesService {

    Set<Recipes> getRecipes();

    Recipes findById(Long id);

    Recipes save(Recipes recipe);

    RecipesCommand findCommandById(Long id);

    RecipesCommand saveRecipeCommand(RecipesCommand command);

    void deleteById(Long id);
}
