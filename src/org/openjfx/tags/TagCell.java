package org.openjfx.tags;

import javafx.scene.control.ListCell;

public class TagCell extends ListCell<Tag> {
    @Override
    protected void updateItem(Tag item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null)
            setText(item.getName());
        else
            setText("");
    }
}
