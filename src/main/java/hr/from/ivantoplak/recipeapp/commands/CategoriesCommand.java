package hr.from.ivantoplak.recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoriesCommand {

    private Long id;
    private String description;
}
