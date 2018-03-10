package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.NotesCommand;
import hr.from.ivantoplak.recipeapp.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {

    private static final Long ID_VALUE = 1L;
    private static final String RECIPE_NOTES = "Notes";
    private NotesCommandToNotes converter;

    @Before
    public void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    public void convert() {

        //given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        //when
        Notes notes = converter.convert(notesCommand);

        //then
        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }
}