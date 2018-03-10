package hr.from.ivantoplak.recipeapp.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImageFile(Long recipeId, MultipartFile file);
}
