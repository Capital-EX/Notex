package org.openjfx.tags;

import javafx.scene.control.ListView;

public class TagListView extends ListView<Tag> {
    public TagListView() {
        setCellFactory(view -> new TagCell());
    }
}
