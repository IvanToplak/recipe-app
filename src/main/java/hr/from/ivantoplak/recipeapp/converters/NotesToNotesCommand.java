package hr.from.ivantoplak.recipeapp.converters;

import hr.from.ivantoplak.recipeapp.commands.NotesCommand;
import hr.from.ivantoplak.recipeapp.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;

@Controller
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Nullable
    @Override
    public NotesCommand convert(Notes source) {

        if (source == null) {
            return null;
        }

        final NotesCommand notesCommand = new NotesCommand();

        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());

        return notesCommand;
    }
}
