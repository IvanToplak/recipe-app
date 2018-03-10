package hr.from.ivantoplak.recipeapp.controllers;

import hr.from.ivantoplak.recipeapp.commands.RecipesCommand;
import hr.from.ivantoplak.recipeapp.domain.Recipes;
import hr.from.ivantoplak.recipeapp.exceptions.NotFoundException;
import hr.from.ivantoplak.recipeapp.services.RecipesService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipesControllerTest {

    @Mock
    private RecipesService recipesService;

    private RecipesController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        controller = new RecipesController(recipesService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void showById() throws Exception {

        Recipes recipe = new Recipes();
        recipe.setId(1L);

        when(recipesService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipesService, times(1)).findById(anyLong());
    }

    @Test
    public void showByIdNotFound() throws Exception {

        when(recipesService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    public void showByIdNumberFormatException() throws Exception {

        mockMvc.perform(get("/recipe/aaa/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    public void newRecipe() throws Exception {

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void updateRecipe() throws Exception {

        RecipesCommand command = new RecipesCommand();
        command.setId(2L);

        when(recipesService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipesService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void saveOrUpdate() throws Exception {

        RecipesCommand command = new RecipesCommand();
        command.setId(2L);

        when(recipesService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
                .param("directions", "some directions")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));

        verify(recipesService, times(1)).saveRecipeCommand(any());
    }

    @Test
    public void saveOrUpdateValidationFail() throws Exception {

        RecipesCommand command = new RecipesCommand();
        command.setId(2L);

        when(recipesService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("cookTime", "3000")

        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));

        verify(recipesService, times(0)).saveRecipeCommand(any());
    }

    @Test
    public void deleteById() throws Exception {

        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipesService, times(1)).deleteById(anyLong());
    }
}