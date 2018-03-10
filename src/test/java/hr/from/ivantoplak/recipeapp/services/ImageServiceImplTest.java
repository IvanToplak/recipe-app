package hr.from.ivantoplak.recipeapp.services;

import hr.from.ivantoplak.recipeapp.domain.Recipes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    private static final long RECIPE_ID = 1L;
    private static final String IMAGE_CONTENT = "Content";

    @Mock
    private RecipesService recipesService;

    private ImageService imageService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipesService);
    }

    @Test
    public void saveImageFile() throws IOException {

        //given
        Recipes recipe = new Recipes();
        recipe.setId(RECIPE_ID);
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", IMAGE_CONTENT.getBytes());

        when(recipesService.findById(anyLong())).thenReturn(recipe);
        ArgumentCaptor<Recipes> argumentCaptor = ArgumentCaptor.forClass(Recipes.class);

        //when
        imageService.saveImageFile(RECIPE_ID, multipartFile);

        //then
        verify(recipesService, times(1)).save(argumentCaptor.capture());
        Recipes savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }
}