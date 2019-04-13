package org.openjfx.note;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class NoteView extends VBox {
    private Label title = new Label();
    private Text text = new Text();
    private ScrollPane bodyView = new ScrollPane(new TextFlow(text));
    private VBox tags = new VBox();

    private Note note;

    public Note getNote() {
        return note;
    }

    public NoteView(Note note) {
        this.note = note;
        title.textProperty().bind(note.titleProperty());
        text.textProperty().bind(note.bodyProperty());
        note.getTags().forEach(e -> tags.getChildren().add(new Label(e.getName())));
        getChildren().addAll(title, new TextFlow(text), tags);
    }

}
