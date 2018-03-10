package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.UnitsOfMeasureCommand;
import hr.from.ivantoplak.recipeapp.domain.UnitsOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitsOfMeasureToUnitsOfMeasureCommandTest {

    private static final Long ID_VALUE = 1L;
    private static final String DESCRIPTION = "description";
    private UnitsOfMeasureToUnitsOfMeasureCommand converter;

    @Before
    public void setUp() {

        converter = new UnitsOfMeasureToUnitsOfMeasureCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitsOfMeasure()));
    }

    @Test
    public void convert() {

        //given
        UnitsOfMeasure uom = new UnitsOfMeasure();
        uom.setId(ID_VALUE);
        uom.setDescription(DESCRIPTION);

        //when
        UnitsOfMeasureCommand uomc = converter.convert(uom);

        //then
        assertNotNull(uomc);
        assertEquals(ID_VALUE, uomc.getId());
        assertEquals(DESCRIPTION, uomc.getDescription());
    }
}