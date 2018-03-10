package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.UnitsOfMeasureCommand;
import hr.from.ivantoplak.recipeapp.domain.UnitsOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitsOfMeasureCommandToUnitsOfMeasure implements Converter<UnitsOfMeasureCommand, UnitsOfMeasure> {

    @Nullable
    @Override
    public UnitsOfMeasure convert(UnitsOfMeasureCommand source) {

        if (source == null) {
            return null;
        }

        final UnitsOfMeasure uom = new UnitsOfMeasure();

        uom.setId(source.getId());
        uom.setDescription(source.getDescription());

        return uom;
    }
}
