package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.RecipesCommand;
import hr.from.ivantoplak.recipeapp.domain.Categories;
import hr.from.ivantoplak.recipeapp.domain.Recipes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipesToRecipesCommand implements Converter<Recipes, RecipesCommand> {

    private final CategoriesToCategoriesCommand categoryConveter;
    private final IngredientsToIngredientsCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    @Autowired
    public RecipesToRecipesCommand(CategoriesToCategoriesCommand categoryConveter, IngredientsToIngredientsCommand ingredientConverter, NotesToNotesCommand notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Nullable
    @Override
    public RecipesCommand convert(Recipes source) {

        if (source == null) {
            return null;
        }

        final RecipesCommand command = new RecipesCommand();

        command.setId(source.getId());
        command.setCookTime(source.getCookTime());
        command.setPrepTime(source.getPrepTime());
        command.setDescription(source.getDescription());
        command.setDifficulty(source.getDifficulty());
        command.setDirections(source.getDirections());
        command.setServings(source.getServings());
        command.setSource(source.getSource());
        command.setUrl(source.getUrl());
        command.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach((Categories category) -> command.getCategories().add(categoryConveter.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        command.setImage(source.getImage());

        return command;
    }
}
