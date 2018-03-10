package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.UnitsOfMeasureCommand;
import hr.from.ivantoplak.recipeapp.domain.UnitsOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitsOfMeasureToUnitsOfMeasureCommand implements Converter<UnitsOfMeasure, UnitsOfMeasureCommand> {

    @Nullable
    @Override
    public UnitsOfMeasureCommand convert(UnitsOfMeasure source) {

        if (source == null) {
            return null;
        }

        final UnitsOfMeasureCommand uomCommand = new UnitsOfMeasureCommand();

        uomCommand.setId(source.getId());
        uomCommand.setDescription(source.getDescription());

        return uomCommand;
    }
}
