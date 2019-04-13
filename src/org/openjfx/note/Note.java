package org.openjfx.note;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.openjfx.tags.Tag;

import java.util.HashSet;

public class Note {
    private StringProperty body = new SimpleStringProperty();

    public StringProperty bodyProperty() {
        return body;
    }

    public String getBody() {
        return body.get();
    }

    public void setBody(String newBody) {
        body.set(newBody);
    }

    private StringProperty title = new SimpleStringProperty();

    public StringProperty titleProperty() {
        return title;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String newTitle) {
        title.setValue(newTitle);
    }

    private HashSet<Tag> tags;

    public HashSet<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public Note() {
    }

    public Note(String titleText) {
        title.setValue(titleText);
    }

    public Note(String titleText, String bodyText) {
        title.setValue(titleText);
        body.setValue(bodyText);
    }

    public Note(String titleText, String bodyText, HashSet<Tag> newTags) {
        title.setValue(titleText);
        body.setValue(bodyText);
        tags = newTags;
    }
}