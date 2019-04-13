package org.openjfx.editor;

import javafx.event.Event;
import javafx.event.EventType;
import org.openjfx.tags.Tag;

import java.util.HashSet;

public class NoteCreatorEvent extends Event {
    public static final EventType<NoteCreatorEvent> ADD_NOTE = new EventType<>("ADD_NOTE");

    private String body;
    private String title;
    private HashSet<Tag> tags;

    public NoteCreatorEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public NoteCreatorEvent(EventType<? extends Event> eventType, String title, String body, HashSet<Tag> tags) {
        super(eventType);
        this.body = body;
        this.title = title;
        this.tags = tags;
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public HashSet<Tag> getTags() {
        return tags;
    }
}
