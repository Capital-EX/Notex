package org.openjfx.note;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;


public class ColumnPane extends Pane {
    private Column[] columns;
    private double columnWidth = 150;
    private double vgap = 0;
    private double hgap = 10;
    int columnCount = -1;


    public ColumnPane() {
        super();
        columnWidth = 150;
    }

    public ColumnPane(Node... children) {
        super();
        getChildren().addAll(children);
    }

    @Override
    protected double computeMinHeight(double width) {
        return super.computePrefHeight(width);
    }

    @Override
    protected double computePrefHeight(double width) {
        if (columns == null)
            return 0;
        else
            return getLongestColumn(columns).height;
    }

    private static int columnCount(double width, double columnWidth) {
        int maxColumns = (int) (width / columnWidth);
        return maxColumns > 0 ? maxColumns : maxColumns + 1;
    }

    private static Column getLongestColumn(Column[] list) {
        int longest = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i].height > list[longest].height) {
                longest = i;
            }
        }
        return list[longest];
    }

    private static Column getShortestColumn(Column[] list) {
        int shortest = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i].height < list[shortest].height) {
                shortest = i;
            }
        }
        return list[shortest];
    }

    private static Column[] getEmptyColumns(double width, double columnWidth) {
        int columnCount = columnCount(width, columnWidth);
        Column[] columns = new Column[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columns[i] = new Column();
        }

        return columns;
    }

    private static Column[] getColumns(List<Node> nodes, double width, double columnWidth) {
        Column[] columns = getEmptyColumns(width, columnWidth);
        for (int i = nodes.size() - 1; i >= 0; i--) {
            var node = nodes.get(i);
            var column = getShortestColumn(columns);
            var rect = new Rect();
            rect.height = node.prefHeight(columnWidth);
            rect.node = node;
            rect.y = column.height;
            column.rects.add(rect);
            column.height += rect.height;
        }
        return columns;
    }


    // Switch to column based system that choices the
    // shortest column to move a child node to
    @Override
    protected void layoutChildren() {
        double innerWidth = getWidth() - getInsets().getLeft() - getInsets().getRight();
        double leftPadding = getInsets().getLeft();
        double topPadding = getInsets().getTop();
        columns = getColumns(getChildren(), innerWidth, columnWidth);
        for (int i = 0; i < columns.length; i++) {
            for (int j = 0; j < columns[i].rects.size(); j++) {
                var rect = columns[i].rects.get(j);
                layoutInArea(
                        rect.node,
                        i * columnWidth + leftPadding + (j != 0 ? hgap : 0),
                        rect.y + topPadding,
                        columnWidth,
                        rect.height,
                        0,
                        HPos.LEFT,
                        VPos.TOP
                );
            }
        }
    }

    static private class Rect {
        double height, y;
        Node node;

    }

    static private class Column {
        double height;
        ArrayList<Rect> rects = new ArrayList<>();
    }
}
