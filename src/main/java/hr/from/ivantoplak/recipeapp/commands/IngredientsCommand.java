package hr.from.ivantoplak.recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class IngredientsCommand {

    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal amount;
    private UnitsOfMeasureCommand uom;
}
