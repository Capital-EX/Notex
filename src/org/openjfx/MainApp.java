package org.openjfx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.openjfx.editor.NoteCreator;
import org.openjfx.editor.NoteCreatorEvent;
import org.openjfx.editor.NoteEditor;
import org.openjfx.note.ColumnPane;
import org.openjfx.note.Note;
import org.openjfx.note.NoteView;
import org.openjfx.tags.Tag;
import org.openjfx.tags.TagContextMenu;
import org.openjfx.tags.TagEvent;
import org.openjfx.tags.TagListView;

import java.util.ArrayList;


public class MainApp extends Application {
    private ArrayList<Note> notes = new ArrayList<>();
    private ObservableList<Tag> tags = FXCollections.observableArrayList();
    private NoteCreator newNote = new NoteCreator();
    private NoteEditor editNote = new NoteEditor();
    private ColumnPane ffpane = new ColumnPane();
    private StackPane rootPane = new StackPane();
    private TagListView tagList = new TagListView();
    private TagContextMenu menu = new TagContextMenu();
    private NoteView active;
    private static double NOTE_SIZE = 250;
    private final Timeline timeline = new Timeline();

    @Override
    public void start(Stage stage) {
        // flowPane.setId("Notes");
        var noteView = new BorderPane();
        var scrollView = new ScrollPane(ffpane);
        noteView.setCenter(scrollView);
        noteView.setLeft(tagList);
        noteView.setTop(newNote);
        tagList.setItems(tags);
        noteView.setFocusTraversable(false);
        newNote.getAddTag().setOnAction(e -> {
            var source = (Button) e.getSource();
            var h = source.getHeight();
            var lx = source.getLocalToSceneTransform().getTx();
            var ly = source.getLocalToSceneTransform().getTy();

            var scene = source.getScene();
            var sx = scene.getX();
            var sy = scene.getY();

            var window = scene.getWindow();
            var wx = window.getX();
            var wy = window.getY();

            menu.show(source, sx + lx + wx, sy + ly + wy + h);
        });
        menu.getTagList().setItems(tags);

        rootPane.setId("RootPane");
        editNote.setVisible(false);
        rootPane.getChildren().addAll(noteView, editNote);
        rootPane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (newNote.isActive()) {
                var bounds = newNote.getBoundsInParent();
                if (!bounds.contains(e.getX(), e.getY())) {
                    newNote.deactivate();
                }
            }

            if (editNote.isVisible()) {
                var bounds = editNote.getBoundsInParent();
                if (!bounds.contains(e.getX(), e.getY())) {
                    e.consume();
                    var startX = editNote.getLocalToSceneTransform().getTx();
                    var startY = editNote.getLocalToSceneTransform().getTy();
                    var startWidth = rootPane.getWidth() - 400.0;
                    var startHeight = rootPane.getHeight() - 200.0;
                    var endX = active.getLocalToSceneTransform().getTx();
                    var endY = active.getLocalToSceneTransform().getTy();
                    EventHandler<ActionEvent> callback = e1 -> {
                        editNote.setVisible(false);
                        active.setVisible(true);
                        active.getNote().titleProperty().unbind();
                        active.getNote().bodyProperty().unbind();
                        active = null;
                    };
                    noteEditorPlayTransition(startX, startY, startWidth, startHeight, endX, endY, NOTE_SIZE, NOTE_SIZE, callback);
                }
            }
        });
        var scene = new Scene(rootPane, 800, 600);
        scene.addEventHandler(NoteCreatorEvent.ADD_NOTE, e -> {
            var note = new Note(e.getTitle(), e.getBody(), e.getTags());
            var view = new NoteView(note);
            view.setPrefSize(NOTE_SIZE, Region.USE_COMPUTED_SIZE);

            view.setOnMouseClicked(e2 -> {
                view.setVisible(false);
                active = view;
                editNote.setVisible(true);
                editNote.edit(note);
                var startX = view.getLocalToSceneTransform().getTx();
                var startY = view.getLocalToSceneTransform().getTy();
                var endWidth = rootPane.getWidth() - 400.0;
                var endHeight = rootPane.getHeight() - 200.0;
                var endX = rootPane.getWidth() / 2.0 - endWidth / 2.0;
                var endY = rootPane.getHeight() / 2.0 - endHeight / 2.0;
                EventHandler<ActionEvent> callback = e3 -> {
                    StackPane.setAlignment(editNote, Pos.CENTER);
                    StackPane.setMargin(editNote, new Insets(100, 200, 100, 200));
                    editNote.setMaxSize(-1, -1);
                    editNote.setTranslateX(0);
                    editNote.setTranslateY(0);
                };
                noteEditorPlayTransition(startX, startY, NOTE_SIZE, NOTE_SIZE, endX, endY, endWidth, endHeight, callback);
            });
            ffpane.getChildren().add(view);
            TilePane.setMargin(view, new Insets(0, 0, view.getHeight(), 0));
            notes.add(note);
        });

        scene.addEventFilter(TagEvent.CREATE_TAG, e -> {
            tags.add(e.getTag());
            if (editNote.isActive())
                editNote.addTag(e.getTag());
            else if (newNote.isActive())
                newNote.addTag(e.getTag());
        });

        var stylesheet = getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }

    public void noteEditorPlayTransition(double startX,
                                         double startY,
                                         double startWidth,
                                         double startHeight,
                                         double endX,
                                         double endY,
                                         double endWidth,
                                         double endHeight,
                                         EventHandler<ActionEvent> callback) {
        editNote.setMaxSize(startWidth, startHeight);
        editNote.setTranslateX(startX);
        editNote.setTranslateY(startY);
        StackPane.setMargin(editNote, null);
        StackPane.setAlignment(editNote, Pos.TOP_LEFT);

        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(
                        Duration.seconds(0.05),
                        callback,
                        new KeyValue(editNote.translateXProperty(), endX),
                        new KeyValue(editNote.translateYProperty(), endY),
                        new KeyValue(editNote.maxWidthProperty(), endWidth),
                        new KeyValue(editNote.maxHeightProperty(), endHeight))
        );
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
