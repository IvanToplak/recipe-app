package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.IngredientsCommand;
import hr.from.ivantoplak.recipeapp.domain.Ingredients;
import hr.from.ivantoplak.recipeapp.domain.Recipes;
import hr.from.ivantoplak.recipeapp.domain.UnitsOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientsToIngredientsCommandTest {

    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final String DESCRIPTION = "Cheeseburger";
    private static final Long ID_VALUE = 1L;
    private static final Long UOM_ID = 2L;
    private static final Long RECIPE_ID = 1L;

    private IngredientsToIngredientsCommand converter;

    @Before
    public void setUp() {
        converter = new IngredientsToIngredientsCommand(new UnitsOfMeasureToUnitsOfMeasureCommand());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Ingredients()));
    }

    @Test
    public void convert() {

        //given
        Ingredients ingredient = new Ingredients();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);

        UnitsOfMeasure uom = new UnitsOfMeasure();
        uom.setId(UOM_ID);
        ingredient.setUom(uom);

        Recipes recipe = new Recipes();
        recipe.setId(RECIPE_ID);
        ingredient.setRecipe(recipe);

        //when
        IngredientsCommand ingredientCommand = converter.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertNotNull(ingredientCommand.getUom());
        assertEquals(ID_VALUE, ingredientCommand.getId());
        assertEquals(UOM_ID, ingredientCommand.getUom().getId());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(RECIPE_ID, ingredientCommand.getRecipeId());
    }

    @Test
    public void convertWithNullUOMAndRecipe() {

        //given
        Ingredients ingredient = new Ingredients();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);

        //when
        IngredientsCommand ingredientCommand = converter.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertNull(ingredientCommand.getUom());
        assertEquals(ID_VALUE, ingredientCommand.getId());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
    }
}