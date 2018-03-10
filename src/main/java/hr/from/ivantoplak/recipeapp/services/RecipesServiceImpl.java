package hr.from.ivantoplak.recipeapp.services;

import hr.from.ivantoplak.recipeapp.commands.RecipesCommand;
import hr.from.ivantoplak.recipeapp.converters.RecipesCommandToRecipes;
import hr.from.ivantoplak.recipeapp.converters.RecipesToRecipesCommand;
import hr.from.ivantoplak.recipeapp.domain.Recipes;
import hr.from.ivantoplak.recipeapp.exceptions.NotFoundException;
import hr.from.ivantoplak.recipeapp.repositories.RecipesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipesServiceImpl implements RecipesService {

    private final RecipesRepository recipesRepository;
    private final RecipesCommandToRecipes recipeCommandToRecipe;
    private final RecipesToRecipesCommand recipeToRecipeCommand;

    @Autowired
    public RecipesServiceImpl(RecipesRepository recipesRepository, RecipesCommandToRecipes recipeCommandToRecipe, RecipesToRecipesCommand recipeToRecipeCommand) {
        this.recipesRepository = recipesRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipes> getRecipes() {

        log.debug("Getting all recipes");

        Set<Recipes> recipesSet = new HashSet<>();
        recipesRepository.findAll().iterator().forEachRemaining(recipesSet::add);
        return recipesSet;
    }

    @Override
    public Recipes findById(Long id) {

        Optional<Recipes> recipeOptional = recipesRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            throw new NotFoundException("Recipe not found for recipe ID: " + id);
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public Recipes save(Recipes recipe) {

        return recipesRepository.save(recipe);
    }

    @Override
    @Transactional
    public RecipesCommand findCommandById(Long id) {

        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public RecipesCommand saveRecipeCommand(RecipesCommand command) {

        Recipes detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipes savedRecipe = recipesRepository.save(detachedRecipe);
        log.debug("Saved Recipe ID: " + savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        recipesRepository.deleteById(id);
    }
}
