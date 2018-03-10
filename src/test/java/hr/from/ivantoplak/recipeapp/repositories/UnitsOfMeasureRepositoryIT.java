package hr.from.ivantoplak.recipeapp.repositories;

import hr.from.ivantoplak.recipeapp.domain.UnitsOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitsOfMeasureRepositoryIT {

    @Autowired
    UnitsOfMeasureRepository unitsOfMeasureRepository;

    @Before
    public void setUp() {
    }

    @Test
    public void findByDescription() {

        Optional<UnitsOfMeasure> uomOptional = unitsOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", uomOptional.get().getDescription());
    }
}