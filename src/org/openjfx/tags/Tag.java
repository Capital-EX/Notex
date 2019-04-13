package org.openjfx.tags;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

public class Tag {
    private StringProperty name = new SimpleStringProperty();

    public StringProperty nameProperty() {
        return name;
    }

    private Color color = Color.WHITE;

    public Color getColor() {
        return color;
    }

    public Tag(String newName) {
        name.setValue(newName);
    }

    public void setName(String newName) {
        name.setValue(newName);
    }

    public String getName() {
        return name.get();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
