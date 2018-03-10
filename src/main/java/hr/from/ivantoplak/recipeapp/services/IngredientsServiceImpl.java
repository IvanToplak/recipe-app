package hr.from.ivantoplak.recipeapp.services;

import hr.from.ivantoplak.recipeapp.commands.IngredientsCommand;
import hr.from.ivantoplak.recipeapp.converters.IngredientsCommandToIngredients;
import hr.from.ivantoplak.recipeapp.converters.IngredientsToIngredientsCommand;
import hr.from.ivantoplak.recipeapp.domain.Ingredients;
import hr.from.ivantoplak.recipeapp.domain.Recipes;
import hr.from.ivantoplak.recipeapp.repositories.IngredientsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientsServiceImpl implements IngredientsService {

    private final IngredientsRepository ingredientsRepository;
    private final RecipesService recipesService;
    private final IngredientsToIngredientsCommand ingredientsToIngredientsCommand;
    private final IngredientsCommandToIngredients ingredientsCommandToIngredients;
    private final UnitsOfMeasureService unitsOfMeasureService;

    @Autowired
    public IngredientsServiceImpl(IngredientsRepository ingredientsRepository, RecipesService recipesService, IngredientsToIngredientsCommand ingredientsToIngredientsCommand, IngredientsCommandToIngredients ingredientsCommandToIngredients, UnitsOfMeasureService unitsOfMeasureService) {
        this.ingredientsRepository = ingredientsRepository;
        this.recipesService = recipesService;
        this.ingredientsToIngredientsCommand = ingredientsToIngredientsCommand;
        this.ingredientsCommandToIngredients = ingredientsCommandToIngredients;
        this.unitsOfMeasureService = unitsOfMeasureService;
    }

    @Override
    public Ingredients findById(Long id) {

        Optional<Ingredients> ingredientOptional = ingredientsRepository.findById(id);

        if (!ingredientOptional.isPresent()) {
            throw new RuntimeException("Ingredient not found!");
        }

        return ingredientOptional.get();
    }

    @Override
    public IngredientsCommand findCommandById(Long id) {

        return ingredientsToIngredientsCommand.convert(findById(id));
    }

    @Override
    public IngredientsCommand findCommandByRecipeIdAndIngredientId(Long recipeId, Long id) {

        Recipes recipe = recipesService.findById(recipeId);

        IngredientsCommand ingredientsCommand = findCommandById(id);

        if (ingredientsCommand.getRecipeId() != recipe.getId()) {
            //todo impl error handling
            log.error("Ingredient id not found: " + id);
        }

        return ingredientsCommand;
    }

    @Override
    @Transactional
    public IngredientsCommand saveIngredientCommand(IngredientsCommand command) {

        Recipes recipe = recipesService.findById(command.getRecipeId());

        Optional<Ingredients> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();

        if (ingredientOptional.isPresent()) {
            Ingredients ingredientFound = ingredientOptional.get();
            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setAmount(command.getAmount());
            ingredientFound.setUom(unitsOfMeasureService
                    .findById(command.getUom().getId()));
        } else {
            //add new Ingredient
            Ingredients ingredient = ingredientsCommandToIngredients.convert(command);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        Recipes savedRecipe = recipesService.save(recipe);

        Optional<Ingredients> savedIngredientOptional;

        if (command.getId() != null) {
            //get ingredient in case of update
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();
        } else {
            //get ingredient in case of save
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                    .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                    .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                    .findFirst();
        }

        //todo check for fail
        return ingredientsToIngredientsCommand.convert(savedIngredientOptional.get());
    }

    @Override
    @Transactional
    public void deleteById(Long recipeId, Long idToDelete) {

        log.debug("Deleting ingredient: " + recipeId + ":" + idToDelete);

        Recipes recipe = recipesService.findById(recipeId);

        Optional<Ingredients> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(idToDelete))
                .findFirst();

        if (ingredientOptional.isPresent()) {
            log.debug("found Ingredient");
            Ingredients ingredientToDelete = ingredientOptional.get();
            ingredientToDelete.setRecipe(null);
            recipe.getIngredients().remove(ingredientOptional.get());
            recipesService.save(recipe);
        }
    }
}
