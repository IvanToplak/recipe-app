package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.RecipesCommand;
import hr.from.ivantoplak.recipeapp.domain.Recipes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipesCommandToRecipes implements Converter<RecipesCommand, Recipes> {

    private final CategoriesCommandToCategories categoryConveter;
    private final IngredientsCommandToIngredients ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    @Autowired
    public RecipesCommandToRecipes(CategoriesCommandToCategories categoryConveter, IngredientsCommandToIngredients ingredientConverter, NotesCommandToNotes notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Nullable
    @Override
    public Recipes convert(RecipesCommand source) {

        if (source == null) {
            return null;
        }

        final Recipes recipe = new Recipes();

        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(category -> recipe.getCategories().add(categoryConveter.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        recipe.setImage(source.getImage());

        return recipe;
    }
}
