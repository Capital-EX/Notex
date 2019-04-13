package org.openjfx.editor;


import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

import java.util.HashSet;

public class NoteCreator extends Editor {
    private static final int ExpandedRowCount = 10;
    private static final int ColapsedRowCount = 1;

    public NoteCreator() {
        BorderPane.setMargin(this, new Insets(10, 150, 10, 150));
        getStyleClass().add("NoteCreator");
        setCenter(body);
        setOnMousePressed(e -> activate());
        setOnKeyPressed(e -> {
            if (e.isControlDown() && e.getCode() == KeyCode.ENTER && !isEmpty()) {
                addNote();
            }
        });
        deactivate();
    }

    public void activate() {
        setTop(title);
        setBottom(bottom);
        setCenter(body);
        body.requestFocus();
        body.setPrefRowCount(ExpandedRowCount);
        body.clear();
        body.setDisable(false);
        body.requestFocus();
    }

    public void deactivate() {
        if (!isEmpty()) {
            addNote();
        } else {
            tags.clear();
            tagView.getChildren().clear();
        }
        setTop(null);
        setBottom(null);

        body.setPrefRowCount(ColapsedRowCount);
        body.setText("Take a note...");
        body.setDisable(true);
    }

    public void addNote() {
        getScene().getRoot().fireEvent(new NoteCreatorEvent(NoteCreatorEvent.ADD_NOTE, title.getText(), body.getText(), tags));
        title.clear();
        body.clear();
        tagView.getChildren().clear();
        tags = new HashSet<>();
        deactivate();
    }

    public boolean isActive() {
        return !body.isDisable();
    }
}
