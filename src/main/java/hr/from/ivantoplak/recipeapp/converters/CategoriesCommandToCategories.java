package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.CategoriesCommand;
import hr.from.ivantoplak.recipeapp.domain.Categories;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoriesCommandToCategories implements Converter<CategoriesCommand, Categories> {

    @Nullable
    @Override
    public Categories convert(CategoriesCommand source) {

        if (source == null) {
            return null;
        }

        final Categories category = new Categories();

        category.setId(source.getId());
        category.setDescription(source.getDescription());

        return category;
    }
}
