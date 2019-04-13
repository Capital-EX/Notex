package org.openjfx.editor;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.openjfx.tags.Tag;
import org.openjfx.tags.TagLabel;

import java.util.HashSet;


public abstract class Editor extends BorderPane {

    protected final TextField title = new TextField();
    protected final TextArea body = new TextArea();
    protected final Button addTag = new Button("add tag");
    protected final Button setColor = new Button("color");
    protected final HBox buttons = new HBox(addTag, setColor);
    protected final HBox tagView = new HBox();
    protected final VBox bottom = new VBox(tagView, buttons);
    protected HashSet<Tag> tags = new HashSet<>();

    public Editor() {
        body.setWrapText(true);
        bottom.setSpacing(10);
        tagView.setSpacing(10);
        HBox.setHgrow(tagView, Priority.ALWAYS);
        setTop(title);
        setCenter(body);

        setBottom(bottom);
    }

    public TextField getTitle() {
        return title;
    }

    public TextArea getBody() {
        return body;
    }

    public Button getAddTag() {
        return addTag;
    }

    public boolean isEmpty() {
        return title.getText().isEmpty() && body.getText().isEmpty();
    }

    public abstract boolean isActive();

    public void addTag(Tag tag) {
        tags.add(tag);
        var label = new TagLabel(tag.getName());
        tagView.getChildren().add(label);
        System.out.println(tagView.getChildren());
    }
}
