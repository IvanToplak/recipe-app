package hr.from.ivantoplak.recipeapp.controllers;

import hr.from.ivantoplak.recipeapp.commands.RecipesCommand;
import hr.from.ivantoplak.recipeapp.services.ImageService;
import hr.from.ivantoplak.recipeapp.services.RecipesService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    private static final long RECIPE_ID = 1L;
    private static final String IMAGE_CONTENT = "Content";

    @Mock
    private ImageService imageService;

    @Mock
    private RecipesService recipesService;

    private MockMvc mockMvc;

    private ImageController controller;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        controller = new ImageController(recipesService, imageService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void showUploadForm() throws Exception {

        //given
        RecipesCommand command = new RecipesCommand();
        command.setId(RECIPE_ID);

        when(recipesService.findCommandById(anyLong())).thenReturn(command);

        //when
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipesService, times(1)).findCommandById(anyLong());

    }

    @Test
    public void handleImagePost() throws Exception {

        //given
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", IMAGE_CONTENT.getBytes());

        //when
        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        //then
        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }

    @Test
    public void renderImageFromDB() throws Exception {

        //given
        RecipesCommand command = new RecipesCommand();
        command.setId(RECIPE_ID);

        Byte[] bytesBoxed = new Byte[IMAGE_CONTENT.getBytes().length];

        int i = 0;
        for (byte b : IMAGE_CONTENT.getBytes()) {
            bytesBoxed[i++] = b;
        }

        command.setImage(bytesBoxed);

        when(recipesService.findCommandById(anyLong())).thenReturn(command);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        //then
        assertEquals(IMAGE_CONTENT.getBytes().length, responseBytes.length);
    }

    @Test
    public void renderImageFromDBNumberFormatException() throws Exception {

        mockMvc.perform(get("/recipe/aaa/recipeimage"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}