package hr.from.ivantoplak.recipeapp.services;

import hr.from.ivantoplak.recipeapp.commands.IngredientsCommand;
import hr.from.ivantoplak.recipeapp.domain.Ingredients;

public interface IngredientsService {

    Ingredients findById(Long id);

    IngredientsCommand findCommandById(Long id);

    IngredientsCommand findCommandByRecipeIdAndIngredientId(Long recipeId, Long id);

    IngredientsCommand saveIngredientCommand(IngredientsCommand command);

    void deleteById(Long recipeId, Long idToDelete);
}
