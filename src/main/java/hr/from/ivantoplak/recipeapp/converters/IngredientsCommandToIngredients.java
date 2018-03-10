package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.IngredientsCommand;
import hr.from.ivantoplak.recipeapp.domain.Ingredients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientsCommandToIngredients implements Converter<IngredientsCommand, Ingredients> {

    private final UnitsOfMeasureCommandToUnitsOfMeasure uomConverter;

    @Autowired
    public IngredientsCommandToIngredients(UnitsOfMeasureCommandToUnitsOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Override
    public Ingredients convert(IngredientsCommand source) {

        if (source == null) {
            return null;
        }

        final Ingredients ingredient = new Ingredients();

        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUom(uomConverter.convert(source.getUom()));
        return ingredient;
    }
}
