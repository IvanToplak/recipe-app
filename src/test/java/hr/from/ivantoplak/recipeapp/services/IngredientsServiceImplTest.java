package hr.from.ivantoplak.recipeapp.services;

import hr.from.ivantoplak.recipeapp.commands.IngredientsCommand;
import hr.from.ivantoplak.recipeapp.converters.IngredientsCommandToIngredients;
import hr.from.ivantoplak.recipeapp.converters.IngredientsToIngredientsCommand;
import hr.from.ivantoplak.recipeapp.converters.RecipesCommandToRecipes;
import hr.from.ivantoplak.recipeapp.converters.RecipesToRecipesCommand;
import hr.from.ivantoplak.recipeapp.domain.Ingredients;
import hr.from.ivantoplak.recipeapp.domain.Recipes;
import hr.from.ivantoplak.recipeapp.repositories.IngredientsRepository;
import hr.from.ivantoplak.recipeapp.repositories.RecipesRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientsServiceImplTest {

    private static final Long RECIPE_ID = 1L;
    private static final Long INGREDIENT_ID = 1L;
    private static final Long INGREDIENT_2_ID = 2L;
    private static final Long INGREDIENT_3_ID = 3L;

    @Mock
    private RecipesToRecipesCommand recipeToRecipeCommand;

    @Mock
    private RecipesCommandToRecipes recipeCommandToRecipe;

    @Mock
    private IngredientsToIngredientsCommand ingredientsToIngredientsCommand;

    @Mock
    private RecipesRepository recipesRepository;

    @Mock
    private IngredientsRepository ingredientsRepository;

    @Mock
    private UnitsOfMeasureService unitsOfMeasureService;

    @Mock
    private IngredientsCommandToIngredients ingredientsCommandToIngredients;

    private IngredientsService ingredientsService;

    private RecipesService recipesService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipesService = new RecipesServiceImpl(recipesRepository, recipeCommandToRecipe, recipeToRecipeCommand);
        ingredientsService = new IngredientsServiceImpl(ingredientsRepository, recipesService, ingredientsToIngredientsCommand, ingredientsCommandToIngredients, unitsOfMeasureService);
    }

    @Test
    public void findById() {

        //given
        Ingredients ingredient = new Ingredients();
        ingredient.setId(INGREDIENT_ID);
        Optional<Ingredients> ingredientOptional = Optional.of(ingredient);

        when(ingredientsRepository.findById(anyLong())).thenReturn(ingredientOptional);

        //when
        Ingredients ingredientReturned = ingredientsService.findById(INGREDIENT_ID);

        //then
        assertNotNull("Null ingredient returned", ingredientReturned);
        verify(ingredientsRepository, times(1)).findById(anyLong());
        verify(ingredientsRepository, never()).findAll();
    }

    @Test
    public void findCommandById() {

        //given
        Ingredients ingredient = new Ingredients();
        ingredient.setId(INGREDIENT_ID);
        Optional<Ingredients> ingredientOptional = Optional.of(ingredient);
        IngredientsCommand ingredientCommand = new IngredientsCommand();
        ingredientCommand.setId(INGREDIENT_ID);

        when(ingredientsRepository.findById(anyLong())).thenReturn(ingredientOptional);
        when(ingredientsService.findCommandById(anyLong())).thenReturn(ingredientCommand);


        //when
        IngredientsCommand ingredientCommandReturned = ingredientsService.findCommandById(INGREDIENT_ID);

        //then
        assertNotNull("Null ingredient returned", ingredientCommandReturned);
        assertEquals(INGREDIENT_ID, ingredientCommandReturned.getId());
        verify(ingredientsRepository, times(2)).findById(anyLong());
    }

    @Test
    public void findCommandByRecipeIdAndIngredientId() {

        //given
        Recipes recipe = new Recipes();
        recipe.setId(RECIPE_ID);

        Ingredients ingredient1 = new Ingredients();
        ingredient1.setId(INGREDIENT_ID);

        Ingredients ingredient2 = new Ingredients();
        ingredient2.setId(INGREDIENT_2_ID);

        Ingredients ingredient3 = new Ingredients();
        ingredient3.setId(INGREDIENT_3_ID);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        ingredient1.setRecipe(recipe);
        ingredient2.setRecipe(recipe);
        ingredient3.setRecipe(recipe);

        Optional<Recipes> recipeOptional = Optional.of(recipe);
        Optional<Ingredients> ingredientOptional = Optional.of(ingredient1);

        IngredientsCommand ingredientCommand = new IngredientsCommand();
        ingredientCommand.setRecipeId(RECIPE_ID);
        ingredientCommand.setId(INGREDIENT_3_ID);

        when(recipesRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(ingredientsRepository.findById(anyLong())).thenReturn(ingredientOptional);
        when(ingredientsService.findCommandById(anyLong())).thenReturn(ingredientCommand);

        //when
        IngredientsCommand ingredientCommandReturned = ingredientsService.findCommandByRecipeIdAndIngredientId(1L, 3L);

        //then
        assertEquals(Long.valueOf(3L), ingredientCommandReturned.getId());
        assertEquals(Long.valueOf(1L), ingredientCommandReturned.getRecipeId());
        verify(recipesRepository, times(1)).findById(anyLong());
        verify(ingredientsRepository, times(2)).findById(anyLong());
    }


    @Test
    public void deleteById() {

        //given
        Recipes recipe = new Recipes();
        recipe.setId(RECIPE_ID);

        Ingredients ingredient = new Ingredients();
        ingredient.setId(INGREDIENT_ID);

        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);

        Optional<Recipes> recipeOptional = Optional.of(recipe);

        when(recipesRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        ingredientsService.deleteById(RECIPE_ID, INGREDIENT_ID);

        //then
        verify(recipesRepository, times(1)).findById(anyLong());
        verify(recipesRepository, times(1)).save(any(Recipes.class));
    }
}