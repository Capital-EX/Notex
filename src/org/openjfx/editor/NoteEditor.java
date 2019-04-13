package org.openjfx.editor;

import org.openjfx.note.Note;

public class NoteEditor extends Editor {

    public void edit(Note note) {

        body.setText(note.getBody());
        title.setText(note.getTitle());
        note.bodyProperty().bind(body.textProperty());
        note.titleProperty().bind(title.textProperty());
    }

    @Override
    public boolean isActive() {
        return isVisible();
    }
}
