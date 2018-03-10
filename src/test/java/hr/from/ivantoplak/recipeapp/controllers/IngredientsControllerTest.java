package hr.from.ivantoplak.recipeapp.controllers;

import hr.from.ivantoplak.recipeapp.commands.IngredientsCommand;
import hr.from.ivantoplak.recipeapp.commands.RecipesCommand;
import hr.from.ivantoplak.recipeapp.services.IngredientsService;
import hr.from.ivantoplak.recipeapp.services.RecipesService;
import hr.from.ivantoplak.recipeapp.services.UnitsOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientsControllerTest {

    private static long RECIPE_ID = 2L;
    private static long INGREDIENT_ID = 3L;
    @Mock
    private RecipesService recipesService;
    @Mock
    private IngredientsService ingredientsService;
    @Mock
    private UnitsOfMeasureService unitsOfMeasureService;
    private IngredientsController controller;
    private MockMvc mockMvc;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        controller = new IngredientsController(recipesService, ingredientsService, unitsOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void listIngredients() throws Exception {

        //given
        RecipesCommand recipeCommand = new RecipesCommand();
        when(recipesService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipesService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void showIngredient() throws Exception {

        //given
        IngredientsCommand ingredientCommand = new IngredientsCommand();
        when(ingredientsService.findCommandByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        //when
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        //then
        verify(ingredientsService, times(1)).findCommandByRecipeIdAndIngredientId(anyLong(), anyLong());
    }

    @Test
    public void newIngredient() throws Exception {

        //given
        RecipesCommand recipeCommand = new RecipesCommand();
        recipeCommand.setId(1L);

        //when
        when(recipesService.findCommandById(anyLong())).thenReturn(recipeCommand);
        when(unitsOfMeasureService.listAll()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(recipesService, times(1)).findCommandById(anyLong());

    }

    @Test
    public void updateIngredient() throws Exception {

        //given
        IngredientsCommand ingredientCommand = new IngredientsCommand();

        //when
        when(ingredientsService.findCommandByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        when(unitsOfMeasureService.listAll()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

    }

    @Test
    public void saveOrUpdate() throws Exception {

        //given
        IngredientsCommand command = new IngredientsCommand();
        command.setId(INGREDIENT_ID);
        command.setRecipeId(RECIPE_ID);

        //when
        when(ingredientsService.saveIngredientCommand(any())).thenReturn(command);

        //then
        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
    }

    @Test
    public void deleteById() throws Exception {

        mockMvc.perform(get("/recipe/2/ingredient/3/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));
        verify(ingredientsService, times(1)).deleteById(anyLong(), anyLong());
    }
}