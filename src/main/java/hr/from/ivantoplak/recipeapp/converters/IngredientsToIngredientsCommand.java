package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.IngredientsCommand;
import hr.from.ivantoplak.recipeapp.domain.Ingredients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientsToIngredientsCommand implements Converter<Ingredients, IngredientsCommand> {

    private final UnitsOfMeasureToUnitsOfMeasureCommand uomConverter;

    @Autowired
    public IngredientsToIngredientsCommand(UnitsOfMeasureToUnitsOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Override
    public IngredientsCommand convert(Ingredients source) {

        if (source == null) {
            return null;
        }

        IngredientsCommand ingredientCommand = new IngredientsCommand();

        ingredientCommand.setId(source.getId());
        ingredientCommand.setAmount(source.getAmount());
        ingredientCommand.setDescription(source.getDescription());
        ingredientCommand.setUom(uomConverter.convert(source.getUom()));
        if (source.getRecipe() != null) {
            ingredientCommand.setRecipeId(source.getRecipe().getId());
        }
        return ingredientCommand;

    }
}
