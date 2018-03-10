package hr.from.ivantoplak.recipeapp.services;

import hr.from.ivantoplak.recipeapp.commands.UnitsOfMeasureCommand;
import hr.from.ivantoplak.recipeapp.converters.UnitsOfMeasureToUnitsOfMeasureCommand;
import hr.from.ivantoplak.recipeapp.domain.UnitsOfMeasure;
import hr.from.ivantoplak.recipeapp.repositories.UnitsOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UnitsOfMeasureServiceImpl implements UnitsOfMeasureService {

    private final UnitsOfMeasureRepository unitsOfMeasureRepository;
    private final UnitsOfMeasureToUnitsOfMeasureCommand unitsOfMeasureToUnitsOfMeasureCommand;

    @Autowired
    public UnitsOfMeasureServiceImpl(UnitsOfMeasureRepository unitsOfMeasureRepository, UnitsOfMeasureToUnitsOfMeasureCommand unitsOfMeasureToUnitsOfMeasureCommand) {
        this.unitsOfMeasureRepository = unitsOfMeasureRepository;
        this.unitsOfMeasureToUnitsOfMeasureCommand = unitsOfMeasureToUnitsOfMeasureCommand;
    }

    @Override
    public UnitsOfMeasure findById(Long id) {

        Optional<UnitsOfMeasure> unitsOfMeasureOptional = unitsOfMeasureRepository.findById(id);

        if (!unitsOfMeasureOptional.isPresent()) {
            throw new RuntimeException("UOM not found!");
        }

        return unitsOfMeasureOptional.get();
    }

    @Override
    public Set<UnitsOfMeasureCommand> listAll() {

        log.debug("Getting all uoms");

        return StreamSupport.stream(unitsOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(unitsOfMeasureToUnitsOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }
}
