package org.openjfx.note;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NoteLayout extends FlowPane {
    private ArrayList<Node> nodes = new ArrayList<>();
    private int columnCount = -1;
    private int oldColumnCount = -2;
    private double columnWidth = 125;
    private double hgap;

    public NoteLayout() {
        widthProperty().addListener((observable, oldValue, newValue) -> {
            updateColumns(false);
        });
        setAlignment(Pos.TOP_CENTER);
        setHgap(5);
    }

    private VBox shortestRow() {
        int shortest = 0;
        var children = getChildren();

        for (int i = 0; i < children.size(); i++) {
            var a = children.get(i);
            var b = children.get(shortest);
            double aheight = a.prefHeight(columnWidth);
            double bheight = b.prefHeight(columnWidth);
            if (aheight < bheight) {
                shortest = i;
            }

        }
        return (VBox) children.get(shortest);
    }

    private static int calculateColumns(double columnWidth, double innerWidth, double hgap) {
        int columns = (int) (innerWidth / columnWidth);
        // Recalculate to account for HGap
        if (columns * (columnWidth + hgap) > innerWidth) {
            return columns - 1 > 0 ? columns - 1 : 1;
        } else {
            return columns > 0 ? columns : 1;
        }
    }

    private static VBox vbox(double columnWidth) {
        VBox vbox = new VBox();
        vbox.setMaxWidth(columnWidth);
        vbox.setMinWidth(columnWidth);
        return vbox;
    }

    private void updateColumns(boolean force){
        double innerWidth = getWidth() - getInsets().getLeft() - getInsets().getRight();

        columnCount = calculateColumns(columnWidth, innerWidth, getHgap());
        if (columnCount != oldColumnCount || force) {
            var children = getChildren();
            children.clear();
            for (int i = 0; i < columnCount; i++)
                children.add(vbox(columnWidth));

            for (int i = nodes.size(); i > 0; i--) {
                var node = nodes.get(i - 1);
                VBox shortest = shortestRow();
                shortest.getChildren().add(node);
            }
        }
        oldColumnCount = columnCount;
    }

    public void addNode(Node view) {
        nodes.add(view);
        updateColumns(true);
    }
}
