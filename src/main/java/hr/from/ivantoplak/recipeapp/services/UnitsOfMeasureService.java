package hr.from.ivantoplak.recipeapp.services;

import hr.from.ivantoplak.recipeapp.commands.UnitsOfMeasureCommand;
import hr.from.ivantoplak.recipeapp.domain.UnitsOfMeasure;

import java.util.Set;

public interface UnitsOfMeasureService {

    UnitsOfMeasure findById(Long id);

    Set<UnitsOfMeasureCommand> listAll();
}
