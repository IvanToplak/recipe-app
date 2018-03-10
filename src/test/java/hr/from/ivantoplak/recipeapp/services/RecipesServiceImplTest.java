package hr.from.ivantoplak.recipeapp.services;

import hr.from.ivantoplak.recipeapp.converters.RecipesCommandToRecipes;
import hr.from.ivantoplak.recipeapp.converters.RecipesToRecipesCommand;
import hr.from.ivantoplak.recipeapp.domain.Recipes;
import hr.from.ivantoplak.recipeapp.exceptions.NotFoundException;
import hr.from.ivantoplak.recipeapp.repositories.RecipesRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipesServiceImplTest {

    private static final long RECIPE_ID = 1L;

    @Mock
    private RecipesToRecipesCommand recipeToRecipeCommand;

    @Mock
    private RecipesCommandToRecipes recipeCommandToRecipe;

    @Mock
    private RecipesRepository recipesRepository;

    private RecipesService recipesService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        recipesService = new RecipesServiceImpl(recipesRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void findById() {

        Recipes recipe = new Recipes();
        recipe.setId(1L);
        Optional<Recipes> recipeOptional = Optional.of(recipe);

        when(recipesRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipes recipeReturned = recipesService.findById(RECIPE_ID);

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipesRepository, times(1)).findById(anyLong());
        verify(recipesRepository, never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void findByIdNotFound() {

        Optional<Recipes> recipeOptional = Optional.empty();

        when(recipesRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipes recipeReturned = recipesService.findById(RECIPE_ID);
    }

    @Test
    public void getRecipes() {

        //given
        Recipes recipe = new Recipes();
        Set<Recipes> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipesService.getRecipes()).thenReturn(recipesData);

        //when
        Set<Recipes> recipes = recipesService.getRecipes();

        //then
        assertEquals(1, recipes.size());
        verify(recipesRepository, times(1)).findAll();
        verify(recipesRepository, never()).findById(anyLong());
    }

    @Test
    public void deleteById() {

        //given
        Long idToDelete = RECIPE_ID;

        //when
        recipesService.deleteById(idToDelete);

        //then
        verify(recipesRepository, times(1)).deleteById(anyLong());
    }
}