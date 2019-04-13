package org.openjfx.tags;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class TagContextMenu extends ContextMenu {

    private final TagListView tagList = new TagListView();
    private final TextField newTag = new TextField();

    public TagContextMenu() {
        newTag.setOnAction(e -> {
            if (!newTag.getText().isEmpty()) {
                getOwnerNode().getScene().getRoot().fireEvent(new TagEvent(TagEvent.CREATE_TAG, newTag.getText()));
                newTag.clear();
            }
        });
        getItems().addAll(new CustomMenuItem(newTag, false), new CustomMenuItem(tagList, false));

    }

    public ListView<Tag> getTagList() {
        return tagList;
    }

    public TextField getNewTag() {
        return newTag;
    }
}
