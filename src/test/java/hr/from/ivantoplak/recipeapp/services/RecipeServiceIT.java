package hr.from.ivantoplak.recipeapp.services;

import hr.from.ivantoplak.recipeapp.commands.RecipesCommand;
import hr.from.ivantoplak.recipeapp.converters.RecipesCommandToRecipes;
import hr.from.ivantoplak.recipeapp.converters.RecipesToRecipesCommand;
import hr.from.ivantoplak.recipeapp.domain.Recipes;
import hr.from.ivantoplak.recipeapp.repositories.RecipesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    private static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipesService recipeService;

    @Autowired
    RecipesRepository recipeRepository;

    @Autowired
    RecipesCommandToRecipes recipeCommandToRecipe;

    @Autowired
    RecipesToRecipesCommand recipeToRecipeCommand;

    @Transactional
    @Test
    public void testSaveOfDescription() {

        //given
        Iterable<Recipes> recipes = recipeRepository.findAll();
        Recipes testRecipe = recipes.iterator().next();
        RecipesCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipesCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
