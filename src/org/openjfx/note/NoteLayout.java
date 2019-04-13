package org.openjfx.note;

import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class NoteLayout extends VBox {
    private List<NoteView> notes = new ArrayList<>();
    private int columnCount = -1;
    private double columnWidth = 125;
    private double hgap;

    public NoteLayout() {
        widthProperty().addListener((observable, oldValue, newValue) -> {

        });
    }

    private VBox shortestRow() {
        int shortest = 0;
        var children = getChildren();
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).prefHeight(columnWidth) < children.get(shortest).prefHeight(columnWidth)) {
                shortest = i;
            }
        }
        return (VBox) children.get(shortest);
    }

    private static int calculateColumns(double columnWidth, double innerWidth, double hgap) {
        int columns = (int) (innerWidth / columnWidth);
        if (columns * (columnWidth + hgap) > innerWidth) {
            return columns - 1;
        } else {
            return columns;
        }
    }

    public void updateColumns() {
        double innerWidth = getWidth() - getInsets().getLeft() - getInsets().getRight();
        columnCount = calculateColumns(columnWidth, innerWidth, hgap);
        var children = getChildren();

        while (children.size() > columnCount) {
            children.remove(children.size() - 1);
        }

        children.forEach(child -> ((VBox) child).getChildren().clear());


    }

    public void addNode(NoteView view) {
        notes.add(view);
        for (int i = notes.size() - 1; i >= 0; i--) {

        }
    }
}
