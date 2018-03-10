package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.RecipesCommand;
import hr.from.ivantoplak.recipeapp.domain.Categories;
import hr.from.ivantoplak.recipeapp.domain.Ingredients;
import hr.from.ivantoplak.recipeapp.domain.Notes;
import hr.from.ivantoplak.recipeapp.domain.Recipes;
import hr.from.ivantoplak.recipeapp.enums.Difficulty;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipesToRecipesCommandTest {

    private static final Long RECIPE_ID = 1L;
    private static final Integer COOK_TIME = 5;
    private static final Integer PREP_TIME = 7;
    private static final String DESCRIPTION = "My Recipe";
    private static final String DIRECTIONS = "Directions";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final Integer SERVINGS = 3;
    private static final String SOURCE = "Source";
    private static final String URL = "Some URL";
    private static final Long CAT_ID_1 = 1L;
    private static final Long CAT_ID2 = 2L;
    private static final Long INGRED_ID_1 = 3L;
    private static final Long INGRED_ID_2 = 4L;
    private static final Long NOTES_ID = 9L;

    private RecipesToRecipesCommand converter;

    @Before
    public void setUp() {
        converter = new RecipesToRecipesCommand(
                new CategoriesToCategoriesCommand(),
                new IngredientsToIngredientsCommand(new UnitsOfMeasureToUnitsOfMeasureCommand()),
                new NotesToNotesCommand());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Recipes()));
    }

    @Test
    public void convert() {

        //given
        Recipes recipe = new Recipes();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);

        recipe.setNotes(notes);

        Categories category = new Categories();
        category.setId(CAT_ID_1);

        Categories category2 = new Categories();
        category2.setId(CAT_ID2);

        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);

        Ingredients ingredient = new Ingredients();
        ingredient.setId(INGRED_ID_1);

        Ingredients ingredient2 = new Ingredients();
        ingredient2.setId(INGRED_ID_2);

        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient2);

        Byte[] image = new Byte[]{0, 1};
        recipe.setImage(image);

        //when
        RecipesCommand command = converter.convert(recipe);

        //then
        assertNotNull(command);
        assertEquals(RECIPE_ID, command.getId());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
        assertEquals(NOTES_ID, command.getNotes().getId());
        assertEquals(2, command.getCategories().size());
        assertEquals(2, command.getIngredients().size());
        assertEquals(2, command.getImage().length);
    }
}