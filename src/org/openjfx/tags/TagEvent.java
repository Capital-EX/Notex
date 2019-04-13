package org.openjfx.tags;

import javafx.event.Event;
import javafx.event.EventType;

public class TagEvent extends Event {
    public static final EventType<TagEvent> CREATE_TAG = new EventType<>("CREATE_TAG");
    public static final EventType<TagEvent> DELETE_TAG = new EventType<>("DELETE_TAG");
    public static final EventType<TagEvent> ADD_TAG = new EventType<>("SELECT_TAG");
    public static final EventType<TagEvent> REMOVE_TAG = new EventType<>("REMOVE_TAG");

    private final Tag tag;

    public TagEvent(EventType<? extends Event> eventType, String tagName) {
        super(eventType);
        tag = new Tag(tagName);
    }

    public Tag getTag() {
        return tag;
    }
}
