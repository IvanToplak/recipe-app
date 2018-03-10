package hr.from.ivantoplak.recipeapp.services;

import hr.from.ivantoplak.recipeapp.domain.Recipes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipesService recipesService;

    @Autowired
    public ImageServiceImpl(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {

        Recipes recipe = recipesService.findById(recipeId);

        try {
            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }
            recipe.setImage(byteObjects);
            recipesService.save(recipe);
        } catch (IOException e) {

            //todo handle errors
            log.error("Error occurred: ", e);
        }
    }
}
