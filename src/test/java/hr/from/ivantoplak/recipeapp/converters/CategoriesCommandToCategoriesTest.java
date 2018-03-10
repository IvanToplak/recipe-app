package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.CategoriesCommand;
import hr.from.ivantoplak.recipeapp.domain.Categories;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoriesCommandToCategoriesTest {

    private static final Long ID_VALUE = 1L;
    private static final String DESCRIPTION = "description";
    private CategoriesCommandToCategories conveter;

    @Before
    public void setUp() {
        conveter = new CategoriesCommandToCategories();
    }

    @Test
    public void testNullObject() {
        assertNull(conveter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(conveter.convert(new CategoriesCommand()));
    }

    @Test
    public void convert() {

        //given
        CategoriesCommand categoryCommand = new CategoriesCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION);

        //when
        Categories category = conveter.convert(categoryCommand);

        //then
        assertNotNull(category);
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}