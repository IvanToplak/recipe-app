package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.UnitsOfMeasureCommand;
import hr.from.ivantoplak.recipeapp.domain.UnitsOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitsOfMeasureCommandToUnitsOfMeasureTest {

    private static final Long ID_VALUE = 1L;
    private static final String DESCRIPTION = "description";
    private UnitsOfMeasureCommandToUnitsOfMeasure converter;

    @Before
    public void setUp() {
        converter = new UnitsOfMeasureCommandToUnitsOfMeasure();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitsOfMeasureCommand()));
    }

    @Test
    public void convert() {

        //given
        UnitsOfMeasureCommand command = new UnitsOfMeasureCommand();
        command.setId(ID_VALUE);
        command.setDescription(DESCRIPTION);

        //when
        UnitsOfMeasure uom = converter.convert(command);

        //then
        assertNotNull(uom);
        assertEquals(ID_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}