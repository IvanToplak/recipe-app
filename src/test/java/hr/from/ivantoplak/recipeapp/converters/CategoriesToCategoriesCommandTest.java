package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.CategoriesCommand;
import hr.from.ivantoplak.recipeapp.domain.Categories;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoriesToCategoriesCommandTest {

    private static final Long ID_VALUE = 1L;
    private static final String DESCRIPTION = "description";
    private CategoriesToCategoriesCommand converter;

    @Before
    public void setUp() {
        converter = new CategoriesToCategoriesCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Categories()));
    }

    @Test
    public void convert() {

        //given
        Categories category = new Categories();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoriesCommand categoryCommand = converter.convert(category);

        //then
        assertNotNull(categoryCommand);
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }
}