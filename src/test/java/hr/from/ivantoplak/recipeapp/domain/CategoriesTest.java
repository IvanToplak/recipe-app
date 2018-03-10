package hr.from.ivantoplak.recipeapp.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoriesTest {

    private Categories category;

    @Before
    public void setUp() {
        category = new Categories();
    }

    @Test
    public void getId() {
        Long id = 4L;
        category.setId(id);
        assertEquals(id, category.getId());
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void getRecipes() {
    }
}