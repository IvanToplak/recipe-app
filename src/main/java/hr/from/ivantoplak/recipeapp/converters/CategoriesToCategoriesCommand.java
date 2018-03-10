package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.CategoriesCommand;
import hr.from.ivantoplak.recipeapp.domain.Categories;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoriesToCategoriesCommand implements Converter<Categories, CategoriesCommand> {

    @Nullable
    @Override
    public CategoriesCommand convert(Categories source) {

        if (source == null) {
            return null;
        }

        final CategoriesCommand categoryCommand = new CategoriesCommand();

        categoryCommand.setId(source.getId());
        categoryCommand.setDescription(source.getDescription());

        return categoryCommand;
    }
}
