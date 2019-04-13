package org.openjfx.tags;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class TagLabel extends Label {
    public TagLabel() {
        super();
    }

    public TagLabel(String text) {
        super(text);
    }

    public TagLabel(String text, Node graphic) {
        super(text, graphic);
    }
}
