package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.IngredientsCommand;
import hr.from.ivantoplak.recipeapp.commands.UnitsOfMeasureCommand;
import hr.from.ivantoplak.recipeapp.domain.Ingredients;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientsCommandToIngredientsTest {

    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final String DESCRIPTION = "Cheeseburger";
    private static final Long ID_VALUE = 1L;
    private static final Long UOM_ID = 2L;
    private static final Long RECIPE_ID = 1L;
    private IngredientsCommandToIngredients converter;


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        converter = new IngredientsCommandToIngredients(new UnitsOfMeasureCommandToUnitsOfMeasure());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientsCommand()));
    }

    @Test
    public void convert() {

        //given
        IngredientsCommand command = new IngredientsCommand();
        command.setId(ID_VALUE);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);
        UnitsOfMeasureCommand unitOfMeasureCommand = new UnitsOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        command.setUom(unitOfMeasureCommand);

        //when
        Ingredients ingredient = converter.convert(command);

        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UOM_ID, ingredient.getUom().getId());
    }

    @Test
    public void convertWithNullUOMAndRecipe() {

        //given
        IngredientsCommand command = new IngredientsCommand();
        command.setId(ID_VALUE);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);

        //when
        Ingredients ingredient = converter.convert(command);

        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }
}