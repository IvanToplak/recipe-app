package hr.from.ivantoplak.recipeapp.controllers;

import hr.from.ivantoplak.recipeapp.domain.Recipes;
import hr.from.ivantoplak.recipeapp.services.RecipesService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IndexControllerTest {

    @Mock
    private RecipesService recipesService;

    @Mock
    private Model model;

    private IndexController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new IndexController(recipesService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("recipes"));
    }

    @Test
    public void getIndexPage() {

        //given
        Set<Recipes> recipes = new HashSet<>();
        recipes.add(new Recipes());
        Recipes recipe = new Recipes();
        recipe.setId(1L);
        recipes.add(recipe);

        when(recipesService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipes>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getIndexPage(model);

        //then
        assertEquals("index", viewName);
        verify(recipesService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipes> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}